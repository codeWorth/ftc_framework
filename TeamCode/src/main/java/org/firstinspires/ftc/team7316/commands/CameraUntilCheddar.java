package org.firstinspires.ftc.team7316.commands;

import android.media.Image;

import org.firstinspires.ftc.team7316.util.commands.Command;
import org.opencv.core.MatOfPoint;

import ftc.vision.ImageProcess;
import ftc.vision.SimpleFrameGrabber;

public class CameraUntilCheddar extends Command {

    public static MatOfPoint contour = null;
    ImageProcess p;

    @Override
    public void init() {
        contour = null;
        p = new ImageProcess();
//        ImageProcess.start();
    }

    @Override
    public void loop() {
        contour = p.processImage(ImageProcess.sourceImg);
    }

    @Override
    public boolean shouldRemove() {
        return contour != null;
    }

    @Override
    protected void end() {
    }
}
