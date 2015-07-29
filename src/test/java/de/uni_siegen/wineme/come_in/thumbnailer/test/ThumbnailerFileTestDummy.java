package de.uni_siegen.wineme.come_in.thumbnailer.test;

import de.uni_siegen.wineme.come_in.thumbnailer.ThumbnailerManager;
import de.uni_siegen.wineme.come_in.thumbnailer.thumbnailers.NativeImageThumbnailer;
import de.uni_siegen.wineme.come_in.thumbnailer.thumbnailers.OpenOfficeThumbnailer;
import de.uni_siegen.wineme.come_in.thumbnailer.thumbnailers.PDFBoxThumbnailer;
import jirau.DWGThumbnailer;
import org.junit.Before;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertFalse;

public class ThumbnailerFileTestDummy extends MyTestCase {
    protected File inputFile;
    protected int height = 120;
    protected int width = 160;

    public ThumbnailerFileTestDummy(File inputFile) {
        super();
        this.inputFile = inputFile;
    }

    ThumbnailerManager thumbnailer;

    @Before
    public void setUp() throws Exception {
        thumbnailer = new ThumbnailerManager();
        thumbnailer.setThumbnailFolder("thumbs/");

        thumbnailer.registerThumbnailer(new NativeImageThumbnailer());


        thumbnailer.registerThumbnailer(new OpenOfficeThumbnailer());
        thumbnailer.registerThumbnailer(new PDFBoxThumbnailer());
        thumbnailer.registerThumbnailer(new DWGThumbnailer());

    }

    public void create_thumbnail(File file) throws Exception {
        create_thumbnail(file, thumbnailer.chooseThumbnailFilename(file, false));
    }

    public void create_thumbnail(File input, File output) throws Exception {
        assertFileExists("Input file does not exist", input);
        if (output != null && output.exists()) {
            output.delete();
        }

        _create_thumbnail(input, output);

        assertFileExists("Output could not be generated", output);
        assertFalse("Output file is empty", 0 == output.length());
        assertPictureFormat(output, width, height);
    }

    public void _create_thumbnail(File input, File output) throws Exception {
        thumbnailer.generateThumbnail(input, output);
    }

    public void setImageSize(int height, int width, int opt) {
        thumbnailer.setImageSize(width, height, opt);
        this.height = height;
        this.width = width;
    }


    public static Collection<Object[]> getFileList(String path) {
        return getFileList(new File(path));
    }

    public static Collection<Object[]> getFileList(File path) {
        Collection<Object[]> files = new ArrayList<Object[]>();

        File[] testfiles = path.listFiles();

        for (File input : testfiles) {
            if (input.isDirectory()) {
                continue;
            }

            files.add(new Object[]{getDisplayName(input), input});
        }
        return files;
    }

    public static String getDisplayName(File inputFile) {
        return inputFile.getName().replaceAll("[^a-zA-Z0-9]", "_");
    }


}