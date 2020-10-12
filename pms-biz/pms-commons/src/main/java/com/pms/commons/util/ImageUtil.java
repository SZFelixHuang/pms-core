package com.pms.commons.util;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

public class ImageUtil
{
	private static String patternStr = "image/(.*)";

	public static String SCALE_SUFFIX = "_x86";

	public static boolean isImageType(String fileType)
	{
		Pattern pattern = Pattern.compile(patternStr);
		return pattern.matcher(fileType).find();
	}

	public static File zoomImageScale(InputStream in, String imageType, String fileName)
			throws IOException
	{
		ImageInputStream imageInputStream = ImageIO.createImageInputStream(in);
		BufferedImage bufferedImage = ImageIO.read(imageInputStream);
		int originalWidth = bufferedImage.getWidth();
		int originalHeight = bufferedImage.getHeight();
		String tmpFolder = System.getProperty("java.io.tmpdir").replaceAll("\\\\", "/") + "/";
		File scaleFile = new File(tmpFolder + fileName + SCALE_SUFFIX);

		BufferedImage newBufferedImage;
		if (originalWidth > originalHeight)
		{
			newBufferedImage = zoomImageUtils(bufferedImage, imageType, 800, 600);
		}
		else
		{
			newBufferedImage = zoomImageUtils(bufferedImage, imageType, 600, 800);
		}
		ImageIO.write(newBufferedImage, imageType, scaleFile);
		return scaleFile;
	}

	private static BufferedImage zoomImageUtils(BufferedImage bufferedImage, String imageType, int width, int height)
			throws IOException
	{
		if ((imageType.toLowerCase().endsWith("png") || imageType.toLowerCase().endsWith("gif")))
		{
			BufferedImage to = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d = to.createGraphics();
			to = g2d.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
			g2d.dispose();

			g2d = to.createGraphics();
			Image from = bufferedImage.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING);
			g2d.drawImage(from, 0, 0, null);
			g2d.dispose();
			return to;
		}
		else
		{
			BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics g = newImage.getGraphics();
			g.drawImage(bufferedImage, 0, 0, width, height, null);
			g.dispose();
			return newImage;
		}
	}
}