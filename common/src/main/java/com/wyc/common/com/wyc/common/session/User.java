package com.wyc.common.com.wyc.common.session;

import lombok.Data;

import java.util.Map;

@Data
public class User {
    private String id;
    private String token;
    private Map<String,Object> info;
    private boolean rule;
}
