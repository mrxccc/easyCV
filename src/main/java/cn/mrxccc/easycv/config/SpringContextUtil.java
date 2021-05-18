package cn.mrxccc.easycv.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author caijiacheng
 * @create 2021/5/18
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {
    private static ApplicationContext ctx;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        ctx = applicationContext;
    }

    public static Object getBean(final String name) {
        return ctx.getBean(name);
    }

    public static <T> T getBean(final Class<T> clazz) {
        return ctx.getBean(clazz);
    }

    public static <T> T getBean(final String name, final Class<T> clazz) {
        return ctx.getBean(name, clazz);
    }
}
