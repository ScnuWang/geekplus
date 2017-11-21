package cn.geekview.geek_spider.entity.mapper;

import cn.geekview.geek_spider.entity.domain.TdreamTbProduct;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

public interface TdreamTbProductMapper {
    String insert = "insert into t_dream_tb_product (pk_id, original_id, crawl_frequency, \n" +
            "      product_name, product_desc, product_url, \n" +
            "      product_image, product_video, product_qrcode, \n" +
            "      begin_date, end_date, update_datetime, \n" +
            "      product_status, status_value, forever_value, \n" +
            "      focus_count, support_count, currency_sign, \n" +
            "      original_target_amount, original_rasied_amount, \n" +
            "      target_amount, rasied_amount, finish_percent, \n" +
            "      remain_day, person_name, person_desc, \n" +
            "      person_image)\n" +
            "    values (#{pkId,jdbcType=INTEGER}, #{originalId,jdbcType=VARCHAR}, #{crawlFrequency,jdbcType=INTEGER}, \n" +
            "      #{productName,jdbcType=VARCHAR}, #{productDesc,jdbcType=VARCHAR}, #{productUrl,jdbcType=VARCHAR}, \n" +
            "      #{productImage,jdbcType=VARCHAR}, #{productVideo,jdbcType=VARCHAR}, #{productQrcode,jdbcType=VARCHAR}, \n" +
            "      #{beginDate,jdbcType=TIMESTAMP}, #{endDate,jdbcType=TIMESTAMP}, #{updateDatetime,jdbcType=TIMESTAMP}, \n" +
            "      #{productStatus,jdbcType=VARCHAR}, #{statusValue,jdbcType=INTEGER}, #{foreverValue,jdbcType=INTEGER}, \n" +
            "      #{focusCount,jdbcType=INTEGER}, #{supportCount,jdbcType=INTEGER}, #{currencySign,jdbcType=VARCHAR}, \n" +
            "      #{originalTargetAmount,jdbcType=DECIMAL}, #{originalRasiedAmount,jdbcType=DECIMAL}, \n" +
            "      #{targetAmount,jdbcType=DECIMAL}, #{rasiedAmount,jdbcType=DECIMAL}, #{finishPercent,jdbcType=INTEGER}, \n" +
            "      #{remainDay,jdbcType=INTEGER}, #{personName,jdbcType=VARCHAR}, #{personDesc,jdbcType=VARCHAR}, \n" +
            "      #{personImage,jdbcType=VARCHAR})";
    @Insert(insert)
    @Options(useGeneratedKeys = true,keyProperty = "pkId")//要利用返回的主键，需要指明这两个属性
    int insert(TdreamTbProduct product);

}
