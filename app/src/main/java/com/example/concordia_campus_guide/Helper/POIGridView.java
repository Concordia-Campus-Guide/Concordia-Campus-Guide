package com.example.concordia_campus_guide.Helper;

import android.util.AttributeSet;
import android.widget.GridView;
import android.content.Context;

public class POIGridView extends GridView {

    public POIGridView(Context context) {
        super(context);
    }

    public POIGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public POIGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightSpec;

        if (getLayoutParams().height == LayoutParams.WRAP_CONTENT) {

            // The two leftmost bits in the height measure spec have
            // a special meaning, hence we can't use them to describe height.
            heightSpec = MeasureSpec.makeMeasureSpec(
                    Integer.MAX_VALUE >>2, MeasureSpec.AT_MOST);
        }
        else {
            // Any other height should be respected as is.
            heightSpec = heightMeasureSpec;
        }

        super.onMeasure(widthMeasureSpec, heightSpec);
    }
}