package com.wyc.common.com.wyc.common.session.com.wyc.common.session.socket;

import lombok.Getter;

abstract  public class SocketSession {
    //游离状态，不列入系统管理
    public static Integer FREE_STATUS = 0;
    //列入系统管理
    public static Integer  LISTEN_IN_STATUS = 1;

    //列入系统管理，但处于关闭状态
    public static Integer CLOSE_STATUS = 2;

    //销毁状态 socket已经关闭
    public static Integer DESTROY_STATUS = 3;
    @Getter
    private int status = FREE_STATUS;

    private Object conn;

    protected OnReadCallback onReadCallback;

    protected OnCloseCallback onCloseCallback;
    public SocketSession(Object conn){
        this.conn = conn;
    }
    abstract public String getId();
    abstract public void init();
    public void onReadMessage(OnReadCallback onReadCallback){
        this.onReadCallback = onReadCallback;
    }
    abstract public void writeMsg(String msg);
    public void onClose(OnCloseCallback onCloseCallback){
        this.onCloseCallback = onCloseCallback;
    }

    public void doClose(){
        this.status = CLOSE_STATUS;
    }

    public void doOpen(){
        this.status = LISTEN_IN_STATUS;
    }

    public void doDestroy(){
        this.status = DESTROY_STATUS;
    }

    abstract public void doReadMessage(String msg);

    public interface OnReadCallback{
        public void onReadMessage(String msg);
    }

    public interface OnCloseCallback{
        public void onClose();
    }
}
