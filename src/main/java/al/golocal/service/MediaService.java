package al.golocal.service;

import al.golocal.entity.Media;
import al.golocal.repository.MediaRepository;
import al.golocal.service.storage.StorageService;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MediaService {

    private final StorageService storageService;

    private final MediaRepository mediaRepository;

    @Value("${minio.bucket}")
    private String bucketName;

    private final Map<String, Boolean> mediaStatus = new HashMap<>(); // Simulated DB for statuses

    @Transactional
    public Media uploadFile(MultipartFile file,Long productId,Long siteId) {
        try (InputStream inputStream = file.getInputStream()) {
            String url = storageService.uploadFile(file);
            String fileName = file.getOriginalFilename();
            Media media = new Media();
            media.setFileName(fileName);
            media.setStatus(1);
            media.setProductId(productId);
            media.setSiteId(siteId);
            media.setUrl(url);
            mediaRepository.save(media);
            return media;
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload file", e);
        }
    }

    @Transactional
    public void deleteFile(Long mediaId) {
        Media media = mediaRepository.findByMediaId(mediaId);
        try {
            storageService.deleteFile(media.getUrl());
            mediaRepository.deleteById(mediaId);
        }catch (Exception e) {
            throw new RuntimeException("Failed to delete file", e);
        }
    }

    @Transactional
    public void disable(Long mediaId) {
        try {
            mediaRepository.disableById(mediaId);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void enable(Long mediaId) {
        try {
            mediaRepository.enableById(mediaId);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String prepareFullUrl(String fileName) {
        return storageService.getFullUrl(fileName);
    }
}
