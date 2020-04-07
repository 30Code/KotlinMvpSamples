package cn.linhome.kotlinmvpsamples.view.pulltorefresh;

import cn.linhome.lib.pulltorefresh.BuildConfig;
import cn.linhome.lib.pulltorefresh.FPullToRefreshView;
import cn.linhome.lib.pulltorefresh.PullToRefreshView;

/**
 * 对下拉刷新进行包裹，避免更换框架的时候要修改大量代码
 */
public class PullToRefreshViewWrapper implements IPullToRefreshViewWrapper<FPullToRefreshView>
{
    private FPullToRefreshView mPullToRefreshView;
    private OnRefreshCallbackWrapper mOnRefreshCallbackWrapper;

    @Override
    public void setPullToRefreshView(FPullToRefreshView pullToRefreshView)
    {
        if (mPullToRefreshView != pullToRefreshView)
        {
            mPullToRefreshView = pullToRefreshView;

            if (pullToRefreshView != null)
            {
                pullToRefreshView.setOnRefreshCallback(mInternalOnRefreshCallback);
                pullToRefreshView.setDebug(BuildConfig.DEBUG);
            }
        }
    }

    private PullToRefreshView.OnRefreshCallback mInternalOnRefreshCallback = new PullToRefreshView.OnRefreshCallback()
    {
        @Override
        public void onRefreshingFromHeader(PullToRefreshView view)
        {
            if (mOnRefreshCallbackWrapper != null)
            {
                mOnRefreshCallbackWrapper.onRefreshingFromHeader();
            }
        }

        @Override
        public void onRefreshingFromFooter(PullToRefreshView view)
        {
            if (mOnRefreshCallbackWrapper != null)
            {
                mOnRefreshCallbackWrapper.onRefreshingFromFooter();
            }
        }
    };

    @Override
    public FPullToRefreshView getPullToRefreshView()
    {
        return mPullToRefreshView;
    }

    @Override
    public void setOnRefreshCallbackWrapper(OnRefreshCallbackWrapper onRefreshCallbackWrapper)
    {
        mOnRefreshCallbackWrapper = onRefreshCallbackWrapper;
    }

    @Override
    public void setModePullFromHeader()
    {
        getPullToRefreshView().setMode(PullToRefreshView.Mode.PULL_FROM_HEADER);
    }

    @Override
    public void setModePullFromFooter()
    {
        getPullToRefreshView().setMode(PullToRefreshView.Mode.PULL_FROM_FOOTER);
    }

    @Override
    public void setModeDisable()
    {
        getPullToRefreshView().setMode(PullToRefreshView.Mode.PULL_DISABLE);
    }

    @Override
    public void startRefreshingFromHeader()
    {
        getPullToRefreshView().startRefreshingFromHeader();
    }

    @Override
    public void startRefreshingFromFooter()
    {
        getPullToRefreshView().startRefreshingFromFooter();
    }

    @Override
    public boolean isRefreshing()
    {
        return getPullToRefreshView().isRefreshing();
    }

    @Override
    public void stopRefreshing()
    {
        getPullToRefreshView().stopRefreshing();
    }
}
