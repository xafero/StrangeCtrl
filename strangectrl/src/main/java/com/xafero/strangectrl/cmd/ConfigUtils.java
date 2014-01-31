package com.xafero.strangectrl.cmd;

import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ConfigUtils {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory
            .getLogger(ConfigUtils.class);

    private ConfigUtils() {
    }

    public static Map<String, Integer> buildKeyMap(final String prefix) {
        final Map<String, Integer> keyMap = new HashMap<String, Integer>();
        for (final Field keyField : KeyEvent.class.getFields()) {
            final String name = keyField.getName();
            if (!name.startsWith(prefix)) {
                continue;
            }
            final String shortName = name.replace(prefix, "");
            try {
                keyMap.put(shortName.toLowerCase(), keyField.getInt(null));
            } catch (final Exception e) {
                logger.error("Cannot build key map!", e);
                throw new RuntimeException("buildKeyMap", e);
            }
        }
        return keyMap;
    }
}
