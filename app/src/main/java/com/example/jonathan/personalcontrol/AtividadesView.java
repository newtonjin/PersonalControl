package com.example.jonathan.personalcontrol;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by Thainara on 05/10/2015.
 */
public class AtividadesView extends FrameLayout {
    public AtividadesView(Context context) {
        super(context);
        init();
    }

    public AtividadesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        inflate(getContext(), R.layout.view_atividades, this);
    }
}

