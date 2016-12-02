package com.example.base_library.multitype;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * Created by landy on 16/11/25.
 *
 */

public abstract class ItemViewProvider<I extends DisplayItem, VH extends RecyclerView.ViewHolder> {

    protected abstract VH onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent);

    protected abstract void onBindViewHolder(@NonNull VH holder, @NonNull I itemData, int position);
}
