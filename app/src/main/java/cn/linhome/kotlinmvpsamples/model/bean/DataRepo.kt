package cn.linhome.kotlinmvpsamples.model.bean

import cn.linhome.kotlinmvpsamples.interfaces.GetTabTagInterface
import com.squareup.moshi.Json
import java.io.Serializable

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/6
 */
data class HttpResult<T>(@Json(name = "data") val data : T) : BaseBean()

//轮播图
data class Banner(
    @Json(name = "desc") val desc: String,
    @Json(name = "id") val id: Int,
    @Json(name = "imagePath") val imagePath: String,
    @Json(name = "isVisible") val isVisible: Int,
    @Json(name = "order") val order: Int,
    @Json(name = "title") val title: String,
    @Json(name = "type") val type: Int,
    @Json(name = "url") val url: String
)

//文章
data class ArticleResponseBody(
    @Json(name = "curPage") val curPage: Int,
    @Json(name = "datas") var datas: MutableList<Article>,
    @Json(name = "offset") val offset: Int,
    @Json(name = "over") val over: Boolean,
    @Json(name = "pageCount") val pageCount: Int,
    @Json(name = "size") val size: Int,
    @Json(name = "total") val total: Int
)

//文章
data class Article(
    @Json(name = "apkLink") val apkLink: String,
    @Json(name = "audit") val audit: Int,
    @Json(name = "author") val author: String,
    @Json(name = "chapterId") val chapterId: Int,
    @Json(name = "chapterName") val chapterName: String,
    @Json(name = "collect") var collect: Boolean,
    @Json(name = "courseId") val courseId: Int,
    @Json(name = "desc") val desc: String,
    @Json(name = "envelopePic") val envelopePic: String,
    @Json(name = "fresh") val fresh: Boolean,
    @Json(name = "id") val id: Int,
    @Json(name = "link") val link: String,
    @Json(name = "niceDate") val niceDate: String,
    @Json(name = "niceShareDate") val niceShareDate: String,
    @Json(name = "origin") val origin: String,
    @Json(name = "prefix") val prefix: String,
    @Json(name = "projectLink") val projectLink: String,
    @Json(name = "publishTime") val publishTime: Long,
    @Json(name = "shareDate") val shareDate: String,
    @Json(name = "shareUser") val shareUser: String,
    @Json(name = "superChapterId") val superChapterId: Int,
    @Json(name = "superChapterName") val superChapterName: String,
    @Json(name = "tags") val tags: MutableList<Tag>,
    @Json(name = "title") val title: String,
    @Json(name = "type") val type: Int,
    @Json(name = "userId") val userId: Int,
    @Json(name = "visible") val visible: Int,
    @Json(name = "zan") val zan: Int,
    @Json(name = "top") var top: String
) : GetTabTagInterface {
    override fun getTabTagText(): String? = title
    override fun getTabTagId(): String? = id.toString()
}

data class Tag(
    @Json(name = "name") val name: String,
    @Json(name = "url") val url: String
)

// 公众号列表实体
data class WXChapterBean(
    @Json(name = "children") val children: MutableList<String>,
    @Json(name = "courseId") val courseId: Int,
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "order") val order: Int,
    @Json(name = "parentChapterId") val parentChapterId: Int,
    @Json(name = "userControlSetTop") val userControlSetTop: Boolean,
    @Json(name = "visible") val visible: Int
)

//知识体系
data class KnowledgeTreeBody(
    @Json(name = "children") val children: MutableList<Knowledge>,
    @Json(name = "courseId") val courseId: Int,
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "order") val order: Int,
    @Json(name = "parentChapterId") val parentChapterId: Int,
    @Json(name = "visible") val visible: Int
) : Serializable

data class Knowledge(
    @Json(name = "children") val children: List<Any>,
    @Json(name = "courseId") val courseId: Int,
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "order") val order: Int,
    @Json(name = "parentChapterId") val parentChapterId: Int,
    @Json(name = "visible") val visible: Int
) : Serializable

// 导航
data class NavigationBean(
    val articles: MutableList<Article>,
    val cid: Int,
    val name: String
)

// 项目
data class ProjectTreeBean(
    @Json(name = "children") val children: List<Any>,
    @Json(name = "courseId") val courseId: Int,
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "order") val order: Int,
    @Json(name = "parentChapterId") val parentChapterId: Int,
    @Json(name = "visible") val visible: Int
)

// 登录数据
data class LoginData(
    @Json(name = "chapterTops") val chapterTops: MutableList<String>,
    @Json(name = "collectIds") val collectIds: MutableList<String>,
    @Json(name = "email") val email: String,
    @Json(name = "icon") val icon: String,
    @Json(name = "id") val id: Int,
    @Json(name = "password") val password: String,
    @Json(name = "token") val token: String,
    @Json(name = "type") val type: Int,
    @Json(name = "username") val username: String
)