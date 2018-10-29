package ftc.vision;

import android.util.Log;
import android.view.SurfaceView;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.Arrays;

public class SimpleFrameGrabber implements CameraBridgeViewBase.CvCameraViewListener2{

    public SimpleFrameGrabber(CameraBridgeViewBase cameraBridgeViewBase, int frameWidthRequest, int frameHeightRequest) {
        cameraBridgeViewBase.setVisibility(SurfaceView.VISIBLE);

        cameraBridgeViewBase.setMinimumWidth(frameWidthRequest);
        cameraBridgeViewBase.setMinimumHeight(frameHeightRequest);
        cameraBridgeViewBase.setMaxFrameSize(frameWidthRequest, frameHeightRequest);
        cameraBridgeViewBase.setCvCameraViewListener(this);
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        ImageProcess.start();
    }

    @Override
    public void onCameraViewStopped() {
        ImageProcess.stop();
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        Mat frame = inputFrame.rgba();
//        ImageProcess.setSourceImage(frame);
//
//        MatOfPoint contour = ImageProcess.grabFrame();
//        if (contour != null) {
////            Imgproc.drawContours(frame, Arrays.asList(contour), 0, new Scalar(0, 0, 255), 2); // draw the contour on the orignial image
//            //paramters: image to draw on, the list of contours, the index of the contour we want to draw, the color to draw it in, the thickness of the line
//            // I make the contour into a list because the drawContours function only accepts lists (very annoying)
//            // The color is red (opencv lists colors as BGR)
//        }

        return frame;
    }

}
