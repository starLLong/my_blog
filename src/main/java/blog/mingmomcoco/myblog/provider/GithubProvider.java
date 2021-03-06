package blog.mingmomcoco.myblog.provider;




import blog.mingmomcoco.myblog.dto.AccessTokenDTO;
import blog.mingmomcoco.myblog.dto.GithubUser;
import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        String client_id = accessTokenDTO.getClient_id();
        String client_secret = accessTokenDTO.getClient_secret();
        String code = accessTokenDTO.getCode();
        String redirect_uri = accessTokenDTO.getRedirect_uri();
        String state = accessTokenDTO.getState();
        String Url = "https://github.com/login/oauth/access_token";

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url(Url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()){
            String string  = response.body().string();
            String token = string.split("&")[0].split("=")[1];
            System.out.println(string);
//            System.out.println(token);
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?")
                .header("Authorization","token "+accessToken)//请将access_token通过作为Authorization HTTP header中的参数传输，而不是作为url中的参数明文传输
                .build();
        try (Response response = client.newCall(request).execute()){

            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
