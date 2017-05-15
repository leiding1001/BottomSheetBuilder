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

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.rubensousa.bottomsheetbuilder.R;
import com.github.rubensousa.bottomsheetbuilder.bean.BsDividerItem;

import me.drakeet.multitype.ItemViewBinder;

/**
 * @author drakeet
 */
public class DividerItemViewBinder extends ItemViewBinder<BsDividerItem, DividerItemViewBinder.DividerViewHolder> {


    @NonNull
    @Override
    protected DividerViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.bs_list_divider, parent, false);
        return new DividerViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull DividerViewHolder holder, @NonNull BsDividerItem item) {
        holder.updateView(item);
    }

    static class DividerViewHolder extends RecyclerView.ViewHolder {
        private View divider;
        private BsDividerItem item;

        public DividerViewHolder(View itemView) {
            super(itemView);
            divider = itemView;
        }


        public void updateView(BsDividerItem item) {
            this.item = item;
            int background = item.getBackground();
            if (background != 0) {
                divider.setBackgroundResource(background);
            }
        }
    }

}
