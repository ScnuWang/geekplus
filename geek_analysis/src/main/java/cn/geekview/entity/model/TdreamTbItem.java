package cn.geekview.entity.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class TdreamTbItem {
    private Integer pkId;

    private Integer productId;

    private String originalItemId;

    private String itemTitle;

    private String itemDesc;

    private String itemImage;

    private String currencySign;

    private BigDecimal originalItemPrice;

    private BigDecimal itemPrice;

    private Integer itemSupport;

    private Integer itemTotal;

    private Date updateDatetime;

    private Date updateTime;

    private String reserve1;

    private String reserve2;

    private String reserve3;
}