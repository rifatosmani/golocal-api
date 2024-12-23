package al.golocal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "media")
@Data
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long mediaId;

    @Column(nullable = false)
    private String url;

    private String fileName;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "productId")
    private Product product;

    @JsonProperty("productId")
    public Long getProductId() {
        return product != null ? product.getProductId() : null;
    }

    public void setProductId(Long productId) {
        if (productId == null) {
            this.product = null; // If productId is null, remove the association
        } else {
            // Fetch the Product entity from the database or create a proxy
            this.product = new Product();
            this.product.setProductId(productId); // Use this if you're using a detached object or JPA reference
        }
    }

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "site_id", referencedColumnName = "siteId")
    private Site site;

    @JsonProperty("siteId")
    public Long getSiteId() {
        return site != null ? site.getSiteId() : null;
    }

    public void setSiteId(Long siteId) {
        if (siteId == null) {
            this.site = null; // If productId is null, remove the association
        } else {
            // Fetch the Product entity from the database or create a proxy
            this.site = new Site();
            this.site.setSiteId(siteId); // Use this if you're using a detached object or JPA reference
        }
    }

    private Boolean main;

    @CreationTimestamp
    @Column(updatable = false)
    private Date addDate;

    @UpdateTimestamp
    @Column
    private Date modDate;

    private Integer status;

}
