package br.edu.ifpb.pweb2.bitbank.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifpb.pweb2.bitbank.model.Correntista;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler) throws Exception {
       
        HttpSession httpSession = request.getSession(false);

        if (httpSession != null) {
            Correntista usuario = (Correntista) httpSession.getAttribute("usuario");

            if (usuario != null) {
              String contextPath = request.getContextPath();
              String path = request.getRequestURI();

              // Remove o contextPath to path
              String relativePath = path.substring(contextPath.length());

              // Verificar se a URL acessada começa com "/correntistas" ou "/contas"
              boolean requerAdmin = relativePath.startsWith("/correntistas") || relativePath.startsWith("/contas");

              // Se exige admin, verifica se o usuário é admin
              if (requerAdmin & !usuario.isAdmin()) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Acesso negado.");
                return false;
              }

              // Está logado e tem permissão
              return true;
            }
        }

        // Sem sessão ou usuário não autenticado
        String baseUrl = request.getContextPath();
        response.sendRedirect(response.encodeRedirectURL(baseUrl + "/auth"));
        return false;
    }
    
    @Override
    public void afterCompletion(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler,
        Exception ex) throws Exception {
        // No specific action needed after completion
    }

    @Override
    public void postHandle(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler,
        ModelAndView modelAndView) throws Exception {
        // No specific action needed after the handler has been executed
    }
}
