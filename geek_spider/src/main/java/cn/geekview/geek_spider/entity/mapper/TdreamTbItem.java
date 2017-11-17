package cn.geekview.geek_spider.entity.mapper;

public interface TdreamTbItem {
    String insertRecordList = " insert into t_dream_tb_item (pk_id, product_id, original_item_id, \n" +
            "      item_title, item_desc, item_image, \n" +
            "      currency_sign, original_item_price, item_price, \n" +
            "      item_support, item_total, update_date, \n" +
            "      update_time, reserve1, reserve2, \n" +
            "      reserve3)\n" +
            "    values (#{pkId,jdbcType=INTEGER}, #{productId,jdbcType=INTEGER}, #{originalItemId,jdbcType=VARCHAR}, \n" +
            "      #{itemTitle,jdbcType=VARCHAR}, #{itemDesc,jdbcType=VARCHAR}, #{itemImage,jdbcType=VARCHAR}, \n" +
            "      #{currencySign,jdbcType=VARCHAR}, #{originalItemPrice,jdbcType=DECIMAL}, #{itemPrice,jdbcType=DECIMAL}, \n" +
            "      #{itemSupport,jdbcType=INTEGER}, #{itemTotal,jdbcType=INTEGER}, #{updateDate,jdbcType=TIMESTAMP}, \n" +
            "      #{updateTime,jdbcType=TIMESTAMP}, #{reserve1,jdbcType=VARCHAR}, #{reserve2,jdbcType=VARCHAR}, \n" +
            "      #{reserve3,jdbcType=VARCHAR})";
}
