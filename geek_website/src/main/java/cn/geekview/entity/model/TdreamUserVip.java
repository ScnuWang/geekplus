package cn.geekview.entity.model;

import lombok.Data;

import java.util.Date;
@Data
public class TdreamUserVip {
    private Integer pkId;

    private Integer userId;

    private Date vipExpiresIn;

}