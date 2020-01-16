package com.kmoonwang.mywenda;

import com.kmoonwang.mywenda.dao.QuestionDAO;
import com.kmoonwang.mywenda.dao.UserDAO;
import com.kmoonwang.mywenda.model.Question;
import com.kmoonwang.mywenda.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MywendaApplication.class)
//@Sql("/init-schema.sql")
public class InitDatabaseTests {
    @Autowired
    UserDAO userDAO;

    @Autowired
    QuestionDAO questionDAO;

    @Test
    public void contextLoads(){
      /*  Random random = new Random();
        for(int i = 41;i < 51;i++){
            User user = new User();
            user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png",random.nextInt(1000)));
            user.setName(String.format("USER%d",i));
            user.setPassword("");
            user.setSalt("");
            //userDAO.addUser(user);

            user.setPassword("newpassword");
            userDAO.updatePassword(user);

            Question question = new Question();
            question.setCommentCount(i);
            Date date = new Date();
            date.setTime(date.getTime() + 1000 * 3600 * 5 * i);
            question.setCreateDate(date);
            question.setUserId(i + 1);
            question.setTitle(String.format("TITLE{%d}",i));
            question.setContent(String.format("Balalaalla Content %d",i));
            System.out.println(question.getCreateDate());
           // questionDAO.addQuestion(question);
        }*/
      int i=0;
        for(Question question:questionDAO.selectLatestQuestions(0,0,10)){
            //Date date = new Date();
            //date.setTime(date.getTime());
            //question.setCreateDate(date);
            System.out.println(questionDAO.selectByid(question.getId()));
        }
       // System.out.println(questionDAO.selectLatestQuestions(0,0,10));
    }
}
