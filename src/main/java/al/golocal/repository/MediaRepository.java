package al.golocal.repository;

import al.golocal.entity.Media;
import al.golocal.entity.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {

    List<Media> findByRefIdAndRefTable(Long refId,String refTable);

    @Modifying
    @Query("UPDATE Media m SET m.status = 0 WHERE m.mediaId = :id")
    void disableById(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Media m SET m.status = 1 WHERE m.mediaId = :id")
    int enableById(@Param("id") Long id);

    List<Media> getMediaByRefIdAndRefTableAndMainIsTrue(Long refId, String refTable);
    }
