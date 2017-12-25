package cn.geekview.entity.model;

import java.util.Date;

public class TdreamUser {
    private Integer pkId;

    private String email;

    private String nickName;

    private String password;

    private String openid;

    private Integer gender;

    private Date registerTime;

    private Integer userStatus;

    private String activeCode;

    private Date exprieTime;

    private String remark;

    public Integer getPkId() {
        return pkId;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public String getActiveCode() {
        return activeCode;
    }

    public void setActiveCode(String activeCode) {
        this.activeCode = activeCode == null ? null : activeCode.trim();
    }

    public Date getExprieTime() {
        return exprieTime;
    }

    public void setExprieTime(Date exprieTime) {
        this.exprieTime = exprieTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    @Override
    public String toString() {
        return "TdreamUser{" +
                "pkId=" + pkId +
                ", email='" + email + '\'' +
                ", nickName='" + nickName + '\'' +
                ", password='" + password + '\'' +
                ", openid='" + openid + '\'' +
                ", gender=" + gender +
                ", registerTime=" + registerTime +
                ", userStatus=" + userStatus +
                ", activeCode='" + activeCode + '\'' +
                ", exprieTime=" + exprieTime +
                ", remark='" + remark + '\'' +
                '}';
    }
}