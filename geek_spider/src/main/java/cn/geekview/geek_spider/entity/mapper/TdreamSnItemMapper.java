package cn.geekview.geek_spider.entity.mapper;

import cn.geekview.geek_spider.entity.model.TdreamSnItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TdreamSnItemMapper {

    int insertRecordList(@Param("productId") Integer productId, @Param("itemList") List<TdreamSnItem> itemList);
}
