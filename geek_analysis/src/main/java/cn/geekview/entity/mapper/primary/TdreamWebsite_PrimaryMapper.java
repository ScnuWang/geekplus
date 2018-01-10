package cn.geekview.entity.mapper.primary;


import cn.geekview.entity.model.TdreamWebsite;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;

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
    String updateDataByWebsiteId = "update t_dream_website\n" +
            "    set " +
            "       update_datetime = #{updateDatetime,jdbcType=TIMESTAMP}," +
            "      total_amount = #{totalAmount,jdbcType=DECIMAL},\n" +
            "      total_supportPeople = #{totalSupportpeople,jdbcType=INTEGER},\n" +
            "      total_products = #{totalProducts,jdbcType=INTEGER},\n" +
            "      average_finish = #{averageFinish,jdbcType=DECIMAL},\n" +
            "      amount_increase_day = #{amountIncreaseDay,jdbcType=DECIMAL}" +
            "    where website_id = #{websiteId,jdbcType=INTEGER}";

    @Update(updateDataByWebsiteId)
    void updateDataByWebsiteId(TdreamWebsite website);


    /**
     * 根据时间和平台编号查询众筹金额
     */
    String queryTotalAmountByUpdateDateTimeAndwebsiteId = "select total_amount from t_dream_website " +
            "   where update_datetime = #{updateDatetime,jdbcType=TIMESTAMP}" +
            "   and website_id = #{websiteId,jdbcType=INTEGER}";

    @Select(queryTotalAmountByUpdateDateTimeAndwebsiteId)
    @Results({
            @Result(column = "total_amount",jdbcType = JdbcType.DECIMAL,property = "totalAmount")
    })
    TdreamWebsite queryTotalAmountByUpdateDateTimeAndwebsiteId(@Param("websiteId")Integer websiteId, @Param("updateDatetime")Date updateDatetime);


}
