package com.mennad.api.association.resolvers

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.mennad.api.association.domain.UserService
import com.mennad.api.association.mappers.UserMapper
import com.mennad.api.association.resolvers.dtos.UserDto
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller

@Controller
class UserMutationResolver (
    val userService: UserService,
): GraphQLMutationResolver {

    @SchemaMapping(typeName = "Mutation", field = "createUser")
    fun createUser(@Argument userDto: UserDto): UserDto {
        return UserMapper.toUserDto(userService.createUser(UserMapper.toUser(userDto)))
    }

    @SchemaMapping(typeName = "Mutation", field = "deleteUser")
    fun deleteUser(@Argument id: String): Boolean {
        return userService.deleteUser(id)
    }

    @SchemaMapping(typeName = "Mutation", field = "updateUser")
    fun updateUser(@Argument id: String, @Argument userDto: UserDto): UserDto {
        return UserMapper.toUserDto(userService.updateUser(id, UserMapper.toUser(userDto)))
    }
}
