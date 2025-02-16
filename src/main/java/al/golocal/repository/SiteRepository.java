package al.golocal.repository;
import al.golocal.entity.Site;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SiteRepository extends CrudRepository<Site, Long>{

    // Get products by category
    @Query("SELECT s FROM Site s WHERE s.userId = :userId")
    List<Site> findByUserId(@Param("userId") Long userId);

    List<Site> findSiteByCategoryId(Long categoryId);

    List<Site> findSiteByStatus(Integer status);

    @Modifying
    @Query("UPDATE Site s SET s.status = 0 WHERE s.siteId = :id")
    void disableById(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Site s SET s.status = 1 WHERE s.siteId = :id")
    void enableById(@Param("id") Long id);



}