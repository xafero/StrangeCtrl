package com.xafero.strangectrl.awt;

import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

public class ResourceUtils {

	public static Image loadImage(String path) {
		URL imgUrl = ClassLoader.getSystemResource(path);
		Image img = Toolkit.getDefaultToolkit().getImage(imgUrl);
		return img;
	}
}