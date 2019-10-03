package com.wyc.common.com.wyc.common.session;

public interface SessionRule {
    public boolean check(Object data);
    public int index();
    public boolean parallel();
}
