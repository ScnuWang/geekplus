package cn.geekview.entity.mapper.primary;


import cn.geekview.entity.model.TdreamWebsite;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public interface TdreamWebsite_PrimaryMapper {

    /**
     * 根据平台编号查询平台相关总的数据
     */
    TdreamWebsite queryWebsiteDataByWebsiteId(@Param("websiteId")Integer websiteId);


    /**
     *  插入数据
     * @param website
     */
    void insert(TdreamWebsite website);

    /**
     * 根据平台编号更新数据
     */
    String updateByUpdateDateTimeAndwebsiteId = "update t_dream_website\n" +
            "    set " +
            "      total_amount = #{totalAmount,jdbcType=DECIMAL},\n" +
            "      total_supportPeople = #{totalSupportpeople,jdbcType=INTEGER},\n" +
            "      total_products = #{totalProducts,jdbcType=INTEGER},\n" +
            "      average_finish = #{averageFinish,jdbcType=DECIMAL},\n" +
            "      amount_increase_day = #{amountIncreaseDay,jdbcType=DECIMAL}" +
            "    where website_id = #{websiteId,jdbcType=INTEGER}" +
            "   and  update_datetime = #{updateDatetime,jdbcType=TIMESTAMP}";
    @Update(updateByUpdateDateTimeAndwebsiteId)
    void updateByUpdateDateTimeAndwebsiteId(TdreamWebsite website);


    /**
     * 根据时间和平台编号查询众筹金额
     */
    String queryByUpdateDateTimeAndwebsiteId = "select total_amount,total_supportPeople," +
            "   total_products,average_finish,amount_increase_day  from t_dream_website " +
            "   where update_datetime = #{updateDatetime,jdbcType=TIMESTAMP}" +
            "   and website_id = #{websiteId,jdbcType=INTEGER}";

    @Select(queryByUpdateDateTimeAndwebsiteId)
    @Results({
            @Result(column = "total_amount",jdbcType = JdbcType.DECIMAL,property = "totalAmount"),
            @Result(column = "total_supportPeople",jdbcType = JdbcType.DECIMAL,property = "totalSupportpeople"),
            @Result(column = "total_products",jdbcType = JdbcType.DECIMAL,property = "totalProducts"),
            @Result(column = "average_finish",jdbcType = JdbcType.DECIMAL,property = "averageFinish"),
            @Result(column = "amount_increase_day",jdbcType = JdbcType.DECIMAL,property = "amountIncreaseDay"),
    })
    TdreamWebsite queryByUpdateDateTimeAndwebsiteId(@Param("websiteId")Integer websiteId, @Param("updateDatetime")Date updateDatetime);


    /**
     * 根据时间查询所有平台的数据
     */
    String queryByUpdateDateTime = "select website_id,website_name,total_amount,total_supportPeople," +
            "   total_products,average_finish,amount_increase_day  from t_dream_website " +
            "   where update_datetime = #{updateDatetime,jdbcType=TIMESTAMP}";
    @Select(queryByUpdateDateTime)
    @Results({
            @Result(column = "website_id",jdbcType = JdbcType.VARCHAR,property = "websiteId"),
            @Result(column = "website_name",jdbcType = JdbcType.VARCHAR,property = "websiteName"),
            @Result(column = "total_amount",jdbcType = JdbcType.DECIMAL,property = "totalAmount"),
            @Result(column = "total_supportPeople",jdbcType = JdbcType.INTEGER,property = "totalSupportpeople"),
            @Result(column = "total_products",jdbcType = JdbcType.INTEGER,property = "totalProducts"),
            @Result(column = "average_finish",jdbcType = JdbcType.DECIMAL,property = "averageFinish"),
            @Result(column = "amount_increase_day",jdbcType = JdbcType.DECIMAL,property = "amountIncreaseDay"),
    })
    List<TdreamWebsite> queryByUpdateDateTime(@Param("updateDatetime")Date updateDatetime);


    /**
     * 查询某个时间段内的每天的众筹金额增长
     */
    String queryByDateRange = "select website_id,amount_increase_day,update_datetime from  t_dream_website" +
            "   where update_datetime > #{updateDatetimeStart,jdbcType=TIMESTAMP}" +
            "   and update_datetime <= #{updateDatetimeEnd,jdbcType=TIMESTAMP}" +
            "   order by update_datetime ASC";
    @Select(queryByDateRange)
    @Results({
            @Result(column = "website_id",jdbcType = JdbcType.VARCHAR,property = "websiteId"),
            @Result(column = "amount_increase_day",jdbcType = JdbcType.DECIMAL,property = "amountIncreaseDay"),
            @Result(column = "update_datetime",jdbcType = JdbcType.TIMESTAMP,property = "updateDatetime"),
    })
    List<TdreamWebsite> queryByDateRange(@Param("updateDatetimeStart")Date updateDatetimeStart, @Param("updateDatetimeEnd")Date updateDatetimeEnd);


}
