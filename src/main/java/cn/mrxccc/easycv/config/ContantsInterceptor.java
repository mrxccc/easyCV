package cn.mrxccc.easycv.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mrxccc
 * @create 2020/12/18
 */
@Configuration
public class ContantsInterceptor implements HandlerInterceptor {
    private static final String HOST_CDN = "http://10.199.1.210/AdminLTE-3.1.0-rc/";

    private static final String TEMPLATE_ADMIN_LET = "adminlte/v2.4.3";

    private static final String ADMIN_LET_PATH = "/static/AdminLTE-2.4.3";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            modelAndView.addObject("adminlte", HOST_CDN );
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
