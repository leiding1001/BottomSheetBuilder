package com.github.rubensousa.bottomsheetbuilder.sample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;

import com.github.rubensousa.bottomsheetbuilder.BottomSheetMenuDialog;
import com.github.rubensousa.bottomsheetbuilder.BsBuilder;
import com.github.rubensousa.bottomsheetbuilder.bean.BsDividerItem;
import com.github.rubensousa.bottomsheetbuilder.bean.BsMenuItem;
import com.github.rubensousa.bottomsheetbuilder.viewbinder.ContentRecyclerBinder;
import com.github.rubensousa.bottomsheetbuilder.viewbinder.DividerItemViewBinder;
import com.github.rubensousa.bottomsheetbuilder.viewbinder.FooterBinder;
import com.github.rubensousa.bottomsheetbuilder.viewbinder.HeaderBinder;
import com.github.rubensousa.bottomsheetbuilder.viewbinder.MenuItemViewBinder;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    public static final String STATE_SIMPLE = "state_simple";
    public static final String STATE_HEADER = "state_header";
    public static final String STATE_GRID = "state_grid";
    public static final String STATE_LONG = "state_long";
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    private BottomSheetMenuDialog mBottomSheetDialog;
    private BottomSheetBehavior mBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        View bottomSheet = createView();

        mBehavior = BottomSheetBehavior.from(bottomSheet);
        mBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    fab.show();
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        // Avoid leaked windows
        if (mBottomSheetDialog != null) {
            mBottomSheetDialog.dismiss();
        }
        super.onDestroy();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.fab)
    public void onFabClick() {
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        fab.hide();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.showViewBtn)
    public void onShowViewClick() {
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }


    @OnClick(R.id.showNewDialog)
    public void onShowNewDialog() {

        List<Object> items = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            items.add(new BsMenuItem(i, null, "Share"));
        }

        MenuItemViewBinder menuItemViewBinder = new MenuItemViewBinder.Builder()
                .isGridLayout(false)
                .gravity(Gravity.CENTER)
                .mListener(new MenuItemViewBinder.BottomSheetItemClickListener() {
                    @Override
                    public void onBottomSheetItemClick(BsMenuItem item) {

                    }
                })
                .build();

        HorizontalDividerItemDecoration divider = new HorizontalDividerItemDecoration.Builder(this)
                .colorResId(R.color.divider)
                .sizeResId(R.dimen.d_0_5)
                .marginResId(R.dimen.d_90)
                .build();

        ContentRecyclerBinder binder = new ContentRecyclerBinder.Builder(this)
                .itemDecoration(divider)
                .register(BsDividerItem.class, new DividerItemViewBinder())
                .register(BsMenuItem.class, menuItemViewBinder)
                .data(items)
                .build();


        mBottomSheetDialog = new BsBuilder(this, R.style.AppTheme_BottomSheetDialog_Custom)
                .addHeaderView(new HeaderBinder.Builder().title("TestHeader").build())
                .addContentView(binder)
                .expandOnStart(true)
                .setAppBarLayout(appBarLayout)
                .addFooterView(new FooterBinder.Builder().title("TestFooter").build())
                .createDialog();

        mBottomSheetDialog.show();
    }

    @OnClick(R.id.showMenuDialog)
    public void showMenuDialog() {


        MenuItemViewBinder menuItemViewBinder = new MenuItemViewBinder.Builder()
                .isGridLayout(false)
                .mListener(new MenuItemViewBinder.BottomSheetItemClickListener() {
                    @Override
                    public void onBottomSheetItemClick(BsMenuItem item) {

                    }
                })
                .build();

        ContentRecyclerBinder binder = new ContentRecyclerBinder.Builder(this)
                .register(BsMenuItem.class, menuItemViewBinder)
                .data(R.menu.menu_bottom_simple_sheet)
                .build();

        mBottomSheetDialog = new BsBuilder(this, R.style.AppTheme_BottomSheetDialog_Custom)
                .addHeaderView(new HeaderBinder.Builder().title("TestHeader").build())
                .addContentView(binder)
                .setAppBarLayout(appBarLayout)
                .createDialog();

        mBottomSheetDialog.show();
    }

    @OnClick(R.id.showNewGridDialog)
    public void showNewGridDialog() {

        int columns = getResources().getInteger(R.integer.bottomsheet_grid_columns);
        GridLayoutManager layoutManager = new GridLayoutManager(this, columns);

        float margin = getResources().getDimensionPixelSize(R.dimen.bottomsheet_grid_horizontal_margin);

        DisplayMetrics dm = new DisplayMetrics();
        //获取屏幕信息
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int screenWidth = dm.widthPixels;
        int screenHeigh = dm.heightPixels;
        int itemWidth = (int) ((screenWidth - 2 * margin) / columns);

        MenuItemViewBinder menuItemViewBinder = new MenuItemViewBinder.Builder()
                .isGridLayout(true)
                .mItemWidth(itemWidth)
                .mListener(new MenuItemViewBinder.BottomSheetItemClickListener() {
                    @Override
                    public void onBottomSheetItemClick(BsMenuItem item) {

                    }
                })
                .build();


        ContentRecyclerBinder binder = new ContentRecyclerBinder.Builder(this)
                .register(BsMenuItem.class, menuItemViewBinder)
                .layoutManager(layoutManager)
                .data(R.menu.menu_bottom_grid_sheet)
                .build();


        mBottomSheetDialog = new BsBuilder(this, R.style.AppTheme_BottomSheetDialog_Custom)
                .addHeaderView(new HeaderBinder.Builder().title("TestHeader").build())
                .addContentView(binder)
                .expandOnStart(true)
                .setAppBarLayout(appBarLayout)
                .createDialog();

        mBottomSheetDialog.show();
    }


    public View createView() {
        int columns = getResources().getInteger(R.integer.bottomsheet_grid_columns);
        GridLayoutManager layoutManager = new GridLayoutManager(this, columns);
        float margin = getResources().getDimensionPixelSize(R.dimen.bottomsheet_grid_horizontal_margin);
        DisplayMetrics dm = new DisplayMetrics();
        //获取屏幕信息
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        int itemWidth = (int) ((screenWidth - 2 * margin) / columns);

        MenuItemViewBinder menuItemViewBinder = new MenuItemViewBinder.Builder()
                .isGridLayout(true)
                .mItemWidth(itemWidth)
                .mListener(new MenuItemViewBinder.BottomSheetItemClickListener() {
                    @Override
                    public void onBottomSheetItemClick(BsMenuItem item) {

                    }
                })
                .build();

        ContentRecyclerBinder binder = new ContentRecyclerBinder.Builder(this)
                .register(BsMenuItem.class, menuItemViewBinder)
                .layoutManager(layoutManager)
                .data(R.menu.menu_bottom_grid_sheet)
                .build();


        return new BsBuilder(this, coordinatorLayout)
                .addContentView(binder)
                .expandOnStart(true)
                .setAppBarLayout(appBarLayout)
                .createView();


    }

}