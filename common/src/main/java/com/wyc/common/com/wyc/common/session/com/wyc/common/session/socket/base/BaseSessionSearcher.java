package com.wyc.common.com.wyc.common.session.com.wyc.common.session.socket.base;

import com.wyc.common.com.wyc.common.session.com.wyc.common.session.socket.SessionSearcher;
import com.wyc.common.com.wyc.common.session.com.wyc.common.session.socket.SocketSession;
import com.wyc.common.com.wyc.common.session.com.wyc.common.session.socket.vo.RegisterVo;
import com.wyc.common.util.CommonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseSessionSearcher implements SessionSearcher {
    private Map<String,Map<String,Object>> socketSessionMap = new HashMap<>();
    @Override
    public List<SocketSession> search(Map<String, Object> params) {
        String token = params.get("token").toString();
        Map<String,Object> data = socketSessionMap.get(token);
        if(CommonUtil.isEmpty(data)){
            return new ArrayList<>();
        }
        SocketSession socketSession = (SocketSession)data.get("socketSession");

        if(CommonUtil.isNotEmpty(socketSession)){
            List<SocketSession> socketSessions = new ArrayList<>();
            socketSessions.add(socketSession);
            return socketSessions;
        }else{
            return new ArrayList<>();
        }
    }

    @Override
    public void register(RegisterVo registerVo, SocketSession socketSession) {
        Map<String,Object> map = socketSessionMap.get(registerVo.getKey());
        if(CommonUtil.isEmpty(map)){
            map = new HashMap<>();
            socketSessionMap.put(registerVo.getKey(),map);
        }

        map.put("token",registerVo.getKey());
        map.put("data",registerVo.getData());
        map.put("socketSession",socketSession);


    }

    @Override
    public void remove(RegisterVo registerVo, SocketSession socketSession) {
        Map<String,Object> map = socketSessionMap.get(registerVo.getKey());
        map.remove(registerVo.getKey());
    }
}
