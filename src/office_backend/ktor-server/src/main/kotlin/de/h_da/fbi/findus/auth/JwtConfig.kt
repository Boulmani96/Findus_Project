package de.h_da.fbi.findus.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.auth.jwt.*
import java.util.*

const val oneWeekInMs: Int = (1000 * 60 * 60 * 24 * 7)

class JwtConfig(jwtSecret: String) {

    companion object Constants {

        // jwt config
        private const val jwtIssuer = "findus"
        private const val jwtRealm = "findus.backend"

        // claims
        private const val CLAIM_USERID = "id"
        private const val CLAIM_USERNAME = "name"

    }

    private val jwtAlgorithm = Algorithm.HMAC512(jwtSecret)
    private val jwtVerifier: JWTVerifier = JWT
        .require(jwtAlgorithm)
        .withIssuer(jwtIssuer)
        .build()

    /**
     * Generate a token for a authenticated user
     * @param user the jwt user for which the token should be generated
     * @return the generated token for a specific user
     */
    fun generateToken(user: JwtUser): String = JWT.create()
        .withSubject("Authentication")
        .withIssuer(jwtIssuer)
        .withClaim(CLAIM_USERID, user.id)
        .withClaim(CLAIM_USERNAME, user.name)
        .withExpiresAt(Date(System.currentTimeMillis() + oneWeekInMs))
        .sign(jwtAlgorithm)

    /**
     * Configure the jwt ktor authentication feature
     * @param config the config for which jwt should be configured
     */
    fun configureKtorFeature(config: JWTAuthenticationProvider.Configuration) = with(config) {
        verifier(jwtVerifier)
        realm = jwtRealm
        validate { credentials ->
            val userId = credentials.payload.getClaim(CLAIM_USERID).asInt()
            val userName = credentials.payload.getClaim(CLAIM_USERNAME).asString()

            if (userId != null && userName != null) {
                JwtUser(userId, userName)
            } else {
                null
            }
        }
    }

}
