package com.example.androidremark.ui3.photocropper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.example.androidremark.R;
import com.example.androidremark.base.BaseActivity;

/**
 * @author: cb
 * @date: 2024/3/11 12:02
 * @desc: 描述
 */
public class PhotocropperActivity extends BaseActivity {

    Bitmap mBitmap;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photocropper);

        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.avatar1);
        final CropImageView cropImageView = findViewById(R.id.crop_image_view);
        cropImageView.setImageBitmap(mBitmap);

        final CropListener listener = new CropListener() {
            @Override
            public void onFinish(Bitmap bitmap) {
//                cropImageView.setImageBitmap(bitmap);
                findViewById(R.id.crop_image_view).setVisibility(View.GONE);

                findViewById(R.id.img_cropped).setVisibility(View.VISIBLE);
                ((ImageView)findViewById(R.id.img_cropped)).setImageBitmap(bitmap);
            }
        };

        findViewById(R.id.btn_crop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cropImageView.crop(listener, true);
            }
        });

        findViewById(R.id.btn_rotate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotateBitmap();
            }
        });
    }

    private void rotateBitmap() {
        Matrix matrix = new Matrix();
        matrix.postRotate( 90);

        mBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), matrix, false);
        ((CropImageView) findViewById(R.id.crop_image_view)).setImageBitmap(mBitmap);
    }
}
