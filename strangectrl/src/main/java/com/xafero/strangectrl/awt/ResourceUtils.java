package com.xafero.strangectrl.awt;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;

public class ResourceUtils {

	private ResourceUtils() {
	}

	public static Image loadImage(final String path) {
		final URL imgUrl = ClassLoader.getSystemResource(path);
		return Toolkit.getDefaultToolkit().getImage(imgUrl);
	}

	public static Image loadImage(final InputStream inputStream)
			throws IOException {
		return ImageIO.read(inputStream);
	}
}