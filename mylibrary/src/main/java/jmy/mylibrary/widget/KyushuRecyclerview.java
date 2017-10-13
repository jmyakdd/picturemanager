package jmy.mylibrary.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import java.util.List;

import jmy.mylibrary.adapter.SelectImageAdapter;

/**
 * 九宫格图片显示
 * Created by jmy on 2017/9/22.
 */

public class KyushuRecyclerview extends RecyclerView implements SelectImageAdapter.OnSelectImageItemClickListener {
    private SelectImageAdapter adapter;
    private Context context;
    private int MAX = 9;//最大张数
    private final int column = 3;//列数
    private OnSelectImageItemClickListener listener;

    public void setOnSelectImageItemClickListener(OnSelectImageItemClickListener listener) {
        this.listener = listener;
    }

    public KyushuRecyclerview(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public KyushuRecyclerview(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public KyushuRecyclerview(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }

    private void init() {
        adapter = new SelectImageAdapter(context);
        adapter.setOnSelectImageItemClickListener(this);
        GridLayoutManager manager = new GridLayoutManager(context, column);
        this.setLayoutManager(manager);
        this.setAdapter(adapter);
    }

    public void setMAX(int MAX) {
        this.MAX = MAX;
        adapter.setMAX(MAX);
    }

    public void setData(List<String> data) {
        adapter.clear();
        for (String str : data) {
            adapter.addData(str);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position) {
        if (listener != null) {
            listener.onItemClick(position);
        }
    }

    @Override
    public void onDeleteClick(int position) {
        if (listener != null) {
            listener.onDeleteClick(position);
        }
    }

    public interface OnSelectImageItemClickListener {
        void onItemClick(int position);

        void onDeleteClick(int position);
    }
}
