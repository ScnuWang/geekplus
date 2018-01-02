package cn.geekview.service.impl;


import cn.geekview.entity.mapper.primary.TdreamTbProduct_PrimaryMapper;
import cn.geekview.entity.mapper.secondary.TdreamTbProduct_SecondaryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("TdreamTbServiceImpl")
public class TdreamTbServiceImpl {

    @Autowired
    private TdreamTbProduct_PrimaryMapper productPrimaryMapper;

    @Autowired
    private TdreamTbProduct_SecondaryMapper productSecondaryMapper;










}
