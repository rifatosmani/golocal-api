package al.golocal.repository;

import al.golocal.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    List<User> findByAddressCity(String city);
    List<User> findByAddressCountry(String country);
    List<User> findByAddressZipCode(String zipCode);
}