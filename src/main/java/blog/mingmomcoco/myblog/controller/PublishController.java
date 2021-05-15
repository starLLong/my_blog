package blog.mingmomcoco.myblog.controller;

import blog.mingmomcoco.myblog.mapper.QuestionMapper;
import blog.mingmomcoco.myblog.mapper.UserMapper;
import blog.mingmomcoco.myblog.model.Question;
import blog.mingmomcoco.myblog.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class PublishController {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;


    @GetMapping("publish")
    public String publish(){
        return "publish";
    }
    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value = "title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model){
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        if (title == null || title==""){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if (description == null || description  == ""){
            model.addAttribute("error","内容不能为空");
            return "publish";
        }
        if (tag == null || tag==""){
            model.addAttribute("error","不能没有标签");
            return "publish";
        }
        //        添加cookie
        User user = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length !=0)
            for(Cookie cookie:cookies){
                if (cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    user = userMapper.findByToken(token);
                    if (user != null ){
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        if (user == null){
            model.addAttribute("error","用户为登录");
            return "publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        questionMapper.create(question);
        return "redirect:/";
    }
}
