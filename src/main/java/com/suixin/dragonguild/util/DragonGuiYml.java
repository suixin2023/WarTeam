package com.suixin.dragonguild.util;

import com.suixin.dragonguild.DragonGuild;
import org.bukkit.configuration.file.YamlConfiguration;

public class DragonGuiYml {
    private static YamlConfiguration shangyiye;
    private static YamlConfiguration xiayiye;
    private static YamlConfiguration applyShangyiye;
    private static YamlConfiguration applyXiayiye;
    private static YamlConfiguration applyAgree;
    private static YamlConfiguration applyRepulse;
    private static YamlConfiguration applyList;
    private static YamlConfiguration applyNum;
    private static YamlConfiguration applyButton;
    private static YamlConfiguration applyPlayerName;
    private static YamlConfiguration applyBackground;
    private static YamlConfiguration kickOut;
    private static YamlConfiguration updateName;
    private static YamlConfiguration renshu;
    private static YamlConfiguration create;
    private static YamlConfiguration join;
    private static YamlConfiguration pictureFrame;
    private static YamlConfiguration name;
    private static YamlConfiguration background;
    private static YamlConfiguration backgroundOfNoTeam;
    private static YamlConfiguration nickName;
    private static YamlConfiguration level;
    private static YamlConfiguration exp;
    private static YamlConfiguration outTeam;
    private static YamlConfiguration dissolveTeam;
    private static YamlConfiguration window;
    private static YamlConfiguration shurukuang;
    private static YamlConfiguration confirm;
    private static YamlConfiguration close;
    private static YamlConfiguration cancel;

    private static YamlConfiguration lobby;
    private static YamlConfiguration notice;
    private static YamlConfiguration chat;
    private static YamlConfiguration apply;
    private static YamlConfiguration top;
    //公告
    private static YamlConfiguration backGroundNotice;
    private static YamlConfiguration title;
    private static YamlConfiguration content;
    private static YamlConfiguration save;
    private static YamlConfiguration clear;
    private static YamlConfiguration edit;
    //聊天
    private static YamlConfiguration backGroundChat;
    private static YamlConfiguration chatContent;
    private static YamlConfiguration messageList;
    private static YamlConfiguration send;
    private static YamlConfiguration chatImg;
    private static YamlConfiguration chatName;
    private static YamlConfiguration chatBar;

    public static boolean loadGui( ) {
        try {
            shangyiye = DragonGuild.getYml("dragon/上一页.yml");
            xiayiye = DragonGuild.getYml("dragon/下一页.yml");
            kickOut = DragonGuild.getYml("dragon/踢人.yml");
            updateName = DragonGuild.getYml("dragon/修改公会.yml");
            renshu = DragonGuild.getYml("dragon/人数.yml");
            create = DragonGuild.getYml("dragon/创建公会.yml");
            join = DragonGuild.getYml("dragon/加入公会.yml");
            pictureFrame = DragonGuild.getYml("dragon/头像框.yml");
            name = DragonGuild.getYml("dragon/公会名.yml");
            background = DragonGuild.getYml("dragon/背景.yml");
            nickName = DragonGuild.getYml("dragon/游戏名.yml");
            level = DragonGuild.getYml("dragon/等级.yml");
            exp = DragonGuild.getYml("dragon/贡献值.yml");
            outTeam = DragonGuild.getYml("dragon/退出公会.yml");
            dissolveTeam = DragonGuild.getYml("dragon/解散公会.yml");
            close = DragonGuild.getYml("dragon/关闭.yml");
            cancel = DragonGuild.getYml("dragon/取消.yml");

            lobby = DragonGuild.getYml("dragon/大厅.yml");
            notice = DragonGuild.getYml("dragon/公告.yml");
            chat = DragonGuild.getYml("dragon/聊天.yml");
            apply = DragonGuild.getYml("dragon/审批.yml");
            top = DragonGuild.getYml("dragon/排行.yml");

            applyShangyiye = DragonGuild.getYml("dragon/apply/审批上一页.yml");
            applyXiayiye = DragonGuild.getYml("dragon/apply/审批下一页.yml");
            applyAgree = DragonGuild.getYml("dragon/apply/审批同意.yml");
            applyRepulse = DragonGuild.getYml("dragon/apply/审批拒绝.yml");
            applyList = DragonGuild.getYml("dragon/apply/审批列表.yml");
            applyButton = DragonGuild.getYml("dragon/apply/审批按钮.yml");
            applyPlayerName = DragonGuild.getYml("dragon/apply/审批玩家名.yml");
            applyBackground = DragonGuild.getYml("dragon/apply/审批背景.yml");
            applyNum = DragonGuild.getYml("dragon/apply/审批数量.yml");

            window = DragonGuild.getYml("dragon/window/弹窗.yml");
            shurukuang = DragonGuild.getYml("dragon/window/输入框.yml");
            confirm = DragonGuild.getYml("dragon/window/确定.yml");

            backGroundNotice = DragonGuild.getYml("dragon/notice/公告背景.yml");
            title = DragonGuild.getYml("dragon/notice/公告标题.yml");
            content = DragonGuild.getYml("dragon/notice/公告内容.yml");
            save = DragonGuild.getYml("dragon/notice/保存.yml");
            clear = DragonGuild.getYml("dragon/notice/清空.yml");
            edit = DragonGuild.getYml("dragon/notice/编辑.yml");

            backGroundChat = DragonGuild.getYml("dragon/chat/聊天背景.yml");
            messageList = DragonGuild.getYml("dragon/chat/消息列表.yml");
            send = DragonGuild.getYml("dragon/chat/发送.yml");
            chatContent = DragonGuild.getYml("dragon/chat/聊天内容.yml");
            chatImg = DragonGuild.getYml("dragon/chat/头像.yml");
            chatName = DragonGuild.getYml("dragon/chat/名字.yml");
            chatBar = DragonGuild.getYml("dragon/chat/bar.yml");
            return true;
        }catch (Exception e){
            return false;
        }

    }

