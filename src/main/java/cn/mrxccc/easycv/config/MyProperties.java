package cn.mrxccc.easycv.config;

import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;

/**
 * @author mrxccc
 * @create 2020/12/18
 */
@Component
@Data
public class MyProperties implements ApplicationContextAware {
    private String imageDirPath;

    @Value("${easycv.rtsp.play-url}")
    private String rtspPlayUrl;

    @Value("${easycv.easy-darwin.url}")
    private String easyDarwinUrl;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        String imagePath = applicationContext.getEnvironment().getProperty("easycv.image-path");
        if (StringUtils.isEmpty(imagePath)) {
            setImageDirPath(System.getProperty("java.io.tmpdir") + "easycvImage" + File.separator);
        } else {
            setImageDirPath(imagePath);
        }
    }
}
