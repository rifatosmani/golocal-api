package al.golocal.service;

import al.golocal.dto.CategoryDto;
import al.golocal.dto.ProductDto;
import al.golocal.entity.Category;
import al.golocal.mapper.GenericMapper;
import al.golocal.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final GenericMapper genericMapper;

    public List<CategoryDto> getAllCategories() {
        List<Category> list = categoryRepository.findByParentIsNull();
        return genericMapper.toDtoList(list, CategoryDto.class);
    }

    public CategoryDto getCategoryById(Long id) {
        // Fetch the category from the database
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return genericMapper.toDto(categoryRepository.findById(id), CategoryDto.class);
    }
}
