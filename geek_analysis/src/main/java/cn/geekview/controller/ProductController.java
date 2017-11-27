package cn.geekview.controller;

import cn.geekview.entity.model.TdreamTbProduct;
import cn.geekview.service.impl.TdreamTbServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private TdreamTbServiceImpl tbService;

    @RequestMapping(value = "/{crawlFrequence}/{originalId}",method = RequestMethod.GET)
    public String productTrend(@PathVariable("crawlFrequence")Integer crawlFrequence, @PathVariable("originalId")String originalId, Model model){
        List<TdreamTbProduct> products = tbService.queryProductPriceTrend(crawlFrequence,originalId);
        model.addAttribute("products",products);
        return "product";
    }
}
