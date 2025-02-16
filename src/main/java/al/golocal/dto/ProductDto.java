package al.golocal.dto;

import al.golocal.entity.Media;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ProductDto {

    private Long productId;

    private String name;

    private String description;

    private Long siteId;

    private Long categoryId;

    private Double price;

    private Date addDate;

    private Date modDate;

    private Integer status;

    private List<Media> media;
}
