package blog.mingmomcoco.myblog.service;

import blog.mingmomcoco.myblog.dto.PageDTO;
import blog.mingmomcoco.myblog.dto.QuestionDTO;
import blog.mingmomcoco.myblog.model.Question;
import org.springframework.stereotype.Service;

@Service
public interface QuestionService {


    PageDTO list(Integer page, Integer size);

    PageDTO listUserById(Integer id, Integer page, Integer size);

    QuestionDTO getById(Integer id);

    void createOrUpdate(Question question);
}
