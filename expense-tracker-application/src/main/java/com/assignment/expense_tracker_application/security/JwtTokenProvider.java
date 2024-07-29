package com.assignment.expense_tracker_application.security;

import com.assignment.expense_tracker_application.exception.ExpenseTrackerAPIException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.UnsupportedKeyException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider  {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app-jwt-expiration-milliseconds}")
    private long  jwtExpirationDate;

    //generate JWT token
    public  String generateToken(Authentication authentication){
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);

        String token = Jwts.builder()
                .subject(username)
                .issuedAt(currentDate)
                .expiration(expireDate)
                .signWith(key())
                .compact();
        return token;
    }

    //get username from JWT token
    public String getUsername(String token){
        return Jwts.parser()
                .verifyWith(key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    //validate JWT token
    public boolean validateToken(String token){
        try{
            Jwts.parser()
                    .verifyWith(key())
                    .build()
                    .parse(token);

            return true;
        }
        catch (MalformedJwtException malformedJwtException){
            throw  new ExpenseTrackerAPIException(HttpStatus.BAD_REQUEST,"Invalid JWT token");
        }
        catch(ExpiredJwtException expiredJwtException){
            throw new ExpenseTrackerAPIException(HttpStatus.BAD_REQUEST,"Expired JWT token");
        }
        catch(UnsupportedJwtException unsupportedJwtException){
            throw new ExpenseTrackerAPIException(HttpStatus.BAD_REQUEST,"Unsupported JWT token");
        }
        catch (IllegalArgumentException illegalArgumentException){
            throw new ExpenseTrackerAPIException(HttpStatus.BAD_REQUEST,"JWT claims string is null or empty");
        }

    }


    private SecretKey key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
}
