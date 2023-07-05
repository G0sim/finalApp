package org.edupoll.config.support;

import java.io.IOException;
import java.util.List;

import org.edupoll.service.JWTService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

	// 특정 이메일로 토큰을 만들고 토큰값을 기반으로 분석 후에 값을 리턴
	private final JWTService jwtService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// 사용자가 jwt token을 가지고 왔다면
		String authorization = request.getHeader("Authorization");
		log.info("Authorization header value : {} ", authorization);

		// 토큰을 안가지고 오거나, 토큰에 문제가 있으면 그냥 통과
		if (authorization == null) {
			log.info("Did not process authentication request since failed to find authorization header ",
					authorization);
			filterChain.doFilter(request, response);
			return;
		}

		try {
			// jwt token의 유효성 검사를 해서 통과 한다면
			String email = jwtService.verifyToken(authorization);
			// 토큰 소유자의 정보가 나옴 , 실패는 exception
			// 있는 authetication을 사용해도 되고, 만들어서 사용해도 된다.
			Authentication authentication = new UsernamePasswordAuthenticationToken(email, "", List.of(new SimpleGrantedAuthority("ROLE_MEMBER")));
			// 첫번째 인자인 principal -> 인증 주체자 정보 :UserDetails 객체(Object)가 보통 설정
			// @AuthenticationPrincipal 했을때 나오는 값
			// 두번째 인자인 credentials -> 인증에 사용되었던 정보 ,크게 상관 없는 value
			// 세번째 인자인 authorities -> 권한 : role에 따른 차단을 설정할 때 쓰는 것.

			// jwt토큰만 있더라도 인증을 했다고 생각하고 통과시켜줌
			SecurityContextHolder.getContext().setAuthentication(authentication);

		} catch (Exception e) {
			// 토큰이 만료됐거나 위조 되었을때
			throw new BadCredentialsException("Invalide authetication token");
		}

		// 여기까지 오면 통과
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		// Authentication을 등록 시키면 된다. 인증 통과 상태로 변경
		log.info("{}", authentication);

		filterChain.doFilter(request, response);
	}
}
