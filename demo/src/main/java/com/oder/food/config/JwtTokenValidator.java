package com.oder.food.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Security;
import java.util.List;

public class JwtTokenValidator extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
    String  jwt=request.getHeader(JwtConstant.JWT_HEADER);

    //Bearer token

       if(jwt!=null){
           jwt= jwt.substring(7);

           try{
               SecretKey key= Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
               Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();

               String email=String.valueOf(claims.get("email"));
               String authorities = String.valueOf((claims.get("authorities")));

               //ROLE_CUSTOMER,ROLE_ADMIN

               List<GrantedAuthority> auth= AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);

               Authentication authenticathion = new UsernamePasswordAuthenticationToken(email, null , auth);
               SecurityContextHolder.getContext().setAuthentication(authenticathion);

           }catch (Exception e){
               throw new BadCredentialsException(("ivalid token..()>>>///"));
           }

       }

       filterChain.doFilter(request,response);


    }
}
