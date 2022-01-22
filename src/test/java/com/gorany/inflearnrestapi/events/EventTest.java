package com.gorany.inflearnrestapi.events;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EventTest {

    @Test
    @DisplayName("have builder")
    public void have_builder() throws Exception {
        //given
        Event event = Event.builder()
            .name("인프런 스프링 레스트 에이피아이")
            .description("REST API Dev with Spring")
            .build();
        //when

        //then
        assertThat(event).isNotNull();
    }

}