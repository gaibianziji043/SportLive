package com.ft.base.tabLayout.transformer;


import androidx.viewpager.widget.ViewPager;

/**
 *     ViewPager的扩展Transformer，配合SlidingScaleTabLayout使用
 *     因为字体的切换效果设置了默认的Transformer，所以扩展此接口
 */
public interface IViewPagerTransformer extends ViewPager.PageTransformer {
}
