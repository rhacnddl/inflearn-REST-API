package com.gorany.inflearnrestapi.events;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class EventControllerTests {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    @Test
    @DisplayName("HTTP POST 요청")
    public void http_post_request() throws Exception {
        //given
        Event event = createEvent("Spring", "REST API Dev", 100, 200, 100, "강남역 D2");

        //when

        //then
        mvc.perform(post("/api/events/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON)
                .content(mapper.writeValueAsString(event))
            )
            .andDo(print())
            .andExpectAll(
                result ->
                jsonPath("id").exists(),
                status().isCreated(),
                header().exists(HttpHeaders.LOCATION),
                header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE),
                jsonPath("id").value(Matchers.not(100)),
                jsonPath("eventStatus").value(Matchers.equalTo(EventStatus.DRAFT.toString()))
            );
    }

    @Test
    @DisplayName("Bad Request - create Event")
    public void bad_request_create_event() throws Exception {
        //given
        Event event = createEvent("Spring", "REST API Dev", 100, 200, 100, "강남역 D2");
        //when

        //then
        mvc.perform(post("/api/event/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(event))
            .accept(MediaTypes.HAL_JSON))
            .andDo(print())
            .andExpectAll(
                result ->
                    status().isBadRequest()
            );
    }

    private Event createEvent(String name, String desc, int basePrice, int maxPrice, int limitOfEnrollment,
        String location) {
        return Event.builder()
            .id(100)
            .name(name)
            .description(desc)
            .beginEnrollmentDateTime(LocalDateTime.now())
            .closeEnrollmentDateTime(LocalDateTime.now().plusDays(1L))
            .beginEventDateTime(LocalDateTime.now().plusWeeks(1L))
            .endEventDateTime(LocalDateTime.now().plusMonths(1L))
            .basePrice(basePrice)
            .maxPrice(maxPrice)
            .limitOfEnrollment(limitOfEnrollment)
            .location(location)
            .eventStatus(EventStatus.PUBLISHED)
            .build();
    }
}
