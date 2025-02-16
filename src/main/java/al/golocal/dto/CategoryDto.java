package al.golocal.dto;

import al.golocal.entity.Media;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CategoryDto {
    private Long categoryId;

    private String name;

    private String description;

    private Date addDate;

    private Date modDate;

    private int status;

    List<Media> media;
}
