package al.golocal.repository;

import al.golocal.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    // Custom query method to find addresses by city
    List<Address> findByCity(String city);

    // Custom query method to find addresses by country
    List<Address> findByCountry(String country);

    // Custom query method to find addresses by zip code
    List<Address> findByZipCode(String zipCode);

}
