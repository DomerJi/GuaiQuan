package com.fd.gq.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by admin on 2016/11/26.
 */
public class LargeImageUtil {

    public static Drawable loadImage(String url){
        URL m;
        InputStream is = null;
        try {
            m = new URL(url);
            is = (InputStream) m.getContent();
            BufferedInputStream bis = new BufferedInputStream(is);
            //标记其实位置，供reset参考
            bis.mark(0);

            BitmapFactory.Options opts = new BitmapFactory.Options();
            //true,只是读图片大小，不申请bitmap内存
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(bis, null, opts);
            Log.v("AsyncImageLoader", "width=" + opts.outWidth + "; height=" + opts.outHeight);
            int flagSize = 1024*1024*4;
            int size = (opts.outWidth * opts.outHeight);
            if( size > flagSize){
                int zoomRate = 2;
                //zommRate缩放比，根据情况自行设定，如果为2则缩放为原来的1/2，如果为1不缩放
                zoomRate = zoomRate>size/(flagSize)?zoomRate:size/(flagSize);
                if(zoomRate <= 0) zoomRate = 1;
                opts.inSampleSize = zoomRate;
                Log.v("AsyncImageLoader", "图片过大，被缩放 1/"+zoomRate);
            }

            //设为false，这次不是预读取图片大小，而是返回申请内存，bitmap数据
            opts.inJustDecodeBounds = false;
            //缓冲输入流定位至头部，mark()
            bis.reset();
            Bitmap bm = BitmapFactory.decodeStream(bis, null, opts);

            is.close();
            bis.close();
            return (bm == null) ? null : new BitmapDrawable(bm);
        } catch (MalformedURLException e1) {
            Log.v("AsyncImageLoader", "MalformedURLException");
            e1.printStackTrace();
        } catch (IOException e) {
            Log.v("AsyncImageLoader", "IOException");
            e.printStackTrace();
        }
        return null;
    }

}
