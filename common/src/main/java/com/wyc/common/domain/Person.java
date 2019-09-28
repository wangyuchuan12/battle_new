package com.wyc.common.domain;

import com.wyc.common.annotation.Column;
import com.wyc.common.annotation.Condition;
import com.wyc.common.annotation.Id;
import com.wyc.common.annotation.Table;
import lombok.Data;

@Data
@Table(name="person")
public class Person {
    @Column(name="name")
    @Condition(properties = "nameCondition")
    private String name2;
    @Id(generator = Id.Generator.UUID)
    @Column(name="id")
    private String id;
}
