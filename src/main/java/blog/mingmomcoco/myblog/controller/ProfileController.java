package blog.mingmomcoco.myblog.controller;

import blog.mingmomcoco.myblog.dto.PageDTO;
import blog.mingmomcoco.myblog.dao.UserMapper;
import blog.mingmomcoco.myblog.entity.User;
import blog.mingmomcoco.myblog.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
//@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    private String profile(@PathVariable(name = "action") String action, Model model,
                           HttpServletRequest request,
                           @RequestParam(name = "page",defaultValue = "1")Integer page,
                           @RequestParam(name = "size",defaultValue = "5")Integer size){
        /*User user = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length !=0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    user = userMapper.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }*/
        User user = (User) request.getSession().getAttribute("user");

        if (user == null){
            return "redirect:/";
        }
        if ("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","????????????");
        }else if ("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","????????????");
        }
        PageDTO pageNation = questionService.listUserById(user.getId(), page, size);
        model.addAttribute("pageNation",pageNation);
        return "profile";
    }
}
