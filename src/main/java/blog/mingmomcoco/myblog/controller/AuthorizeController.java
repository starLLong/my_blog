package blog.mingmomcoco.myblog.controller;

import blog.mingmomcoco.myblog.dto.AccessTokenDTO;
import blog.mingmomcoco.myblog.dto.GithubUser;
import blog.mingmomcoco.myblog.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

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

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code")String code,
                           @RequestParam(name = "state")String state) throws IOException {
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
        GithubUser USER = githubProvider.getUser("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");
//        GithubUser USER = githubProvider.getUser(accessToken);
        System.out.println(USER.toString());
        return "index";
    }
}
