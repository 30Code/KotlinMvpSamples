package cn.linhome.kotlinmvpsamples.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import cn.linhome.kotlinmvpsamples.R;
import cn.linhome.kotlinmvpsamples.view.pulltorefresh.PullToRefreshViewWrapper;
import cn.linhome.lib.pulltorefresh.FPullToRefreshView;
import cn.linhome.lib.statelayout.FStateLayout;
import cn.linhome.library.view.SDAppView;


public class BaseAppView extends SDAppView
{
    public BaseAppView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public BaseAppView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public BaseAppView(Context context)
    {
        super(context);
    }

    private FStateLayout mStateLayout;
    private PullToRefreshViewWrapper mPullToRefreshViewWrapper;

    /**
     * 返回状态布局
     *
     * @return
     */
    public final FStateLayout getStateLayout()
    {
        if (mStateLayout == null)
        {
            View stateLayout = findViewById(R.id.view_state_layout);
            if (stateLayout instanceof FStateLayout)
            {
                setStateLayout((FStateLayout) stateLayout);
            }
        }
        return mStateLayout;
    }

    /**
     * 设置状态布局
     *
     * @param stateLayout
     */
    public final void setStateLayout(FStateLayout stateLayout)
    {
        if (mStateLayout != stateLayout)
        {
            mStateLayout = stateLayout;
            if (stateLayout != null)
            {
                stateLayout.getEmptyView().setContentView(R.layout.view_state_empty_content);
                stateLayout.getErrorView().setContentView(R.layout.view_state_error_net);
            }
        }
    }

    /**
     * 返回下拉刷新包裹对象
     *
     * @return
     */
    public final PullToRefreshViewWrapper getPullToRefreshViewWrapper()
    {
        if (mPullToRefreshViewWrapper == null)
        {
            mPullToRefreshViewWrapper = new PullToRefreshViewWrapper();

            View pullToRefreshView = findViewById(R.id.view_pull_to_refresh);
            if (pullToRefreshView instanceof FPullToRefreshView)
            {
                mPullToRefreshViewWrapper.setPullToRefreshView((FPullToRefreshView) pullToRefreshView);
            }
        }
        return mPullToRefreshViewWrapper;
    }
}
