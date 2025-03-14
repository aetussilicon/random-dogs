package br.com.projetos.randomdog;

import br.com.projetos.randomdog.models.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    @Query(value = "SELECT * FROM images OFFSET floor(random() * (SELECT COUNT(*) FROM images)) LIMIT 1", nativeQuery = true)
    Photo findRandomPhoto();
}
