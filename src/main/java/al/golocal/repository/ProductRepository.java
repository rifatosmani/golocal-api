package al.golocal.repository;

import al.golocal.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Get product by name or description (case-insensitive search for search bar)
    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Product> findByNameOrDescriptionContainingIgnoreCase(@Param("keyword") String keyword);

    // Get products by category
    @Query("SELECT p FROM Product p WHERE p.category.categoryId = :categoryId")
    List<Product> findByCategoryId(@Param("categoryId") Long categoryId);

    @Query("SELECT p FROM Product p WHERE p.site.siteId = :siteId")
    List<Product> findProductsBySiteId(@Param("siteId") Long siteId);

    @Modifying
    @Query("UPDATE Product p SET p.status = 0 WHERE p.productId = :id")
    void disableById(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Product p SET p.status = 1 WHERE p.productId = :id")
    void enableById(@Param("id") Long id);
}
