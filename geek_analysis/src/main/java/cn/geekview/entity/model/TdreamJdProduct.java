package cn.geekview.entity.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class TdreamJdProduct extends BasicProduct{
    private Integer pkId;

    private String originalId;

    private Integer crawlFrequency;

    private String productName;

    private String productDesc;

    private String productUrl;

    private String productImage;

    private String productVideo;

    private String productQrcode;

    private Date beginDate;

    private Date endDate;

    private Date updateDatetime;

    private String productStatus;

    private Integer statusValue;

    private Integer foreverValue;

    private Integer focusCount;

    private Integer supportCount;

    private String currencySign;

    private BigDecimal originalTargetAmount;

    private BigDecimal originalRasiedAmount;

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