package com.kmoonwang.mywenda.service;

import com.kmoonwang.mywenda.dao.QuestionDAO;
import com.kmoonwang.mywenda.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.Date;
import java.util.List;

@Service
public class QuestionService {
    //service是连接controller和dao之间的
    @Autowired
    QuestionDAO questiondao;

    @Autowired
    SensitiveService sensitiveService;

    public List<Question> getLatetQuestions(int userId,int offset,int limit){
        return questiondao.selectLatestQuestions(userId,offset,limit);
    }

    public Question selectByid(int id){
        return questiondao.selectById(id);
    }

    public int addQuestion(Question question){
        //敏感词过滤
        question.setTitle(sensitiveService.filter(question.getTitle()));
        question.setContent(sensitiveService.filter(question.getContent()));
        //会对html做一个转译，这样就不会乱套了
        question.setContent(HtmlUtils.htmlEscape(question.getContent()));
        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));
       return questiondao.addQuestion(question) > 0? question.getId():0;
    }

    public Date selectById(int id){
        return questiondao.selectByid(id);
    }

    public int updateCommentCount(int id, int count) {
        return questiondao.updateCommentCount(id, count);
    }
}
