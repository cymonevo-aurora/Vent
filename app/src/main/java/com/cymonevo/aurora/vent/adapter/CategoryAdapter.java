package com.cymonevo.aurora.vent.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cymonevo.aurora.vent.R;
import com.cymonevo.aurora.vent.constant.ClickHandler;
import com.cymonevo.aurora.vent.handler.ClickEventHandler;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private ClickEventHandler activity;
    private String[] topic;
    private String[] desc;
    private String[] theme;

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        private View layout;

        private CategoryViewHolder(View layout) {
            super(layout);
            this.layout = layout;
        }

        private void setData(String topic, String desc, String color) {
            ((TextView) layout.findViewById(R.id.category_topic)).setText(topic);
            ((TextView) layout.findViewById(R.id.category_desc)).setText(desc);
            ((GradientDrawable)layout.getBackground()).setColor(Color.parseColor(color));
        }

        private void setHandler(final ClickEventHandler activity, final int position) {
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onClickEvent(ClickHandler.CATEGORY_REQUEST, position);
                }
            });
        }
    }

    public CategoryAdapter(Activity activity) {
        this.topic = activity.getApplicationContext().getResources().getStringArray(R.array.category_topic);
        this.desc = activity.getApplicationContext().getResources().getStringArray(R.array.category_desc);
        this.theme = activity.getApplicationContext().getResources().getStringArray(R.array.category_color);
        this.activity = (ClickEventHandler) activity;
    }

    @Override
    public int getItemCount() {
        return topic.length;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_category, parent, false);
        return new CategoryViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.setData(topic[position], desc[position], theme[position]);
        holder.setHandler(activity, position);
    }
}
