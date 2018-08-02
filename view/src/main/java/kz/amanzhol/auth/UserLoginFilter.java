package kz.amanzhol.auth;

import kz.amanzhol.auth.domain.ShopUser;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {UserLoginFilter.USER_FILTER_URI + "*", UserLoginFilter.ADMIN_FILTER_URI + "*"})
public class UserLoginFilter implements Filter {
    public static final String USER_FILTER_URI = "/user/";
    public static final String ADMIN_FILTER_URI = "/admin/";

    @Inject
    private AuthBean authBean;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (authBean.getRole() != null) {
            String uri = request.getRequestURI();
            if (uri.startsWith(request.getContextPath() + ADMIN_FILTER_URI) && authBean.getRole() != ShopUser.Role.ADMIN) {
                response.sendRedirect(request.getContextPath() + "/errors.xhtml");
            }
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }



        authBean.setRequestedPage(request.getRequestURI());
        response.sendRedirect(request.getContextPath() + "/login.xhtml");
    }

    @Override
    public void destroy() {

    }
}
