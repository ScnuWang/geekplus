package cn.geekview.service.impl;

import cn.geekview.entity.mapper.primary.TdreamProduct_PrimaryMapper;
import cn.geekview.entity.mapper.primary.TdreamTbProduct_PrimaryMapper;
import cn.geekview.entity.model.BasicProduct;
import cn.geekview.entity.model.TdreamProduct;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("TdreamProductServiceImpl")
public class TdreamProductServiceImpl {

    @Autowired
    private TdreamProduct_PrimaryMapper productPrimaryMapper;


    /**
     * 插入或更新t_dream_product表
     * @param websiteId
     * @param resourceProduct 平台产品对象
     */
    public void insertOrUpdate(Integer websiteId,BasicProduct resourceProduct){
        //Bean复制,排除pkId属性
        TdreamProduct product = new TdreamProduct();
        BeanUtils.copyProperties(resourceProduct,product,"pkId");
        product.setWebsiteId(websiteId);
        //判断是否已经存在
        TdreamProduct resultProduct = productPrimaryMapper.queryByWebsiteIdAndOriginalId(websiteId,product.getOriginalId());
        if (resultProduct==null){
            //插入数据库
            productPrimaryMapper.insert(product);
        }else {
            //更新数据库：需要对比一下时间，避免旧的数据覆盖最新的数据
            if (resultProduct.getUpdateDatetime().getTime()<product.getUpdateDatetime().getTime()){
                product.setPkId(resultProduct.getPkId());
                productPrimaryMapper.update(product);
            }
        }
    }

}
