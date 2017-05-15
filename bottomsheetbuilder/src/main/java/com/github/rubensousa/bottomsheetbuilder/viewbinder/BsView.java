package com.github.rubensousa.bottomsheetbuilder.viewbinder;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by dinglei on 2017/5/12.
 */

public interface BsView {
    View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent);

    void updateView();


}
