package cn.geekview.service.impl;

import cn.geekview.entity.mapper.primary.TdreamProduct_PrimaryMapper;
import cn.geekview.entity.mapper.primary.TdreamTbProduct_PrimaryMapper;
import cn.geekview.entity.model.TdreamProduct;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("TdreamProductServiceImpl")
public class TdreamProductServiceImpl {

    @Autowired
    private TdreamProduct_PrimaryMapper productPrimaryMapper;


    @Autowired
    private TdreamTbProduct_PrimaryMapper tbProductPrimaryMapper;


    /**
     * 插入或更新t_dream_product表
     * @param websiteId
     * @param originalId
     */
    public void insertOrUpdate(Integer websiteId,String originalId){
        //查询平台项目
        Object object = tbProductPrimaryMapper.queryByOriginalId(originalId);
        //Bean复制,排除pkId属性
        TdreamProduct product = new TdreamProduct();
        BeanUtils.copyProperties(object,product,"pkId");
        product.setWebsiteId(websiteId);
        //判断是否已经存在
        TdreamProduct result = productPrimaryMapper.queryByWebsiteIdAndOriginalId(websiteId,originalId);
        if (result==null){
            //插入数据库
            productPrimaryMapper.insert(product);
        }else {
            //更新数据库
            product.setPkId(result.getPkId());
            productPrimaryMapper.update(product);
        }
    }

}
