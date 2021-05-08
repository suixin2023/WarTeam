package com.suixin.dragonguild.util;


/**
 * 图片资源枚举
 */
public enum ImageUrlEnum {
    create("create","创建一个公会.png", "创建一个公会"),
    join("join","加入一个公会.png", "加入一个公会"),
    kickOut("kickOut","踢出成员.png", "踢出成员"),
    updateName("updateName","修改公会名.png", "修改公会名"),
    dissolveTeam("dissolveTeam","解散当前公会.png", "解散当前公会"),
    backgroundOfNoTeam("backgroundOfNoTeam","无公会.png", "无公会"),
    confirm("confirm","确定键.png", "确定键"),
    shangyiye("shangyiye","上一页.png", "上一页"),
    outTeam("outTeam","退出当前公会.png", "退出当前公会"),
    xiayiye("xiayiye","下一页.png", "下一页"),
    background("background","公会.png", "公会"),
    pictureFrame("pictureFrame","公会成员框.png", "公会成员框"),
    creator("creator","公会创建者.png", "公会创建者"),
    window("window","公会名.png", "公会名"),
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
        return "DragonGuild/"+url;
    }
}
