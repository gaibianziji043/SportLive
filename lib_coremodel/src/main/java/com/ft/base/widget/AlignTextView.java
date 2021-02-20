package com.ft.base.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * TextView中英文混排自动换行
 *//*
public class AlignTextView extends TextView {
    *//**
     * 文本是否变化
     *//*
    boolean mIsDirty = false;
    AdaptableText mAdaptableText;

    public AlignTextView(Context context) {
        super(context);

        requestLayoutFor();
    }

    public AlignTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        requestLayoutFor();
    }

    public AlignTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        requestLayoutFor();
    }

    private void requestLayoutFor() {
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (getWidth() > 0) {
                    if (Build.VERSION.SDK_INT >= 16) {
                        getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    } else {
                        getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }

                    requestLayout();
                }
            }
        });
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int before, int after) {
        super.onTextChanged(text, start, before, after);
        mIsDirty = true;
    }

    @Override
    public int getLineCount() {
        AdaptableText helper = getAdaptableText();
        return null == helper ? 0 : helper.getLineCount();
    }

    @Override
    public void setMaxLines(int maxLines) {
        super.setMaxLines(maxLines);
        mIsDirty = true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = measureWidth(widthMeasureSpec);
        int h = measureHeight(heightMeasureSpec);
        setMeasuredDimension(w, h);
    }

    private int measureWidth(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            String text = getText().toString();
            result = (int) getPaint().measureText(text) + getPaddingLeft()
                    + getPaddingRight();
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }

        return result;
    }

    @SuppressLint("NewApi")
    private int measureHeight(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = 0;
            String text = getText().toString();
            if (!TextUtils.isEmpty(text)) {
                int lineCount = getLineCount();
                int maxLines = getMaxLines();
                lineCount = Math.min(maxLines, lineCount);
                int lineHeight = getLineHeight();
                result = lineCount * lineHeight;
            }
        }

        return result;
    }

    @Override
    @SuppressLint("NewApi")
    protected void onDraw(Canvas canvas) {
        getAdaptableText();
        if (mIsDirty) {
            mIsDirty = false;
            String text = getText().toString();
            int maxLines = getMaxLines();
            if (!mAdaptableText.getText().equals(text))
                mAdaptableText.setText(text);
            if (mAdaptableText.getMaxLines() != maxLines)
                mAdaptableText.setMaxLines(maxLines);
        }
        mAdaptableText.draw(canvas);
    }

    @SuppressLint("NewApi")
    private AdaptableText getAdaptableText() {
        if (mAdaptableText == null) {
            int measuredWidth = getMeasuredWidth();
            if (measuredWidth <= 0) {
                return null;
            }
            TextPaint paint = getPaint();
            paint.setColor(getCurrentTextColor());
            paint.drawableState = getDrawableState();
            int paddingLeft = getPaddingLeft();
            int paddingRight = getPaddingRight();
            int lineHeight = getLineHeight();
            String text = getText().toString();
            mAdaptableText = new AdaptableText(text, paint, measuredWidth - paddingLeft - paddingRight, lineHeight);
            mAdaptableText.setMaxLines(getMaxLines());
        }
        return mAdaptableText;
    }

    private class AdaptableText {
        *//**
         * 文本宽高
         *//*
        int mLineWidth, mLineHeight;
        *//**
         * 最大行数
         *//*
        int mMaxLines;
        private TextPaint mPaint;
        private String mText;
        *//**
         * 存储分割后的每行
         *//*
        ArrayList<String> strs = new ArrayList<String>();

        public AdaptableText(String text, TextPaint paint, int lineWidth, int lineHeight) {
            mLineHeight = lineHeight;
            mLineWidth = lineWidth;
            mPaint = paint;
            mText = text;
            parseText();
        }

        *//**
         * 根据控件宽度，计算得出每行的字符串
         *//*
        private void parseText() {
            if (mLineWidth > 0 && mLineHeight > 0 && mText.length() > 0) {
                strs.clear();
                int start = 0;//行起始Index
                int curLineWidth = 0;//当前行宽
                for (int i = 0; i < mText.length(); i++) {
                    char ch = mText.charAt(i);//获取当前字符
                    float[] widths = new float[1];
                    String srt = String.valueOf(ch);
                    mPaint.getTextWidths(srt, widths);//获取这个字符的宽度
                    if (ch == '\n') {//如果是换行符，则当独一行
                        strs.add(mText.substring(start, i));
                        start = i + 1;
                        curLineWidth = 0;
                    } else {
                        curLineWidth += (int) (Math.ceil(widths[0]));//计算当前宽度
                        if (curLineWidth > mLineWidth) {//直到当前行宽度大于控件宽度，截取为一行
                            strs.add(mText.substring(start, i));
                            start = i;
                            i--;
                            curLineWidth = 0;
                        } else {
                            if (i == (mText.length() - 1)) {//剩余的单独一行
                                String s = mText.substring(start, mText.length());
                                if (!TextUtils.isEmpty(s)) {
                                    strs.add(s);
                                }
                            }
                        }
                    }
                }
            }
        }

        public void draw(Canvas canvas) {
            int lines = mMaxLines > 0 && mMaxLines <= strs.size() ? mMaxLines : strs.size();
            for (int i = 0; i < lines; i++) {
                String text = strs.get(i);
                //如果是最大行的最后一行但不是真实的最后一行则自动添加省略号
                if (i == lines - 1 && i < strs.size() - 1 && text.length() > 3)
                    text = text.substring(0, text.length() - 3) + "...";
                canvas.drawText(text, getPaddingLeft(), getPaddingTop() + mPaint.getTextSize() + mLineHeight * i, mPaint);
            }
        }

        public void setText(String text) {
            mText = text;
            parseText();
        }

        public String getText() {
            return mText;
        }

        public void setMaxLines(int maxLines) {
            mMaxLines = maxLines;
        }

        public int getMaxLines() {
            return mMaxLines;
        }

        public int getLineCount() {
            return strs.size();
        }

        public int getLineEnd(int line) {
            int size = 0;
            for (int i = 0; i <= line; i++) {
                size += strs.get(i).length();
            }
            return size;
        }
    }
}
*//*
public class AlignTextView extends TextView {
    private boolean mEnabled = true;

