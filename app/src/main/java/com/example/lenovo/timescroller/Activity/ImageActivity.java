package com.example.lenovo.timescroller.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.view.ViewCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.lenovo.timescroller.R;

import java.io.File;
import java.io.FileOutputStream;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kevin.tian on 2016/8/16.
 */
public class ImageActivity extends BaseActivity {
    @InjectView(R.id.activity_image_im)
    ImageView image;
    public static final String PICTURE = "picture";
    public static final String TITLE = "title";
    String title;
    Bitmap bitmap;

    public static Intent startImageActivity(Context context, String url, String title) {
        Intent intent = new Intent(context, ImageActivity.class);
        intent.putExtra(BaseActivity.OBJECT_EXTRA, url);
        intent.putExtra(TITLE, title);
        return intent;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_image_layout;
    }

    @Override
    public void initData() {
        extra = getIntent().getStringExtra(BaseActivity.OBJECT_EXTRA);
        title = getIntent().getStringExtra(TITLE);
        ViewCompat.setTransitionName(image, PICTURE);
//        Intent intent = new Intent("com.example.lenovo.timescroller");
//        intent.setClass(this, AlarmReciver.class);
//        sendBroadcast(intent);
        //ImageLoaderUtil.loadImage(this,extra,image);
        /**
         * new SimpleTarget<Bitmap>保持context引用问题
         */
        Glide.with(getApplicationContext()).load(extra).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                if (image == null) {
                    return;
                }
                bitmap = resource;
                image.setImageBitmap(resource);
            }

            @Override
            public void onStart() {
                super.onStart();
                image.setImageResource(R.drawable.kevin);
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                super.onLoadFailed(e, errorDrawable);
//                image.setImageResource(R.drawable.kevin);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_picture,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_save:
                onSave();
                return true;
            case R.id.action_share:
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    void onSave() {
        File file = new File(Environment.getExternalStorageDirectory(), "Keming");
        String fileName = title.replace('/', '-') + ".jpg";
        File pic = new File(file,fileName);
        if (!file.exists()) {
           file.mkdir();
        }
        try {
            FileOutputStream outputStream = new FileOutputStream(pic);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
            outputStream.flush();
            outputStream.close();
            Toast.makeText(ImageActivity.this, getString(R.string.save_success), Toast.LENGTH_SHORT).show();
            Uri uri = Uri.fromFile(file);
            // 通知图库更新
            Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
            sendBroadcast(scannerIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bitmap = null;
        ButterKnife.reset(this);
    }

    @Override
    public void initUI() {
        setToolBarTitle(getIntent().getStringExtra(TITLE));
    }
}
