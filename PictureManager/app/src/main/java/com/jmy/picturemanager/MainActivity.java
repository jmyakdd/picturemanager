package com.jmy.picturemanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import jmy.mylibrary.widget.KyushuRecyclerview;
import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;

public class MainActivity extends AppCompatActivity {
    private KyushuRecyclerview kyushuRecyclerview;
    private List<String> datas = new ArrayList<>();
    private final int MAX = 9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        kyushuRecyclerview = (KyushuRecyclerview) findViewById(R.id.ky_rv);

        kyushuRecyclerview.setMAX(MAX);//设置最大显示图片张数
        kyushuRecyclerview.setOnSelectImageItemClickListener(new KyushuRecyclerview.OnSelectImageItemClickListener() {
            @Override
            public void onItemClick(int position) {//点击选中图片
                if (position == datas.size()) {
                    PhotoPicker.builder()
                            .setPhotoCount(MAX)
                            .setSelected((ArrayList<String>) datas)
                            .setShowCamera(true)
                            .setShowGif(true)
                            .setPreviewEnabled(false)
                            .start(MainActivity.this, PhotoPicker.REQUEST_CODE);
                } else {
                    PhotoPreview.builder()
                            .setPhotos((ArrayList<String>) datas)
                            .setCurrentItem(position)
                            .setShowDeleteButton(false)
                            .start(MainActivity.this);
                }
            }

            @Override
            public void onDeleteClick(int position) {//删除选中图片
                datas.remove(position);
            }
        });
        kyushuRecyclerview.setData(datas);//设置图片数据
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PhotoPicker.REQUEST_CODE && resultCode == Activity.RESULT_OK) {//获取选中图片
            if (data != null) {
                datas = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                kyushuRecyclerview.setData(datas);
            }
        }
    }

}