          public AlignTextView(Context context) {
               super(context);
           }

           public AlignTextView(Context context, AttributeSet attrs) {
                super(context, attrs);
            }

          public AlignTextView(Context context, AttributeSet attrs, int defStyle) {
               super(context, attrs, defStyle);
           }

             public void setAutoSplitEnabled(boolean enabled) {
               mEnabled = enabled;
           }

   @Override
     protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY
                    && MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY
                   && getWidth() > 0
                    && getHeight() > 0
                && mEnabled) {
                      String newText = autoSplitText(this);
                     if (!TextUtils.isEmpty(newText)) {
                              setText(newText);
                         }
                  }
               super.onMeasure(widthMeasureSpec, heightMeasureSpec);
         }

          private String autoSplitText(final TextView tv) {
             final String rawText = tv.getText().toString(); //原始文本
          final Paint tvPaint = tv.getPaint(); //paint，包含字体等信息
                final float tvWidth = tv.getWidth() - tv.getPaddingLeft() - tv.getPaddingRight(); //控件可用宽度

               //将原始文本按行拆分
                String [] rawTextLines = rawText.replaceAll("\r", "").split("\n");
               StringBuilder sbNewText = new StringBuilder();
                 for (String rawTextLine : rawTextLines) {
                       if (tvPaint.measureText(rawTextLine) <= tvWidth) {
                                //如果整行宽度在控件可用宽度之内，就不处理了
                               sbNewText.append(rawTextLine);
                            } else {
                                //如果整行宽度超过控件可用宽度，则按字符测量，在超过可用宽度的前一个字符处手动换行
                                 float lineWidth = 0;
                                for (int cnt = 0; cnt != rawTextLine.length(); ++cnt) {
                                        char ch = rawTextLine.charAt(cnt);
                                       lineWidth += tvPaint.measureText(String.valueOf(ch));
                                     if (lineWidth <= tvWidth) {
                                                sbNewText.append(ch);
                                            } else {
                                              sbNewText.append("\n");
                                               lineWidth = 0;
                                               --cnt;
                                            }
                                    }
                           }
                      sbNewText.append("\n");
                   }

              //把结尾多余的\n去掉
                if (!rawText.endsWith("\n")) {
                       sbNewText.deleteCharAt(sbNewText.length() - 1);
                   }

                 return sbNewText.toString();
             }

 }
