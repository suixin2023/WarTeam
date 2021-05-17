package com.suixin.dragonguild.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 玩家龙核组件对象缓存
 */
public class Component {
    private Integer current = 1;
    private List<String> memBerList  = new ArrayList<>();
    private List<String> applyList  = new ArrayList<>();
    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public List<String> getMemBerList() {
        return memBerList;
    }

    public void setMemBerList(List<String> memBerList) {
        this.memBerList = memBerList;
    }

    public List<String> getApplyList() {
        return applyList;
    }

    public void setApplyList(List<String> applyList) {
        this.applyList = applyList;
    }
}
