package com.example.jonathan.personalcontrol;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by Thainara on 28/09/2015.
 */

public class PerfilView extends FrameLayout {
    public PerfilView(Context context) {
        super(context);
        init();
    }

    public PerfilView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        inflate(getContext(), R.layout.view_perfil, this);
    }
}
