package com.recyclerloadandrefresh.library;

/**
 * Created by Administrator on 2017/8/22 0022.
 */
/**
 *  @作者 Administrator
 *  @日期 2017/8/22 0022
 *  @desc 多种布局时,Item的点击事件的多样性
 */
public interface OnItemClickListener<T> {
    /**
     * 根据数据或者当前位置执行相应的Item点击事件
     * @param item
     * @param pos
     */
    void OnItemClick(T item,int pos);
}
