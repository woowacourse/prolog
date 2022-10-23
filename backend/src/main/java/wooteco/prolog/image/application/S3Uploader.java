package wooteco.prolog.image.application;

import java.io.IOException;
import java.nio.ByteBuffer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@RequiredArgsConstructor
@Component
public class S3Uploader {

    private final S3Client s3Client;

    @Value("${application.bucket.name}")
    private String bucket;

    @Value("${application.cloudfront.url}")
    private String cloudFrontUrl;

    public String upload(final MultipartFile uploadImageFile) {
        try {
            s3Client.putObject(getPutObjectRequest(uploadImageFile), getRequestBody(uploadImageFile));
            return cloudFrontUrl + getUrl(uploadImageFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private PutObjectRequest getPutObjectRequest(final MultipartFile uploadImageFile) {
        return PutObjectRequest.builder()
                .bucket(bucket)
                .key(uploadImageFile.getOriginalFilename())
                .contentType(uploadImageFile.getContentType())
                .contentLength(uploadImageFile.getSize())
                .build();
    }

    private static RequestBody getRequestBody(final MultipartFile uploadImageFile) throws IOException {
        return RequestBody.fromByteBuffer(ByteBuffer.wrap(uploadImageFile.getBytes()));
    }

    private String getUrl(final MultipartFile uploadImageFile) {
        return s3Client.utilities()
                .getUrl(getUrlRequest(uploadImageFile))
                .getPath();
    }

    private GetUrlRequest getUrlRequest(final MultipartFile uploadImageFile) {
        return GetUrlRequest.builder()
                .bucket(bucket)
                .key(uploadImageFile.getOriginalFilename())
                .build();
    }
}
