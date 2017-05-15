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

package com.github.lei.bsbuilder.viewbinder;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.lei.bsbuilder.R;
import com.github.lei.bsbuilder.bean.BsMenuItem;

import me.drakeet.multitype.ItemViewBinder;

/**
 * @author drakeet
 */
public class MenuItemViewBinder extends ItemViewBinder<BsMenuItem, MenuItemViewBinder.ItemViewHolder> {

    BottomSheetItemClickListener mListener;

    private boolean isGridLayout;
    private int mItemWidth;
    private int gravity = Gravity.CENTER;


    public MenuItemViewBinder(BottomSheetItemClickListener listener) {
        mListener = listener;
    }

    private MenuItemViewBinder(Builder builder) {
        mListener = builder.mListener;
        isGridLayout = builder.isGridLayout;
        mItemWidth = builder.mItemWidth;
        gravity = builder.gravity;
    }

    @NonNull
    @Override
    protected ItemViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root;

        if (isGridLayout) {
            root = inflater.inflate(R.layout.bs_grid_item, parent, false);

            if (mItemWidth > 0) {
                ViewGroup.LayoutParams layoutParams = root.getLayoutParams();
                layoutParams.width = mItemWidth;
                root.setLayoutParams(layoutParams);
            }
        } else {
            root = inflater.inflate(R.layout.bs_list_item, parent, false);
        }

        return new ItemViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ItemViewHolder holder, @NonNull BsMenuItem item) {
        holder.bind(item);
    }

    public interface BottomSheetItemClickListener {
        void onBottomSheetItemClick(BsMenuItem item);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private AppCompatImageView imageView;
        private TextView textView;
        private BsMenuItem item;

        public ItemViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imageView = (AppCompatImageView) itemView.findViewById(R.id.imageView);
            textView = (TextView) itemView.findViewById(R.id.textView);
            ((LinearLayout) itemView).setGravity(gravity);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onBottomSheetItemClick(item);
            }
        }

        public void bind(BsMenuItem item) {
            this.item = item;
            if(item.getIcon()!=null) {
                imageView.setImageDrawable(item.getIcon());
            }else{
                imageView.setVisibility(View.GONE);
            }
            textView.setText(item.getTitle());

        }
    }

    public static final class Builder {
        private BottomSheetItemClickListener mListener;
        private boolean isGridLayout;
        private int mItemWidth;
        private int gravity;

        public Builder() {
        }

        public Builder mListener(BottomSheetItemClickListener val) {
            mListener = val;
            return this;
        }

        public Builder isGridLayout(boolean val) {
            isGridLayout = val;
            return this;
        }

        public Builder mItemWidth(int val) {
            mItemWidth = val;
            return this;
        }

        public Builder gravity(int val) {
            gravity = val;
            return this;
        }

        public MenuItemViewBinder build() {
            return new MenuItemViewBinder(this);
        }
    }
}
