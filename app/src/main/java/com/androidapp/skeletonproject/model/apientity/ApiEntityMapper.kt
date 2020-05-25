package com.androidapp.skeletonproject.model.apientity

import com.androidapp.skeletonproject.model.User

/**
 * A list of mapping functions for converting API entities to business entities
 */

fun UserApiEntity.toBusinessEntity(): User {
    return User(
        user_id = this.user_id,
        profile_image = this.profile_image,
        name = this.name,
        email = this.email
    )
}

