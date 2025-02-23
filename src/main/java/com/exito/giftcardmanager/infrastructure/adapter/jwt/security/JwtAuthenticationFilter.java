package com.exito.giftcardmanager.infrastructure.adapter.jwt.security;

import com.exito.giftcardmanager.domain.model.user.gateway.UserRepository;
import com.exito.giftcardmanager.infrastructure.adapter.database.sql.user.repository.UserDataRepository;
import com.exito.giftcardmanager.infrastructure.adapter.jwt.JwtProviderImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProviderImpl jwtProvider;
    private final UserRepository UserRepository;

    public JwtAuthenticationFilter(JwtProviderImpl jwtProvider, UserRepository UserRepository) {
        this.jwtProvider = jwtProvider;
        this.UserRepository = UserRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        log.info("Authorization Header: {}", authorizationHeader);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String userName = jwtProvider.extractUserName(token);

            if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                var user = this.UserRepository.findByUserName(userName);
                if (user == null) {
                    return;
                }
                UserLogin userDetails = new UserLogin(user);
                if (validateToken(token, userDetails)) {
                    var authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        chain.doFilter(request, response);
    }

    private boolean validateToken(String token, UserDetails userDetails) {
        final String userName = jwtProvider.extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(jwtProvider.getKey()).build().parseClaimsJws(token).getBody();
    }
}