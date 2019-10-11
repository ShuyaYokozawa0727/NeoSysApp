package com.example.myapplication.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

public class GetableBottomScrollView extends ScrollView {
    public interface ScrollToBottomListener {
        void onScrollToBottom(GetableBottomScrollView scrollView);
    }

    private boolean stopFlag = true;

    public void setStopFlag(boolean stopFlag) {
        this.stopFlag = stopFlag;
    }

    public boolean isStopFlag() {
        return stopFlag;
    }

    private ScrollToBottomListener scrollToBottomListener;
    private int scrollBottomMargin = 0;

    public GetableBottomScrollView(Context context) {
        super(context);
    }

    public GetableBottomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GetableBottomScrollView(Context context, AttributeSet attrs, int defs) {
        super(context, attrs, defs);
    }

    public void setScrollToBottomListener(ScrollToBottomListener listener) {
        this.scrollToBottomListener = listener;
    }

    public void setScrollBottomMargin(int value) {
        this.scrollBottomMargin = value;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        View content = getChildAt(0);
        if (scrollToBottomListener == null) return;
        if (content == null) return;
        if (y + this.getHeight() >= content.getHeight() - scrollBottomMargin) {
            scrollToBottomListener.onScrollToBottom(this);
        }
    }
}
