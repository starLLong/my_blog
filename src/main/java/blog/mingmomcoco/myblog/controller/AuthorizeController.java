package blog.mingmomcoco.myblog.controller;

import blog.mingmomcoco.myblog.dto.AccessTokenDTO;
import blog.mingmomcoco.myblog.dto.GithubUser;
import blog.mingmomcoco.myblog.mapper.UserMapper;
import blog.mingmomcoco.myblog.model.User;
import blog.mingmomcoco.myblog.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.url}")
    private String redirect_url;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code")String code,
                           @RequestParam(name = "state")String state,
                           HttpServletRequest request,
                           HttpServletResponse response) throws IOException {
        System.out.println(code);
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirect_url);
        accessTokenDTO.setState(state);
//        此处的accesstoken出现了问题
        String accessToken =  githubProvider.getAccessToken(accessTokenDTO);
//        https://github.com/login/oauth/access_token
        GithubUser githubUser = githubProvider.getUser("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    ");
//        GithubUser USER = githubProvider.getUser(accessToken);
        System.out.println(githubUser);
        if (githubUser != null ){
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
            //登录成功，写入Cookie 和 Session
            response.addCookie(new Cookie("token",token));
//            request.getSession().setAttribute("user",githubUser);
            return "redirect:/";

        }else {
            //登录失败，重新登录
            return "redirect:/";

        }
    }
}
