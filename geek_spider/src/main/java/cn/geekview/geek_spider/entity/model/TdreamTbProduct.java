package cn.geekview.geek_spider.entity.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class TdreamTbProduct {
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

    private List<TdreamTbItem> itemList;

    public List<TdreamTbItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<TdreamTbItem> itemList) {
        this.itemList = itemList;
    }

    public Integer getPkId() {
        return pkId;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }

    public String getOriginalId() {
        return originalId;
    }

    public void setOriginalId(String originalId) {
        this.originalId = originalId == null ? null : originalId.trim();
    }

    public Integer getCrawlFrequency() {
        return crawlFrequency;
    }

    public void setCrawlFrequency(Integer crawlFrequency) {
        this.crawlFrequency = crawlFrequency;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc == null ? null : productDesc.trim();
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl == null ? null : productUrl.trim();
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage == null ? null : productImage.trim();
    }

    public String getProductVideo() {
        return productVideo;
    }

    public void setProductVideo(String productVideo) {
        this.productVideo = productVideo == null ? null : productVideo.trim();
    }

    public String getProductQrcode() {
        return productQrcode;
    }

    public void setProductQrcode(String productQrcode) {
        this.productQrcode = productQrcode == null ? null : productQrcode.trim();
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public String getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus == null ? null : productStatus.trim();
    }

    public Integer getStatusValue() {
        return statusValue;
    }

    public void setStatusValue(Integer statusValue) {
        this.statusValue = statusValue;
    }

    public Integer getForeverValue() {
        return foreverValue;
    }

    public void setForeverValue(Integer foreverValue) {
        this.foreverValue = foreverValue;
    }

    public Integer getFocusCount() {
        return focusCount;
    }

    public void setFocusCount(Integer focusCount) {
        this.focusCount = focusCount;
    }

    public Integer getSupportCount() {
        return supportCount;
    }

    public void setSupportCount(Integer supportCount) {
        this.supportCount = supportCount;
    }

    public String getCurrencySign() {
        return currencySign;
    }

    public void setCurrencySign(String currencySign) {
        this.currencySign = currencySign == null ? null : currencySign.trim();
    }

    public BigDecimal getOriginalTargetAmount() {
        return originalTargetAmount;
    }

    public void setOriginalTargetAmount(BigDecimal originalTargetAmount) {
        this.originalTargetAmount = originalTargetAmount;
    }

    public BigDecimal getOriginalRasiedAmount() {
        return originalRasiedAmount;
    }

    public void setOriginalRasiedAmount(BigDecimal originalRasiedAmount) {
        this.originalRasiedAmount = originalRasiedAmount;
    }

    public BigDecimal getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(BigDecimal targetAmount) {
        this.targetAmount = targetAmount;
    }

    public BigDecimal getRasiedAmount() {
        return rasiedAmount;
    }

    public void setRasiedAmount(BigDecimal rasiedAmount) {
        this.rasiedAmount = rasiedAmount;
    }

    public Integer getFinishPercent() {
        return finishPercent;
    }

    public void setFinishPercent(Integer finishPercent) {
        this.finishPercent = finishPercent;
    }

    public Integer getRemainDay() {
        return remainDay;
    }

    public void setRemainDay(Integer remainDay) {
        this.remainDay = remainDay;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName == null ? null : personName.trim();
    }

    public String getPersonDesc() {
        return personDesc;
    }

    public void setPersonDesc(String personDesc) {
        this.personDesc = personDesc == null ? null : personDesc.trim();
    }

    public String getPersonImage() {
        return personImage;
    }

    public void setPersonImage(String personImage) {
        this.personImage = personImage == null ? null : personImage.trim();
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
        return "TdreamTbProduct{" +
                "pkId=" + pkId +
                ", originalId='" + originalId + '\'' +
                ", crawlFrequency=" + crawlFrequency +
                ", productName='" + productName + '\'' +
                ", productDesc='" + productDesc + '\'' +
                ", productUrl='" + productUrl + '\'' +
                ", productImage='" + productImage + '\'' +
                ", productVideo='" + productVideo + '\'' +
                ", productQrcode='" + productQrcode + '\'' +
                ", beginDate=" + beginDate +
                ", endDate=" + endDate +
                ", updateDatetime=" + updateDatetime +
                ", productStatus='" + productStatus + '\'' +
                ", statusValue=" + statusValue +
                ", foreverValue=" + foreverValue +
                ", focusCount=" + focusCount +
                ", supportCount=" + supportCount +
                ", currencySign='" + currencySign + '\'' +
                ", originalTargetAmount=" + originalTargetAmount +
                ", originalRasiedAmount=" + originalRasiedAmount +
                ", targetAmount=" + targetAmount +
                ", rasiedAmount=" + rasiedAmount +
                ", finishPercent=" + finishPercent +
                ", remainDay=" + remainDay +
                ", personName='" + personName + '\'' +
                ", personDesc='" + personDesc + '\'' +
                ", personImage='" + personImage + '\'' +
                ", updateTime=" + updateTime +
                ", reserve1='" + reserve1 + '\'' +
                ", reserve2='" + reserve2 + '\'' +
                ", reserve3='" + reserve3 + '\'' +
                '}';
    }
}