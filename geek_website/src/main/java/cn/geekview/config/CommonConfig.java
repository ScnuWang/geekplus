package cn.geekview.config;

import cn.geekview.entity.model.TdreamReturnObject;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class CommonConfig {

    @Bean
    public TdreamReturnObject returnObject(){
        return new TdreamReturnObject();
    }
}
