package de.uni_siegen.wineme.come_in.thumbnailer.test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class MyTestCase implements TestConfiguration {

	public void assertFileExists(File file)
	{
		assertFileExists("", file);
	}
	
	public static void assertFileExists(String msg, File file)
	{
		if (!msg.isEmpty())
			msg += ": ";

		assertNotNull(msg + "File is null", file);
		assertTrue(msg + "File " + file.getAbsolutePath() + " does not exist", file.exists());
	}
	
	public static void assertPictureFormat(File file, int width, int height) throws IOException
	{
		assertFileExists("Picture file does not exist", file);
		
		Image image = ImageIO.read(file);
		assertNotNull("Picture " + file.getAbsolutePath() + " could not be decoded by ImageIO", image);
		assertPictureFormat(image, width, height);
	}
	
	public static void assertPictureFormat(Image img, int expectedWidth, int expectedHeight)
	{
		assertNotNull("Picture is null", img);
		int realWidth = img.getWidth(null);
		int realHeight = img.getHeight(null);
		String realFormat =  realWidth + "x" + realHeight;
		String expectedFormat = expectedWidth + "x" + expectedHeight;
		assertEquals("Picture has not the right width (expected: " + expectedFormat + ", actual: " + realFormat + ")", expectedWidth, realWidth);
		assertEquals("Picture has not the right height (expected: " + expectedFormat + ", actual: " + realFormat + ")", expectedHeight, realHeight);
	}
	
	public void testDummy()
	{
		// Somehow this test needs to be present so that JUnit detects the tests in sub-classes.
		assertTrue(true);
	}
}
