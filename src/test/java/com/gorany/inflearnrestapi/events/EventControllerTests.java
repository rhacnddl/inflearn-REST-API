package com.gorany.inflearnrestapi.events;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
@ExtendWith(MockitoExtension.class)
public class EventControllerTests {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    EventRepository eventRepository;

    @Test
    @DisplayName("HTTP POST 요청")
    public void http_post_request() throws Exception {
        //given
        Event event = createEvent("Spring", "REST API Dev", 100, 200, 100, "강남역 D2");

        when(eventRepository.save(event)).thenReturn(event);

        mvc.perform(post("/api/events/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON)
                .content(mapper.writeValueAsString(event))
            )
            .andDo(print())
            .andExpect(jsonPath("id").exists())
            .andExpect(status().isCreated());
        //when

        //then

    }

    private Event createEvent(String name, String desc, int basePrice, int maxPrice, int limitOfEnrollment,
        String location) {
        return Event.builder()
            .id(1)
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
            .build();
    }
}
