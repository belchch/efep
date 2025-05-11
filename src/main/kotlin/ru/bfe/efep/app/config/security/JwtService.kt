package ru.bfe.efep.app.config.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.SecretKey

@Service
class JwtService(
    private val jwtProperties: JwtProperties
) {
    private val jwtParser = Jwts.parser().verifyWith(getSignInKey()).build()

    private fun getSignInKey(): SecretKey {
        val keyBytes = Decoders.BASE64.decode(jwtProperties.secret)
        return Keys.hmacShaKeyFor(keyBytes)
    }

    fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T {
        val claims = extractAllClaims(token)
        return claimsResolver(claims)
    }

    fun generateAccessToken(username: String): String = generateToken(username, jwtProperties.accessTokenExpiration)

    fun generateRefreshToken(username: String): String = generateToken(username, jwtProperties.refreshTokenExpiration)

    private fun generateToken(
        username: String,
        expiration: Long
    ): String = Jwts.builder()
        .subject(username)
        .issuedAt(Date())
        .expiration(Date(System.currentTimeMillis() + expiration))
        .signWith(getSignInKey(), Jwts.SIG.HS256)
        .compact()

    fun validateToken(token: String): Boolean {
        return try {
            jwtParser.parse(token)
            true
        } catch (e: JwtException) {
            e.printStackTrace()
            false
        }
    }

    fun isTokenExpired(token: String): Boolean =
        extractExpiration(token).before(Date())

    fun extractExpiration(token: String): Date =
        extractClaim(token, Claims::getExpiration)

    fun extractUsername(token: String): String =
        extractClaim(token, Claims::getSubject)

    private fun extractAllClaims(token: String): Claims =
        jwtParser.parseSignedClaims(token).payload


}