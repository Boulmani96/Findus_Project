package de.h_da.fbi.findus.auth

import com.papsign.ktor.openapigen.model.Described
import com.papsign.ktor.openapigen.model.security.APIKeyLocation
import com.papsign.ktor.openapigen.model.security.HttpSecurityScheme
import com.papsign.ktor.openapigen.model.security.SecuritySchemeModel
import com.papsign.ktor.openapigen.model.security.SecuritySchemeType
import com.papsign.ktor.openapigen.modules.providers.AuthProvider
import com.papsign.ktor.openapigen.route.path.auth.OpenAPIAuthenticatedRoute
import com.papsign.ktor.openapigen.route.path.normal.NormalOpenAPIRoute
import de.h_da.fbi.findus.authProvider
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.util.pipeline.*

const val noJWTPrincipal = "No JWTPrincipal"
const val profile = "Some scope"

class JwtProvider : AuthProvider<JwtUser> {
    override val security: Iterable<Iterable<AuthProvider.Security<*>>> =
        listOf(
            listOf(
                AuthProvider.Security(
                    SecuritySchemeModel(
                        SecuritySchemeType.http,
                        scheme = HttpSecurityScheme.bearer,
                        bearerFormat = "JWT",
                        referenceName = "jwtAuth",
                    ), listOf()
                ),
                AuthProvider.Security(
                    SecuritySchemeModel(
                        SecuritySchemeType.apiKey,
                        `in` = APIKeyLocation.cookie,
                        name = "jwtAuthToken",
                        referenceName = "scheme",
                    ), emptyList<Scopes>()
                )
            )
        )

    override suspend fun getAuth(pipeline: PipelineContext<Unit, ApplicationCall>): JwtUser {
        return pipeline.context.authentication.principal()
            ?: throw RuntimeException(noJWTPrincipal)
    }

    override fun apply(route: NormalOpenAPIRoute): OpenAPIAuthenticatedRoute<JwtUser> {
        val authenticatedKtorRoute = route.ktorRoute.authenticate { }
        return OpenAPIAuthenticatedRoute(authenticatedKtorRoute, route.provider.child(), this)
    }
}

enum class Scopes(override val description: String) : Described {
    Profile(profile)
}

inline fun NormalOpenAPIRoute.auth(route: OpenAPIAuthenticatedRoute<JwtUser>.() -> Unit): OpenAPIAuthenticatedRoute<JwtUser> {
    val authenticatedKtorRoute = this.ktorRoute.authenticate { }
    val openAPIAuthenticatedRoute = OpenAPIAuthenticatedRoute(
        authenticatedKtorRoute,
        this.provider.child(),
        authProvider = authProvider
    );
    return openAPIAuthenticatedRoute.apply {
        route()
    }
}
