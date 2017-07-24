package adamyy.github.com.kiwi.data.repository

import adamyy.github.com.kiwi.data.common.AsyncResult
import adamyy.github.com.kiwi.data.entity.User
import io.reactivex.Observable

interface UserRepository {

    fun verifyCredentials(): Observable<AsyncResult<User>>

    fun getUserById(id: String): Observable<AsyncResult<User>>

    fun getUserByName(name: String): Observable<AsyncResult<User>>

}