package cn.mrxccc.easycv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author mrxccc
 * @create 2020/11/26
 */
@SpringBootApplication
@MapperScan(basePackages = "cn.mrxccc.easycv.mapper")
@EnableCaching
public class EasyCVApplication {
    public static void main(String[] args) {
        SpringApplication.run(EasyCVApplication.class, args);
    }
}