    public static YamlConfiguration getShangyiye() {
        return shangyiye;
    }

    public static void setShangyiye(YamlConfiguration shangyiye) {
        DragonGuiYml.shangyiye = shangyiye;
    }

    public static YamlConfiguration getXiayiye() {
        return xiayiye;
    }

    public static void setXiayiye(YamlConfiguration xiayiye) {
        DragonGuiYml.xiayiye = xiayiye;
    }

    public static YamlConfiguration getRenshu() {
        return renshu;
    }

    public static void setRenshu(YamlConfiguration renshu) {
        DragonGuiYml.renshu = renshu;
    }

    public static YamlConfiguration getCreate() {
        return create;
    }

    public static void setCreate(YamlConfiguration create) {
        DragonGuiYml.create = create;
    }

    public static YamlConfiguration getJoin() {
        return join;
    }

    public static void setJoin(YamlConfiguration join) {
        DragonGuiYml.join = join;
    }

    public static YamlConfiguration getPictureFrame() {
        return pictureFrame;
    }

    public static void setPictureFrame(YamlConfiguration pictureFrame) {
        DragonGuiYml.pictureFrame = pictureFrame;
    }

    public static YamlConfiguration getName() {
        return name;
    }

    public static void setName(YamlConfiguration name) {
        DragonGuiYml.name = name;
    }

    public static YamlConfiguration getBackground() {
        return background;
    }

    public static void setBackground(YamlConfiguration background) {
        DragonGuiYml.background = background;
    }

    public static YamlConfiguration getBackgroundOfNoTeam() {
        return backgroundOfNoTeam;
    }

    public static void setBackgroundOfNoTeam(YamlConfiguration backgroundOfNoTeam) {
        DragonGuiYml.backgroundOfNoTeam = backgroundOfNoTeam;
    }

    public static YamlConfiguration getNickName() {
        return nickName;
    }

    public static void setNickName(YamlConfiguration nickName) {
        DragonGuiYml.nickName = nickName;
    }

    public static YamlConfiguration getLevel() {
        return level;
    }

    public static void setLevel(YamlConfiguration level) {
        DragonGuiYml.level = level;
    }

    public static YamlConfiguration getExp() {
        return exp;
    }

    public static void setExp(YamlConfiguration exp) {
        DragonGuiYml.exp = exp;
    }

    public static YamlConfiguration getOutTeam() {
        return outTeam;
    }

    public static void setOutTeam(YamlConfiguration outTeam) {
        DragonGuiYml.outTeam = outTeam;
    }

    public static YamlConfiguration getDissolveTeam() {
        return dissolveTeam;
    }

    public static void setDissolveTeam(YamlConfiguration dissolveTeam) {
        DragonGuiYml.dissolveTeam = dissolveTeam;
    }

    public static YamlConfiguration getWindow() {
        return window;
    }

    public static void setWindow(YamlConfiguration window) {
        DragonGuiYml.window = window;
    }

    public static YamlConfiguration getShurukuang() {
        return shurukuang;
    }

    public static void setShurukuang(YamlConfiguration shurukuang) {
        DragonGuiYml.shurukuang = shurukuang;
    }

    public static YamlConfiguration getConfirm() {
        return confirm;
    }

    public static void setConfirm(YamlConfiguration confirm) {
        DragonGuiYml.confirm = confirm;
    }

    public static YamlConfiguration getKickOut() {
        return kickOut;
    }

    public static void setKickOut(YamlConfiguration kickOut) {
        DragonGuiYml.kickOut = kickOut;
    }

    public static YamlConfiguration getUpdateName() {
        return updateName;
    }

    public static void setUpdateName(YamlConfiguration updateName) {
        DragonGuiYml.updateName = updateName;
    }

    public static YamlConfiguration getApplyShangyiye() {
        return applyShangyiye;
    }

    public static void setApplyShangyiye(YamlConfiguration applyShangyiye) {
        DragonGuiYml.applyShangyiye = applyShangyiye;
    }

    public static YamlConfiguration getApplyXiayiye() {
        return applyXiayiye;
    }

    public static void setApplyXiayiye(YamlConfiguration applyXiayiye) {
        DragonGuiYml.applyXiayiye = applyXiayiye;
    }

