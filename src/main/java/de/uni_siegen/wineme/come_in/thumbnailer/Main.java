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

package de.uni_siegen.wineme.come_in.thumbnailer;

import de.uni_siegen.wineme.come_in.thumbnailer.thumbnailers.NativeImageThumbnailer;
import de.uni_siegen.wineme.come_in.thumbnailer.thumbnailers.OpenOfficeThumbnailer;
import de.uni_siegen.wineme.come_in.thumbnailer.thumbnailers.PDFBoxThumbnailer;
import jirau.DWGThumbnailer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.*;

import java.io.File;

/**
 * Little Command-line Application to illustrate the usage of this library.
 * @author Benjamin
 * @needs Commons-cli (included in jodconverter)
 */
@Slf4j
public class Main {
    private static Options options;
    private static ThumbnailerManager thumbnailer;
    private static File outFile;
    private static File inFile;

    public static void main(String[] params) throws Exception {
        if (params.length == 0) {
            explainUsage();
            System.exit(-1);
        }

        thumbnailer = new ThumbnailerManager();

        loadExistingThumbnailers();

        // Set Default Values
        thumbnailer.setImageSize(160, 120, 0);
        thumbnailer.setThumbnailFolder("thumbs/");

        initParams();
        parseParams(params);

        if (outFile == null) {
            outFile = thumbnailer.createThumbnail(inFile);
        } else {
            thumbnailer.generateThumbnail(inFile, outFile);
        }

        System.out.println("SUCCESS: Thumbnail created:\n" + outFile.getAbsolutePath());
    }

    private static void initParams() {
        options = new Options();
        Option option = Option.builder("size").desc("Size of the new thumbnail (default: 160x120)")
                .hasArg().argName("WIDTHxHEIGHT").build();
        options.addOption(option);
    }

    private static void parseParams(String[] params) {
        CommandLineParser parser = new DefaultParser();
        CommandLine line = null;
        try {
            line = parser.parse(options, params);
        } catch (ParseException e) {
            System.err.println("Invalid command line: " + e.getMessage());
            explainUsage();
            System.exit(1);
        }

        if (line.hasOption("size")) {
            // TODO Set
        }

        String[] files = line.getArgs();
        if (files.length == 0 || files.length > 2) {
            explainUsage();
            System.exit(1);
        }
        inFile = new File(files[0]);
        if (files.length > 1) {
            outFile = new File(files[1]);
        }
    }

    private static void explainUsage() {
        System.out.println("JavaThumbnailer");
        System.out.println("===============");
        System.out.println("");
        System.out.println("Usage: java -jar javathumbnailer-standalone.jar [-size 160x120] inputfile [outputfile]");

        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("java -jar javathumbnailer-standalone.jar", options);
    }

    protected static void loadExistingThumbnailers() {
        if (classExists("de.uni_siegen.wineme.come_in.thumbnailer.thumbnailers.NativeImageThumbnailer")) {
            thumbnailer.registerThumbnailer(new NativeImageThumbnailer());
        }

        thumbnailer.registerThumbnailer(new OpenOfficeThumbnailer());
        thumbnailer.registerThumbnailer(new PDFBoxThumbnailer());
        thumbnailer.registerThumbnailer(new DWGThumbnailer());
    }

    public static boolean classExists(String qualifiedClassname) {
        try {
            Class.forName(qualifiedClassname);
        } catch (ClassNotFoundException e) {
            return false;
        }
        return true;
    }
}
