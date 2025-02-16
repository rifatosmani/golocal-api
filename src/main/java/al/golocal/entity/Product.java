package al.golocal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Table(name = "product")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long productId;

    private String name;

    private String description;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "site_id", referencedColumnName = "siteId", nullable = false)
    private Site site;

    @JsonProperty("siteId")
    public Long getSiteId() {
        return site != null ? site.getSiteId() : null;
    }

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "categoryId", nullable = false)
    private Category category;

    @JsonProperty("categoryId")
    public Long getCategoryId() {
        return category != null ? category.getCategoryId() : null;
    }

    Double price;

    @CreationTimestamp
    @Column(updatable = false)
    private Date addDate;

    @UpdateTimestamp
    @Column
    private Date modDate;

    private Integer status;

}
