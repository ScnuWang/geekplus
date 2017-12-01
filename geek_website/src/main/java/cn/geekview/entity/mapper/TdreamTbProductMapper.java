package cn.geekview.entity.mapper;

import cn.geekview.entity.model.TdreamTbProduct;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TdreamTbProductMapper {

    String queryProductPriceTrend = "SELECT original_id,product_name,product_url,update_datetime,product_status,status_value,focus_count,support_count,target_amount,rasied_amount\n" +
            "      FROM t_dream_tb_product\n" +
            "      WHERE\n" +
            "        crawl_frequency = #{crawlFrequence,jdbcType=INTEGER}\n" +
            "      AND\n" +
            "        original_id = #{originalId,jdbcType=VARCHAR}";

    @Select(queryProductPriceTrend)
    List<TdreamTbProduct> queryProductPriceTrend(@Param("crawlFrequence") Integer crawlFrequence, @Param("originalId") String orignalId);
}
