package com.suixin.dragonguild.entity;

import java.util.Date;

/**
 * 公会信息
 */
public class DragonGuildEntity {
    private Integer id;
    //创建人
    private String uid;
    //创建人昵称
    private String creator;
    //公会名称
    private String name;
    //公会队等级
    private Integer level;
    //公会总经验
    private Integer expAll;
    //公会当前升级经验
    private Integer expCurrent;
    //公会队最大成员
    private Integer maxMember;
    //有效性
    private Integer status;
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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getExpAll() {
        return expAll;
    }

    public void setExpAll(Integer expAll) {
        this.expAll = expAll;
    }

    public Integer getExpCurrent() {
        return expCurrent;
    }

    public void setExpCurrent(Integer expCurrent) {
        this.expCurrent = expCurrent;
    }

    public Integer getMaxMember() {
        return maxMember;
    }

    public void setMaxMember(Integer maxMember) {
        this.maxMember = maxMember;
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
