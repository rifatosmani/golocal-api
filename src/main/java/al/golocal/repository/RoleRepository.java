package al.golocal.repository;

import al.golocal.entity.Role;
import al.golocal.entity.RoleEnum;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
    Optional<Role> findByName(RoleEnum name);
    Optional<Role> findById(int id);

}