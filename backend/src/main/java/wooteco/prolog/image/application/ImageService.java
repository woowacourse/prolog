package wooteco.prolog.image.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import wooteco.prolog.image.application.dto.ImageUrlResponse;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final S3Uploader s3Uploader;

    public ImageUrlResponse upload(final MultipartFile uploadImageFile) {
        return new ImageUrlResponse(s3Uploader.upload(uploadImageFile));
    }
}
