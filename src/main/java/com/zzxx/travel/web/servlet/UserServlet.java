package com.zzxx.travel.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zzxx.travel.domain.ResultInfo;
import com.zzxx.travel.domain.User;
import com.zzxx.travel.exception.LoginException;
import com.zzxx.travel.service.UserService;
import com.zzxx.travel.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    private UserService userService = new UserServiceImpl();

    //检测用户名是否存在
    public void checkUser(HttpServletRequest request,HttpServletResponse response) throws IOException{
        // 1.接收到请求参数 username
        String username = request.getParameter("username");
        // 2.调用service检查用户是否存在 存在-true, 不存在-false
        boolean flag = userService.checkUserExist(username);
        // 3.创建返回给前端的数据对象
        ResultInfo info = new ResultInfo();
        // 4.设置flag为通过,不通过
        info.setFlag(!flag);
        // 5.将数据对象封装为json返回给客户端
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(), info);
    }

    //注册账号
    public void register(HttpServletRequest request,HttpServletResponse response) throws IOException {
        Map<String, String[]> map = request.getParameterMap();
        // 2.封装对象
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        // 3.调用service进行注册
        boolean flag = userService.registerUser(user);
        // 4.结果判断, 封装结果数据
        ResultInfo info = new ResultInfo();
        info.setFlag(flag);
        if (!flag) {
            info.setErrorMsg("注册失败!");
        }
        // 5.将结果数据返回个客户端
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(), info);
    }

    //激活账号
    public void active(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String code = request.getParameter("code");
        boolean flag = userService.active(code);
        response.setContentType("text/html;charset=utf-8");
        if (flag) {
            response.getWriter().write("激活成功!<a href='/travel/login.html'>登录</a>");
        } else {
            response.getWriter().write("激活失败! 请联系管理员!");
        }
    }

    //账号登录
    public void login(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String check = request.getParameter("check");
        String checkcode_server = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
        ResultInfo info = new ResultInfo();
        if (check==null){
            info.setFlag(false);
            info.setErrorMsg("验证码为空");
        }else if (!check.equalsIgnoreCase(checkcode_server)){
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
        }else {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            try {
                User user = userService.login(username, password);
                info.setFlag(true);
                request.getSession().setAttribute("loginUser",user);
            } catch (LoginException e) {
                info.setFlag(false);
                info.setErrorMsg("账号/密码错误");
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(), info);
    }

    //查询登陆信息
    public void findOne(HttpServletRequest request,HttpServletResponse response) throws IOException{
        Object user = request.getSession().getAttribute("loginUser");
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),user);
    }

    //账号退出
    public void logout(HttpServletRequest request,HttpServletResponse response) throws IOException{
        request.getSession().removeAttribute("loginUser");
        response.sendRedirect(request.getContextPath() + "/login.html");
    }
}

