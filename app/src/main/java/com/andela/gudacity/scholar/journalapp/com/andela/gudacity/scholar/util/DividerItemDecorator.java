package com.andela.gudacity.scholar.journalapp.com.andela.gudacity.scholar.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class DividerItemDecorator extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = new int[] {
        android.R.attr.listDivider
    };

    private Drawable mDivider;

    public DividerItemDecorator(Context context) {
        super();
        final TypedArray typedArray = context.obtainStyledAttributes(DividerItemDecorator.ATTRS);
        mDivider = typedArray.getDrawable(0);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);



    }
}
