package cn.geekview.entity.mapper.primary;

import cn.geekview.entity.model.TdreamProduct;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

public interface TdreamProduct_PrimaryMapper {

    /**
     *  根据平台编号查询指定众筹状态的产品个数
     * @param websiteId
     * @param statusValue
     * @return
     */
    Integer  queryProductsByWebsiteIdAndStatus(@Param("websiteId")Integer websiteId,@Param("statusValue")Integer statusValue);



    /**
     *  更新数据
     *
     */
    String update = "update t_dream_product set " +
            "   status_value = #{statusValue,jdbcType=INTEGER}," +
            "   product_status = #{productStatus,jdbcType=VARCHAR}," +
            "   support_count = #{supportCount,jdbcType=INTEGER}," +
            "   focus_count = #{focusCount,jdbcType=INTEGER}," +
            "   rasied_amount = #{rasiedAmount,jdbcType=DECIMAL}," +
            "   finish_percent = #{finishPercent,jdbcType=INTEGER}," +
            "   remain_day = #{remainDay,jdbcType=INTEGER}" +
            "   where pk_id = #{pkId,jdbcType=INTEGER}";
    @Update(update)
    void update(TdreamProduct product);





    /**
     *  插入一条数据
     */
    String insert ="insert into t_dream_product (pk_id, website_id, original_id, \n" +
            "      status_value, product_status, product_name, \n" +
            "      product_desc, product_url, product_image, \n" +
            "      product_video, product_qrcode, begin_date, \n" +
            "      end_date, update_datetime, forever_value, \n" +
            "      support_count, focus_count, currency_sign, \n" +
            "      target_amount, rasied_amount, finish_percent, \n" +
            "      remain_day, person_name, person_desc, \n" +
            "      person_image, reserve1, \n" +
            "      reserve2, reserve3)\n" +
            "    values (#{pkId,jdbcType=INTEGER}, #{websiteId,jdbcType=INTEGER}, #{originalId,jdbcType=VARCHAR}, \n" +
            "      #{statusValue,jdbcType=INTEGER}, #{productStatus,jdbcType=VARCHAR}, #{productName,jdbcType=VARCHAR}, \n" +
            "      #{productDesc,jdbcType=VARCHAR}, #{productUrl,jdbcType=VARCHAR}, #{productImage,jdbcType=VARCHAR}, \n" +
            "      #{productVideo,jdbcType=VARCHAR}, #{productQrcode,jdbcType=VARCHAR}, #{beginDate,jdbcType=TIMESTAMP}, \n" +
            "      #{endDate,jdbcType=TIMESTAMP}, #{updateDatetime,jdbcType=TIMESTAMP}, #{foreverValue,jdbcType=INTEGER}, \n" +
            "      #{supportCount,jdbcType=INTEGER}, #{focusCount,jdbcType=INTEGER}, #{currencySign,jdbcType=VARCHAR}, \n" +
            "      #{targetAmount,jdbcType=DECIMAL}, #{rasiedAmount,jdbcType=DECIMAL}, #{finishPercent,jdbcType=DECIMAL}, \n" +
            "      #{remainDay,jdbcType=INTEGER}, #{personName,jdbcType=VARCHAR}, #{personDesc,jdbcType=VARCHAR}, \n" +
            "      #{personImage,jdbcType=VARCHAR}, #{reserve1,jdbcType=VARCHAR}, \n" +
            "      #{reserve2,jdbcType=VARCHAR}, #{reserve3,jdbcType=VARCHAR})";

    @Insert(insert)
    void insert(TdreamProduct product);





    /**
     * 根据平台编号以及原始编号判断是否已经存在
     */
    String queryByWebsiteIdAndOriginalId = "Select pk_id,original_id,update_datetime  from t_dream_product " +
            "   where website_id = #{websiteId,jdbcType=INTEGER}" +
            "   and  original_id = #{originalId,jdbcType=VARCHAR}" +
            "   limit 1";

    @Select(queryByWebsiteIdAndOriginalId)
    @Results({
            @Result(column = "pk_id",property = "pkId",jdbcType = JdbcType.INTEGER),
            @Result(column = "original_id",property = "originalId",jdbcType = JdbcType.INTEGER),
            @Result(column = "update_datetime",property = "updateDatetime",jdbcType = JdbcType.INTEGER),
    })
    TdreamProduct queryByWebsiteIdAndOriginalId(@Param("websiteId") Integer websiteId, @Param("originalId") String originalId);

}
