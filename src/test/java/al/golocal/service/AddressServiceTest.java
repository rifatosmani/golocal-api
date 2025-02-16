package al.golocal.service;

import al.golocal.dto.AddressDto;
import al.golocal.entity.Address;
import al.golocal.mapper.GenericMapper;
import al.golocal.repository.AddressRepository;
import junit.framework.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {
    @Mock
    AddressRepository addressRepository;

    @Mock
    GenericMapper genericMapper;

    @InjectMocks
    AddressService addressService;

    @Test
    public void addressService_save_returnAddress(){
        //Arrange
        Address address = Address.builder()
                .address("test address")
                .city("test city")
                .country("test country")
                .latitude(0.0)
                .longitude(0.0)
                .zipCode("")
                .build();
        AddressDto addressDto = AddressDto.builder()
                .address("test address")
                .city("test city")
                .country("test country")
                .latitude(0.0)
                .longitude(0.0)
                .zipCode("")
                .build();

        when(addressRepository.save(Mockito.any(Address.class))).thenReturn(address);
        when(genericMapper.toEntity(Mockito.any(AddressDto.class),Mockito.eq(Address.class))).thenReturn(address);
        when(genericMapper.toDto(Mockito.any(Address.class),Mockito.eq(AddressDto.class))).thenReturn(addressDto);
        AddressDto savedAddress = addressService.save(addressDto);

        Assert.assertEquals(savedAddress.getAddress(),address.getAddress());
    }
}
