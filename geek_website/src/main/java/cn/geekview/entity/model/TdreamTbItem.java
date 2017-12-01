package cn.geekview.entity.model;

import java.math.BigDecimal;
import java.util.Date;

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

    public Integer getPkId() {
        return pkId;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getOriginalItemId() {
        return originalItemId;
    }

    public void setOriginalItemId(String originalItemId) {
        this.originalItemId = originalItemId == null ? null : originalItemId.trim();
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle == null ? null : itemTitle.trim();
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc == null ? null : itemDesc.trim();
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage == null ? null : itemImage.trim();
    }

    public String getCurrencySign() {
        return currencySign;
    }

    public void setCurrencySign(String currencySign) {
        this.currencySign = currencySign == null ? null : currencySign.trim();
    }

    public BigDecimal getOriginalItemPrice() {
        return originalItemPrice;
    }

    public void setOriginalItemPrice(BigDecimal originalItemPrice) {
        this.originalItemPrice = originalItemPrice;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Integer getItemSupport() {
        return itemSupport;
    }

    public void setItemSupport(Integer itemSupport) {
        this.itemSupport = itemSupport;
    }

    public Integer getItemTotal() {
        return itemTotal;
    }

    public void setItemTotal(Integer itemTotal) {
        this.itemTotal = itemTotal;
    }

    public Date getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getReserve1() {
        return reserve1;
    }

    public void setReserve1(String reserve1) {
        this.reserve1 = reserve1 == null ? null : reserve1.trim();
    }

    public String getReserve2() {
        return reserve2;
    }

    public void setReserve2(String reserve2) {
        this.reserve2 = reserve2 == null ? null : reserve2.trim();
    }

    public String getReserve3() {
        return reserve3;
    }

    public void setReserve3(String reserve3) {
        this.reserve3 = reserve3 == null ? null : reserve3.trim();
    }

    @Override
    public String toString() {
        return "TdreamTbItem{" +
                "pkId=" + pkId +
                ", productId=" + productId +
                ", originalItemId='" + originalItemId + '\'' +
                ", itemTitle='" + itemTitle + '\'' +
                ", itemDesc='" + itemDesc + '\'' +
                ", itemImage='" + itemImage + '\'' +
                ", currencySign='" + currencySign + '\'' +
                ", originalItemPrice=" + originalItemPrice +
                ", itemPrice=" + itemPrice +
                ", itemSupport=" + itemSupport +
                ", itemTotal=" + itemTotal +
                ", updateDatetime=" + updateDatetime +
                ", updateTime=" + updateTime +
                ", reserve1='" + reserve1 + '\'' +
                ", reserve2='" + reserve2 + '\'' +
                ", reserve3='" + reserve3 + '\'' +
                '}';
    }
}