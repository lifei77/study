package com.imooc.mall.filter;

import com.imooc.mall.common.Constant;
import com.imooc.mall.model.pojo.User;
import com.imooc.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * TODO
 *
 * @author 86182
 * @version 1.0
 * @date 2021/10/5 17:13
 */
public class AdminFilter implements Filter {

    @Autowired
    UserService userService;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute(Constant.IMOOC_MALL_USER);
        if (currentUser == null) {
            PrintWriter out  = new HttpServletResponseWrapper((HttpServletResponse) servletResponse).getWriter();
            out.write("");
            out.flush();
            out.close();
            return;
        }
        //校验是否是管理员
        boolean adminRole = userService.checkAdminRole(currentUser);
        if (adminRole){
            filterChain.doFilter(servletRequest,servletResponse);
        }else{
            PrintWriter out  = new HttpServletResponseWrapper((HttpServletResponse) servletResponse).getWriter();
            out.write("");
            out.flush();
            out.close();
        }
    }

    @Override
    public void destroy() {

    }
}
