package al.golocal.repository;

import al.golocal.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    // Custom query method to find addresses by city
    List<Address> findByCity(String city);

    // Custom query method to find addresses by country
    List<Address> findByCountry(String country);

    // Custom query method to find addresses by zip code
    List<Address> findByZipCode(String zipCode);

}
