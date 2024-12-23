package al.golocal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Table(name = "product")
@Entity
@Getter @Setter
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

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<Media> mediaList;

    Double price;

    @CreationTimestamp
    @Column(updatable = false)
    private Date addDate;

    @UpdateTimestamp
    @Column
    private Date modDate;

    private Integer status;

}
