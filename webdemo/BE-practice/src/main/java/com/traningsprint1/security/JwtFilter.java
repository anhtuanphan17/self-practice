package com.traningsprint1.security;

import com.traningsprint1.service.IAccountService;
import com.traningsprint1.service.impl.AccountDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Version: 20-sept-2022
 * @Author: TuanPA3
 * */
@Component
public class JwtFilter extends OncePerRequestFilter {
    private String jwtToken;
    @Autowired
    private JwtUtility jwtUtility;

    @Autowired
    private AccountDetailsServiceImpl accountDetailsService;

    @Autowired
    private IAccountService iAccountService;

    /** this doFilterInternal is the class used to fill any request from client to server to check whether that request have authorization or not
     * @Version: 20-sept-2022
     * @Author: TuanPA3
     * */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = getTokenFromRequest(request);
            if (token != null && jwtUtility.validateJwtToken(token)) {
                // save token to jwtToken variable to use in findAccountByJwtToken() method below
                jwtToken = token;
                String username = jwtUtility.getUserNameFromJwtToken(token);
                UserDetails userDetails = accountDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }
        filterChain.doFilter(request, response);
    }

    /** this getTokenFromRequest is the class used to get token from client 's request
     * @Version: 20-sept-2022
     * @Author: TuanPA3
     * */
    private String getTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return token.substring(7, token.length());
        }
        return null;
    }
}
