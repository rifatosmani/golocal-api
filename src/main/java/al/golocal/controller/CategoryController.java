package al.golocal.controller;

import al.golocal.dto.ApiResponse;
import al.golocal.dto.CategoryDto;
import al.golocal.dto.ProductDto;
import al.golocal.entity.Category;
import al.golocal.entity.User;
import al.golocal.repository.CategoryRepository;
import al.golocal.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    // Get all categories and return them as a category tree
    @GetMapping
    public ApiResponse<List<CategoryDto>> getCategoryTree() {
        return new ApiResponse<List<CategoryDto>>(0, categoryService.getAllCategories(), "");
    }

    // Get category by ID
    @GetMapping("/{id}")
    public ApiResponse<CategoryDto> getCategoryById(@PathVariable Long id) {
        return new ApiResponse<CategoryDto>(0,categoryService.getCategoryById(id),"");
    }

}

