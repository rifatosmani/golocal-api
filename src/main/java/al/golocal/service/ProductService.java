package al.golocal.service;

import al.golocal.dto.ProductDto;
import al.golocal.mapper.GenericMapper;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import al.golocal.repository.ProductRepository;
import al.golocal.entity.Product;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final GenericMapper genericMapper;

    public ProductDto getProductById(Long id) {
         return genericMapper.toDto(productRepository.findById(id), ProductDto.class);
    }
    public List<ProductDto> getProductsBySite(Long siteId) {
        return genericMapper.toDtoList(productRepository.findProductsBySiteId(siteId),ProductDto.class);
    }
    public ProductDto save(ProductDto productDto) {
        if(productDto.getStatus() == null) {
            productDto.setStatus(1);
        }
        try {
            Product p = genericMapper.toEntity(productDto, Product.class);
            productDto = genericMapper.toDto(productRepository.save(p),ProductDto.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return productDto;
    }

    @Transactional
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Transactional
    public void disable(Long id) {
        try {
            productRepository.disableById(id);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Transactional
    public void enable(Long id) {
        try {
            productRepository.enableById(id);
        }catch (Exception e){
            e.printStackTrace();
        }
    }



}
