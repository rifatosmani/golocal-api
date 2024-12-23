package al.golocal.dto;

import al.golocal.entity.Address;
import lombok.Data;

import java.util.Date;

@Data
public class SiteDto {

    private Long siteId;

    private String name;

    private String description;

    private Long userId;

    private Long categoryId;

    private Integer status;

    private Address address;

    private Date addDate;

    private Date modDate;

}
