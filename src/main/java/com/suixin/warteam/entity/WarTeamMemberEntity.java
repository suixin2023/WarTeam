package com.suixin.warteam.entity;

import java.util.Date;

/**
 * 战队成员信息
 */
public class WarTeamMemberEntity {
    private Integer id;
    //创建人
    private String uid;
    //战队Id
    private Integer warTeamId;
    //所属战队名字
    private String warTeamName;
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
