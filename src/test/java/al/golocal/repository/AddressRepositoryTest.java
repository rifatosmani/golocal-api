package al.golocal.repository;

import al.golocal.entity.Address;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;


@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ActiveProfiles("test")
public class AddressRepositoryTest {

    @Autowired
    private AddressRepository addressRepository;

    @Test
    public void addressRepository_saveAddress_returnAddress(){
        //Arrange
        Address address = Address.builder()
                .address("test address")
                .city("test city")
                .country("test country")
                .latitude(0.0)
                .longitude(0.0)
                .zipCode("")
                .build();
        //Act
        addressRepository.save(address);

        //Assert
        Assertions.assertThat(address.getAddressId()).isGreaterThan(0);
    }

    @Test
    public void addressRepository_updateAddress_returnAddress(){
        //Arrange
        Address address = Address.builder()
                .address("test address")
                .city("test city")
                .country("test country")
                .latitude(0.0)
                .longitude(0.0)
                .zipCode("")
                .build();
        //Act
        addressRepository.save(address);
        Address savedAddress = addressRepository.findById(address.getAddressId()).get();
        savedAddress.setZipCode("1");
        Address updatedAddress = addressRepository.save(savedAddress);

        //Assert
        Assertions.assertThat(updatedAddress.getZipCode()).isEqualTo("1");
    }

    @Test
    public void addressRepository_deleteAddress_returnAddress(){
        //Arrange
        Address address = Address.builder()
                .address("test address")
                .city("test city")
                .country("test country")
                .latitude(0.0)
                .longitude(0.0)
                .zipCode("")
                .build();
        //Act
        addressRepository.save(address);
        addressRepository.deleteById(address.getAddressId());

        Optional<Address> address1 = addressRepository.findById(address.getAddressId());
        //Assert
        Assertions.assertThat(address1).isEmpty();
    }
}
