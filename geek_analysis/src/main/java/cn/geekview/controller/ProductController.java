package cn.geekview.controller;

import cn.geekview.service.impl.TdreamTbServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private TdreamTbServiceImpl tbService;

    /**
     *  新建一张总的产品表，平台总统计表，单个产品分析表用的时候加缓存（但是数据一直在变化，所以要解决缓存的有效性）
     *
     */


    /**
     *  插入产品总表：所有平台所有的产品保留最新的消息在这里面，目前全部加起来数据大概在2万多条数据
     */
    

    /**
     *  更新产品总表：由于是多线程，所以要数据库添加行锁
     */


}
