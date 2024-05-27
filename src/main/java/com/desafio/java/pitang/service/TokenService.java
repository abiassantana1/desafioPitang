package com.desafio.java.pitang.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.desafio.java.pitang.exception.BusinessException;
import com.desafio.java.pitang.model.entity.Usuario;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service

public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    @Value("${api.security.token.validade}")
    private long tempoValidadeToken;

    public String generateToken(Usuario user){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            Date tempoAtual = new Date();
            return JWT.create()
                    .withIssuer("desafio-pitang-api")
                    .withSubject(user.getLogin())
                    .withExpiresAt(new Date(tempoAtual.getTime() + tempoValidadeToken))
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new BusinessException("Unauthorized - invalid session");
        }
    }

    public String validateToken(String token){
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.require(algorithm)
                .withIssuer("desafio-pitang-api")
                .build()
                .verify(token)
                .getSubject();
    }

}
