package al.golocal.repository;

import al.golocal.entity.*;
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
public class SiteRepositoryTest {

    @Autowired
    private SiteRepository siteRepository;

    @Test
    public void SiteRepository_saveSite_returnSite(){
        //Arrange
        Site site = Site.builder()
                .categoryId(1L)
                .name("test").build();
        //Act
        siteRepository.save(site);
        //Assert
        Assert.assertTrue(site.getSiteId() > 0);
    }

}
