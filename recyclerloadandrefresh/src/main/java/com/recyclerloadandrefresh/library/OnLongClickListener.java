package com.recyclerloadandrefresh.library;

/**
 * Created by Administrator on 2017/8/22 0022.
 */
/**
 *  @作者 Administrator
 *  @日期 2017/8/22 0022
 *  @desc  多种布局时,Item的长按事件的多样性
 */
public interface OnLongClickListener<T> {
    /**
     * 根据数据或者当前位置执行相应的Item长按事件
     * @param item
     * @param pos
     */
    boolean OnLongClick(T item,int pos);
}
