package wooteco.prolog.image.application;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Component
public class S3Uploader {

    private final AmazonS3 amazonS3;

    @Value("${application.bucket.name}")
    private String bucket;

    @Value("${application.cloudfront.url}")
    private String cloudFrontUrl;

    public String upload(final MultipartFile uploadImageFile) {
        amazonS3.putObject(createPutObjectRequest(uploadImageFile));
        return cloudFrontUrl + uploadImageFile.getOriginalFilename();
    }

    private PutObjectRequest createPutObjectRequest(final MultipartFile uploadImageFile) {
        try {
            return new PutObjectRequest(bucket,
                    uploadImageFile.getOriginalFilename(),
                    uploadImageFile.getInputStream(),
                    createObjectMetaData(uploadImageFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ObjectMetadata createObjectMetaData(final MultipartFile uploadImageFile) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(uploadImageFile.getContentType());
        objectMetadata.setContentLength(uploadImageFile.getSize());
        return objectMetadata;
    }
}
