package wooteco.prolog.image.application;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import wooteco.prolog.image.application.dto.ImageUrlResponse;
import wooteco.prolog.image.exception.FileNameEmptyException;
import wooteco.prolog.image.infrastructure.FileNameGenerator;
import wooteco.prolog.image.infrastructure.S3Uploader;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final S3Uploader s3Uploader;
    private final FileNameGenerator fileNameGenerator;

    public ImageUrlResponse upload(final MultipartFile uploadImageFile) {
        validate(uploadImageFile);
        final String fileName = fileNameGenerator.generate(uploadImageFile.getOriginalFilename());
        final String uploadUrl = s3Uploader.upload(uploadImageFile, fileName);
        return new ImageUrlResponse(uploadUrl);
    }

    private void validate(final MultipartFile uploadImageFile) {
        if (isEmptyFileName(uploadImageFile)) {
            throw new FileNameEmptyException();
        }
    }

    private static boolean isEmptyFileName(final MultipartFile uploadImageFile) {
        return Objects.requireNonNull(uploadImageFile.getOriginalFilename()).trim().isEmpty();
    }
}
