package com.mennad.api.association.mappers

import com.mennad.api.association.domain.model.User
import com.mennad.api.association.helpers.PasswordGenerator
import com.mennad.api.association.resolvers.dtos.UserDto

object UserMapper {

    fun toUser(userDto: UserDto): User {
        return User(
            userDto.firstName ?: "",
            userDto.lastName ?: "",
            userDto.userName ?: "",
            userDto.email ?: "",
            if (!userDto.password.isNullOrEmpty()) PasswordGenerator.generateHash(userDto.password) else "",
            userDto.wallet ?: -1,
            userDto.associationId ?: ""
        )
    }

    fun toUserDto(user: User): UserDto {
        return UserDto(
            user.id,
            user.firstName,
            user.lastName,
            user.userName,
            user.email,
            null,
            user.wallet,
            user.associationId
        )
    }

    fun toUserDtos(users: List<User>): List<UserDto> {
        return users.map(UserMapper::toUserDto).toList()
    }
}