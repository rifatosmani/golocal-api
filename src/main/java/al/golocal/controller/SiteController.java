package al.golocal.controller;

import al.golocal.dto.ApiResponse;
import al.golocal.dto.SiteDto;
import al.golocal.entity.Site;
import al.golocal.service.SiteService;
import al.golocal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/site")
public class SiteController {
    private final SiteService siteService;

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SiteDto>> getSiteById(@PathVariable Long id) {
        ApiResponse<SiteDto> apiResponse = new ApiResponse<SiteDto>(0, siteService.getSiteById(id),"");
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<ApiResponse<List<SiteDto>>> getSiteByCategoryId(@PathVariable Long id) {
        ApiResponse<List<SiteDto>> apiResponse = new ApiResponse<List<SiteDto>>(0, siteService.getSiteByCategoryId(id),"");
        return ResponseEntity.ok(apiResponse);
    }
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<List<SiteDto>>> getSitesByUser() {
        ApiResponse<List<SiteDto>> apiResponse = new ApiResponse<List<SiteDto>>(0, siteService.getSiteByUser(),"");
        return ResponseEntity.ok(apiResponse);
    }
    @GetMapping()
    public ResponseEntity<ApiResponse<List<SiteDto>>> getAllSites() {
        ApiResponse<List<SiteDto>> apiResponse = new ApiResponse<List<SiteDto>>(0, siteService.getAllActiveSites(),"");
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<SiteDto>> addSite(@RequestBody SiteDto siteDto) {
        siteDto.setUserId(userService.getUserId());
        ApiResponse<SiteDto> apiResponse = new ApiResponse<SiteDto>(0, siteService.save(siteDto),"");

        return ResponseEntity.ok(apiResponse);
    }
    @DeleteMapping
    public ResponseEntity<ApiResponse<SiteDto>> deleteSite(@RequestParam Long siteId) {
        siteService.delete(siteId);
        ApiResponse<SiteDto> apiResponse = new ApiResponse<>(0,null,"");
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/disable")
    public ResponseEntity<ApiResponse<SiteDto>> disableSite(@RequestParam Long siteId) {
        siteService.disable(siteId);
        ApiResponse<SiteDto> apiResponse = new ApiResponse<>(0,null,"");
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/enable")
    public ResponseEntity<ApiResponse<SiteDto>> enableSite(@RequestParam Long siteId) {
        siteService.enable(siteId);
        ApiResponse<SiteDto> apiResponse = new ApiResponse<>(0,null,"");
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/locator")
    public ResponseEntity<ApiResponse<List<SiteDto>>> getSitesByLocation(@RequestParam("latitude") Double lat, @RequestParam("longitude") Double lon,@RequestParam("distance") Integer distance) {
        List<SiteDto> sites =  siteService.getSitesWithinRadius(lat, lon, distance);
        ApiResponse<List<SiteDto>> apiResponse = new ApiResponse<List<SiteDto>>(0, sites,"");
        return ResponseEntity.ok(apiResponse);
    }
}
