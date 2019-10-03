package com.wyc.common.com.wyc.common.session.com.wyc.common.session.socket.base;

import com.wyc.common.com.wyc.common.session.com.wyc.common.session.socket.MessageExecuter;
import com.wyc.common.com.wyc.common.session.com.wyc.common.session.socket.SessionSearcher;
import com.wyc.common.com.wyc.common.session.com.wyc.common.session.socket.SocketContext;
import com.wyc.common.com.wyc.common.session.com.wyc.common.session.socket.SocketSession;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseMessageExecuter implements MessageExecuter {
    @Autowired
    private SocketContext socketContext;

    @Override
    public void onClose(String key) {
        System.out.println("onClose.key:"+key);
    }

    @Override
    public void onReadMessage(String key, String msg) {
        System.out.println("onReadMessage.msg:"+msg);
    }

    @Override
    public String onAcceopt(SocketSession socketSession) {
        System.out.println("onAcceopt.socketSession:"+socketSession);
        return null;
    }

    @Override
    public void write(String token, String msg) {
        SessionSearcher sessionSearcher = socketContext.getSessionSearcher();
        Map<String,Object> map = new HashMap<>();
        map.put("token",token);
        List<SocketSession> socketSessions = sessionSearcher.search(map);
        for(SocketSession socketSession:socketSessions){
            socketSession.writeMsg(msg);
        }
    }
}
