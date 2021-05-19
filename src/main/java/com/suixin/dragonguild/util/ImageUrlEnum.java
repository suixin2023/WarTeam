package com.suixin.dragonguild.util;


/**
 * 图片资源枚举
 */
public enum ImageUrlEnum {
    create("create","创建公会.png", "创建公会"),
    join("join","加入公会.png", "加入公会"),
    kickOut("kickOut","踢出成员.png", "踢出成员"),
    updateName("updateName","修改会名.png", "修改会名"),
    dissolveTeam("dissolveTeam","解散公会.png", "解散公会"),
    confirm("confirm","确认.png", "确认"),
    cancel("cancel","取消.png", "取消"),
    shangyiye("shangyiye","上一页.png", "上一页"),
    xiayiye("xiayiye","下一页.png", "下一页"),
    outTeam("outTeam","退出公会.png", "退出公会"),
    background("background","公会主页.png", "公会主页"),
    applyAgree("applyAgree","同意.png", "同意"),
    applyRepulse("applyRepulse","拒绝.png", "不同意"),
    applyShangyiye("applyShangyiye","审批上一页.png", "审批上一页"),
    applyXiayiye("applyXiayiye","审批下一页.png", "审批下一页"),
    apply("apply","入会申请.png", "入会申请"),
    close("close","close.png", "close"),
    back("back","back.png", "back"),
    notice("notice","公告.png", "公告"),
    chat("chat","聊天.png", "聊天"),
    top("top","排行.png", "排行"),
    send("send","发送.png", "发送"),
    lobby("lobby","大厅.png", "大厅"),
    applyList("applyList","申请列表.png", "申请列表"),
    memberList("memberList","成员列表.png", "成员列表"),
    guildList("guildList","公会列表.png", "公会列表"),


    neirong("neirong","内容.png", "内容"),
    shurukuang("shurukuang","输入框.png", "输入框"),
    window("window","弹窗.png", "弹窗"),
    window2("window2","弹窗2.png", "弹窗2"),
    pictureFrame("pictureFrame","成员.png", "成员"),
    creator("creator","会长.png", "会长"),
    guild("creator","公会.png", "公会"),
    title("title","标题.png", "标题"),
    bar("bar","bar.png", "bar"),
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
