package com.example.adria.myappmvp.task;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * Created by adria on 14.05.2018.
 */

public class MyDragShadowBuilder extends View.DragShadowBuilder
{
    private static Drawable shadow;

    public MyDragShadowBuilder(View v)
    {
        super(v);

        shadow = new ColorDrawable(Color.LTGRAY);
    }

    @Override
    public void onProvideShadowMetrics(Point outShadowSize, Point outShadowTouchPoint) {

        int width, height;

        width = getView().getWidth() / 2;
        height = getView().getHeight() / 2;
        shadow.setBounds(0,0,width,height);

        outShadowSize.set(width, height);
        outShadowTouchPoint.set(width/2, height/2);

    }

    @Override
    public void onDrawShadow(Canvas canvas) {
        shadow.draw(canvas);
    }
}
