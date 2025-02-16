package al.golocal.repository;

import al.golocal.entity.Category;
import al.golocal.entity.Product;
import al.golocal.entity.Site;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ActiveProfiles("test")
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void productRepository_saveProduct_returnProduct(){
        //Arrange
        Product product = Product.builder()
                .name("test")
                .category(Category.builder().categoryId(1L).build())
                .site(Site.builder().siteId(1L).build())
                .build();
        //Act
        productRepository.save(product);
        //Assert
        Assert.assertTrue(product.getProductId() > 0);
    }

}
