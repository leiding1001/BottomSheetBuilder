/*
 * Copyright 2016 drakeet. https://github.com/drakeet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.rubensousa.bottomsheetbuilder.viewbinder;

import static com.github.rubensousa.bottomsheetbuilder.R.id.recyclerView;

import android.content.Context;
import android.support.annotation.MenuRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.rubensousa.bottomsheetbuilder.R;
import com.github.rubensousa.bottomsheetbuilder.util.BsBuilderUtils;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.multitype.ItemViewBinder;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * @author drakeet
 */
public class ContentRecyclerBinder implements BsView {

    public MultiTypeAdapter mMultiTypeAdapter;
    public LinearLayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private Context mContext;
    private RecyclerView.ItemDecoration mItemDecoration;
    private List<?> data = new ArrayList<>();


    public ContentRecyclerBinder(Context context) {
        mContext = context;
        mMultiTypeAdapter = new MultiTypeAdapter();
        mLayoutManager = new LinearLayoutManager(context);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.bs_recyclerview, parent, true);
        mRecyclerView = (RecyclerView) root.findViewById(recyclerView);
        mRecyclerView.setLayoutManager(mLayoutManager);


        if(mItemDecoration!=null){
            mRecyclerView.addItemDecoration(mItemDecoration);
        }

        mRecyclerView.setAdapter(mMultiTypeAdapter);

        return root;
    }

    @Override
    public void updateView() {
        mMultiTypeAdapter.setItems(data);
        mMultiTypeAdapter.notifyDataSetChanged();
    }


    public static final class Builder {

        ContentRecyclerBinder mRecyclerItemViewBinder;

        private Context mContext;

        public Builder(Context context) {
            mContext = context;
            mRecyclerItemViewBinder = new ContentRecyclerBinder(context);
        }


        public Builder layoutManager(LinearLayoutManager val) {
            mRecyclerItemViewBinder.mLayoutManager = val;
            return this;
        }

        public Builder itemDecoration(RecyclerView.ItemDecoration val) {
            mRecyclerItemViewBinder.mItemDecoration = val;
            return this;
        }

        public <T> Builder register(@NonNull Class<? extends T> clazz, @NonNull ItemViewBinder<T, ?> binder) {
            mRecyclerItemViewBinder.mMultiTypeAdapter.register(clazz, binder);
            return this;
        }

        public Builder data(List<?> data) {
            mRecyclerItemViewBinder.data = data;
            return this;
        }

        public Builder data(@MenuRes int menuRes) {
            mRecyclerItemViewBinder.data = BsBuilderUtils.fromMenu(mContext, menuRes);
            return this;
        }


        public ContentRecyclerBinder build() {
            return mRecyclerItemViewBinder;
        }
    }
}
