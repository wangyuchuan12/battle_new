package com.wyc.common.com.wyc.common.session.com.wyc.common.session.socket;


import com.wyc.common.com.wyc.common.session.com.wyc.common.session.socket.vo.RegisterVo;

public interface RegisterCondition {
    //连接之后是否符合注册条件
    public RegisterVo onAcceptRegQt(SocketSession socketSession, Integer status);

    //接收消息是否符合注册条件
    public RegisterVo onReadMsgRegQt(String msg, SocketSession socketSession, Integer status);

    public RegisterVo onCloseRemoveQt(SocketSession socketSession, Integer status);

    public RegisterVo onReadRemoveQt(String msg, SocketSession socketSession, Integer status);

    public void init(SessionSearcher sessionSearcher);
}

