package al.golocal.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "media")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long mediaId;

    @Column(nullable = false)
    private String url;

    private String fileName;

    public Long refId;

    public String refTable;

    private Boolean main;

    @CreationTimestamp
    @Column(updatable = false)
    private Date addDate;

    @UpdateTimestamp
    @Column
    private Date modDate;

    private Integer status;

}
