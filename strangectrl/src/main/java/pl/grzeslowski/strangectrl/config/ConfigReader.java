package pl.grzeslowski.strangectrl.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

import com.xafero.strangectrl.App;

public class ConfigReader {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory
            .getLogger(ConfigReader.class);
    private static final String CFG_FILE = "config.xml";

    private final ConfigLoader configLoader = new XStreamConfigLoader();

    public Configuration loadConfiguration() {
        try {
            final String parentPath = new File(getClass().getProtectionDomain()
                    .getCodeSource().getLocation().toURI()).getParent();

            final File file = new File(parentPath + CFG_FILE);

            final InputStream configStream;
            if (file.isFile()) {

                // load from external file
                configStream = new FileInputStream(file);
                logger.info("Loading conf from file " + file.getAbsolutePath());
            } else {

                // load from normal file
                configStream = App.class.getResourceAsStream(App.RESOURCES_PATH
                        + CFG_FILE);
                logger.info("Loading conf from jar");
            }

            final String readedFile = readConfigFile(configStream);
            configStream.close();

            return configLoader.loadXml(readedFile);

        } catch (final IOException e) {
            logger.error("Cannot load configuration!", e);
            throw new RuntimeException(e);
        } catch (final URISyntaxException e1) {
            logger.error("Cannot load configuration!", e1);
            throw new RuntimeException(e1);
        }
    }

    private String readConfigFile(final InputStream configStream) {
        BufferedReader in = null;
        String readedFile = "";
        try {
            in = new BufferedReader(new InputStreamReader(configStream));
            String str;
            final StringBuilder builder = new StringBuilder();
            while ((str = in.readLine()) != null) {
                builder.append(str);
            }

            readedFile = builder.toString();
        } catch (final IOException e) {
            logger.error("Error in reading file!", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (final IOException e) {
                    logger.error("Error in reading file (closing in final)!", e);
                }
            }
        }
        return readedFile;
    }
}
