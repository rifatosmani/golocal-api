package al.golocal.service;

import al.golocal.entity.Category;
import al.golocal.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        // Fetch all categories from the database
        List<Category> allCategories = categoryRepository.findByParentIsNull();
        return allCategories;
    }

    public Category getCategoryById(Long id) {
        // Fetch the category from the database
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return category;
    }
}
