package cn.mrxccc.easycv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author mrxccc
 * @create 2020/11/26
 */
@SpringBootApplication
@MapperScan(basePackages = "cn.mrxccc.easycv.mapper")
public class EasyCVApplication {
    public static void main(String[] args) {
        SpringApplication.run(EasyCVApplication.class, args);
    }
}
