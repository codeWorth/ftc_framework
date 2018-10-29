package ftc.vision;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class ImageUtil {

	
	/**
	 * Applys the Core.inRange function to a Mat after accounting for rollover
	 * on the hsv hue channel.
	 * @param srcHSV source Mat in HSV format
	 * @param min Scalar that defines the min h, s, and v values
	 * @param max Scalar that defines the max h, s, and v values
	 * @param dst the output binary image
	 */
	public static void hsvInRange(Mat srcHSV, Scalar min, Scalar max, Mat dst){
		//if the max hue is greater than the min hue
		if(max.val[0] > min.val[0]) {
			//use inRange once
			Core.inRange(srcHSV, min, max, dst);
		} else {
			//otherwise, compute 2 ranges and bitwise or them
			double[] vals = min.val.clone();
			vals[0] = 0;
			Scalar min2 = new Scalar(vals);
			vals = max.val.clone();
			vals[0] = 179;
			Scalar max2 = new Scalar(vals);

			Mat tmp1 = new Mat(), tmp2 = new Mat();
			Core.inRange(srcHSV, min, max2, tmp1);
			Core.inRange(srcHSV, min2, max, tmp2);
			Core.bitwise_or(tmp1, tmp2, dst);
		}
	}

	// We don't need this yet, but we will need it
	/**
	 * rotates a Mat by any angle. If the angle is 90n, use transpose and/or flip.
	 * Otherwise, use warpAffine
	 * @param src Mat to be rotated
	 * @param dst output Mat
	 * @param angle angle to rotate by
	 */
	public static void rotate(Mat src, Mat dst, int angle) {
		angle = angle % 360;
		if (angle == 270 || angle == -90) {
			// Rotate clockwise 270 degrees
			Core.transpose(src, dst);
			Core.flip(dst, dst, 0);
		}
		else if (angle == 180 || angle == -180) {
			// Rotate clockwise 180 degrees
			Core.flip(src, dst, -1);
		}
		else if (angle == 90 || angle == -270) {
			// Rotate clockwise 90 degrees
			Core.transpose(src, dst);
			Core.flip(dst, dst, 1);
		}
		else if (angle == 360 || angle == 0 || angle == -360) {
			if (src.dataAddr() != dst.dataAddr()) {
				src.copyTo(dst);
			}
		}
		else {
			Point srcCenter = new Point(src.width()/2, src.height()/2);
			Size size = new RotatedRect(srcCenter, src.size(), angle).boundingRect().size();
			Point center = new Point(size.width/2, size.height/2);

			Mat rotationMatrix2D = Imgproc.getRotationMatrix2D(center, angle, 1);
			Imgproc.warpAffine(src, dst, rotationMatrix2D, size);
		}
	}
	
}
