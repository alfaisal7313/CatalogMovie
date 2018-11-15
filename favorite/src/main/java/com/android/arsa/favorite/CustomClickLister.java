package com.android.arsa.favorite;

import android.view.View;

public class CustomClickLister implements View.OnClickListener {

    private String id;
    private OnItemClickListener onItemClickListener;
    public CustomClickLister(String id, OnItemClickListener onItemClickListener) {
        this.id = id;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onClick(View view) {
        onItemClickListener.onClick(view, id);
    }

    interface  OnItemClickListener{
        void onClick(View view, String id);
    }
}
