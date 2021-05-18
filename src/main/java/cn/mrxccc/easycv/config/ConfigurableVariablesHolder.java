package cn.mrxccc.easycv.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author caijiacheng
 * @create 2021/5/18
 */
@Getter
@Component
public class ConfigurableVariablesHolder {
    @Value("${easycv.cdn-url}")
    private String cdnUrl;
}
