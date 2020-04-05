package cn.linhome.kotlinmvpsamples.http.exception

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/5
 */
class ApiException : RuntimeException {

    private var code: Int? = null

    constructor(throwable: Throwable, code: Int) : super(throwable) {
        this.code = code
    }

    constructor(message: String) : super(Throwable(message))
}