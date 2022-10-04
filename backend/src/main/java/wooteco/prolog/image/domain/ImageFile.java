package wooteco.prolog.image.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Getter
public class ImageFile {

    private final FileName originFileName;
    private final FileExtension fileExtension;

    public static ImageFile from(final MultipartFile imageFile) {
        final FileName fileName = new FileName(imageFile.getOriginalFilename());
        final FileExtension fileExtension = FileExtension.from(fileName.getValue());
        return new ImageFile(fileName, fileExtension);
    }
}
