package al.golocal.controller;

import al.golocal.service.storage.MinioStorageService;
import al.golocal.service.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import java.io.InputStream;

@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
public class StorageController {

    private final StorageService storageService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            storageService.uploadFile(file);
            return ResponseEntity.ok("File uploaded successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error uploading file: " + e.getMessage());
        }
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<InputStream> downloadFile(@PathVariable String fileName) {
        try {
            InputStream fileStream = storageService.downloadFile(fileName);
            return ResponseEntity.ok(fileStream);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
