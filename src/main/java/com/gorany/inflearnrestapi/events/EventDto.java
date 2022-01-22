package com.gorany.inflearnrestapi.events;

import java.time.LocalDateTime;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//입력 값을 받는다.
public class EventDto {

    private String name;
    private String description;
    private String location;

    private LocalDateTime beginEnrollmentDateTime;
    private LocalDateTime closeEnrollmentDateTime;
    private LocalDateTime beginEventDateTime;
    private LocalDateTime endEventDateTime;

    private int basePrice;
    private int maxPrice;
    private int limitOfEnrollment;

}
