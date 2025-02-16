package al.golocal.mapper;

import al.golocal.dto.CategoryDto;
import al.golocal.dto.ProductDto;
import al.golocal.dto.SiteDto;
import al.golocal.entity.Category;
import al.golocal.entity.Site;
import al.golocal.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GenericMapper {

    private final MediaService mediaService;
    private final ModelMapper modelMapper;


    public <E, D> D toDto(E entity, Class<D> dtoClass) {
        D dto =modelMapper.map(entity, dtoClass);
        if (dtoClass.equals(CategoryDto.class)) {
            // Example: Fetch and set media list for CategoryDto
            CategoryDto categoryDto = (CategoryDto) dto;
            categoryDto.setMedia(mediaService.getMediaByRefId(((CategoryDto) dto).getCategoryId(), "category"));
        }else if(dtoClass.equals(SiteDto.class)){
            // Example: Fetch and set media list for CategoryDto
            SiteDto siteDto = (SiteDto) dto;
            siteDto.setMedia(mediaService.getMediaByRefId(((SiteDto) dto).getSiteId(), "site"));

        }else if(dtoClass.equals(ProductDto.class)){
            // Example: Fetch and set media list for CategoryDto
            ProductDto productDto = (ProductDto) dto;
            productDto.setMedia(mediaService.getMediaByRefId(((ProductDto) dto).getProductId(), "product"));

        }
        return dto;
    }

    public <E, D> E toEntity(D dto, Class<E> entityClass) {
        return modelMapper.map(dto, entityClass);
    }

    // Map a list of entities to a list of DTOs
    public <E, D> List<D> toDtoList(List<E> entities, Class<D> dtoClass) {
        return entities.stream()
                .map(entity -> {
                    D dto = modelMapper.map(entity, dtoClass);
                    if (dtoClass.equals(CategoryDto.class)) {
                        // Example: Fetch and set media list for CategoryDto
                        CategoryDto categoryDto = (CategoryDto) dto;
                        categoryDto.setMedia(mediaService.getMediaByRefId(((CategoryDto) dto).getCategoryId(), "category"));
                    }else if(dtoClass.equals(SiteDto.class)){
                        // Example: Fetch and set media list for CategoryDto
                        SiteDto siteDto = (SiteDto) dto;
                        siteDto.setMedia(mediaService.getMediaByRefId(((SiteDto) dto).getSiteId(), "site"));

                    }else if(dtoClass.equals(ProductDto.class)){
                        // Example: Fetch and set media list for CategoryDto
                        ProductDto productDto = (ProductDto) dto;
                        productDto.setMedia(mediaService.getMediaByRefId(((ProductDto) dto).getProductId(), "product"));

                    }

                    return dto;
                })
                .collect(Collectors.toList());
    }

    // Map a list of DTOs to a list of entities
    public <E, D> List<E> toEntityList(List<D> dtos, Class<E> entityClass) {
        return dtos.stream()
                .map(dto -> modelMapper.map(dto, entityClass))
                .collect(Collectors.toList());
    }
}
