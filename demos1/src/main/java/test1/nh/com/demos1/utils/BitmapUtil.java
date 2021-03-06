package test1.nh.com.demos1.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.widget.ImageView;

/**
 * @author m
 */
public class BitmapUtil {
    /**
     * 获取正确 缩放&裁剪 适应IamgeView的Bitmap
     */
    public static Bitmap createFitBitmap(ImageView imageView, Bitmap bitmap) {
        return createFitBitmap(imageView.getWidth(), imageView.getHeight(), bitmap);
    }

    /**
     * 根据目的长宽 缩放&裁剪 Bitmap
     * @param destWidth 需要的长度
     * @param destHeight 需要的宽度
     * @param bitmap 待处理图片
     * @return 修改完成的图片
     */
    public static Bitmap createFitBitmap(int destWidth, int destHeight, Bitmap bitmap){

        int orignWidth = bitmap.getWidth();
        int orignHeight = bitmap.getHeight();
        Bitmap result = null;
        if (orignWidth >= destWidth && orignHeight >= destHeight) {
            result = createLargeToSmallBitmap(
                    orignWidth, orignHeight, destWidth, destHeight, bitmap);
        } else if (orignWidth >= destWidth && orignHeight < destHeight) {
            Bitmap temp = createLargeWidthToEqualHeightBitmap(
                    orignWidth, orignHeight, destWidth, destHeight, bitmap);
            result = createLargeToSmallBitmap(
                    temp.getWidth(), temp.getHeight(), destWidth, destHeight, temp);
        } else if (orignWidth < destWidth && orignHeight >= destHeight) {
            Bitmap temp = createLargeHeightToEqualWidthBitmap(
                    orignWidth, orignHeight, destWidth, destHeight, bitmap);
            result = createLargeToSmallBitmap(
                    temp.getWidth(), temp.getHeight(), destWidth, destHeight, temp);
        } else {
            Bitmap temp = createSmallToEqualBitmap(
                    orignWidth, orignHeight, destWidth, destHeight, bitmap);
            result = createFitBitmap(destWidth, destHeight, temp);
        }

        return result;
    }

    /**
     * 获取缩放的图片
     */
    public static Bitmap createScaledBitmap(int destWidth, int destHeight, Bitmap bitmap){
        int orignWidth = bitmap.getWidth();
        int orignHeight = bitmap.getHeight();
        Bitmap result = null;
        if (orignWidth >= destWidth && orignHeight >= destHeight) {
            result = createLargeToEqualBitmap(
                    orignWidth, orignHeight, destWidth, destHeight, bitmap);
        } else if (orignWidth >= destWidth && orignHeight < destHeight) {
            result = createLargeWidthToEqualHeightBitmap(
                    orignWidth, orignHeight, destWidth, destHeight, bitmap);
        } else if (orignWidth < destWidth && orignHeight >= destHeight) {
            result = createLargeHeightToEqualWidthBitmap(
                    orignWidth, orignHeight, destWidth, destHeight, bitmap);
        } else {
            result = createSmallToEqualBitmap(
                    orignWidth, orignHeight, destWidth, destHeight, bitmap);
        }
        return result;
    }


    /*  -----------  */
    private static Bitmap createLargeToSmallBitmap(
            int widthBitmap, int heightBitmap, int widthTarget, int heightTarget, Bitmap bitmap) {

        int x = (widthBitmap - widthTarget) / 2;
        int y = (heightBitmap - heightTarget) / 2;
        return Bitmap.createBitmap(bitmap, x, y, widthTarget, heightTarget);
    }

    private static Bitmap createLargeWidthToEqualHeightBitmap(
            int widthBitmap, int heightBitmap, int widthTarget, int heightTarget, Bitmap bitmap) {

        double scale = (heightTarget * 1.0) / heightBitmap;
        return Bitmap.createScaledBitmap(bitmap, (int) (widthBitmap * scale), heightTarget, false);
    }

    private static Bitmap createLargeHeightToEqualWidthBitmap(
            int widthBitmap, int heightBitmap, int widthTarget, int heightTarget, Bitmap bitmap) {

        double scale = (widthTarget * 1.0) / widthBitmap;
        return Bitmap.createScaledBitmap(bitmap, widthTarget, (int) (heightTarget * scale), false);
    }

    private static Bitmap createLargeToEqualBitmap(
            int widthBitmap, int heightBitmap, int widthTarget, int heightTarget, Bitmap bitmap){

        return createSmallToEqualBitmap(widthBitmap, heightBitmap, widthTarget, heightTarget, bitmap);
    }

    private static Bitmap createSmallToEqualBitmap(
            int widthBitmap, int heightBitmap, int widthTarget, int heightTarget, Bitmap bitmap) {

        double scaleWidth = (widthTarget * 1.0) / widthBitmap;
        double scaleHeight = (heightTarget * 1.0) / heightBitmap;
        double scale = Math.min(scaleWidth, scaleHeight);
        return Bitmap.createScaledBitmap(bitmap, (int) (widthBitmap * scale), (int) (heightBitmap * scale), false);
    }

//* -------------------

    /**
     * 覆盖图片颜色
     */
    public static Bitmap overlapBitmapColor(Bitmap bitmap, int color){
        Bitmap bmp = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(bmp);
        canvas.drawColor(color, PorterDuff.Mode.SRC_IN);
        bitmap.recycle();
        return bmp;
    }

}
