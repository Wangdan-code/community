package com.nowcoder.community.config;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KaptchaConfig {
    //Producer是Kaptcha的核心接口
    @Bean
    public Producer kaptchaProducer(){
        //Spring boot没有为Kaptcha提供自动配置 需要手动设置
        Properties properties = new Properties();
        properties.setProperty("kaptcha.image.width","100");
        properties.setProperty("kaptcha.image.hight","40");
        properties.setProperty("kaptcha.textproducer.font.size","32");
        properties.setProperty("kaptcha.textproducer.font.color","0,0,0");//黑色
        properties.setProperty("kaptcha.textproducer.char.string","0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        properties.setProperty("kaptcha.textproducer.char.length","4");
        properties.setProperty("kaptcha.textproducer.noise.impl","com.google.code.kaptcha.impl.NoNoise");//生成的图片上的噪音干扰选项

        DefaultKaptcha kaptcha = new DefaultKaptcha();//核心接口的默认实现类
        Config config = new Config(properties);
        kaptcha.setConfig(config);
        return kaptcha;
    }
}
