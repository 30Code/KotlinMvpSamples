package cn.linhome.kotlinmvpsamples.appview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexboxLayoutManager;

import cn.linhome.kotlinmvpsamples.R;
import cn.linhome.kotlinmvpsamples.adapter.TagFlexBoxAdapter;
import cn.linhome.kotlinmvpsamples.base.BaseAppView;
import cn.linhome.kotlinmvpsamples.interfaces.GetTabTagInterface;
import cn.linhome.lib.utils.extend.FDrawable;
import cn.linhome.library.view.SDRecyclerView;

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/22
 */
public class TagFlexBoxView<T extends GetTabTagInterface> extends BaseAppView
{
    private SDRecyclerView mRecyclerView;
    private TagFlexBoxAdapter<T> mTagFlexBoxAdapter;
    private boolean mIsSetStyle;//只设置一次

    public TagFlexBoxView(Context context)
    {
        super(context);
        init();
    }

    public TagFlexBoxView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }
    
    private void init()
    {
        mRecyclerView = new SDRecyclerView(getActivity());
        mRecyclerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setContentView(mRecyclerView);
        mRecyclerView.setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        mRecyclerView.setLayoutManager(new FlexboxLayoutManager(getActivity()));
        mTagFlexBoxAdapter = new TagFlexBoxAdapter<>(getActivity());
        mRecyclerView.setAdapter(mTagFlexBoxAdapter);
    }

    public TagFlexBoxAdapter<T> getAdapter()
    {
        return mTagFlexBoxAdapter;
    }

    public SDRecyclerView getRecyclerView()
    {
        return mRecyclerView;
    }

    /**
     * 搜索样式
     */
    public void setSearchStyle()
    {
        if (mIsSetStyle) return;
        mIsSetStyle = true;
        mRecyclerView.addDividerVertical(new FDrawable().size((int) getResources().getDimension(R.dimen.res_px_21)).color(getResources().getColor(R.color.transparent)));
        mRecyclerView.addDividerHorizontal(new FDrawable().size((int) getResources().getDimension(R.dimen.res_px_16)).color(getResources().getColor(R.color.transparent)));
    }
}
