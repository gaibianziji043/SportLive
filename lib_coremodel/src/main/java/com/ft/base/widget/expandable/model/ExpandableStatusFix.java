package com.ft.base.widget.expandable.model;

import com.ft.base.widget.expandable.StatusType;

/**
 * @desc: 为ExpandableTextView添加展开和收回状态的记录
 */
public interface ExpandableStatusFix {
    void setStatus(StatusType status);

    StatusType getStatus();
}
