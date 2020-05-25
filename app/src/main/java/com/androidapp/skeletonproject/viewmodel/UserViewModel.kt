package com.androidapp.skeletonproject.viewmodel

import android.content.Context
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.ViewModel
import com.androidapp.skeletonproject.model.User
import com.androidapp.skeletonproject.respository.AppUserRepository
import com.androidapp.skeletonproject.utils.AppResource
import com.androidapp.skeletonproject.utils.ViewModelField
import io.reactivex.Observable


class UserViewModel(
    private val appUserRepository: AppUserRepository
) : ViewModel() {

    val user_id= ViewModelField("")
    var name= ViewModelField("")
    val profile_image = ViewModelField("")
    val email = ViewModelField("")

    /**
     * Get user detail data
     */
    fun getUserDetail(): Observable<AppResource<User>> {
        return appUserRepository.getUser(userId=user_id.value).map { response ->
            if (response.isSuccess()) {
                profile_image.value = response.data!!.profile_image
                name.value = response.data.name
                email.value = response.data.email
            }
            response
        }
    }
}
