package com.teamsix.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class CaptchaConfig {
	
	@Bean(name="captchaProducer")
    public DefaultKaptcha getKaptchaBean(){
        DefaultKaptcha defaultKaptcha=new DefaultKaptcha();
        Properties properties=new Properties();
        //驗證碼字符範圍
        properties.setProperty("kaptcha.textproducer.char.string", "23456789");
        //圖片邊框顏色
        properties.setProperty("kaptcha.border.color", "245,248,249");
        //字體顏色
        properties.setProperty("kaptcha.textproducer.font.color", "black");
        //文字間格
        properties.setProperty("kaptcha.textproducer.char.space", "1");
        //圖片寬度
        properties.setProperty("kaptcha.image.width", "100");
        //圖片高度
        properties.setProperty("kaptcha.image.height", "35");
        //字體大小
        properties.setProperty("kaptcha.textproducer.font.size", "30");
        //session的key
        //properties.setProperty("kaptcha.session.key", "code");
        //長度
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        //字體
        properties.setProperty("kaptcha.textproducer.font.names", "宋體,楷體,微軟雅黑");
        Config config=new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }

	
}
