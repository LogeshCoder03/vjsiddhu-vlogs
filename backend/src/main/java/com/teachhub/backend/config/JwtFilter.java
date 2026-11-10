package com.teachhub.backend.config;

import com.teachhub.backend.Utils.JwtUtil;
import jakarta.annotation.Nullable;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
public class JwtFilter extends OncePerRequestFilter{

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                               @Nullable  HttpServletResponse response,
                               @Nullable FilterChain filterChain)
    throws IOException, ServletException,NullPointerException {
        String token=null;
        if (request.getCookies()!=null){
            for (Cookie cookie:request.getCookies()){
                if (cookie.getName().equals("token")){
                    token=cookie.getValue();
                }
            }
        }
        if (token!=null){
            String username=jwtUtil.extractUsername(token);
            UserDetails userDetails=userDetailsService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken auth=new UsernamePasswordAuthenticationToken(
                    userDetails,null,userDetails.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request,response);
    }
}
