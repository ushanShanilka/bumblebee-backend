package com.bumblebee.bumblebeebackend.filter;

import com.bumblebee.bumblebeebackend.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 2/16/2023
 **/

@Component
public class JwtRequestFilter extends OncePerRequestFilter  {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    UserDetailService userDetailService;

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain){
        final String authorizationHeader = request.getHeader("Authorization");
        String userName = null;
        String jwt = null;
        Map<String, Long> map = null;
        try {
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
                jwt = authorizationHeader.substring(7);
                try {
                    userName = jwtUtil.extractUserName(jwt);
                    Claims claims = jwtUtil.extractAllClaims(jwt);
                    request.setAttribute("userName",userName);
                    request.setAttribute("type",claims.get("type"));
                } catch (Exception e) {
                    request.setAttribute("code", 401);
                    request.setAttribute("message", "Expired JWT token");
                    request.setAttribute("error",e.getMessage());
                    SecurityContextHolder.clearContext();
                }
            } else {
                request.setAttribute("code", 404);
                request.setAttribute("message", "Token is Missing");
                request.setAttribute("error", "Token is null");
                SecurityContextHolder.clearContext();
            }

            if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailService.loadUserByUsername(userName);
                if (jwtUtil.validateToken(jwt, userDetails)){
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(map);
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                } else {
                    request.setAttribute("code", 401);
                    request.setAttribute("message", "Token Exception");
                    request.setAttribute("error", "Token is not valid");
                    SecurityContextHolder.clearContext();
                    throw new AuthenticationCredentialsNotFoundException("Token is not valid");
                }
            }
        } catch (Exception e) {
            request.setAttribute("code", 400);
            request.setAttribute("message", "Filter Exception");
            request.setAttribute("error",e.getMessage());
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}
