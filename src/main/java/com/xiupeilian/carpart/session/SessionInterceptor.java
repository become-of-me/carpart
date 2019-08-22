package com.xiupeilian.carpart.session;

import com.xiupeilian.carpart.model.Menu;
import com.xiupeilian.carpart.model.SysUser;
import com.xiupeilian.carpart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @Description: session拦截、权限控制
 * @Author: Tu Xu
 * @CreateDate: 2019/8/21 13:59
 * @Version: 1.0
 **/
public class SessionInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService;
    /**
     *
     * @param request
     * @param response
     * @param handler
     * @return
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        //获取请求资源目录
        String path=request.getRequestURI();
        //判断资源路径是不是需要登陆才可以访问的
        if (path.contains("login")){
            return  true;
        }else{
            //获取session如果为空 就不要创建返回null
            HttpSession session=request.getSession(false);
            if (null==session){
                //如果session为null就代表未登录 跳转到登陆界面
                response.sendRedirect(request.getContextPath()+"/login/toLogin");
                return  false;
            }else {
                //获取session中的用户为空 代表未登录
                if (null==session.getAttribute("user")){
                    response.sendRedirect(request.getContextPath()+"/login/toLogin");
                    return  false;
                }else {
                    //代表用户登录过 session校验完毕 开始权限过滤
                    SysUser user=(SysUser) session.getAttribute("user");
                    //查询出该用户对应的权限
                    List<Menu> menulist=userService.findMenusById(user.getId());
                    boolean check=false;
                    for (Menu menu:menulist) {
                        //判断用户请求的资源路径包不包含 自己所拥有的权限关键字
                        if (path.contains(menu.getMenuKey())){
                            check=true;
                        }
                    }
                    if (check){
                        return true;
                    }else{
                        response.sendRedirect(request.getContextPath()+"/login/feifa");
                        return  false;
                    }
                }
            }

        }

    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
           request.setAttribute("ctx",request.getContextPath());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }
}
