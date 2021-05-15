package blog.mingmomcoco.myblog.service.impl;

import blog.mingmomcoco.myblog.dto.PageDTO;
import blog.mingmomcoco.myblog.dto.QuestionDTO;
import blog.mingmomcoco.myblog.mapper.QuestionMapper;
import blog.mingmomcoco.myblog.mapper.UserMapper;
import blog.mingmomcoco.myblog.model.Question;
import blog.mingmomcoco.myblog.model.User;
import blog.mingmomcoco.myblog.service.QuestionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;
    @Override
    public PageDTO list(Integer page, Integer size) {
        PageDTO pageDTO = new PageDTO();
        Integer totalCount = questionMapper.count();

        pageDTO.setPageNation(totalCount,page,size);
        if (page<1){
            page = 1;
        }
        if (page>pageDTO.getTotalPage()){
            page = pageDTO.getTotalPage();
        }

        Integer offset = size * (page -1);

        List<Question> questions = questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            User user =  userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageDTO.setQuestions(questionDTOList);


        return pageDTO;
    }
}
