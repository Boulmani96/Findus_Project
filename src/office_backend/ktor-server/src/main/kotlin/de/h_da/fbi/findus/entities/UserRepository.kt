package de.h_da.fbi.findus.entities

import de.h_da.fbi.findus.auth.JwtUser

class UserRepository {

    private var users = mapOf(
        "Admin:Admin" to JwtUser(1, "Admin"),
        "Admin2:Admin2" to JwtUser(2, "Admin2")
    )

    fun getUser(username: String, password: String): JwtUser? {
        return users["$username:$password"]
    }

}
