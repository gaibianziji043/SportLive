package com.ft.base.smartrefresh.header;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;


import androidx.annotation.NonNull;

import com.ft.base.net.R;
import com.ft.base.smartrefresh.api.RefreshHeader;
import com.ft.base.smartrefresh.api.RefreshKernel;
import com.ft.base.smartrefresh.api.RefreshLayout;
import com.ft.base.smartrefresh.internal.InternalAbstract;
import com.ft.base.smartrefresh.util.SmartUtil;

import static android.view.View.MeasureSpec.EXACTLY;
import static android.view.View.MeasureSpec.makeMeasureSpec;

/**
 * 虚假的 Header
 * 用于 正真的 Header 在 RefreshLayout 外部时，
 * 使用本虚假的 FalsifyHeader 填充在 RefreshLayout 内部
 * 具体使用方法 参考 纸飞机（FlyRefreshHeader）
 */
public class FalsifyHeader extends InternalAbstract implements RefreshHeader {

    protected RefreshKernel mRefreshKernel;

    //<editor-fold desc="FalsifyHeader">
    public FalsifyHeader(Context context) {
        this(context, null);
    }

    public FalsifyHeader(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        final View thisView = this;
        if (thisView.isInEditMode()) {//这段代码在运行时不会执行，只会在Studio编辑预览时运行，不用在意性能问题
            final int d = SmartUtil.dp2px(5);
            final Context context = thisView.getContext();

            Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(0xcccccccc);
            paint.setStrokeWidth(SmartUtil.dp2px(1));
            paint.setPathEffect(new DashPathEffect(new float[]{d, d, d, d}, 1));
            canvas.drawRect(d, d, thisView.getWidth() - d, thisView.getBottom() - d, paint);

            TextView textView = new TextView(context);
            textView.setText(context.getString(R.string.srl_component_falsify, getClass().getSimpleName(), SmartUtil.px2dp(thisView.getHeight())));
            textView.setTextColor(0xcccccccc);
            textView.setGravity(Gravity.CENTER);
            //noinspection UnnecessaryLocalVariable
            View view = textView;
            view.measure(makeMeasureSpec(thisView.getWidth(), EXACTLY), makeMeasureSpec(thisView.getHeight(), EXACTLY));
            view.layout(0, 0, thisView.getWidth(), thisView.getHeight());
            view.draw(canvas);
        }
    }
    //</editor-fold>

    //<editor-fold desc="RefreshHeader">
    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int maxDragHeight) {
        mRefreshKernel = kernel;
    }

    @Override
    public void onReleased(@NonNull RefreshLayout layout, int height, int maxDragHeight) {
        if (mRefreshKernel != null) {
            /*
             * 2020-3-15 BUG修复
             * https://github.com/scwang90/SmartRefreshLayout/issues/1018
             * 强化了 closeHeaderOrFooter 的关闭逻辑，帮助 Header 取消刷新
             * FalsifyHeader 是不能触发刷新的
             */
            layout.closeHeaderOrFooter();
//            mRefreshKernel.setState(RefreshState.None);
//            //onReleased 的时候 调用 setState(RefreshState.None); 并不会立刻改变成 None
//            //而是先执行一个回弹动画，RefreshFinish 是介于 Refreshing 和 None 之间的状态
//            //RefreshFinish 用于在回弹动画结束时候能顺利改变为 None
//            mRefreshKernel.setState(RefreshState.RefreshFinish);
        }
    }
    //</editor-fold>

}
