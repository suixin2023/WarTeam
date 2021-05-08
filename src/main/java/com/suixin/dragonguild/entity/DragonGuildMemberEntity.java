package com.suixin.dragonguild.entity;

import java.util.Date;

/**
 * 公会成员信息
 */
public class DragonGuildMemberEntity {
    private Integer id;
    //创建人
    private String uid;
    //公会Id
    private Integer dragonGuildId;
    //所属公会名字
    private String dragonGuildName;
    //职位
    private String position;
    //贡献exp
    private Integer exp;
    //有效性
    private Integer status;
    //加入时间
    private Date joinTime;

    private Date created;
    private Date modified;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getDragonGuildId() {
        return dragonGuildId;
    }

    public void setDragonGuildId(Integer dragonGuildId) {
        this.dragonGuildId = dragonGuildId;
    }

    public String getDragonGuildName() {
        return dragonGuildName;
    }

    public void setDragonGuildName(String dragonGuildName) {
        this.dragonGuildName = dragonGuildName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public Date getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Date joinTime) {
        this.joinTime = joinTime;
    }
}
