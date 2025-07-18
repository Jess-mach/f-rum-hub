package com.forum.hub.security;

import com.forum.hub.model.usuario.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository repository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        System.out.println("=== MEU FILTRO CUSTOMIZADO EXECUTADO PRIMEIRO ===");
        System.out.println("URL: " + request.getRequestURL());
        System.out.println("Method: " + request.getMethod());

        var tokenJWT = recuperarToken(request);

        if (tokenJWT != null) {
            try {
                System.out.println("Token encontrado, validando...");
                var subject = tokenService.getSubject(tokenJWT);
                var usuario = repository.findByLogin(subject);

                if (usuario != null) {
                    var authentication = new UsernamePasswordAuthenticationToken(
                            usuario, null, usuario.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    System.out.println("✅ Usuário autenticado: " + subject);
                } else {
                    System.out.println("❌ Usuário não encontrado: " + subject);
                }
            } catch (Exception e) {
                System.out.println("❌ Erro ao validar token: " + e.getMessage());
            }
        } else {
            System.out.println("Nenhum token encontrado na requisição");
        }

        System.out.println("=== CONTINUANDO PARA OS FILTROS DO SPRING SECURITY ===");
        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        System.out.println("Authorization Header: " + authorizationHeader);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.replace("Bearer ", "").trim();
        }

        return null;
    }
}
