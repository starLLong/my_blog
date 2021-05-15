package blog.mingmomcoco.myblog.controller;

import blog.mingmomcoco.myblog.dto.PageDTO;
import blog.mingmomcoco.myblog.dto.QuestionDTO;
import blog.mingmomcoco.myblog.mapper.QuestionMapper;
import blog.mingmomcoco.myblog.mapper.UserMapper;
import blog.mingmomcoco.myblog.model.Question;
import blog.mingmomcoco.myblog.model.User;
import blog.mingmomcoco.myblog.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller

public class IndexController {
//    @ResponseBody

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;
    @GetMapping("/")
    public String hello(HttpServletRequest request,
                        HttpServletResponse response,
                        Model model,
                        @RequestParam(name = "page",defaultValue = "1")Integer page,
                        @RequestParam(name = "size",defaultValue = "5")Integer size){
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length !=0)
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
        PageDTO pageNation = questionService.list(page,size);
        model.addAttribute("pageNation",pageNation);
        return "index";
    }
}
