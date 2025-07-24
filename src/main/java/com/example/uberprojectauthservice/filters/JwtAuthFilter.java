package com.example.uberprojectauthservice.filters;

import com.example.uberprojectauthservice.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * OncePerRequestFilter this class makes sure one time executed per request
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {


    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;

    public JwtAuthFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        String token = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("JwtToken".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        if (token == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        String email = jwtService.extractEmail(token);
        if (email != null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            if (jwtService.validateToken(token, email)) {
                UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/api/v1/auth/signup/") || path.startsWith("/api/v1/auth/signin/");
    }





    //not working code

    /*
    private final RequestMatcher uriMatcher =  new
        AntPathRequestMatcher("/api/v1/auth/validate", "GET");


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {


        String token = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("JwtToken".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        if(token==null){
            // user has not provided any Jwt token request should not move forward.
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        String email = jwtService.extractEmail(token);
        boolean authenticated = false;
        if(email!=null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
             if(jwtService.validateToken(token, email))
             {
                 UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                     = new UsernamePasswordAuthenticationToken(userDetails, null);
                 usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetails(request));

                 SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

             }
        }
        filterChain.doFilter(request, response);

    }
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        RequestMatcher requestMatcher = new NegatedRequestMatcher(uriMatcher);
        return requestMatcher.matches(request);
    }

     */
}
