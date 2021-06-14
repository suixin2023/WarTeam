package com.suixin.dragonguild.entity;

import eos.moe.dragoncore.api.easygui.component.EasyButton;
import org.bukkit.entity.Player;

public class EasyButtonEx extends EasyButton {
    private String type;
    private String applyName;
    private String guildName;
    private String appointName;
    public EasyButtonEx(int x, int y, int w, int h, String url) {
        super(x, y, w, h, url);
    }

    public EasyButtonEx(int x, int y, int w, int h, String url, String urlHov) {
        super(x, y, w, h, url, urlHov);
    }

    public EasyButtonEx(int x, int y, int w, int h, String url, String urlHov, String text) {
        super(x, y, w, h, url, urlHov, text);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getApplyName() {
        return applyName;
    }

    public void setApplyName(String applyName) {
        this.applyName = applyName;
    }

    public String getGuildName() {
        return guildName;
    }

    public void setGuildName(String guildName) {
        this.guildName = guildName;
    }

    public String getAppointName() {
        return appointName;
    }

    public void setAppointName(String appointName) {
        this.appointName = appointName;
    }
}
