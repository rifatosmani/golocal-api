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
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void userRepository_saveUser_returnUser(){
        //Arrange
        User user = User.builder()
                .email("test")
                .password("")
                .firstName("test")
                .role(Role.builder().roleId(1L).name(RoleEnum.ADMIN).description("admin")
                        .build())
                .lastName("test")
                .username("test").build();
        //Act
        userRepository.save(user);
        //Assert
        Assert.assertTrue(user.getUserId() > 0);
    }

}
