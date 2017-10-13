package jmy.mylibrary.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import jmy.mylibrary.R;

/**
 * 图片适配器
 * Created by jmy on 2017/9/22.
 */

public class SelectImageAdapter extends RecyclerView.Adapter<SelectImageAdapter.ViewHolder> {
    private int MAX = 9;//最大张数
    private List<String> data = new ArrayList<>();//数据源
    private Context context;
    private OnSelectImageItemClickListener listener;

    public void setOnSelectImageItemClickListener(OnSelectImageItemClickListener listener) {
        this.listener = listener;
    }

    public SelectImageAdapter(Context context) {
        this.context = context;
    }

    /**
     * 设置最大张数
     * @param MAX
     */
    public void setMAX(int MAX) {
        this.MAX = MAX;
    }

    /**
     * 添加图片
     * @param path
     */
    public void addData(String path) {
        data.add(path);
    }

    /**
     * 清理所有数据
     */
    public void clear() {
        data.clear();
    }

    @Override
    public SelectImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_image_select, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (data.size() >= MAX) {
            Glide.with(context).load(data.get(position)).into(holder.image);
            holder.delete.setVisibility(View.VISIBLE);
        } else {
            if (position != data.size()) {
                holder.delete.setVisibility(View.VISIBLE);
                Glide.with(context).load(data.get(position)).into(holder.image);
            } else {
                holder.delete.setVisibility(View.GONE);
                Glide.with(context).load(R.drawable.icon_add).into(holder.image);
            }
        }
        if (listener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(position);
                }
            });

            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDeleteClick(position);
                    data.remove(position);
                    notifyItemRemoved(position);
                    if(data.size()<MAX) {
                        notifyItemRangeChanged(position, data.size()+1 - position);
                    }else{
                        notifyItemRangeChanged(position, data.size() - position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (data.size() < MAX) {
            return data.size() + 1;
        } else {
            return MAX;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        ImageView delete;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            delete = (ImageView) itemView.findViewById(R.id.delete);
        }
    }

    public interface OnSelectImageItemClickListener {
        void onItemClick(int position);

        void onDeleteClick(int position);
    }
}
