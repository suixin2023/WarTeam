package com.suixin.warteam.entity;

import java.util.Date;

/**
 * 申请表
 */
public class WarTeamApplyEntity {
    private Integer id;
    //创建人
    private String uid;
    //审批人
    private String apply;
    //战队Id
    private Integer warTeamId;
    //所属战队名字
    private String warTeamName;
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

    public Integer getWarTeamId() {
        return warTeamId;
    }

    public void setWarTeamId(Integer warTeamId) {
        this.warTeamId = warTeamId;
    }

    public String getWarTeamName() {
        return warTeamName;
    }

    public void setWarTeamName(String warTeamName) {
        this.warTeamName = warTeamName;
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
