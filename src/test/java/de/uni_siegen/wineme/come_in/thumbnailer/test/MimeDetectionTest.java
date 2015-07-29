package de.uni_siegen.wineme.come_in.thumbnailer.test;

import de.uni_siegen.wineme.come_in.thumbnailer.util.mime.MimeTypeDetector;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.fail;

public class MimeDetectionTest extends MyTestCase {
    private File parent;

    @Before
    public void setUp() {
        parent = new File(TESTFILES_DIR + "wrong_extension");
    }

    @Test
    public void testImageFiles() {
        assertMime("image/png", "test2-png.jpg");
    }

    @Test
    public void testImageFiles2() {
        assertMime("image/jpeg", "test2-jpg.bmp");
    }

    @Test
    public void testImageFiles3() {
        assertMime("image/bmp", "test2-bmp.png");
    }

    @Test
    public void testTextFiles() {
        assertMime("text/rtf", "test2-rtf.pdf");
    }

    @Test
    public void testTextFiles2() {
        assertMime("application/pdf", "test2-pdf.ps");
    }

    @Test
    public void testOpenOfficeFiles() {
        assertMime("application/vnd.oasis.opendocument.presentation", "test2-odp.pps");
    }

    @Test
    public void testOpenOfficeFiles2() {
        assertMime("application/vnd.oasis.opendocument.spreadsheet", "test2-ods.xls");
    }

    @Test
    public void testOpenOfficeFiles3() {
        assertMime("application/vnd.oasis.opendocument.text", "test2-odt.sxw");
    }

    @Test
    public void testOpenOfficeFiles4() {
        assertMime("application/vnd.oasis.opendocument.text", "test2-odt.doc");
    }

    @Test
    public void testOffice2007Files() {
        assertMime("application/vnd.openxmlformats-officedocument.wordprocessingml", "test2-docx.doc");
    }

    @Test
    public void testOffice2007Files2() {
        assertMime("application/vnd.openxmlformats-officedocument.presentationml", "test2-pptx.ppt");
    }

    @Test
    public void testOffice2007Files3() {
        assertMime("application/vnd.openxmlformats-officedocument.spreadsheetml", "test2-xlsx.ods");
    }

    @Test
    public void testOffice2007Files4() {
        assertMime("application/vnd.openxmlformats-officedocument.spreadsheetml", "test2-xlsx.ppt");
    }

    @Test
    public void testOfficeFiles() {
        assertMime("application/vnd.ms-powerpoint", "test2-ppt.odp");
    }

    @Test
    public void testOfficeFiles2() {
        assertMime("application/vnd.ms-word", "test2-doc.ott");
    }

    @Test
    public void testOfficeFiles3() {
        assertMime("application/vnd.ms-excel", "test2-xls.odp");
    }

    @Test
    public void testAutoCad() {
        assertMime("image/x-dwg", "test2-dwg.doc");
    }

    public void assertMime(String expectedMime, String filename) {
        File file = new File(parent, filename);
        if (!file.exists()) {
            fail("File " + filename + " does not exist");
        }

        String mime = MimeTypeDetector.getMimeType(file);
        if (!expectedMime.equalsIgnoreCase(mime)) {
            fail("File " + filename + ": Mime is not equal: expected \"" + expectedMime + "\", but was \"" + mime + "\".");
        }
    }
}
