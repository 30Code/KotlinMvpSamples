package cn.linhome.kotlinmvpsamples.view.pulltorefresh;

/**
 * des:
 * Created by 30Code
 * on 2019/1/1
 */
public interface IPullToRefreshViewWrapper<T>
{
    /**
     * 设置下拉刷新的View
     *
     * @param pullToRefreshView
     */
    void setPullToRefreshView(T pullToRefreshView);

    /**
     * 返回设置的下拉刷新View
     *
     * @return
     */
    T getPullToRefreshView();

    /**
     * 设置回调
     *
     * @param onRefreshCallbackWrapper
     */
    void setOnRefreshCallbackWrapper(OnRefreshCallbackWrapper onRefreshCallbackWrapper);

    /**
     * 设置只能从头部下拉刷新
     */
    void setModePullFromHeader();

    /**
     * 设置只能上拉加载
     */
    void setModePullFromFooter();

    /**
     * 设置不可以下拉刷新或者上拉加载更多
     */
    void setModeDisable();

    /**
     * 设置HeaderView处处于刷新状态
     */
    void startRefreshingFromHeader();

    /**
     * 设置FooterView处处于刷新状态
     */
    void startRefreshingFromFooter();

    /**
     * 是否正在刷新中
     *
     * @return
     */
    boolean isRefreshing();

    /**
     * 停止刷新
     */
    void stopRefreshing();

    interface OnRefreshCallbackWrapper
    {
        /**
         * 下拉触发刷新回调
         */
        void onRefreshingFromHeader();

        /**
         * 上拉触发刷新回调
         */
        void onRefreshingFromFooter();
    }
}
