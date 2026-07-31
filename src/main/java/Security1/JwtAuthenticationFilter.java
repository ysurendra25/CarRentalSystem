package Security1;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import CarEntity.UserTable;
import CarRepo.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CustomerDetailsService customerDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        // Skip JWT validation for public APIs
        String path = request.getServletPath();

        if (path.equals("/user/register") || path.equals("/user/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Read Authorization Header
        final String authHeader = request.getHeader("Authorization");

        // No JWT Token
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Remove "Bearer "
        String jwtToken = authHeader.substring(7);

        // Extract Email from Token
        String userEmail = jwtService.extractUsername(jwtToken);

        // Authenticate only if not already authenticated
        if (userEmail != null &&
                SecurityContextHolder.getContext().getAuthentication() == null) {

            UserTable user = userRepository.findByEmail(userEmail).orElse(null);

            if (user != null && jwtService.isTokenValid(jwtToken, user)) {

                UserDetails userDetails =
                        customerDetailsService.loadUserByUsername(userEmail);

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities());

                authToken.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request));

                SecurityContextHolder.getContext()
                        .setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}