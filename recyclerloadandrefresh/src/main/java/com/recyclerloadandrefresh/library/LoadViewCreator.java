package com.recyclerloadandrefresh.library;

/**
 * Created by Administrator on 2017/8/21 0021.
 */

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 *  @作者 Administrator
 *  @日期 2017/8/21 0021
 *  @desc 上拉加载辅助类
 */
public interface LoadViewCreator {
    /**
     * 获取上拉加载的View
     * @param context
     * @param parent
     * @return
     */
    View getLoadView(Context context, ViewGroup parent);

    /**
     * 正在向上拉
     * @param currentRefreshHeight 上拉的距离
     * @param RefreshViewHeight    上拉加载View的高度
     * @param status               上拉加载状态
     */
    void onPull(int currentRefreshHeight, int RefreshViewHeight,int status);

    /**
     * 正在加载
     */
    void onLoading();

    /**
     * 停止加载
     */
    void onStopLoad();

}
