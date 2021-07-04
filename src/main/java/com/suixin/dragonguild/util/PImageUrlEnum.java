package com.suixin.dragonguild.util;


/**
 * 图片资源枚举
 */
public enum PImageUrlEnum {
    create("create","创建公会2.png", "创建公会"),
    join("join","加入公会2.png", "加入公会"),
    kickOut("kickOut","踢出成员2.png", "踢出成员"),
    updateName("updateName","修改会名2.png", "修改会名"),
    dissolveTeam("dissolveTeam","解散公会2.png", "解散公会"),
    confirm("confirm","确认2.png", "确认"),
    cancel("cancel","取消2.png", "取消"),
    shangyiye("shangyiye","上一页2.png", "上一页"),
    xiayiye("xiayiye","下一页2.png", "下一页"),
    outTeam("outTeam","退出公会2.png", "退出公会"),
    applyAgree("applyAgree","同意2.png", "同意"),
    applyRepulse("applyRepulse","拒绝2.png", "不同意"),
    apply("apply","审批2.png", "审批"),
    applyJoin("apply","申请加入2.png", "申请加入"),
    close("close","close2.png", "close"),
    back("back","back2.png", "back"),
    notice("notice","公告2.png", "公告"),
    chat("chat","聊天2.png", "聊天"),
    top("top","排行2.png", "排行"),
    send("send","发送2.png", "发送"),
    applyList("applyList","申请列表2.png", "申请列表"),
    memberList("memberList","成员列表2.png", "成员列表"),
    guildList("guildList","公会列表2.png", "公会列表"),
    lobby("lobby","大厅2.png", "大厅"),

    vice_chairman("vice_chairman","副会长2.png", "副会长"),
    veteran("veteran","元老2.png", "元老"),
    god_of_war("god_of_war","战神2.png", "战神"),
    elite("elite","精英2.png", "精英"),
    ordinary("ordinary","普通成员2.png", "普通成员"),
    appoint("appoint","任职2.png", "任职"),

    cheng("chatBox","color2/橙.png", "橙"),
    hong("chatBox","color2/红.png", "红"),
    huang("chatBox","color2/黄.png", "黄"),
    hui("chatBox","color2/灰.png", "灰"),
    huilan("chatBox","color2/灰蓝.png", "灰蓝"),
    lianglan("chatBox","color2/亮蓝.png", "亮蓝"),
    lianglv("chatBox","color2/亮绿.png", "亮绿"),
    qianhong("chatBox","color2/浅红.png", "浅红"),
    qianlan("chatBox","color2/浅蓝.png", "浅蓝"),
    shenlan("chatBox","color2/深蓝.png", "深蓝"),
    shenlv("chatBox","color2/深绿.png", "深蓝"),
    zi("chatBox","color2/紫.png", "紫"),

    //暂无图片
    edit("edit","编辑2.png", "编辑"),
    save("save","保存2.png", "保存"),
    clear("clear","清空2.png", "清空"),
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
