package al.golocal.dto;

import lombok.Data;

import java.util.Date;

@Data
public class MediaDto {

    private Long mediaId;

    private String url;

    private String fileName;

    public Long refId;

    public String refTable;

    private Boolean main;

    private Date addDate;

    private Date modDate;

    private Integer status;

}