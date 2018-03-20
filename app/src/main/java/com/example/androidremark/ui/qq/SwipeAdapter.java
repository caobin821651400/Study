package com.example.androidremark.ui.qq;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidremark.R;
import com.example.androidremark.widget.swipe_menu.SwipeMenuLayout;


/**
 * Created by hello-world on 2017/6/9.
 */

public class SwipeAdapter extends RecyclerView.Adapter<SwipeAdapter.SwipeHolder> {
    private Context mContext;

    public SwipeAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public SwipeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.swipe_menu_item, parent, false);
        return new SwipeHolder(view);
    }

    @Override
    public void onBindViewHolder(final SwipeHolder holder, final int position) {
        holder.tv_content.setText("这是第" + (position + 1) + "条数据");
        holder.tv_to_unread.setVisibility(position % 2 == 0 ? View.VISIBLE : View.GONE);
        holder.tv_to_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.swipe_menu.isMenuOpen()) {
                    holder.swipe_menu.smoothToCloseMenu();
                }
                Toast.makeText(mContext, "置顶", Toast.LENGTH_SHORT).show();
            }
        });
        holder.tv_to_unread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.swipe_menu.isMenuOpen()) {
                    holder.swipe_menu.smoothToCloseMenu();
                }
                Toast.makeText(mContext, "标为未读", Toast.LENGTH_SHORT).show();
            }
        });
        holder.tv_to_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.swipe_menu.isMenuOpen()) {
                    holder.swipe_menu.smoothToCloseMenu();
                }
                Toast.makeText(mContext, "删除", Toast.LENGTH_SHORT).show();
            }
        });
        holder.swipe_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "这是第" + (position + 1) + "条数据", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 30;
    }

    public class SwipeHolder extends RecyclerView.ViewHolder {
        private TextView tv_to_top, tv_to_unread, tv_to_delete, tv_content;
        private SwipeMenuLayout swipe_menu;

        public SwipeHolder(View itemView) {
            super(itemView);
            swipe_menu = (SwipeMenuLayout) itemView.findViewById(R.id.swipe_menu);
            tv_to_top = (TextView) itemView.findViewById(R.id.tv_to_top);
            tv_to_unread = (TextView) itemView.findViewById(R.id.tv_to_unread);
            tv_to_delete = (TextView) itemView.findViewById(R.id.tv_to_delete);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
        }
    }
}
