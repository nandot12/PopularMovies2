package id.co.imastudio.popularmovie;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

/**
 * Created by idn on 7/29/2017.
 */

class ImageUtils {
    private ImageUtils() {
    }

    /**
     * Source code from the post:
     * http://stackoverflow.com/questions/37779515/how-can-i-convert-an-imageview-to-byte-array-in-android-studio
     *
     * @param imageView
     * @return byte array
     */
    public static byte[] bytesFromImageView(@NonNull final ImageView imageView) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
        if (bitmapDrawable == null) return null;

        Bitmap bitmap = bitmapDrawable.getBitmap();
        if (bitmap == null) return null;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        return baos.toByteArray();
    }

    public static Bitmap bitmapFromBytes(byte[] data) {
        if (data == null || data.length == 0) {
            return null;
        }
        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }
}
