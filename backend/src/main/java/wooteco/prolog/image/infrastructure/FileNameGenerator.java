package wooteco.prolog.image.infrastructure;

import java.util.UUID;
import org.apache.commons.compress.utils.FileNameUtils;
import wooteco.prolog.image.domain.FileExtension;
import wooteco.prolog.image.exception.UnsupportedFilExtensionException;

public class FileNameGenerator {

    public String generate(final String originalFilename) {
        final String fileName = createNewName();
        final String extension = getExtension(originalFilename);
        return fileName + "." + extension;
    }

    private static String getExtension(final String originalFilename) {
        final String extension = FileNameUtils.getExtension(originalFilename);
        if (FileExtension.isSupport(extension)) {
            return extension;
        }
        throw new UnsupportedFilExtensionException();
    }

    private static String createNewName() {
        return UUID.randomUUID().toString();
    }
}
