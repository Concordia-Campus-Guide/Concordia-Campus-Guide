package com.example.concordia_campus_guide.customViews;

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

    /**
     * This overrides the default implementation of the onMeasure method.
     * the default implementation is not aware of how much space your view
     * actually takes (since it takes into account only a combination of the
     * layout params, usually specified in the XML, and the minWidth / minHeight
     * of our view). The default method does not allow the default gridview to
     * take enough space. It will make it appear as only one row. This method makes
     * sure to allow enough space for the gridview to display its elements and was
     * a suggestion in the following blog:
     * https://stackoverflow.com/questions/8481844/gridview-height-gets-cut
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(MEASURED_SIZE_MASK, MeasureSpec.AT_MOST));
        getLayoutParams().height = getMeasuredHeight();
    }
}