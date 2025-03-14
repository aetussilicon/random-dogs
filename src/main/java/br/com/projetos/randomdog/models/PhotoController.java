package br.com.projetos.randomdog.models;

import br.com.projetos.randomdog.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@RestController
@RequiredArgsConstructor
public class PhotoController {
    private final PhotoRepository photoRepository;

    @CrossOrigin("*")
    @PostMapping("new")
    public HttpStatus addPhoto(MultipartFile photo) {
        try {
            byte[] bytes = photo.getBytes();
            Photo newPhoto = new Photo();
            newPhoto.setImage(bytes);

            photoRepository.save(newPhoto);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return HttpStatus.CREATED;
    }

    @CrossOrigin("*")
    @GetMapping
    public PhotoDto getPhoto() {
        Tika tika = new Tika();

        Photo photo = photoRepository.findRandomPhoto();
        String base64Picture = Base64.getEncoder().encodeToString(photo.getImage());
        String mimeType = tika.detect(photo.getImage());


        return new PhotoDto(
                photo.getId(),
                base64Picture,
                mimeType
        );
    }
}
