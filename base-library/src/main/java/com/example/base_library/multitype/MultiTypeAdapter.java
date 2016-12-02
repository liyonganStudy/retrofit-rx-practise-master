package com.example.base_library.multitype;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import static utils.Preconditions.checkNotNull;

/**
 * Created by landy on 16/11/25.
 *
 */

public class MultiTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected List<DisplayItem> items = new ArrayList<>();
    protected LayoutInflater inflater;
    protected TypePool typePool;

    public MultiTypeAdapter() {
        this.typePool = new MultiTypePool();
    }

    public void registerMultiType(@NonNull Class<? extends DisplayItem> clazz,
                                  @NonNull ItemViewProvider provider) {
        checkNotNull(clazz, "clazz is null");
        checkNotNull(provider, "provider is null");
        typePool.register(clazz, provider);
    }

    public void addItems(@NonNull List<? extends DisplayItem> data) {
        int insertPosition = items.size();
        items.addAll(data);
        notifyItemRangeInserted(insertPosition, items.size());
    }

    public void setItems(@NonNull List<? extends DisplayItem> data) {
        items.clear();
        items.addAll(data);
        notifyItemInserted(items.size());
    }

    @Override
    public int getItemViewType(int position) {
        DisplayItem item = items.get(position);
        return typePool.indexOf(item.getClass());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        return typePool.getProviderByIndex(viewType).onCreateViewHolder(inflater, parent);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DisplayItem item = items.get(position);
        typePool.getProviderByClass(item.getClass()).onBindViewHolder(holder, item, position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
