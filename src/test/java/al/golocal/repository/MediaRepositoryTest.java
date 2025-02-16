package al.golocal.repository;

import al.golocal.entity.Media;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;


@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ActiveProfiles("test")
class MediaRepositoryTest {

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private EntityManager entityManager;  // Inject EntityManager

    @Test
    public void mediaRepository_saveAll_returnSavedMedia(){
        //Arrange
        Media media = Media.builder()
                .fileName("test.png")
                .refId(1L)
                .url("http://...")
                .refTable("site").build();
        //Act
        Media savedMedia = mediaRepository.save(media);

        //Assert
        Assertions.assertThat(savedMedia).isNotNull();
        Assertions.assertThat(savedMedia.getMediaId()).isGreaterThan(0);
    }

    @Test
    public void mediaRepository_findAll_returnsMoreThanOne(){
        //Arrange
        Media m1 = Media.builder()
                .fileName("test.png")
                .refId(1L)
                .refTable("site")
                .url("http://...").build();
        Media m2 = Media.builder()
                .fileName("tes2.png")
                .refId(1L)
                .refTable("site")
                .url("http://...").build();
        //Act
        mediaRepository.save(m1);
        mediaRepository.save(m2);

        //Assert
        Assertions.assertThat(mediaRepository.findAll()).isNotNull();
        Assertions.assertThat(mediaRepository.findAll().size()).isEqualTo(2);
        Assertions.assertThat(mediaRepository.findAll().get(0).getMediaId()).isGreaterThan(0);
    }

    @Test
    public void mediaRepository_findById_returnMedia(){
        //Arrange
        Media m1 = Media.builder()
                .fileName("test.png")
                .refId(1L)
                .refTable("site")
                .url("http://...").build();

        //Act
        mediaRepository.save(m1);

        //Assert
        Assertions.assertThat(mediaRepository.findById(m1.getMediaId())).isNotEmpty();
    }

    @Test
    public void mediaRepository_findByRefIdAndRefTable_returnMedia(){
        //Arrange
        Media m1 = Media.builder()
                .fileName("test.png")
                .refId(1L)
                .refTable("site")
                .url("http://...").build();
        //Act
        mediaRepository.save(m1);
        List<Media> mediaList = mediaRepository.findByRefIdAndRefTable(1L,"site");

        //Assert
        Assertions.assertThat(mediaList.size()).isEqualTo(1);
    }

    @Test
    public void mediaRepository_enableById_returnUpdatedMedia(){
        //Arrange
        Media m1 = Media.builder()
                .fileName("test.png")
                .refId(1L)
                .refTable("site")
                .status(0)
                .url("http://...").build();
        //Act
        mediaRepository.save(m1);
        int res = mediaRepository.enableById(m1.getMediaId());
        entityManager.refresh(m1);

        //Assert
        Assertions.assertThat(res).isEqualTo(1);
        Assertions.assertThat(m1.getStatus()).isEqualTo(1);    }

    @Test
    public void mediaRepository_getMediaByRefIdAndRefTableAndMainIsTrue_returnMedia(){
        //Arrange
        Media m1 = Media.builder()
                .fileName("test.png")
                .refId(1L)
                .refTable("site")
                .status(0)
                .main(true)
                .url("http://...").build();

        //Act
        mediaRepository.save(m1);
        List<Media> mediaList  = mediaRepository.getMediaByRefIdAndRefTableAndMainIsTrue(1L,"site");

        //Assert
        Assertions.assertThat(mediaList).isNotNull();
        Assertions.assertThat(mediaList.get(0).getMain()).isEqualTo(true);
    }

}