*/
public class AlignTextView extends TextView {

    public AlignTextView(Context context) {
        super(context);
    }

    public AlignTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 获取用于显示当前文本的布局
        Layout layout = getLayout();
        if (layout == null) return;
        final int lineCount = layout.getLineCount();
        if (lineCount < 2) {
            //想只有一行 则不需要转化
            super.onDraw(canvas);
            return;
        }
        Paint.FontMetrics fm = getPaint().getFontMetrics();
        int textHeight = (int) (Math.ceil(fm.descent - fm.ascent));
        textHeight = (int) (textHeight * layout.getSpacingMultiplier() + layout.getSpacingAdd());
        measureText(getMeasuredWidth(), getText(), textHeight, canvas);
    }

/**
     * 计算一行  显示的文字
     *
     * @param width      文本的宽度
     * @param text//文本内容
     * @param textHeight 文本大小
     */

    public void measureText(int width, CharSequence text, int textHeight, Canvas canvas) {
        TextPaint paint = getPaint();
        paint.setColor(getCurrentTextColor());
        paint.drawableState = getDrawableState();
        float textWidth = StaticLayout.getDesiredWidth(text, paint);
        int textLength = text.length();
        float textSize = paint.getTextSize();
        if (textWidth < width) canvas.drawText(text, 0, textLength, 0, textSize, paint);   //不需要换行
        else {
            //需要换行
            CharSequence lineOne = getOneLine(width, text, paint);
            int lineOneNum = lineOne.length();
            canvas.drawText(lineOne, 0, lineOneNum, 0, textSize, paint);
            //画第二行
            if (lineOneNum < textLength) {
                CharSequence lineTwo = text.subSequence(lineOneNum, textLength);
                lineTwo = getTwoLine(width, lineTwo, paint);
                canvas.drawText(lineTwo, 0, lineTwo.length(), 0, textSize + textHeight, paint);
            }
        }
    }

    public CharSequence getTwoLine(int width, CharSequence lineTwo, TextPaint paint) {
        int length = lineTwo.length();
        String ellipsis = "...";
        float ellipsisWidth = StaticLayout.getDesiredWidth(ellipsis, paint);
        for (int i = 0; i < length; i++) {
            CharSequence cha = lineTwo.subSequence(0, i);
            float textWidth = StaticLayout.getDesiredWidth(cha, paint);
            if (textWidth + ellipsisWidth > width) {//需要显示 ...
                lineTwo = lineTwo.subSequence(0, i - 1) + ellipsis;
                return lineTwo;
            }
        }
        return lineTwo;
    }

/**
     * 获取第一行 显示的文本
     *
     * @param width 控件宽度
     * @param text  文本
     * @param paint 画笔
     * @return
     */

    public CharSequence getOneLine(int width, CharSequence text, TextPaint paint) {
        CharSequence lineOne = null;
        int length = text.length();
        for (int i = 0; i < length; i++) {
            lineOne = text.subSequence(0, i);
            float textWidth = StaticLayout.getDesiredWidth(lineOne, paint);
            if (textWidth >= width) {
                CharSequence lastWorld = text.subSequence(i - 1, i);//最后一个字符
                float lastWidth = StaticLayout.getDesiredWidth(lastWorld, paint);//最后一个字符的宽度
                if (textWidth - width < lastWidth) {//不够显示一个字符 //需要缩放
                    lineOne = text.subSequence(0, i - 1);
                }
                return lineOne;
            }
        }
        return lineOne;
    }
}

