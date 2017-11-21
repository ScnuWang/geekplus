package cn.geekview.geek_spider.entity.mapper;

import cn.geekview.geek_spider.entity.domain.TdreamTbItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TdreamTbItemMapper {

    int insertRecordList(@Param("productId")Integer productId, @Param("itemList")List<TdreamTbItem> itemList);
}
