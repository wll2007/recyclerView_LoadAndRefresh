package com.recyclerloadandrefresh.library;

/**
 * Created by Administrator on 2017/8/22 0022.
 */
/**
 *  @作者 Administrator
 *  @日期 2017/8/22 0022
 *  @desc  支持多种类型的布局,使用Adapter中getItemViewType来确定具体的某个位置的布局
 */
public interface MultiTypeSupport<T> {
    /**
     * 根据数据或者当前位置获取布局
     * @param item
     * @param position
     * @return  返回布局的资源id
     */
    int getLayoutId(T item,int position);
}
