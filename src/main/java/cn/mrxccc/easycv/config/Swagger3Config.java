package cn.mrxccc.easycv.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author mrxccc
 * @create 2020/12/16
 */
@Configuration
public class Swagger3Config {

    @Bean
    public GroupedOpenApi createAllRestApi() {
        return GroupedOpenApi.builder()
                .group("ALL")
                .pathsToMatch("/**")
                .build();
    }

    @Bean
    public GroupedOpenApi createImageRestApi() {
        return GroupedOpenApi.builder()
                .group("图片管理")
                .pathsToMatch("/images/**")
                .build();
    }

    @Bean
    public GroupedOpenApi createRecordRestApi() {
        return GroupedOpenApi.builder()
                .group("录像管理")
                .pathsToMatch("/record/**")
                .build();
    }

}
