package com.suixin.warteam.util;

import lk.vexview.gui.components.DynamicComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * 玩家vv组件对象缓存
 */
public class Component {
    private Integer current = 1;
    private List<DynamicComponent> memBerlist  = new ArrayList<>();
    private List<DynamicComponent> applylist  = new ArrayList<>();
    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public List<DynamicComponent> getMemBerlist() {
        return memBerlist;
    }

    public void setMemBerlist(List<DynamicComponent> memBerlist) {
        this.memBerlist = memBerlist;
    }

    public List<DynamicComponent> getApplylist() {
        return applylist;
    }

    public void setApplylist(List<DynamicComponent> applylist) {
        this.applylist = applylist;
    }
}
