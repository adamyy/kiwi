package adamyy.github.com.kiwi.data.repository

import adamyy.github.com.kiwi.data.common.SingleResult
import adamyy.github.com.kiwi.data.common.wrapInSingleResult
import adamyy.github.com.kiwi.data.entity.User
import adamyy.github.com.kiwi.data.source.network.UserApi
import io.reactivex.Observable

class UserRepositoryImpl(val userApi: UserApi): UserRepository {

    override fun getUserById(id: String): Observable<SingleResult<User>> =
            userApi.getUserById(id).wrapInSingleResult{}

    override fun getUserByName(name: String): Observable<SingleResult<User>> =
            userApi.getUserByName(name).wrapInSingleResult{}

    override fun verifyCredentials(): Observable<SingleResult<User>> =
            userApi.verifyCredentials().wrapInSingleResult{}

}
