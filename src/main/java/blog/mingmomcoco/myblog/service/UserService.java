package blog.mingmomcoco.myblog.service;

import blog.mingmomcoco.myblog.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    void createOrUpdate(User user);
}
