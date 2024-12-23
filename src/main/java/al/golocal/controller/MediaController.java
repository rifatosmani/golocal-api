package al.golocal.controller;

import al.golocal.dto.ApiResponse;
import al.golocal.entity.Media;
import al.golocal.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/media")
public class MediaController {
    private final MediaService mediaService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<Media>> uploadMedia(@RequestParam("file") MultipartFile file,@RequestParam(value = "productId",required = false) Long productId, @RequestParam(value = "siteId",required = false) Long siteId) {
        Media media = mediaService.uploadFile(file,productId,siteId);
        media.setUrl(mediaService.prepareFullUrl(media.getUrl()));
        ApiResponse<Media> apiResponse = new ApiResponse<>(0, media, "Media uploaded successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<String>> deleteMedia(@RequestParam("mediaId") Long mediaId) {
        try {
            mediaService.deleteFile(mediaId);
        }catch (Exception e){
            e.printStackTrace();
        }
        ApiResponse<String> apiResponse = new ApiResponse<>(0, null, "Media deleted successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/disable")
    public ResponseEntity<ApiResponse<String>> disableMedia(@RequestParam("mediaId") Long mediaId) {
        mediaService.disable(mediaId);
        ApiResponse<String> apiResponse = new ApiResponse<>(0, null, "Media disabled successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/enable")
    public ResponseEntity<ApiResponse<String>> enableMedia(@RequestParam("mediaId") Long mediaId) {
        mediaService.enable(mediaId);
        ApiResponse<String> apiResponse = new ApiResponse<>(0, null, "Media enabled successfully");
        return ResponseEntity.ok(apiResponse);
    }
}
