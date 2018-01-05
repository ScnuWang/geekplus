package cn.geekview.entity.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TdreamProduct extends BasicProduct{

    private Integer pkId;

    private Integer websiteId;

    private String originalId;

    private Integer statusValue;

    private String productStatus;

    private String productName;

    private String productDesc;

    private String productUrl;

    private String productImage;

    private String productVideo;

    private String productQrcode;

    private Date beginDate;

    private Date endDate;

    private Date updateDatetime;

    private Integer foreverValue;

    private Integer focusCount;

    private Integer supportCount;

    private String currencySign;

    private BigDecimal targetAmount;

    private BigDecimal rasiedAmount;

    private Integer finishPercent;

    private Integer remainDay;

    private String personName;

    private String personDesc;

    private String personImage;

    private Date updateTime;

    private String reserve1;

    private String reserve2;

    private String reserve3;

}