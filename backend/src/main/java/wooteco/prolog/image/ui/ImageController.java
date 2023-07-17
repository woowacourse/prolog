package wooteco.prolog.image.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import wooteco.prolog.image.application.ImageService;
import wooteco.prolog.image.application.dto.ImageUrlResponse;

@RequiredArgsConstructor
@RequestMapping("/images")
@RestController
public class ImageController {

    private final ImageService imageService;

    @PostMapping
    public ResponseEntity<ImageUrlResponse> upload(
        @RequestPart("file") MultipartFile uploadImageFile) {
        return ResponseEntity.ok(imageService.upload(uploadImageFile));
    }
}
