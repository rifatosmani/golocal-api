package al.golocal.dto;

import al.golocal.entity.Address;
import al.golocal.entity.Media;
import lombok.Data;

import java.util.Date;
import java.util.List;

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

    private List<Media> media;

    private String extDescription;

}
