package blog.mingmomcoco.myblog.service;

import blog.mingmomcoco.myblog.dto.PageDTO;
import org.springframework.stereotype.Service;

@Service
public interface QuestionService {


    PageDTO list(Integer page, Integer size);
}
