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

    @PostMapping
    public ResponseEntity<ApiResponse<Media>> uploadMedia(@RequestParam("file") MultipartFile file,@RequestParam(value = "refId",required = false) Long refId, @RequestParam(value = "refTable",required = false) String refTable, @RequestParam(value = "main",required = false) Boolean main,@RequestParam(value = "filename",required = false) String filename) {
        if(main == true){
            this.mediaService.deleteMainMedia(refId,refTable);
        }
        Media media = mediaService.uploadFile(file,refId,refTable,main,filename);
        media.setUrl(mediaService.prepareFullUrl(media.getUrl()));
        ApiResponse<Media> apiResponse = new ApiResponse<>(0, media, "Media uploaded successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteMedia(@PathVariable Long id) {
        try {
            mediaService.deleteFile(id);
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
