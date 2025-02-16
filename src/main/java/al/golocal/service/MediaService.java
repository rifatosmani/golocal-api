package al.golocal.service;

import al.golocal.dto.MediaDto;
import al.golocal.entity.Media;
import al.golocal.mapper.GenericMapper;
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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class MediaService {

    private final StorageService storageService;

    private final MediaRepository mediaRepository;

    @Value("${minio.bucket}")
    private String bucketName;

    private final Map<String, Boolean> mediaStatus = new HashMap<>(); // Simulated DB for statuses

    @Transactional
    public Media uploadFile(MultipartFile file,Long refId, String refTable,Boolean main,String filename) {
        try (InputStream inputStream = file.getInputStream()) {
            String url = storageService.uploadFile(file);
            Media media = new Media();
            media.setRefTable(refTable);
            media.setRefId(refId);
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            media.setFileName(filename);
            media.setUrl(url);
            media.setMain(main);
            media.setStatus(1);
            media = mediaRepository.save(media);
            return media;
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload file", e);
        }
    }

    @Transactional
    public void deleteFile(Long mediaId) {
        Media media = mediaRepository.findById(mediaId).get();
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

    public void deleteMainMedia(Long refId,String refTable){
        mediaRepository.getMediaByRefIdAndRefTableAndMainIsTrue(refId,refTable).stream().forEach(med->{
            deleteFile(med.getMediaId());
        });
    }

    public String prepareFullUrl(String fileName) {
        return storageService.getFullUrl(fileName);
    }

    public List<Media> getMediaByRefId(Long refId, String refTable){
        List<Media> l = mediaRepository.findByRefIdAndRefTable(refId,refTable);

        return l.stream().map(element -> {
            String url = element.getUrl();
            String fullUrl = prepareFullUrl(url);
            element.setUrl(fullUrl);
            return element;
        }).collect(Collectors.toList());
    }
}
