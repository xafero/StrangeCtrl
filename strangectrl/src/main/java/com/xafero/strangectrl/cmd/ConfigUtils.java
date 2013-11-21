package com.xafero.strangectrl.cmd;

import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.TreeMap;

public class ConfigUtils {

	public static Map<String, ICommand> loadCommands(String cfgFile)
			throws IOException {
		Map<String, ICommand> cmds = new TreeMap<String, ICommand>();
		final Properties prop = new Properties();
		prop.loadFromXML(new FileInputStream(cfgFile));
		for (Entry<Object, Object> e : prop.entrySet()) {
			String key = (e.getKey() + "").trim();
			String[] value = (e.getValue() + "").split(" ", 2);
			String cmdStr = value[0].trim();
			String argsStr = value[1].trim();
			Class<?> cmdCl = findCmd(cmdStr);
			if (cmdCl == null)
				throw new UnsupportedOperationException("I don't know '"
						+ cmdStr + "'!");
			try {
				Constructor<?> constr = cmdCl.getConstructor(String.class);
				Object obj = constr.newInstance(argsStr);
				ICommand cmd = (ICommand) obj;
				cmds.put(key, cmd);
			} catch (Exception ex) {
				throw new RuntimeException("loadCommands", ex);
			}
		}
		return cmds;
	}

	public static Class<?> findCmd(String key) {
		Class<?>[] classes = Commands.class.getClasses();
		for (Class<?> cmdClass : classes) {
			String shortName = cmdClass.getSimpleName().replace("Cmd", "");
			if (key.equalsIgnoreCase(shortName))
				return cmdClass;
		}
		return null;
	}

	public static Map<String, Integer> buildKeyMap(String prefix) {
		Map<String, Integer> keyMap = new HashMap<String, Integer>();
		for (Field keyField : KeyEvent.class.getFields()) {
			String name = keyField.getName();
			if (!name.startsWith(prefix))
				continue;
			String shortName = name.replace(prefix, "");
			try {
				keyMap.put(shortName.toLowerCase(), keyField.getInt(null));
			} catch (Exception e) {
				throw new RuntimeException("buildKeyMap", e);
			}
		}
		return keyMap;
	}
}