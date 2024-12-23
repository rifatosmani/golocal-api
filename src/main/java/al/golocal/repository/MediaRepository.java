package al.golocal.repository;

import al.golocal.entity.Media;
import al.golocal.entity.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {

    Media findByMediaId(Long mediaId);

    @Query("SELECT m FROM Media m WHERE m.product.productId = :productId")
    List<Media> findByProductId(@Param("productId") Long productId);

    @Modifying
    @Query("UPDATE Media m SET m.status = 0 WHERE m.mediaId = :id")
    void disableById(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Media m SET m.status = 1 WHERE m.mediaId = :id")
    void enableById(@Param("id") Long id);
}
