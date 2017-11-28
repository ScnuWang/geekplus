package cn.geekview.geek_spider.entity.mapper;

import cn.geekview.geek_spider.entity.model.TdreamTask;
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

    String queryTaskListByCrawlInterval = "select pk_id, crawl_url, crawl_frequency, crawl_status, crawl_time, next_crawl_time, original_id, \n" +
            "      website_id from t_dream_task" +
            "      where " +
            "      website_id = #{websiteId,jdbcType=INTEGER}\n" +
            "      and crawl_status = #{crawlStatus,jdbcType=INTEGER}\n" +
            "      and next_crawl_time < #{crawlTimeRight,jdbcType=TIMESTAMP}" +
            "      and next_crawl_time > #{crawlTimeLeft,jdbcType=TIMESTAMP}" ;

    /**
     * 查询即将被抓取的任务
     * @param websiteId
     * @param crawlStatus
     * @param crawlTimeLeft
     * @param crawlTimeRight
     * @return
     */
    @Select(queryTaskListByCrawlInterval)
    @Results({
            @Result(property = "crawlUrl",column = "crawl_url",jdbcType = JdbcType.VARCHAR),
            @Result(property = "originalId",column = "original_id",jdbcType = JdbcType.VARCHAR),
            @Result(property = "pkId",column = "pk_id",jdbcType = JdbcType.VARCHAR),
            @Result(property = "crawlFrequency",column = "crawl_frequency",jdbcType = JdbcType.VARCHAR),
    })
    List<TdreamTask> queryTaskListByCrawlInterval(@Param("websiteId") Integer websiteId, @Param("crawlStatus") Integer crawlStatus,@Param("crawlTimeLeft") Date crawlTimeLeft, @Param("crawlTimeRight") Date crawlTimeRight);


    String updateByPrimaryKey = "update t_dream_task set\n" +
            "      crawl_status = #{crawlStatus,jdbcType=INTEGER},\n" +
            "      crawl_time = #{crawlTime,jdbcType=TIMESTAMP},\n" +
            "      next_crawl_time = #{nextCrawlTime,jdbcType=TIMESTAMP}\n" +
            "    where pk_id = #{pkId,jdbcType=INTEGER}";

    /**
     * 修改抓取任务
     * @param task
     * @return
     */
    @Update(updateByPrimaryKey)
    int updateCrawlStatusByPrimaryKey(TdreamTask task);


    String queryAllTaskList = "select website_id,original_id,crawl_status,crawl_frequency" +
            "  from t_dream_task" ;

    @Select(queryAllTaskList)
    @Results({
            @Result(property = "websiteId",column = "website_id",jdbcType = JdbcType.INTEGER),
            @Result(property = "originalId",column = "original_id",jdbcType = JdbcType.VARCHAR),
            @Result(property = "crawlFrequency",column = "crawl_frequency",jdbcType = JdbcType.INTEGER),
    })
    List<TdreamTask> queryAllTaskList();

}
