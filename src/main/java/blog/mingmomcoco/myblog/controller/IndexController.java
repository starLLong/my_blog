package blog.mingmomcoco.myblog.controller;

import blog.mingmomcoco.myblog.mapper.UserMapper;
import blog.mingmomcoco.myblog.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller

public class IndexController {
//    @ResponseBody

    @Autowired
    private UserMapper userMapper;
    @GetMapping("/")
    public String hello(HttpServletRequest request,
                        HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie:cookies){
            if (cookie.getName().equals("token")){
                String token = cookie.getValue();
                User user = userMapper.findByToken(token);
                if (user != null ){
                    request.getSession().setAttribute("user",user);
                }
                break;
            }
        }

        return "index";
    }
}
