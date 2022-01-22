package com.gorany.inflearnrestapi.events;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/api/events", produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
public class EventController {

    private final EventRepository eventRepository;

    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity createEvent(@RequestBody EventDto eventDto) {

        //원래 DTO -> Entity를 해줘야하나, ModelMapper라는 라이브러리를 사용할 수 있다.
        Event event = modelMapper.map(eventDto, Event.class);
        eventRepository.save(event);

        //HATEOS가 제공하는 linkTo(), methodOn() (WebMvcLinkBuilder)
        URI createdUri = linkTo(EventController.class).slash(event.getId()).toUri();

        return ResponseEntity.created(createdUri).body(event);
    }
}
