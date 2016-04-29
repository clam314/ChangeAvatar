package com.example.clam314.changeavatar;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by clam314 on 2016/4/28.
 */
public class GroupAdapter extends BaseAdapter {

    private Activity activity;
    private List<ImageBean> imageBeanList = new ArrayList<>();
    private List<String> imageList = new ArrayList<>();
    private Halder hadler;

    public GroupAdapter(Activity activity) {
        this.activity = activity;
    }

    public List<ImageBean> getImageBeanList() {
        return imageBeanList;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        if(imageList != null) {
            this.imageList = imageList;
        }
    }

    public void setImageBeanList(List<ImageBean> imageBeanList) {
        if(imageBeanList == null){
            return;
        }
        this.imageBeanList = imageBeanList;
        for (ImageBean imageBean : imageBeanList){
            imageList.addAll(imageBean.getImagesPath());
        }
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        hadler = null;
        if (convertView == null) {
            convertView = View.inflate(activity, R.layout.item_image, null);
            hadler = new Halder();
            hadler.imageView = (ImageView)convertView.findViewById(R.id.image);
            convertView.setTag(hadler);
        } else {
            hadler = (Halder) convertView.getTag();
        }
        int height = (int) activity.getResources().getDisplayMetrics().widthPixels / 4;
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        convertView.setLayoutParams(params);

        ImageLoader.getInstance().displayImage("file://" + imageList.get(position),
                hadler.imageView,
                new DisplayImageOptions.Builder()
                        .showImageOnLoading(new ColorDrawable(255))
                        .cacheInMemory(true)
                        .cacheOnDisk(true)
                        .bitmapConfig(Bitmap.Config.RGB_565)
                        .build());
        return convertView;
    }


    static class Halder{
        ImageView imageView;
    }
}
