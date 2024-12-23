package al.golocal.service.storage;

import io.minio.*;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;


@Service
public class MinioStorageService implements StorageService {

    private final MinioClient minioClient;

    @Value("${minio.url}")
    private String minioUrl;

    @Value("${minio.bucket}")
    private String bucketName;

    @Autowired
    public MinioStorageService(MinioClient minioClient) {
        this.minioClient = minioClient;
        this.bucketName = bucketName;
    }

    @Override
    public String uploadFile(MultipartFile file) throws Exception {
        // Get the current timestamp
        String timestamp = String.valueOf(Instant.now().toEpochMilli());

        // Get the original file name and append the timestamp
        String originalFileName = file.getOriginalFilename();
        String fileNameWithTimestamp = timestamp + "_" + originalFileName;

        String encodedFileName = URLEncoder.encode(fileNameWithTimestamp, StandardCharsets.UTF_8.toString());


        // Upload the file with the unique name
        InputStream inputStream = file.getInputStream();
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(encodedFileName) // File name in the bucket with timestamp
                        .stream(inputStream, file.getSize(), -1)
                        .build()
        );
        return bucketName + "/" +encodedFileName;
    }


    @Override
    public InputStream downloadFile(String fileName) throws Exception {
        return minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(fileName)  // The file you want to download
                        .build()
        );
    }
    @Override
    public void deleteFile(String url) throws Exception {
        try {
            String bucketName = url.substring(0, url.lastIndexOf("/"));
            String fileName = url.substring(url.lastIndexOf("/") + 1);
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .build()
            );
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public String getFullUrl(String fileName) {
            // Encode the fileName to handle special characters
            return minioUrl + "/" + fileName;
    }

}
