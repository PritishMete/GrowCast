package com.example.growcast;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentContainerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;

public final class ShopBinding implements ViewBinding {
    @NonNull
    private final RelativeLayout rootView;

    @NonNull
    public final FragmentContainerView map;

    private ShopBinding(@NonNull RelativeLayout rootView, @NonNull FragmentContainerView map) {
        this.rootView = rootView;
        this.map = map;
    }

    @Override
    @NonNull
    public RelativeLayout getRoot() {
        return rootView;
    }

    @NonNull
    public static ShopBinding inflate(@NonNull LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    @NonNull
    public static ShopBinding inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent,
                                      boolean attachToParent) {
        View root = inflater.inflate(R.layout.shop, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    @NonNull
    public static ShopBinding bind(@NonNull View rootView) {
        // The body of this method is generated in a way you would not otherwise write.
        // This is done to optimize the compiled bytecode for size and performance.
        int id;
        missingId: {
            id = R.id.map;
            FragmentContainerView map = ViewBindings.findChildViewById(rootView, id);
            if (map == null) {
                break missingId;
            }

            return new ShopBinding((RelativeLayout) rootView, map);
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}

