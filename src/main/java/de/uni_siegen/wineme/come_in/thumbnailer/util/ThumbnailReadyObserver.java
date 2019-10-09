package de.uni_siegen.wineme.come_in.thumbnailer.util;

import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.awt.image.ImageObserver;

/**
 * Not quite sure if this is necessary: This is intended to give awt a chance to draw image
 * asynchronously.
 * @author Benjamin
 */
@Slf4j
public class ThumbnailReadyObserver implements ImageObserver {

    public volatile boolean ready = false;
    private Thread toNotify;

    public ThumbnailReadyObserver(Thread toNotify) {
        this.toNotify = toNotify;
        ready = false;
    }

    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {

        log.info("Observer debug info: imageUpdate: " + infoflags);
        if ((infoflags & ImageObserver.ALLBITS) > 0) {
            ready = true;
            log.info("Observer says: Now ready!");
            toNotify.notify();
            return true;
        }
        return false;
    }
}
