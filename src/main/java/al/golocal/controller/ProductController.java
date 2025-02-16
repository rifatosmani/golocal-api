package al.golocal.controller;

import al.golocal.dto.ApiResponse;
import al.golocal.dto.ProductDto;
import al.golocal.service.ProductService;
import al.golocal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    private final UserService userService;

    @GetMapping()
    public ResponseEntity<ApiResponse<ProductDto>> getProductById(@RequestParam Long productId) {
        ApiResponse<ProductDto> apiResponse = new ApiResponse<ProductDto>(0,  productService.getProductById(productId), "");
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/site/{id}")
    public ResponseEntity<ApiResponse<List<ProductDto>>> getProductsBySite(@PathVariable Long id) {
        ApiResponse<List<ProductDto>> apiResponse = new ApiResponse<List<ProductDto>>(0, productService.getProductsBySite(id), "");
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductDto>> addProduct(@RequestBody ProductDto productDto) {
        ApiResponse<ProductDto> apiResponse = new ApiResponse<ProductDto>(0, productService.save(productDto), "");
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<ProductDto>> deleteProduct(@RequestParam Long productId) {
        productService.delete(productId);
        ApiResponse<ProductDto> apiResponse = new ApiResponse<>(0, null, "");
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/disable")
    public ResponseEntity<ApiResponse<ProductDto>> disableProduct(@RequestParam Long productId) {
        productService.disable(productId);
        ApiResponse<ProductDto> apiResponse = new ApiResponse<>(0, null, "");
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/enable")
    public ResponseEntity<ApiResponse<ProductDto>> enableProduct(@RequestParam Long productId) {
        productService.enable(productId);
        ApiResponse<ProductDto> apiResponse = new ApiResponse<>(0, null, "");
        return ResponseEntity.ok(apiResponse);
    }
}
