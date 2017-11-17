package cn.geekview.geek_spider.entity.mapper;

import cn.geekview.geek_spider.entity.domain.TdreamTask;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;
import java.util.List;

public interface TdreamTaskMapper {
    String insert = "insert into t_dream_task (pk_id, crawl_url, crawl_frequency, \n" +
            "      crawl_status, crawl_time, next_crawl_time, \n" +
            "      original_id, website_id, \n" +
            "      reserve1, reserve2, reserve3\n" +
            "      )\n" +
            "    values (#{pkId,jdbcType=INTEGER}, #{crawlUrl,jdbcType=VARCHAR}, #{crawlFrequency,jdbcType=INTEGER}, \n" +
            "      #{crawlStatus,jdbcType=INTEGER}, #{crawlTime,jdbcType=TIMESTAMP}, #{nextCrawlTime,jdbcType=TIMESTAMP}, \n" +
            "      #{originalId,jdbcType=VARCHAR}, #{websiteId,jdbcType=INTEGER}, \n" +
            "      #{reserve1,jdbcType=VARCHAR}, #{reserve2,jdbcType=VARCHAR}, #{reserve3,jdbcType=VARCHAR}\n" +
            "      )";

    @Insert(insert)
    int insert(TdreamTask task);

    String queryTaskList = "select pk_id, crawl_url, crawl_frequency, crawl_status, crawl_time, next_crawl_time, original_id, \n" +
            "      website_id from t_dream_task" +
            "      where " +
            "      website_id = #{websiteId,jdbcType=INTEGER}\n" +
            "      and crawl_status = #{crawlStatus,jdbcType=INTEGER}\n" +
            "      and next_crawl_time < #{crawlTimeRight,jdbcType=TIMESTAMP}" +
            "      and next_crawl_time > #{crawlTimeLeft,jdbcType=TIMESTAMP}" ;

    @Select(queryTaskList)
    @Results({
            @Result(property = "crawlUrl",column = "crawl_url",jdbcType = JdbcType.VARCHAR),
            @Result(property = "originalId",column = "original_id",jdbcType = JdbcType.VARCHAR)
    })
    List<TdreamTask> queryTaskList(@Param("websiteId") Integer websiteId, @Param("crawlStatus") Integer crawlStatus, @Param("crawlTimeRight") Date crawlTimeRight, @Param("crawlTimeLeft") Date crawlTimeLeft);
}
