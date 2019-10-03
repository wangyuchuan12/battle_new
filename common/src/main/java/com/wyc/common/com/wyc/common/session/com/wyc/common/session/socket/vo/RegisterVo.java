package com.wyc.common.com.wyc.common.session.com.wyc.common.session.socket.vo;

import lombok.Data;

import java.util.Map;

@Data
public class RegisterVo {
    private String key;
    private boolean isPass;
    private Map<String,Object> data;
    private Map<String,Object> conditon;
}
