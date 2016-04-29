package com.example.clam314.changeavatar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private HashMap<String, List<String>> mGruopMap = new HashMap<String, List<String>>();
    private List<ImageBean> list = new ArrayList<ImageBean>();
    private final static int SCAN_OK = 1;
    private ProgressDialog mProgressDialog;
    private GroupAdapter adapter;
    private TextView title;
    private GridView mGroupGridView;
    private PopupWindow popupWindow;
    private FrameLayout frameLayout;
    private View drawer;
    private PopupWindowAdapter popupWindowAdapter;

    private Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SCAN_OK:
                    //关闭进度条
                    mProgressDialog.dismiss();

                    adapter = new GroupAdapter(MainActivity.this);
                    mGroupGridView.setAdapter(adapter);
                    adapter.setImageBeanList(subGroupOfImage(mGruopMap));
                    adapter.notifyDataSetChanged();
                    break;
            }
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGroupGridView = (GridView)findViewById(R.id.grid);



        frameLayout = (FrameLayout)findViewById(R.id.scroll_list);
        title = (TextView)findViewById(R.id.title);
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                initPopupWindow();
//                setWindowDark(true);
//                popupWindow.showAsDropDown(v,0,20);
                if (frameLayout.getVisibility() == View.VISIBLE) {
                    exitAnim();
                    return;
                }
                showFrameLayout();
            }
        });
        initDate();
    }



    public void changeAlbum(List<String> album){
        if(adapter != null){
            adapter.setImageList(album);
            adapter.notifyDataSetChanged();
            exitAnim();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        changeAlbum(popupWindowAdapter.getList().get(position).getImagesPath());
    }

    private void initPopupWindow(){
        View drawer = View.inflate(this,R.layout.popupwindow,null);
        ListView popupList = (ListView)drawer.findViewById(R.id.image_list);
        popupWindowAdapter = new PopupWindowAdapter(this);
        popupList.setAdapter(popupWindowAdapter);
        popupList.setOnItemClickListener(this);
        popupWindowAdapter.setList(subGroupOfImage(mGruopMap));
        popupWindow = new PopupWindow(drawer, WindowManager.LayoutParams.MATCH_PARENT,
                (int)(getResources().getDisplayMetrics().heightPixels*0.6));
        popupWindow.setFocusable(true);

        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setAnimationStyle(R.style.PopupAnimation);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setWindowDark(false);
            }
        });
    }




    private  void showFrameLayout(){
        drawer = View.inflate(this,R.layout.popupwindow,null);
        ListView popupList = (ListView)drawer.findViewById(R.id.image_list);
        popupWindowAdapter = new PopupWindowAdapter(this);
        popupList.setAdapter(popupWindowAdapter);
        popupList.setOnItemClickListener(this);
        popupWindowAdapter.setList(subGroupOfImage(mGruopMap));
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                (int)(getResources().getDisplayMetrics().heightPixels*0.6));
        drawer.setLayoutParams(lp);
        frameLayout.addView(drawer);
        frameLayout.setVisibility(View.VISIBLE);
        enterAnim();
    }

    private void enterAnim(){
        ObjectAnimator.ofFloat(drawer,"translationY",-500f,dip2px(this,48f)).setDuration(250).start();
    }

    private void exitAnim(){
        ObjectAnimator animator = ObjectAnimator.ofFloat(drawer, "translationY", dip2px(this,48f), -500f);
        animator.setDuration(250);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                frameLayout.setVisibility(View.GONE);
            }
        });
        animator.start();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(frameLayout.getVisibility() == View.VISIBLE){
                exitAnim();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void setWindowDark(boolean isDark){
        WindowManager.LayoutParams lp = getWindow().getAttributes();
//        lp.alpha = isDark ? 0.3f: 1f;
        getWindow().setAttributes(lp);
    }



    private void initDate(){
        //显示进度条
        mProgressDialog = ProgressDialog.show(this, null, "正在加载...");

        new Thread(new Runnable() {

            @Override
            public void run() {
                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = MainActivity.this.getContentResolver();

                //只查询jpeg和png的图片
                Cursor mCursor = mContentResolver.query(mImageUri, null,
                        MediaStore.Images.Media.MIME_TYPE + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=?",
                        new String[] { "image/jpeg", "image/png" }, MediaStore.Images.Media.DATE_MODIFIED);

                if(mCursor == null){
                    return;
                }

                while (mCursor.moveToNext()) {
                    //获取图片的路径
                    String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA));

                    //获取该图片的父路径名
                    String parentName = new File(path).getParentFile().getName();


                    //根据父路径名将图片放入到mGruopMap中
                    if (!mGruopMap.containsKey(parentName)) {
                        List<String> chileList = new ArrayList<String>();
                        chileList.add(path);
                        mGruopMap.put(parentName, chileList);
                    } else {
                        mGruopMap.get(parentName).add(path);
                    }
                }

                //通知Handler扫描图片完成
                mHandler.sendEmptyMessage(SCAN_OK);
                mCursor.close();
            }
        }).start();
    }
    /**
     * 组装分组界面GridView的数据源，因为我们扫描手机的时候将图片信息放在HashMap中
     * 所以需要遍历HashMap将数据组装成List
     *
     * @param mGruopMap
     * @return
     */
    private List<ImageBean> subGroupOfImage(HashMap<String, List<String>> mGruopMap){
        if(mGruopMap.size() == 0){
            return null;
        }
        List<ImageBean> list = new ArrayList<ImageBean>();

        Iterator<Map.Entry<String, List<String>>> it = mGruopMap.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry<String, List<String>> entry = it.next();
            ImageBean mImageBean = new ImageBean();
            String key = entry.getKey();
            List<String> value = entry.getValue();
            mImageBean.setFolderName(key);
            mImageBean.setImageCounts(value.size());
            mImageBean.setTopImagePath(value.get(0));//获取该组的第一张图片
            mImageBean.setImagesPath(value);
            list.add(mImageBean);
        }
        return list;
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
