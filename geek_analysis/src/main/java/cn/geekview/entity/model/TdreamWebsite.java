package cn.geekview.entity.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class TdreamWebsite {
    private Integer pkId;

    private Integer websiteId;

    private String websiteName;

    private Integer websiteStatus;

    private Integer websiteType;

    private Date updateDatetime;

    private Integer crawlStatus;

    private Integer rankStatus;

    private Integer analysisStatus;

    private BigDecimal totalAmount;

    private Integer totalSupportpeople;

    private Integer totalProducts;

    private Double averageFinish;

    private BigDecimal amountIncreaseDay;

    private Date updateTime;

    private String reserve1;

    private String reserve2;

    private String reserve3;

}