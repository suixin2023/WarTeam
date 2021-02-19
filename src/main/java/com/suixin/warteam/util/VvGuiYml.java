package com.suixin.warteam.util;

import com.suixin.warteam.WarTeam;
import org.bukkit.configuration.file.YamlConfiguration;

public class VvGuiYml {
    private static YamlConfiguration shangyiye;
    private static YamlConfiguration xiayiye;
    private static YamlConfiguration applyShangyiye;
    private static YamlConfiguration applyXiayiye;
    private static YamlConfiguration applyClose;
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

    public static boolean loadGui( ) {
        try {
            shangyiye = WarTeam.getYml("vexview/上一页.yml");
            xiayiye = WarTeam.getYml("vexview/下一页.yml");
            applyShangyiye = WarTeam.getYml("vexview/审批上一页.yml");
            applyXiayiye = WarTeam.getYml("vexview/审批下一页.yml");
            applyClose = WarTeam.getYml("vexview/审批关闭.yml");
            applyAgree = WarTeam.getYml("vexview/审批同意.yml");
            applyRepulse = WarTeam.getYml("vexview/审批拒绝.yml");
            applyList = WarTeam.getYml("vexview/审批列表.yml");
            applyButton = WarTeam.getYml("vexview/审批按钮.yml");
            applyPlayerName = WarTeam.getYml("vexview/审批玩家名.yml");
            applyBackground = WarTeam.getYml("vexview/审批背景.yml");
            applyNum = WarTeam.getYml("vexview/审批数量.yml");
            kickOut = WarTeam.getYml("vexview/踢人.yml");
            updateName = WarTeam.getYml("vexview/修改队名.yml");
            renshu = WarTeam.getYml("vexview/人数.yml");
            create = WarTeam.getYml("vexview/创建战队.yml");
            join = WarTeam.getYml("vexview/加入战队.yml");
            pictureFrame = WarTeam.getYml("vexview/头像框.yml");
            name = WarTeam.getYml("vexview/战队名.yml");
            background = WarTeam.getYml("vexview/背景.yml");
            backgroundOfNoTeam = WarTeam.getYml("vexview/无战队背景.yml");
            nickName = WarTeam.getYml("vexview/游戏名.yml");
            level = WarTeam.getYml("vexview/等级.yml");
            exp = WarTeam.getYml("vexview/贡献值.yml");
            outTeam = WarTeam.getYml("vexview/退出战队.yml");
            dissolveTeam = WarTeam.getYml("vexview/解散战队.yml");
            window = WarTeam.getYml("vexview/弹窗.yml");
            shurukuang = WarTeam.getYml("vexview/输入框.yml");
            confirm = WarTeam.getYml("vexview/确定.yml");
            return true;
        }catch (Exception e){
            return false;
        }

    }

    public static YamlConfiguration getShangyiye() {
        return shangyiye;
    }

    public static void setShangyiye(YamlConfiguration shangyiye) {
        VvGuiYml.shangyiye = shangyiye;
    }

    public static YamlConfiguration getXiayiye() {
        return xiayiye;
    }

    public static void setXiayiye(YamlConfiguration xiayiye) {
        VvGuiYml.xiayiye = xiayiye;
    }

    public static YamlConfiguration getRenshu() {
        return renshu;
    }

    public static void setRenshu(YamlConfiguration renshu) {
        VvGuiYml.renshu = renshu;
    }

    public static YamlConfiguration getCreate() {
        return create;
    }

    public static void setCreate(YamlConfiguration create) {
        VvGuiYml.create = create;
    }

    public static YamlConfiguration getJoin() {
        return join;
    }

    public static void setJoin(YamlConfiguration join) {
        VvGuiYml.join = join;
    }

    public static YamlConfiguration getPictureFrame() {
        return pictureFrame;
    }

    public static void setPictureFrame(YamlConfiguration pictureFrame) {
        VvGuiYml.pictureFrame = pictureFrame;
    }

    public static YamlConfiguration getName() {
        return name;
    }

    public static void setName(YamlConfiguration name) {
        VvGuiYml.name = name;
    }

    public static YamlConfiguration getBackground() {
        return background;
    }

    public static void setBackground(YamlConfiguration background) {
        VvGuiYml.background = background;
    }

    public static YamlConfiguration getBackgroundOfNoTeam() {
        return backgroundOfNoTeam;
    }

