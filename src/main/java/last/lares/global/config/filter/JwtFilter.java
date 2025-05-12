package last.lares.global.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import last.lares.domain.auth.CustomUserDetails;
import last.lares.domain.auth.dto.FilterErrorDto;
import last.lares.domain.user.User;
import last.lares.domain.user.types.UserRole;
import last.lares.global.config.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        if (header == null) {
            log.warn("토큰이 존재하지 않습니다.");
            filterChain.doFilter(request, response);
            return ;
        } else if(!header.startsWith("Bearer ")) {
            log.error("토큰이 올바르지 않습니다.");
            filterChain.doFilter(request, response);
            return ;
        }

        String token = header.substring(7);

        try {
            if (jwtUtil.isExpired(token)) {
                throw new RuntimeException("만료된 토큰입니다.");
            }

            String userId = jwtUtil.getUserId(token);
            String userRole = jwtUtil.getUserRole(token);

            User user = User.builder()
                    .userId(userId)
                    .userPassword("")
                    .userRole(UserRole.valueOf(userRole))
                    .build();

            CustomUserDetails userDetails = new CustomUserDetails(user);
            Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(auth);

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            FilterErrorDto responseBody = FilterErrorDto.builder()
                    .status(false)
                    .message(e.getMessage())
                    .build();

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write(objectMapper.writeValueAsString(responseBody));
        }
    }
}
