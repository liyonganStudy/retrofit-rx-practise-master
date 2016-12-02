package com.example.base_library.multitype;

import android.support.annotation.NonNull;
import android.support.v4.util.SparseArrayCompat;
import android.util.Log;

/**
 * Created by landy on 16/11/25.
 *
 */

class MultiTypePool implements TypePool {

    private SparseArrayCompat<Class<? extends DisplayItem>> contents;
    private SparseArrayCompat<ItemViewProvider> providers;

    MultiTypePool() {
        contents = new SparseArrayCompat<>(1);
        providers = new SparseArrayCompat<>(1);
    }

    @Override
    public void register(@NonNull Class<? extends DisplayItem> clazz, @NonNull ItemViewProvider provider) {
        int index = contents.indexOfValue(clazz);
        if (index < 0) {
            contents.append(contents.size(), clazz);
            providers.append(providers.size(), provider);
        } else {
            providers.append(index, provider);
        }
    }

    @Override
    public int indexOf(@NonNull Class<? extends DisplayItem> clazz) {
        int index = contents.indexOfValue(clazz);
        if (index < 0) {
            throw new IllegalStateException("no such type register");
        }
        return contents.indexOfValue(clazz);
    }

//    @NonNull
//    @Override
//    public ArrayList<Class<? extends DisplayItem>> getContents() {
//        return contents;
//    }

//    @NonNull
//    @Override
//    public ArrayList<ItemViewProvider> getProviders() {
//        return null;
//    }

    @Override
    public ItemViewProvider getProviderByIndex(int index) {
        Log.e("lya", "getProviderByIndex: " + providers.size());
        return providers.get(index);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends ItemViewProvider> T getProviderByClass(@NonNull Class<? extends DisplayItem> clazz) {
        return (T) providers.get(indexOf(clazz));
    }
}
