package cn.mrxccc.easycv.config;

import org.springframework.beans.BeansException;
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
public class MyProperties implements ApplicationContextAware {
    private String imageDirPath;

    public String getImageDirPath() {
        return imageDirPath;
    }

    public void setImageDirPath(String imageDirPath) {
        this.imageDirPath = imageDirPath;
    }

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
