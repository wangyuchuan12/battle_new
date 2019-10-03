package com.wyc.common.com.wyc.common.session.com.wyc.common.session.socket.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wyc.common.com.wyc.common.session.com.wyc.common.session.socket.RegisterCondition;
import com.wyc.common.com.wyc.common.session.com.wyc.common.session.socket.SessionSearcher;
import com.wyc.common.com.wyc.common.session.com.wyc.common.session.socket.SocketSession;
import com.wyc.common.com.wyc.common.session.com.wyc.common.session.socket.vo.RegisterVo;

import java.util.Map;

public class BaseRegisterCondtion implements RegisterCondition {
    @Override
    public RegisterVo onAcceptRegQt(SocketSession socketSession, Integer status) {
        RegisterVo registerVo = new RegisterVo();
        registerVo.setPass(false);
        registerVo.setKey(socketSession.getId());
        return registerVo;
    }

    @Override
    public RegisterVo onReadMsgRegQt(String msg, SocketSession socketSession, Integer status) {
        RegisterVo registerVo = new RegisterVo();
        registerVo.setPass(false);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, Object> map = objectMapper.readValue(msg, Map.class);
            if(map.get("messageType").equals("login")){
                Map<String,String> data = (Map<String, String>) map.get("data");
                String token = data.get("token");
                registerVo.setKey(token);
                registerVo.setPass(true);
            }
        }catch (Exception e){

        }
        return registerVo;
    }


    @Override
    public RegisterVo onCloseRemoveQt(SocketSession socketSession, Integer status) {
        RegisterVo registerVo = new RegisterVo();
        registerVo.setPass(true);
        registerVo.setKey(socketSession.getId());
        return registerVo;
    }

    @Override
    public RegisterVo onReadRemoveQt(String msg, SocketSession socketSession, Integer status) {
        RegisterVo registerVo = new RegisterVo();
        registerVo.setPass(false);
        registerVo.setKey(socketSession.getId());
        return registerVo;
    }

    @Override
    public void init(SessionSearcher sessionSearcher) {

    }
}
