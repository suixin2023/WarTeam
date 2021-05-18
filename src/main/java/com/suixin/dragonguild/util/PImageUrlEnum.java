package com.suixin.dragonguild.util;


/**
 * 图片资源枚举
 */
public enum PImageUrlEnum {
    create("create","创建一个公会2.png", "创建一个公会"),
    join("join","加入一个公会2.png", "加入一个公会"),
    kickOut("kickOut","踢出成员2.png", "踢出成员"),
    updateName("updateName","修改公会名2.png", "修改公会名"),
    dissolveTeam("dissolveTeam","解散当前公会2.png", "解散当前公会"),
    confirm("confirm","确定键2.png", "确定键"),
    shangyiye("shangyiye","上一页2.png", "上一页"),
    outTeam("outTeam","退出当前公会2.png", "退出当前公会"),
    xiayiye("xiayiye","下一页2.png", "下一页"),
    applyShangyiye("applyShangyiye","审批上一页2.png", "审批上一页"),
    applyXiayiye("applyXiayiye","审批下一页2.png", "审批下一页"),
    applyClose("applyClose","关闭按钮2.png", "关闭按钮"),
    applyAgree("applyAgree","同意2.png", "同意"),
    applyList("applyRepulse","审批列表2.png", "审批列表"),
    applyRepulse("applyRepulse","不同意2.png", "不同意"),

    lobby("applyRepulse","大厅2.png", "大厅"),
    notice("applyRepulse","公告2.png", "公告"),
    chat("applyRepulse","聊天2.png", "聊天"),
    apply("applyRepulse","审批2.png", "审批"),
    top("applyRepulse","排行2.png", "排行"),
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
        return "DragonGuild/"+url;
    }
}
