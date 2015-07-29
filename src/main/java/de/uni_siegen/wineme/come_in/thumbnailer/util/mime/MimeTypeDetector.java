/*
 * regain/Thumbnailer - A file search engine providing plenty of formats (Plugin)
 * Copyright (C) 2011  Come_IN Computerclubs (University of Siegen)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * Contact: Come_IN-Team <come_in-team@listserv.uni-siegen.de>
 */

package de.uni_siegen.wineme.come_in.thumbnailer.util.mime;

import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicMatch;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Wrapper class for MIME Identification of Files.
 * <p/>
 * Depends: <li>Aperture (for MIME-Detection)
 */
public class MimeTypeDetector {

    private MimeTypeDetector() {
    }

    private static String guessContentTypeFrom(byte[] data) {
        String contentType = "application/octet-stream";// NOI18N
        try {
            MagicMatch magicMatch = Magic.getMagicMatch(data);
            contentType = magicMatch.getMimeType();
        } catch (Exception ex) {
        }
        return contentType;
    }

    /**
     * Detect MIME-Type for this file.
     * @param file File to analyse
     * @return String of MIME-Type, or null if no detection was possible (or unknown MIME Type)
     */
    public static String getMimeType(File file) {
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] bytes = new byte[fis.available()];
            fis.read(bytes);
            return guessContentTypeFrom(bytes);
        } catch (IOException e) {
            return null; // File does not exist or other I/O Error
        }
    }
}
