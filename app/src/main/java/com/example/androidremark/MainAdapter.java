package com.example.androidremark;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.androidremark.bean.MainMenuNameBean;

import java.util.List;

/**
 * Created by 82165 on 2017/6/15.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {
    private Context mContext;
    private List<MainMenuNameBean> mList;
    private MainMenuItemListener menuItemListener;

    public MainAdapter(Context context) {
        this.mContext = context;
    }

    /**
     * 添加item
     */
    public void setList(List<MainMenuNameBean> list) {
        this.mList = list;
    }

    /**
     * 按钮监听事件
     */
    public void setOnItemClickListener(MainMenuItemListener listener) {
        this.menuItemListener = listener;
    }

    /**
     * 在这里加载布局文件
     */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list_main, parent, false);
        return new MyViewHolder(view);
    }

    /**
     *
     */
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        //这里是循环添加item的背景色
        int pos = position % 7;
        switch (pos) {
            case 0:
                setBackGroundColor(holder, R.color.orange_salmon);
                break;
            case 1:
                setBackGroundColor(holder, R.color.pure_yellow_color);
                break;
            case 2:
                setBackGroundColor(holder, R.color.orange);
                break;
            case 3:
                setBackGroundColor(holder, R.color.green_deep_color);
                break;
            case 4:
                setBackGroundColor(holder, R.color.blue_color);
                break;
            case 5:
                setBackGroundColor(holder, R.color.light_black);
                break;
            case 6:
                setBackGroundColor(holder, R.color.light_yellow);
                break;
        }
        //--------
        if (mList.size() <= 0) return;
        MainMenuNameBean bean = mList.get(position);
        if (bean != null) {
            holder.tv_title.setText(bean.getTitle());
            holder.tv_view_one.setText(bean.getBtn_one());
            holder.tv_view_two.setText(bean.getBtn_two());
            holder.tv_view_three.setText(bean.getBtn_three());
            holder.tv_view_four.setText(bean.getBtn_four());
            holder.tv_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (menuItemListener != null) {
                        menuItemListener.onItemClick(v, holder.getAdapterPosition());
                    }
                }
            });
            holder.tv_view_one.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (menuItemListener != null) {
                        menuItemListener.onItemClick(v, holder.getAdapterPosition());
                    }
                }
            });
            holder.tv_view_two.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (menuItemListener != null) {
                        menuItemListener.onItemClick(v, holder.getAdapterPosition());
                    }
                }
            });
            holder.tv_view_three.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (menuItemListener != null) {
                        menuItemListener.onItemClick(v, holder.getAdapterPosition());
                    }
                }
            });
            holder.tv_view_four.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (menuItemListener != null) {
                        menuItemListener.onItemClick(v, holder.getAdapterPosition());
                    }
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    /**
     * ViewHolder
     */
    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_title, tv_view_one, tv_view_two, tv_view_three, tv_view_four;

        MyViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_view_one = (TextView) itemView.findViewById(R.id.tv_view_one);
            tv_view_two = (TextView) itemView.findViewById(R.id.tv_view_two);
            tv_view_three = (TextView) itemView.findViewById(R.id.tv_view_three);
            tv_view_four = (TextView) itemView.findViewById(R.id.tv_view_four);
        }
    }

    /**
     * @param holder Holder
     * @param color  颜色
     */
    private void setBackGroundColor(MyViewHolder holder, int color) {
        holder.tv_title.setBackgroundColor(mContext.getResources().getColor(color));
        holder.tv_view_one.setBackgroundColor(mContext.getResources().getColor(color));
        holder.tv_view_two.setBackgroundColor(mContext.getResources().getColor(color));
        holder.tv_view_three.setBackgroundColor(mContext.getResources().getColor(color));
        holder.tv_view_four.setBackgroundColor(mContext.getResources().getColor(color));
    }
}
