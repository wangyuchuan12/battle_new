package com.wyc.common.com.wyc.common.session.com.wyc.common.session.socket;


import com.wyc.common.com.wyc.common.session.com.wyc.common.session.socket.vo.RegisterVo;

import java.util.List;
import java.util.Map;

public interface SessionSearcher {
    public List<SocketSession> search(Map<String, Object> params);
    public void register(RegisterVo registerVo, SocketSession socketSession);
    public void remove(RegisterVo registerVo, SocketSession socketSession);
}
