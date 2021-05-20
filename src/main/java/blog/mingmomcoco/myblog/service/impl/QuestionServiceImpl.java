package blog.mingmomcoco.myblog.service.impl;

import blog.mingmomcoco.myblog.dto.PageDTO;
import blog.mingmomcoco.myblog.dto.QuestionDTO;
import blog.mingmomcoco.myblog.dao.QuestionMapper;
import blog.mingmomcoco.myblog.dao.UserMapper;
import blog.mingmomcoco.myblog.entity.Question;
import blog.mingmomcoco.myblog.entity.QuestionExample;
import blog.mingmomcoco.myblog.entity.User;
import blog.mingmomcoco.myblog.exception.CustomizeException;
import blog.mingmomcoco.myblog.service.QuestionService;
import org.apache.ibatis.session.RowBounds;
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
        Integer totalPage;
        Integer totalCount = (int) questionMapper.countByExample(new QuestionExample());
//        Integer totalCount = questionMapper.count();
        if (totalCount % size == 0 ){
            totalPage = totalCount / size;

        }else {
            totalPage = totalCount / size + 1;
        }

        if (page<1){
            page = 1;
        }
        if (page>totalPage){
            page =totalPage;
        }
        pageDTO.setPageNation(totalPage,page);

        Integer offset = size * (page -1);
        QuestionExample example = new QuestionExample();
        RowBounds rowBounds = new RowBounds(offset, size);
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(example, rowBounds);
//        List<Question> questions = questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
//            User user =  userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageDTO.setQuestions(questionDTOList);


        return pageDTO;
    }

    @Override
    public PageDTO listUserById(Integer userId, Integer page, Integer size) {
        PageDTO pageDTO = new PageDTO();
        Integer totalPage;
//        Integer totalCount = questionMapper.countByUserId(userId);
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria()
                .andCreatorEqualTo(userId);
        Integer totalCount = (int) questionMapper.countByExample(questionExample);
        if (totalCount % size == 0 ){
            totalPage = totalCount / size;

        }else {
            totalPage = totalCount / size + 1;
        }

        if (page<1){
            page = 1;
        }
        if (page>totalPage){
            page =totalPage;
        }

        pageDTO.setPageNation(totalPage,page);
        Integer offset = size * (page -1);

//        List<Question> questions = questionMapper.listUserById(userId,offset,size);
        QuestionExample example = new QuestionExample();
        example.createCriteria()
                .andCreatorEqualTo(userId);
        RowBounds rowBounds = new RowBounds(offset, size);
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(example, rowBounds);
        List<QuestionDTO> questionDTOList = new ArrayList<>();


        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
//            User user =  userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageDTO.setQuestions(questionDTOList);


        return pageDTO;
    }

    @Override
    public QuestionDTO getById(Integer id) {
//        Question question = questionMapper.getById(id);
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null){
            throw new CustomizeException("你找的问题不在了，要不要换个试试？？？");
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
//        User user =  userMapper.findById(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    @Override
    public void createOrUpdate(Question question) {
        if (question.getId() == null){
            //创建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insert(question);
        }else {
            //编辑
//            question.setGmtModified(System.currentTimeMillis());
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());
            QuestionExample example = new QuestionExample();
            example.createCriteria()
                    .andIdEqualTo(question.getId());
            questionMapper.updateByExampleSelective(updateQuestion, example);
        }
    }
}
