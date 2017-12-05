package cn.geekview.geek_spider.entity.mapper;

import cn.geekview.geek_spider.entity.model.TdreamJdItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TdreamJdItemMapper {

    int insertRecordList(@Param("productId") Integer productId, @Param("itemList") List<TdreamJdItem> itemList);
}