    public static YamlConfiguration getClose() {
        return close;
    }

    public static void setClose(YamlConfiguration close) {
        DragonGuiYml.close = close;
    }

    public static YamlConfiguration getApplyAgree() {
        return applyAgree;
    }

    public static void setApplyAgree(YamlConfiguration applyAgree) {
        DragonGuiYml.applyAgree = applyAgree;
    }

    public static YamlConfiguration getApplyRepulse() {
        return applyRepulse;
    }

    public static void setApplyRepulse(YamlConfiguration applyRepulse) {
        DragonGuiYml.applyRepulse = applyRepulse;
    }

    public static YamlConfiguration getApplyList() {
        return applyList;
    }

    public static void setApplyList(YamlConfiguration applyList) {
        DragonGuiYml.applyList = applyList;
    }

    public static YamlConfiguration getApplyPlayerName() {
        return applyPlayerName;
    }

    public static void setApplyPlayerName(YamlConfiguration applyPlayerName) {
        DragonGuiYml.applyPlayerName = applyPlayerName;
    }

    public static YamlConfiguration getApplyBackground() {
        return applyBackground;
    }

    public static void setApplyBackground(YamlConfiguration applyBackground) {
        DragonGuiYml.applyBackground = applyBackground;
    }

    public static YamlConfiguration getApplyButton() {
        return applyButton;
    }

    public static void setApplyButton(YamlConfiguration applyButton) {
        DragonGuiYml.applyButton = applyButton;
    }

    public static YamlConfiguration getApplyNum() {
        return applyNum;
    }

    public static void setApplyNum(YamlConfiguration applyNum) {
        DragonGuiYml.applyNum = applyNum;
    }

    public static YamlConfiguration getTitle() {
        return title;
    }

    public static void setTitle(YamlConfiguration title) {
        DragonGuiYml.title = title;
    }

    public static YamlConfiguration getContent() {
        return content;
    }

    public static void setContent(YamlConfiguration content) {
        DragonGuiYml.content = content;
    }

    public static YamlConfiguration getChatContent() {
        return chatContent;
    }

    public static void setChatContent(YamlConfiguration chatContent) {
        DragonGuiYml.chatContent = chatContent;
    }

    public static YamlConfiguration getMessageList() {
        return messageList;
    }

    public static void setMessageList(YamlConfiguration messageList) {
        DragonGuiYml.messageList = messageList;
    }

    public static YamlConfiguration getSend() {
        return send;
    }

    public static void setSend(YamlConfiguration send) {
        DragonGuiYml.send = send;
    }

    public static YamlConfiguration getSave() {
        return save;
    }

    public static void setSave(YamlConfiguration save) {
        DragonGuiYml.save = save;
    }

    public static YamlConfiguration getClear() {
        return clear;
    }

    public static void setClear(YamlConfiguration clear) {
        DragonGuiYml.clear = clear;
    }

    public static YamlConfiguration getBackGroundNotice() {
        return backGroundNotice;
    }

    public static void setBackGroundNotice(YamlConfiguration backGroundNotice) {
        DragonGuiYml.backGroundNotice = backGroundNotice;
    }

    public static YamlConfiguration getBackGroundChat() {
        return backGroundChat;
    }

    public static void setBackGroundChat(YamlConfiguration backGroundChat) {
        DragonGuiYml.backGroundChat = backGroundChat;
    }

    public static YamlConfiguration getCancel() {
        return cancel;
    }

    public static void setCancel(YamlConfiguration cancel) {
        DragonGuiYml.cancel = cancel;
    }

    public static YamlConfiguration getChatImg() {
        return chatImg;
    }

    public static void setChatImg(YamlConfiguration chatImg) {
        DragonGuiYml.chatImg = chatImg;
    }

    public static YamlConfiguration getChatName() {
        return chatName;
    }

    public static void setChatName(YamlConfiguration chatName) {
        DragonGuiYml.chatName = chatName;
    }

    public static YamlConfiguration getChatBar() {
        return chatBar;
    }

    public static void setChatBar(YamlConfiguration chatBar) {
        DragonGuiYml.chatBar = chatBar;
    }

    public static YamlConfiguration getNotice() {
        return notice;
    }

    public static void setNotice(YamlConfiguration notice) {
        DragonGuiYml.notice = notice;
    }

    public static YamlConfiguration getChat() {
        return chat;
    }

    public static void setChat(YamlConfiguration chat) {
        DragonGuiYml.chat = chat;
    }

    public static YamlConfiguration getApply() {
        return apply;
    }

    public static void setApply(YamlConfiguration apply) {
        DragonGuiYml.apply = apply;
    }

    public static YamlConfiguration getLobby() {
        return lobby;
    }

    public static void setLobby(YamlConfiguration lobby) {
        DragonGuiYml.lobby = lobby;
    }

    public static YamlConfiguration getTop() {
        return top;
    }

    public static void setTop(YamlConfiguration top) {
        DragonGuiYml.top = top;
    }

    public static YamlConfiguration getEdit() {
        return edit;
    }

    public static void setEdit(YamlConfiguration edit) {
        DragonGuiYml.edit = edit;
    }
}
