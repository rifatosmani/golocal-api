package al.golocal.dto;

import lombok.Data;

import java.util.Date;

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

}
