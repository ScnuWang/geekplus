package cn.geekview.entity.mapper.primary;


import cn.geekview.entity.model.TdreamWebsite;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface TdreamWebsite_PrimaryMapper {

    /**
     * 根据平台编号查询
     */
    TdreamWebsite queryWebsiteDataByWebsiteId(@Param("websiteId")Integer websiteId);


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

}
