package al.golocal.service.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface StorageService {
    String uploadFile(MultipartFile file) throws Exception;
    InputStream downloadFile(String fileName) throws Exception;
    void deleteFile(String url) throws Exception;
    String getFullUrl(String fileName);
    // Add more methods as needed
}
