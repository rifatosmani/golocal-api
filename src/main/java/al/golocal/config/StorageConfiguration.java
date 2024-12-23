package al.golocal.config;

import al.golocal.service.storage.MinioStorageService;
import al.golocal.service.storage.StorageService;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfiguration {

    @Value("${storage.type}")
    private String storageType;  // 'minio' or 'local'

    @Value("${minio.url}")
    private String minioUrl;
    @Value("${minio.bucket}")
    private String minioBucket;

    @Value("${minio.access-key}")
    private String accessKey;

    @Value("${minio.secret-key}")
    private String secretKey;

    @Value("${minio.bucket}")
    private String bucketName;


    @Bean
    public MinioClient minioClient() {
        try {
            return MinioClient.builder()
                    .endpoint(minioUrl)
                    .credentials(accessKey, secretKey)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create MinIO client", e);
        }
    }

    @Bean
    public StorageService storageService(MinioClient minioClient) {
        if (storageType.equalsIgnoreCase("minio")) {
            return new MinioStorageService(minioClient);
        }else return new MinioStorageService(minioClient);
    }
}
