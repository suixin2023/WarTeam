package com.suixin.warteam.util;


/**
 * 图片资源枚举
 */
public enum ImageUrlEnum {
    create("create","创建一个战队.png", "创建一个战队"),
    join("join","加入一个战队.png", "加入一个战队"),
    kickOut("kickOut","踢出成员.png", "踢出成员"),
    updateName("updateName","修改战队名.png", "修改战队名"),
    dissolveTeam("dissolveTeam","解散当前战队.png", "解散当前战队"),
    backgroundOfNoTeam("backgroundOfNoTeam","无战队.png", "无战队"),
    confirm("confirm","确定键.png", "确定键"),
    shangyiye("shangyiye","上一页.png", "上一页"),
    outTeam("outTeam","退出当前战队.png", "退出当前战队"),
    xiayiye("xiayiye","下一页.png", "下一页"),
    background("background","战队.png", "战队"),
    pictureFrame("pictureFrame","战队成员框.png", "战队成员框"),
    creator("creator","战队创建者.png", "战队创建者"),
    window("window","战队名.png", "战队名"),
    window2("window2","队员名.png", "队员名"),
    applyShangyiye("applyShangyiye","审批上一页.png", "审批上一页"),
    applyXiayiye("applyXiayiye","审批下一页.png", "审批下一页"),
    applyClose("applyClose","关闭按钮.png", "关闭按钮"),
    applyAgree("applyAgree","同意.png", "同意"),
    applyRepulse("applyRepulse","不同意.png", "不同意"),
    backgroundOfApply("applyRepulse","审批背景.png", "审批背景"),
    applyList("applyRepulse","审批列表.png", "审批列表"),
    applyModel("applyRepulse","审批组件.png", "审批组件"),
    ;

    private String id;
    private String url;
    private String name;

    ImageUrlEnum(String id, String url, String name) {
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
