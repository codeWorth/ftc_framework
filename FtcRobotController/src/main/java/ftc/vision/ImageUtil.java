package ftc.vision;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class ImageUtil {

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
