package last.lares.global.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import last.lares.domain.auth.CustomUserDetails;
import last.lares.domain.auth.dto.LoginRequestDto;
import last.lares.domain.auth.dto.LoginResponseDto;
import last.lares.global.config.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager manager;
    private final ObjectMapper objectMapper;
    private final JwtUtil jwtUtil;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        LoginRequestDto loginRequestDto;

        try {
            loginRequestDto = objectMapper.readValue(request.getInputStream(), LoginRequestDto.class);
        } catch (IOException e) {
            throw new RuntimeException("유효하지 않은 로그인 데이터입니다!", e);
        }

        String userId = loginRequestDto.getUserId();
        String userPassword = loginRequestDto.getUserPassword();

        if (userId == null) {
            throw new RuntimeException("유효하지 않은 아이디 값입니다!");
        } if (userPassword == null) {
            throw new RuntimeException("유효하지 않은 비밀번호 값입니다!");
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userId, userPassword);

        return manager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        CustomUserDetails customUserDetails = (CustomUserDetails) authResult.getPrincipal();

        String userId = customUserDetails.getUserId();
        String userName = customUserDetails.getUserName();
        String userRole = customUserDetails.getUserRole();

        String token = jwtUtil.createJwt(userId, userRole);

        response.addHeader("Authorization", "Bearer " + token);

        LoginResponseDto.Data loginData = LoginResponseDto.Data.builder()
                .userId(userId)
                .userName(userName)
                .userRole(userRole)
                .build();

        LoginResponseDto responseBody = LoginResponseDto.builder()
                .status(true)
                .message("로그인에 성공하였습니다!")
                .data(loginData)
                .build();

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(responseBody));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        LoginResponseDto responseBody = LoginResponseDto.builder()
                .status(false)
                .message("로그인에 실패하였습니다.")
                .data(null)
                .build();

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(responseBody));
    }
}
