package adamyy.github.com.kiwi.data.repository

import adamyy.github.com.kiwi.data.common.SingleResult
import adamyy.github.com.kiwi.data.entity.User
import io.reactivex.Observable

interface UserRepository {

    fun verifyCredentials(): Observable<SingleResult<User>>

    fun getUserById(id: String): Observable<SingleResult<User>>

    fun getUserByName(name: String): Observable<SingleResult<User>>

}