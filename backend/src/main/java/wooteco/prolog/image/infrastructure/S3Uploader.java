package wooteco.prolog.image.infrastructure;

import static wooteco.prolog.common.exception.BadRequestCode.FILE_UPLOAD_FAIL_EXCEPTION;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import wooteco.prolog.common.exception.BadRequestException;

@RequiredArgsConstructor
@Component
public class S3Uploader {

    private final AmazonS3 amazonS3;

    @Value("${application.bucket.name}")
    private String bucket;

    @Value("${application.cloudfront.url}")
    private String cloudFrontUrl;

    public String upload(final MultipartFile uploadImageFile, final String fileName) {
        putImageFileToS3(uploadImageFile, fileName);
        return createUploadUrl(fileName);
    }

    private void putImageFileToS3(final MultipartFile uploadImageFile, final String fileName) {
        try {
            amazonS3.putObject(new PutObjectRequest(bucket,
                fileName,
                uploadImageFile.getInputStream(),
                createObjectMetaData(uploadImageFile)));
        } catch (IOException e) {
            throw new BadRequestException(FILE_UPLOAD_FAIL_EXCEPTION);
        }
    }

    private String createUploadUrl(final String fileName) {
        return cloudFrontUrl + fileName;
    }

    private ObjectMetadata createObjectMetaData(final MultipartFile uploadImageFile) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(uploadImageFile.getContentType());
        objectMetadata.setContentLength(uploadImageFile.getSize());
        return objectMetadata;
    }
}
