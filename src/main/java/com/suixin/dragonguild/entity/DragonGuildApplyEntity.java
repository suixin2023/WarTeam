package com.suixin.dragonguild.entity;

import java.util.Date;

/**
 * 申请表
 */
public class DragonGuildApplyEntity {
    private Integer id;
    //创建人
    private String uid;
    //审批人
    private String apply;
    //公会Id
    private Integer dragonGuildId;
    //所属公会名字
    private String dragonGuildName;
    //有效性
    private Integer status;
    //加入时间
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

    public String getApply() {
        return apply;
    }

    public void setApply(String apply) {
        this.apply = apply;
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
}
