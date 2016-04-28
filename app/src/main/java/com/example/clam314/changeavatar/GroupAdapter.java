package com.example.clam314.changeavatar;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by clam314 on 2016/4/28.
 */
public class GroupAdapter extends BaseAdapter {

    private Activity activity;
    private List<ImageBean> imageBeanList = new ArrayList<>();
    private List<String> imageList = new ArrayList<>();

    public GroupAdapter(Activity activity) {
        this.activity = activity;
    }

    public List<ImageBean> getImageBeanList() {
        return imageBeanList;
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
        ImageView imageView = new ImageView(activity);
        Bitmap bitmap = BitmapFactory.decodeFile(imageList.get(position));
        return imageView;
    }
}
