package cn.geekview.entity.model;

import java.util.Date;

public class TdreamUserVip {
    private Integer pkId;

    private Integer userId;

    private Date vipExpiresIn;

    public Integer getPkId() {
        return pkId;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getVipExpiresIn() {
        return vipExpiresIn;
    }

    public void setVipExpiresIn(Date vipExpiresIn) {
        this.vipExpiresIn = vipExpiresIn;
    }
}