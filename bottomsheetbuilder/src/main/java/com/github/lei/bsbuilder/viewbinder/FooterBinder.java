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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.github.lei.bsbuilder.R;


/**
 * @author drakeet
 */
public class FooterBinder implements BsView {

    private Button btn;

    private String mTitle;


    public FooterBinder() {

    }

    private FooterBinder(Builder builder) {
        mTitle = builder.title;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.bs_footer, parent, true);
        btn = (Button) root.findViewById(R.id.btn);
        return root;
    }

    @Override
    public void updateView() {
        btn.setText(mTitle);
    }


    public static final class Builder {

        private String title;

        public Builder() {
        }


        public Builder title(String val) {
            title = val;
            return this;
        }

        public FooterBinder build() {
            return new FooterBinder(this);
        }
    }
}
