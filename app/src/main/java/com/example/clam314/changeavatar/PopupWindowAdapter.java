package com.example.clam314.changeavatar;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by clam314 on 2016/4/29.
 */
public class PopupWindowAdapter extends BaseAdapter {
    private Activity activity;
    private List<ImageBean> list = new ArrayList<>();

    public PopupWindowAdapter(Activity activity) {
        this.activity = activity;
    }

    public List<ImageBean> getList() {
        return list;
    }

    public void setList(List<ImageBean> list) {
        if(list == null){
            return;
        }
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
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
        Holder holder = null;
        if(convertView == null){
            convertView = View.inflate(activity,R.layout.item_album,null);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (Holder)convertView.getTag();
        }

        ImageLoader.getInstance().displayImage("file://"+list.get(position).getTopImagePath(),holder.albumImage,
                new DisplayImageOptions.Builder()
                        .showImageOnLoading(new ColorDrawable(255))
                        .cacheInMemory(true)
                        .cacheOnDisk(true)
                        .bitmapConfig(Bitmap.Config.RGB_565)
                        .build());

        holder.albumName.setText(list.get(position).getFolderName());
        holder.albumNum.setText(String.valueOf(list.get(position).getImageCounts()));
        return convertView;
    }

    static class Holder{
        ImageView albumImage;
        TextView albumName;
        TextView albumNum;
        Holder(View view){
            albumImage = (ImageView)view.findViewById(R.id.album);
            albumName = (TextView)view.findViewById(R.id.album_name);
            albumNum  = (TextView)view.findViewById(R.id.album_num);
        }
    }
}
