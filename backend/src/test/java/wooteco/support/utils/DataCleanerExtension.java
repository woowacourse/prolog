package wooteco.support.utils;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import wooteco.prolog.common.DataInitializer;

public class DataCleanerExtension implements AfterEachCallback {

    @Override
    public void afterEach(final ExtensionContext context) {
        DataInitializer dataCleaner = getDataCleaner(context);
        dataCleaner.execute();
    }

    private DataInitializer getDataCleaner(final ExtensionContext extensionContext) {
        return (DataInitializer) SpringExtension.getApplicationContext(extensionContext)
            .getBean("dataInitializer");
    }
}
