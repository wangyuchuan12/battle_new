package com.wyc.common.dto;

import com.wyc.common.domain.Person;
import lombok.Data;

@Data
public class PersonDto extends Person {
    private String nameCondition;
}
