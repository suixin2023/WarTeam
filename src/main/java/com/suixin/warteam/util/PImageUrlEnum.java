package com.suixin.warteam.util;


/**
 * 图片资源枚举
 */
public enum PImageUrlEnum {
    create("create","创建一个战队2.png", "创建一个战队"),
    join("join","加入一个战队2.png", "加入一个战队"),
    kickOut("kickOut","踢出成员2.png", "踢出成员"),
    updateName("updateName","修改战队名2.png", "修改战队名"),
    dissolveTeam("dissolveTeam","解散当前战队2.png", "解散当前战队"),
    confirm("confirm","确定键2.png", "确定键"),
    shangyiye("shangyiye","上一页2.png", "上一页"),
    outTeam("outTeam","退出当前战队2.png", "退出当前战队"),
    xiayiye("xiayiye","下一页2.png", "下一页"),
    applyShangyiye("applyShangyiye","审批上一页2.png", "审批上一页"),
    applyXiayiye("applyXiayiye","审批下一页2.png", "审批下一页"),
    applyClose("applyClose","同意2.png", "同意"),
    applyAgree("applyAgree","不同意2.png", "不同意"),
    applyRepulse("applyRepulse","关闭按钮2.png", "关闭按钮"),
    applyButton("applyButton","审批列表2.png", "审批列表"),
    ;

    private String id;
    private String url;
    private String name;

    PImageUrlEnum(String id, String url, String name) {
        this.id = id;
        this.url = url;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return "[local]warteam/"+url;
    }
}
