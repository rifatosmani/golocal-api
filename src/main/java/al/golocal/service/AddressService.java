package al.golocal.service;

import al.golocal.dto.AddressDto;
import al.golocal.dto.CategoryDto;
import al.golocal.dto.ProductDto;
import al.golocal.dto.SiteDto;
import al.golocal.entity.Address;
import al.golocal.entity.Category;
import al.golocal.mapper.GenericMapper;
import al.golocal.repository.AddressRepository;
import al.golocal.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final GenericMapper genericMapper;

    public AddressDto getAddressById(Long id) {
        // Fetch the category from the database
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return genericMapper.toDto(addressRepository.findById(id), AddressDto.class);
    }

    public AddressDto save(AddressDto addressDto) {
        try{
            Address a = genericMapper.toEntity(addressDto, Address.class);
            addressDto = genericMapper.toDto(addressRepository.save(a), AddressDto.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return addressDto;
    }
}
