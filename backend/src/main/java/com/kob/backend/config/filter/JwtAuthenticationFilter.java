package com.kob.backend.config.filter;

import com.kob.backend.mapper.UserMapper;
import com.kob.backend.pojo.User;
import com.kob.backend.service.impl.utils.UserDetailsImpl;
import com.kob.backend.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserMapper userMapper;

    public JwtAuthenticationFilter(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (!StringUtils.hasText(authHeader) || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        try {
            Claims claims = JwtUtil.parseJWT(token);
            String userId = claims.getSubject();

            if (!StringUtils.hasText(userId)) {
                filterChain.doFilter(request, response);
                return;
            }

            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                User user = userMapper.selectById(Long.parseLong(userId));
                if (user == null) {
                    filterChain.doFilter(request, response);
                    return;
                }

                UserDetailsImpl userDetails = new UserDetailsImpl(user);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            filterChain.doFilter(request, response);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
