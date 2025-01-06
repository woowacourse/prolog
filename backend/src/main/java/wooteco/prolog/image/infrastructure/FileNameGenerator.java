package wooteco.prolog.image.infrastructure;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import wooteco.prolog.common.exception.BadRequestException;
import wooteco.prolog.image.domain.FileExtension;

import java.util.UUID;

import static wooteco.prolog.common.exception.BadRequestCode.UNSUPPORTED_FILE_EXTENSION_EXCEPTION;

@Component
public class FileNameGenerator {

    public String generate(final String originalFilename) {
        final String fileName = createNewName();
        final String extension = getExtension(originalFilename);
        return fileName + "." + extension;
    }

    private static String getExtension(final String originalFilename) {
        final String extension = FilenameUtils.getExtension(originalFilename);
        if (FileExtension.isSupport(extension)) {
            return extension;
        }
        throw new BadRequestException(UNSUPPORTED_FILE_EXTENSION_EXCEPTION);
    }

    private static String createNewName() {
        return UUID.randomUUID().toString();
    }
}
