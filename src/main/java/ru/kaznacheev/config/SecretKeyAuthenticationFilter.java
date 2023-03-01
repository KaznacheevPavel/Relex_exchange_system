package ru.kaznacheev.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.kaznacheev.entity.Client;
import ru.kaznacheev.service.SupportiveService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class SecretKeyAuthenticationFilter extends OncePerRequestFilter {

    private SupportiveService supportiveService;

    @Transactional
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        CachedHttpServletRequest cachedHttpServletRequest = new CachedHttpServletRequest(request);
        String secretKey = getSecretKey(cachedHttpServletRequest.getInputStream());
        if (secretKey == null) {
            filterChain.doFilter(cachedHttpServletRequest, response);
            return;
        }
        Client client = supportiveService.getClient(secretKey);
        List<SimpleGrantedAuthority> role = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + client.getRole().getTitle()));
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(null, null, role);
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(cachedHttpServletRequest, response);
    }

    private String getSecretKey(ServletInputStream servletInputStream) {
        byte[] inputStreamBytes;
        Map<String, String> map;
        try {
            inputStreamBytes = StreamUtils.copyToByteArray(servletInputStream);
            map = new ObjectMapper().readValue(inputStreamBytes, Map.class);
        } catch (IOException e) {
            return null;
        }
        return map.get("secret_key");
    }

}