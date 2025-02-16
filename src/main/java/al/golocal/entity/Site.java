package al.golocal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import java.util.Date;

@Table(name = "site")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Site {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long siteId;

    private String name;

    private String description;

    private Long userId;

    /*
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "categoryId")
    private Category category;

    @JsonProperty("categoryId")
    public Long getCategoryId() {
        return category != null ? category.getCategoryId() : null;
    }
    */

    private Long categoryId;
    @OneToOne
    @JoinColumn(name = "address_id", referencedColumnName = "addressId", nullable = true)
    private Address address;

    private Integer status;

    @CreationTimestamp
    @Column(updatable = false)
    private Date addDate;

    @UpdateTimestamp
    private Date modDate;

    private String extDescription;
}
