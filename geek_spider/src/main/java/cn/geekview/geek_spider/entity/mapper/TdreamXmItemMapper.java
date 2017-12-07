package cn.geekview.geek_spider.entity.mapper;

import cn.geekview.geek_spider.entity.model.TdreamTbItem;
import cn.geekview.geek_spider.entity.model.TdreamXmItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TdreamXmItemMapper {

    int insertRecordList(@Param("productId") Integer productId, @Param("itemList") List<TdreamXmItem> itemList);
}
