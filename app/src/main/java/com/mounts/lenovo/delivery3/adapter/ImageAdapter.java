package com.mounts.lenovo.delivery3.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.mounts.lenovo.delivery3.R;
import com.mounts.lenovo.delivery3.activity.Details;
import com.mounts.lenovo.delivery3.activity.ServiceDetail;
import com.mounts.lenovo.delivery3.fragment.CategoriesFragment;

public class ImageAdapter extends PagerAdapter {
    Context mContext;

    public ImageAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ImageView) object);
    }

    private int[] sliderImageId = new int[]{
            R.drawable.img1, R.drawable.images1, R.drawable.img4, R.drawable.donut, R.drawable.img5};

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(sliderImageId[position]);
        ((ViewPager) container).addView(imageView, 0);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "clicked the image" + (position + 1), Toast.LENGTH_LONG).show();
                mContext.startActivity(new Intent(mContext, ServiceDetail.class));//when click the image from view pager,intent to "Details.java"
                Log.e("Clicked", "view pager");
            }
        });
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }

    @Override
    public int getCount() {
        return sliderImageId.length;
    }
}
