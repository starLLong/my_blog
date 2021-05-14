package blog.mingmomcoco.myblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller

public class IndexController {
//    @ResponseBody
    @GetMapping("/")
    public String hello(){
        return "index";
    }
}
