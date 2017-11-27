package cn.geekview.geek_spider.entity.model;

import java.util.Date;

public class TdreamTask {
    private Integer pkId;

    private String crawlUrl;

    private Integer crawlFrequency;

    private Integer crawlStatus;

    private Date crawlTime;

    private Date nextCrawlTime;

    private String originalId;

    private Integer websiteId;

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

    public String getCrawlUrl() {
        return crawlUrl;
    }

    public void setCrawlUrl(String crawlUrl) {
        this.crawlUrl = crawlUrl == null ? null : crawlUrl.trim();
    }

    public Integer getCrawlFrequency() {
        return crawlFrequency;
    }

    public void setCrawlFrequency(Integer crawlFrequency) {
        this.crawlFrequency = crawlFrequency;
    }

    public Integer getCrawlStatus() {
        return crawlStatus;
    }

    public void setCrawlStatus(Integer crawlStatus) {
        this.crawlStatus = crawlStatus;
    }

    public Date getCrawlTime() {
        return crawlTime;
    }

    public void setCrawlTime(Date crawlTime) {
        this.crawlTime = crawlTime;
    }

    public Date getNextCrawlTime() {
        return nextCrawlTime;
    }

    public void setNextCrawlTime(Date nextCrawlTime) {
        this.nextCrawlTime = nextCrawlTime;
    }

    public String getOriginalId() {
        return originalId;
    }

    public void setOriginalId(String originalId) {
        this.originalId = originalId == null ? null : originalId.trim();
    }

    public Integer getWebsiteId() {
        return websiteId;
    }

    public void setWebsiteId(Integer websiteId) {
        this.websiteId = websiteId;
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
        return "TdreamTask{" +
                "pkId=" + pkId +
                ", crawlUrl='" + crawlUrl + '\'' +
                ", crawlFrequency=" + crawlFrequency +
                ", crawlStatus=" + crawlStatus +
                ", crawlTime=" + crawlTime +
                ", nextCrawlTime=" + nextCrawlTime +
                ", originalId='" + originalId + '\'' +
                ", websiteId=" + websiteId +
                ", updateTime=" + updateTime +
                ", reserve1='" + reserve1 + '\'' +
                ", reserve2='" + reserve2 + '\'' +
                ", reserve3='" + reserve3 + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TdreamTask task = (TdreamTask) o;

        if (!crawlUrl.equals(task.crawlUrl)) return false;
        if (!crawlFrequency.equals(task.crawlFrequency)) return false;
        if (!crawlStatus.equals(task.crawlStatus)) return false;
        if (!crawlTime.equals(task.crawlTime)) return false;
        if (!nextCrawlTime.equals(task.nextCrawlTime)) return false;
        if (!originalId.equals(task.originalId)) return false;
        return websiteId.equals(task.websiteId);
    }

    @Override
    public int hashCode() {
        int result = crawlUrl.hashCode();
        result = 31 * result + crawlFrequency.hashCode();
        result = 31 * result + crawlStatus.hashCode();
        result = 31 * result + crawlTime.hashCode();
        result = 31 * result + nextCrawlTime.hashCode();
        result = 31 * result + originalId.hashCode();
        result = 31 * result + websiteId.hashCode();
        return result;
    }
}