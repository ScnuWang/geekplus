package cn.geekview.entity.model;

import lombok.Data;
import lombok.NonNull;

import java.util.Date;
@Data
public class TdreamUser {
    private Integer pkId;

//    @NonNull
    private String email;

    private String nickName;

//    @NonNull
    private String password;

    private String openid;

    private Integer gender;

    private Date registerTime;

    private Integer userStatus;

    private String activeCode;

    private Date exprieTime;

    private String remark;

}