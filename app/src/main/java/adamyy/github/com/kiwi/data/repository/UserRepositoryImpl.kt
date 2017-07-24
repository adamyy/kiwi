package adamyy.github.com.kiwi.data.repository

import adamyy.github.com.kiwi.data.common.AsyncResult
import adamyy.github.com.kiwi.data.common.wrapInAsyncResult
import adamyy.github.com.kiwi.data.entity.User
import adamyy.github.com.kiwi.data.source.network.UserApi
import io.reactivex.Observable

class UserRepositoryImpl(val userApi: UserApi): UserRepository {

    override fun getUserById(id: String): Observable<AsyncResult<User>> =
            userApi.getUserById(id).wrapInAsyncResult {}

    override fun getUserByName(name: String): Observable<AsyncResult<User>> =
            userApi.getUserByName(name).wrapInAsyncResult {}

    override fun verifyCredentials(): Observable<AsyncResult<User>> =
            userApi.verifyCredentials().wrapInAsyncResult {}

}
