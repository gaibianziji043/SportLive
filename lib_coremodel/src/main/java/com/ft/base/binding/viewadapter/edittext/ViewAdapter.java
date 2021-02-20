package com.ft.base.binding.viewadapter.edittext;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.databinding.BindingAdapter;

import com.ft.base.binding.command.BindingCommand;
import com.ft.base.utils.ToastUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ViewAdapter {
    @BindingAdapter(value = {"length"}, requireAll = false)
    public static void setFilters(EditText editText, int length){
        editText.setFilters(new InputFilter[]{inputFilter,new InputFilter.LengthFilter(length)});
    }

    /**
     * EditText重新获取焦点的事件绑定
     */
    @BindingAdapter(value = {"requestFocus"}, requireAll = false)
    public static void requestFocusCommand(EditText editText, final Boolean needRequestFocus) {
        if (needRequestFocus) {
            editText.setSelection(editText.getText().length());
            editText.requestFocus();
            InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        }
        editText.setFocusableInTouchMode(needRequestFocus);
    }

    /**
     * EditText输入文字改变的监听
     */
    @BindingAdapter(value = {"textChanged"}, requireAll = false)
    public static void addTextChangedListener(EditText editText, final BindingCommand<String> textChanged) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence text, int i, int i1, int i2) {
                if (textChanged != null) {
                    textChanged.execute(text.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public static InputFilter inputFilter = new InputFilter() {
        Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            Matcher emojiMatcher = emoji.matcher(source);
            if (emojiMatcher.find()) {
                ToastUtils.showLong("不支持输入表情");
                return "";
            }
            return null;
        }
    };

}
