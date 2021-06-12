package com.suixin.dragonguild.util;

import eos.moe.dragoncore.api.easygui.component.EasyScrollingList;

import java.util.ArrayList;
import java.util.List;

/**
 * 玩家龙核组件对象缓存
 */
public class Component {
    private Integer current = 1;
    private List<String> memBerList  = new ArrayList<>();
    private List<String> applyList  = new ArrayList<>();
    private List<String> chatList  = new ArrayList<>();
    private List<String> guildList  = new ArrayList<>();
    private List<String> sidebar  = new ArrayList<>();
    private EasyScrollingList scrollingList;
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

    public List<String> getChatList() {
        return chatList;
    }

    public void setChatList(List<String> chatList) {
        this.chatList = chatList;
    }

    public EasyScrollingList getScrollingList() {
        return scrollingList;
    }

    public void setScrollingList(EasyScrollingList scrollingList) {
        this.scrollingList = scrollingList;
    }

    public List<String> getGuildList() {
        return guildList;
    }

    public void setGuildList(List<String> guildList) {
        this.guildList = guildList;
    }

    public List<String> getSidebar() {
        return sidebar;
    }

    public void setSidebar(List<String> sidebar) {
        this.sidebar = sidebar;
    }

}