    public static void setBackgroundOfNoTeam(YamlConfiguration backgroundOfNoTeam) {
        VvGuiYml.backgroundOfNoTeam = backgroundOfNoTeam;
    }

    public static YamlConfiguration getNickName() {
        return nickName;
    }

    public static void setNickName(YamlConfiguration nickName) {
        VvGuiYml.nickName = nickName;
    }

    public static YamlConfiguration getLevel() {
        return level;
    }

    public static void setLevel(YamlConfiguration level) {
        VvGuiYml.level = level;
    }

    public static YamlConfiguration getExp() {
        return exp;
    }

    public static void setExp(YamlConfiguration exp) {
        VvGuiYml.exp = exp;
    }

    public static YamlConfiguration getOutTeam() {
        return outTeam;
    }

    public static void setOutTeam(YamlConfiguration outTeam) {
        VvGuiYml.outTeam = outTeam;
    }

    public static YamlConfiguration getDissolveTeam() {
        return dissolveTeam;
    }

    public static void setDissolveTeam(YamlConfiguration dissolveTeam) {
        VvGuiYml.dissolveTeam = dissolveTeam;
    }

    public static YamlConfiguration getWindow() {
        return window;
    }

    public static void setWindow(YamlConfiguration window) {
        VvGuiYml.window = window;
    }

    public static YamlConfiguration getShurukuang() {
        return shurukuang;
    }

    public static void setShurukuang(YamlConfiguration shurukuang) {
        VvGuiYml.shurukuang = shurukuang;
    }

    public static YamlConfiguration getConfirm() {
        return confirm;
    }

    public static void setConfirm(YamlConfiguration confirm) {
        VvGuiYml.confirm = confirm;
    }

    public static YamlConfiguration getKickOut() {
        return kickOut;
    }

    public static void setKickOut(YamlConfiguration kickOut) {
        VvGuiYml.kickOut = kickOut;
    }

    public static YamlConfiguration getUpdateName() {
        return updateName;
    }

    public static void setUpdateName(YamlConfiguration updateName) {
        VvGuiYml.updateName = updateName;
    }

    public static YamlConfiguration getApplyShangyiye() {
        return applyShangyiye;
    }

    public static void setApplyShangyiye(YamlConfiguration applyShangyiye) {
        VvGuiYml.applyShangyiye = applyShangyiye;
    }

    public static YamlConfiguration getApplyXiayiye() {
        return applyXiayiye;
    }

    public static void setApplyXiayiye(YamlConfiguration applyXiayiye) {
        VvGuiYml.applyXiayiye = applyXiayiye;
    }

    public static YamlConfiguration getApplyClose() {
        return applyClose;
    }

    public static void setApplyClose(YamlConfiguration applyClose) {
        VvGuiYml.applyClose = applyClose;
    }

    public static YamlConfiguration getApplyAgree() {
        return applyAgree;
    }

    public static void setApplyAgree(YamlConfiguration applyAgree) {
        VvGuiYml.applyAgree = applyAgree;
    }

    public static YamlConfiguration getApplyRepulse() {
        return applyRepulse;
    }

    public static void setApplyRepulse(YamlConfiguration applyRepulse) {
        VvGuiYml.applyRepulse = applyRepulse;
    }

    public static YamlConfiguration getApplyList() {
        return applyList;
    }

    public static void setApplyList(YamlConfiguration applyList) {
        VvGuiYml.applyList = applyList;
    }

    public static YamlConfiguration getApplyPlayerName() {
        return applyPlayerName;
    }

    public static void setApplyPlayerName(YamlConfiguration applyPlayerName) {
        VvGuiYml.applyPlayerName = applyPlayerName;
    }

    public static YamlConfiguration getApplyBackground() {
        return applyBackground;
    }

    public static void setApplyBackground(YamlConfiguration applyBackground) {
        VvGuiYml.applyBackground = applyBackground;
    }

    public static YamlConfiguration getApplyButton() {
        return applyButton;
    }

    public static void setApplyButton(YamlConfiguration applyButton) {
        VvGuiYml.applyButton = applyButton;
    }

    public static YamlConfiguration getApplyNum() {
        return applyNum;
    }

    public static void setApplyNum(YamlConfiguration applyNum) {
        VvGuiYml.applyNum = applyNum;
    }
}
