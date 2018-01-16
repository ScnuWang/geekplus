package cn.geekview.entity.mapper.primary;

import cn.geekview.entity.model.TdreamTbProduct;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.cache.annotation.Cacheable;

import java.util.Date;
import java.util.List;

public interface TdreamTbProduct_PrimaryMapper {


    List<TdreamTbProduct> queryProductPriceTrend(@Param("crawlFrequence") Integer crawlFrequence, @Param("originalId") String orignalId);

    /**
     *  查询根据原始编号查询产品
     * @param orignalId 原始编号
     * @return
     */
    TdreamTbProduct queryByOriginalId(@Param("originalId") String orignalId);

    /**
     * 查询某个时间点正在抓取的产品的最新数据
     */
    List<TdreamTbProduct> queryProduct_Newest(@Param("beforeTime")Date beforeTime,@Param("afterTime")Date afterTime);
}

