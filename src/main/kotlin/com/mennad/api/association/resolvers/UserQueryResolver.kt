package com.mennad.api.association.resolvers

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.mennad.api.association.domain.UserService
import com.mennad.api.association.mappers.UserMapper
import com.mennad.api.association.resolvers.dtos.UserDto
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class UserQueryResolver(
    val userService: UserService,
): GraphQLQueryResolver {

    @QueryMapping(name= "getUsers")
    fun getUsers(): List<UserDto?> {
        return UserMapper.toUserDtos(userService.getUsers())
    }

    @QueryMapping(name= "getUser")
    fun getUser(@Argument id: String): UserDto? {
        return userService.getUser(id)?.let { UserMapper.toUserDto(it) }
    }
}