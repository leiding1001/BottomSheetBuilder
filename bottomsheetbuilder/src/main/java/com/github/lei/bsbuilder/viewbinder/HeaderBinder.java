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


import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.lei.bsbuilder.R;


/**
 * @author drakeet
 */
public class HeaderBinder implements BsView {

    private TextView textView;

    private String mTitle;

    @ColorInt
    private int mTextColor;

    public HeaderBinder() {

    }

    private HeaderBinder(Builder builder) {
        mTextColor = builder.mTextColor;
        mTitle = builder.title;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.bs_header, parent, true);
        textView = (TextView) root.findViewById(R.id.textView);
        if (mTextColor > 0) {
            textView.setTextColor(mTextColor);
        }
        return root;
    }

    @Override
    public void updateView() {
        textView.setText(mTitle);
    }


    public static final class Builder {
        private int mTextColor;
        private String title;

        public Builder() {
        }


        public Builder title(String val) {
            title = val;
            return this;
        }

        public Builder mTextColor(int val) {
            mTextColor = val;
            return this;
        }

        public HeaderBinder build() {
            return new HeaderBinder(this);
        }
    }
}
