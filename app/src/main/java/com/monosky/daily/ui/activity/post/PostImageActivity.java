package com.monosky.daily.ui.activity.post;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bm.library.PhotoView;
import com.monosky.daily.BaseApplication;
import com.monosky.daily.R;
import com.monosky.daily.ui.activity.BaseActivity;
import com.monosky.daily.util.ImageLoaderOption;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.Bind;

public class PostImageActivity extends BaseActivity {

    @Bind(R.id.photo_view)
    PhotoView mPhotoView;
    private ImageLoader mImageLoader = ImageLoader.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                supportFinishAfterTransition();
            }
        });
        mPhotoView.enable();
        String imageUrl = getIntent().getStringExtra("imageUrl");
        mImageLoader.displayImage(imageUrl, mPhotoView, ImageLoaderOption.optionInfoImage(R.mipmap.ic_default_image_light));
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_post_image;
    }

    public static void gotoPostImage(String imageUrl) {
        Intent intent = new Intent(BaseApplication.getContext(), PostImageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("imageUrl", imageUrl);
        BaseApplication.getContext().startActivity(intent);
    }
}
