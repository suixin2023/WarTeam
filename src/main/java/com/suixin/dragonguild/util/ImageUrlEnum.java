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
    apply("apply","审批.png", "审批"),
    applyJoin("apply","申请加入.png", "申请加入"),
    close("close","close.png", "close"),
    back("back","back.png", "back"),
    notice("notice","公告.png", "公告"),
    chat("chat","聊天.png", "聊天"),
    top("top","排行.png", "排行"),
    send("send","发送.png", "发送"),
    lobby("lobby","大厅.png", "大厅"),
    applyList("applyList","申请列表.png", "申请列表"),
    memberList("memberList","成员列表.png", "成员列表"),
    memberBackground("memberBackground","列表背景.png", "列表背景"),
    guildList("guildList","公会列表.png", "公会列表"),
    listbg("listbg","列表背景.png", "列表背景"),
    listbgk("listbgk","列表背景框.png", "列表背景"),


    neirong("neirong","内容.png", "内容"),
    shurukuang("shurukuang","输入框.png", "输入框"),
    window("window","弹窗.png", "弹窗"),
    window2("window2","弹窗2.png", "弹窗2"),
    pictureFrame("pictureFrame","成员.png", "成员"),
    creator("creator","会长.png", "会长"),
    guild("guild","公会.png", "公会"),
    guild2("guild2","公会2.png", "公会"),
    guild3("guild3","公会3.png", "公会"),
    guild4("guild4","公会4.png", "公会"),
    guildImg("creator","公会图腾.png", "图腾"),
    title("title","标题.png", "标题"),
    bar("bar","bar.png", "bar"),

    vice_chairman("vice_chairman","副会长.png", "副会长"),
    veteran("veteran","元老.png", "元老"),
    god_of_war("god_of_war","战神.png", "战神"),
    elite("elite","精英.png", "精英"),
    ordinary("ordinary","普通成员.png", "普通成员"),
    appoint("appoint","任职.png", "任职"),
    //暂无图片
    edit("edit","编辑.png", "编辑"),
    save("save","保存.png", "保存"),
    clear("clear","清空.png", "清空"),
    chatBox("chatBox","消息框.png", "消息框"),
    chatBox2("chatBox","消息框2.png", "消息框"),
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
