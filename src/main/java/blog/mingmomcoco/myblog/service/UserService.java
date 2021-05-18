package blog.mingmomcoco.myblog.service;

import blog.mingmomcoco.myblog.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    void createOrUpdate(User user);
}
