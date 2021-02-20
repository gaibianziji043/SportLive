package com.ft.base.binding.viewadapter.expandableTextView;

import androidx.databinding.BindingAdapter;

import com.ft.base.widget.expandable.ExpandableNewTextView;
import com.ft.base.widget.expandable.ExpandableTextView;

public class ViewAdapter {
    @BindingAdapter(value = {"originText","maxLines","initWidth","closeInNewLine"},requireAll = true)
    public static void setExpandableAttrs(ExpandableTextView expandableTextView,String originText,int maxLines,int initWidth, boolean closeInNewLine){
        expandableTextView.setInitWidth(initWidth);
        expandableTextView.setMaxLines(maxLines);
        expandableTextView.setCloseInNewLine(closeInNewLine);
        expandableTextView.setOriginalText(originText);
        /*  expandableTextView.setHasAnimation(true);*/
        /*expandableTextView.setOpenSuffixColor(getResources().getColor(R.color.colorAccent));
        expandableTextView.setCloseSuffixColor(getResources().getColor(R.color.colorAccent));*/
    }

    @BindingAdapter(value = {"originText"},requireAll = true)
    public static void setExpandableAttrs(ExpandableNewTextView textView, String originText){
        textView.setContent(originText);
    }

}
