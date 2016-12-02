package com.example.base_library.multitype;

import android.support.annotation.NonNull;

/**
 * Created by landy on 16/11/25.
 *
 */

interface TypePool {

    void register(@NonNull Class<? extends DisplayItem> clazz, @NonNull ItemViewProvider provider);

    /**
     * 返回item的对应的type的index
     * @param clazz 类别
     * @return 返回值可以作为viewType使用
     */
    int indexOf(@NonNull Class<? extends DisplayItem> clazz);

//    @NonNull
//    ArrayList<Class<? extends DisplayItem>> getContents();

//    @NonNull
//    ArrayList<ItemViewProvider> getProviders();

    ItemViewProvider getProviderByIndex(int index);

    <T extends ItemViewProvider> T getProviderByClass(@NonNull Class<? extends DisplayItem> clazz);
}
