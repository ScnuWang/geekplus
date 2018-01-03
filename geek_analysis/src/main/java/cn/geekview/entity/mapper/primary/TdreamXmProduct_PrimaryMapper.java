package cn.geekview.entity.mapper.primary;

import cn.geekview.entity.model.TdreamXmProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TdreamXmProduct_PrimaryMapper {


    List<TdreamXmProduct> queryProductPriceTrend(@Param("crawlFrequence") Integer crawlFrequence, @Param("originalId") String orignalId);

    /**
     *  查询根据原始编号查询产品
     * @param orignalId 原始编号
     * @return
     */
    TdreamXmProduct queryByOriginalId(@Param("originalId") String orignalId);

    /**
     * 查询所有产品的最新数据
     */
    List<TdreamXmProduct> queryAllProduct();
}

