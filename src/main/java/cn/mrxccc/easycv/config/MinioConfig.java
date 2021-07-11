package cn.mrxccc.easycv.config;

import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@Data
//@Component
//@ConfigurationProperties(prefix = "minio")
public class MinioConfig {

    private String endpoint;

    private int port;

    private String accessKey;

    private String secretKey;

    private Boolean secure;

    private String bucketName;

    private String configDir;

    @Bean
    public MinioClient getMinioClient() throws InvalidEndpointException, InvalidPortException {
        MinioClient minioClient = MinioClient.builder().endpoint(endpoint, port, secure)
                .credentials(accessKey, secretKey)
                .build();
        return minioClient;
    }
}
