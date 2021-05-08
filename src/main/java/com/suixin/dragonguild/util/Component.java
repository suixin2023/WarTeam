package com.suixin.dragonguild.util;

import eos.moe.dragoncore.api.easygui.component.EasyComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * 玩家龙核组件对象缓存
 */
public class Component {
    private Integer current = 1;
    private List<EasyComponent> memBerlist  = new ArrayList<>();
    private List<EasyComponent> applylist  = new ArrayList<>();
    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public List<EasyComponent> getMemBerlist() {
        return memBerlist;
    }

    public void setMemBerlist(List<EasyComponent> memBerlist) {
        this.memBerlist = memBerlist;
    }

    public List<EasyComponent> getApplylist() {
        return applylist;
    }

    public void setApplylist(List<EasyComponent> applylist) {
        this.applylist = applylist;
    }
}
