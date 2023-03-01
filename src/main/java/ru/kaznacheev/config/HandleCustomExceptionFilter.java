package ru.kaznacheev.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.kaznacheev.exception.ErrorException;
import ru.kaznacheev.dto.response.ErrorResponseDto;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;

@Component
public class HandleCustomExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (ErrorException e) {
            ErrorResponseDto errorResponseDto = new ErrorResponseDto(new Timestamp(System.currentTimeMillis()), e.getError());
            ObjectMapper mapper = new ObjectMapper().setDateFormat(new StdDateFormat().withColonInTimeZone(true));
            String json = mapper.writeValueAsString(errorResponseDto);
            if (request.getHeader("Accept").equals("*/*")) {
                response.setContentType("application/json");
            } else {
                response.setContentType(request.getHeader("Accept"));
            }
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(json);
        }
    }

}
