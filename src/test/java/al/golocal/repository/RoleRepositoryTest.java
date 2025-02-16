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
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void RoleRepository_saveRole_returnRole(){
        //Arrange
        Role role = Role.builder()
                .name(RoleEnum.ADMIN)
                .description("admin")
                .build();
        //Act
        roleRepository.save(role);
        //Assert
        Assert.assertTrue(role.getRoleId() > 0);
    }

}
