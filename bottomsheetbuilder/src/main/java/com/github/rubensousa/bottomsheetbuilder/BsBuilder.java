/*
 * Copyright 2016 Rúben Sousa
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.rubensousa.bottomsheetbuilder;

import android.content.Context;
import android.os.Build;
import android.support.annotation.StyleRes;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.github.rubensousa.bottomsheetbuilder.viewbinder.BsView;

public class BsBuilder {


    @StyleRes
    private int mTheme;

    private boolean mDelayedDismiss = true;
    private boolean mExpandOnStart = false;

    private CoordinatorLayout mCoordinatorLayout;
    private AppBarLayout mAppBarLayout;
    private Context mContext;


    private BsView headerView;
    private BsView contentView;
    private BsView footerView;


    public BsBuilder(Context context, CoordinatorLayout coordinatorLayout) {
        mContext = context;
        mCoordinatorLayout = coordinatorLayout;
    }

    public BsBuilder(Context context) {
        this(context, 0);
    }

    public BsBuilder(Context context, @StyleRes int theme) {
        mContext = context;
        mTheme = theme;
    }



    public BsBuilder addHeaderView(BsView headerView) {
        this.headerView = headerView;
        return this;
    }

    public BsBuilder addContentView(BsView contentView) {
        this.contentView = contentView;
        return this;
    }

    public BsBuilder addFooterView(BsView footerView) {
        this.footerView = footerView;
        return this;
    }

    public BsBuilder expandOnStart(boolean expand) {
        mExpandOnStart = expand;
        return this;
    }

    public BsBuilder delayDismissOnItemClick(boolean dismiss) {
        mDelayedDismiss = dismiss;
        return this;
    }

    public BsBuilder setAppBarLayout(AppBarLayout appbar) {
        mAppBarLayout = appbar;
        return this;
    }


    public BottomSheetMenuDialog createDialog() {

        BottomSheetMenuDialog dialog = mTheme == 0
                ? new BottomSheetMenuDialog(mContext, R.style.BottomSheetBuilder_DialogStyle)
                : new BottomSheetMenuDialog(mContext, mTheme);

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        View sheet = layoutInflater.inflate(R.layout.bs_content_view, null);

        ViewCompat.setElevation(sheet, mContext.getResources()
                .getDimensionPixelSize(R.dimen.bottomsheet_elevation));

        FrameLayout headerContainer = (FrameLayout) sheet.findViewById(R.id.bs_title_container);
        if (headerView != null) {
            headerView.onCreateView(layoutInflater, headerContainer);
            headerView.updateView();
        } else {
            headerContainer.setVisibility(View.GONE);
        }
        FrameLayout contentContainer = (FrameLayout) sheet.findViewById(R.id.bs_content_container);
        if (contentView != null) {
            contentView.onCreateView(layoutInflater, contentContainer);
            contentView.updateView();
        }

        FrameLayout footerContainer = (FrameLayout) sheet.findViewById(R.id.bs_footer_container);
        if (footerView != null) {
            footerView.onCreateView(layoutInflater, footerContainer);
            footerView.updateView();
        } else {
            footerContainer.setVisibility(View.GONE);
        }

        sheet.findViewById(R.id.fakeShadow).setVisibility(View.GONE);
        dialog.setAppBar(mAppBarLayout);
        dialog.expandOnStart(mExpandOnStart);
        dialog.delayDismiss(mDelayedDismiss);

        if (mContext.getResources().getBoolean(R.bool.tablet_landscape)) {
            FrameLayout.LayoutParams layoutParams
                    = new FrameLayout.LayoutParams(mContext.getResources()
                    .getDimensionPixelSize(R.dimen.bottomsheet_width),
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.setContentView(sheet, layoutParams);
        } else {
            dialog.setContentView(sheet);
        }

        return dialog;
    }


    public View createView() {

        if (mCoordinatorLayout == null) {
            throw new IllegalStateException("You need to provide a coordinatorLayout" +
                    "so the view can be placed on it");
        }
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View sheet = layoutInflater.inflate(R.layout.bs_content_view, null);

        ViewCompat.setElevation(sheet, mContext.getResources()
                .getDimensionPixelSize(R.dimen.bottomsheet_elevation));

        FrameLayout headerContainer = (FrameLayout) sheet.findViewById(R.id.bs_title_container);
        if (headerView != null) {
            headerView.onCreateView(layoutInflater, headerContainer);
            headerView.updateView();
        } else {
            headerContainer.setVisibility(View.GONE);
        }
        FrameLayout contentContainer = (FrameLayout) sheet.findViewById(R.id.bs_content_container);
        if (contentView != null) {
            contentView.onCreateView(layoutInflater, contentContainer);
            contentView.updateView();
        }

        FrameLayout footerContainer = (FrameLayout) sheet.findViewById(R.id.bs_footer_container);
        if (footerView != null) {
            footerView.onCreateView(layoutInflater, footerContainer);
            footerView.updateView();
        } else {
            footerContainer.setVisibility(View.GONE);
        }

        ViewCompat.setElevation(sheet, mContext.getResources().getDimensionPixelSize(R.dimen.bottomsheet_elevation));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sheet.findViewById(R.id.fakeShadow).setVisibility(View.GONE);
        }

        CoordinatorLayout.LayoutParams layoutParams
                = new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.MATCH_PARENT,
                CoordinatorLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        layoutParams.setBehavior(new BottomSheetBehavior());

        if (mContext.getResources().getBoolean(R.bool.tablet_landscape)) {
            layoutParams.width = mContext.getResources()
                    .getDimensionPixelSize(R.dimen.bottomsheet_width);
        }

        mCoordinatorLayout.addView(sheet, layoutParams);
        mCoordinatorLayout.postInvalidate();
        return sheet;
    }


}
