package cn.geekview.controller;

import cn.geekview.entity.model.TdreamWebsite;
import cn.geekview.service.impl.TdreamWebsiteServiceImpl;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private TdreamWebsiteServiceImpl websiteService;

    @GetMapping("/allwebsite")
    public List<TdreamWebsite> allWebsite(){
        DateTime dateTime = new DateTime(DateTime.now().getYear(),DateTime.now().getMonthOfYear(),DateTime.now().getDayOfMonth(),12,0,0);
        return websiteService.queryByUpdateDateTime(dateTime.toDate());
    }



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
