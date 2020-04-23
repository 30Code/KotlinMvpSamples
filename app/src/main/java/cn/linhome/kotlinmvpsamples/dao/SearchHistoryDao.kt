package cn.linhome.kotlinmvpsamples.dao

import android.text.TextUtils
import cn.linhome.kotlinmvpsamples.model.bean.SearchHistoryBean
import cn.linhome.lib.cache.FDisk
import java.util.*

/**
 * des :
 * Created by 30Code
 * date : 2020/4/23
 */
class SearchHistoryDao {

    private val mLinkedHashSetBean = LinkedHashSet<SearchHistoryBean>()

    constructor(){}

    /**
     * 保存实体到本地
     */
    private fun save() {
        FDisk.openInternal().cacheObject().put(this)
    }

    /**
     * 保存搜索信息
     *
     * @param bean
     */
    fun put(bean: SearchHistoryBean) {
        val key = bean.key
        if (TextUtils.isEmpty(key)) {
            return
        }
        mLinkedHashSetBean.add(bean)
        save()
    }

    /**
     * 返回全部的搜索记录
     *
     * @return
     */
    fun query(): ArrayList<SearchHistoryBean> {
        return ArrayList(mLinkedHashSetBean)
    }

    /**
     * 移除key对应的记录
     * @param bean
     */
    fun remove(bean: SearchHistoryBean) {
        mLinkedHashSetBean.remove(bean)
        save()
    }

    /**
     * 清除所有搜索记录信息
     */
    fun clear() {
        mLinkedHashSetBean.clear()
        save()
    }

    companion object {
        fun get(): SearchHistoryDao {
            var dao = FDisk.openInternal().cacheObject().get(SearchHistoryDao::class.java)
            if (dao == null) {
                dao = SearchHistoryDao()
                dao.save()
            }
            return dao
        }
    }
}