package org.firstinspires.ftc.team7316.commands;

import android.media.Image;

import org.firstinspires.ftc.team7316.util.commands.Command;
import org.opencv.core.MatOfPoint;

import ftc.vision.ImageProcess;
import ftc.vision.SimpleFrameGrabber;

public class CameraUntilCheddar extends Command {

    public static MatOfPoint contour = null;

    @Override
    public void init() {
        ImageProcess.start();
    }

    @Override
    public void loop() {
    }

    @Override
    public boolean shouldRemove() {
        return false;
    }

    @Override
    protected void end() {
        ImageProcess.stop();
        contour = ImageProcess.grabContour();
    }
}
