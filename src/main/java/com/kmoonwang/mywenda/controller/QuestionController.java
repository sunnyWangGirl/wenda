package com.kmoonwang.mywenda.controller;

import com.kmoonwang.mywenda.model.*;
import com.kmoonwang.mywenda.service.CommentService;
import com.kmoonwang.mywenda.service.LikeService;
import com.kmoonwang.mywenda.service.QuestionService;
import com.kmoonwang.mywenda.service.UserService;
import com.kmoonwang.mywenda.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.View;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 处理问题
 */
@Controller
public class QuestionController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    QuestionService questionService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    UserService userservice;

    @Autowired
    CommentService commentService;

    @Autowired
    LikeService likeService;

    @RequestMapping(value = "/question/add",method = {RequestMethod.POST})
    @ResponseBody
    public String addQuestion(@RequestParam("title") String title,
                              @RequestParam("content") String content){
        try{
            Question question = new Question();
            question.setTitle(title);
            question.setContent(content);
            question.setCreateDate(new Date());
            question.setCommentCount(0);

            if(hostHolder.getUser() == null){
                //用户没登录的，设置为匿名用户
                //question.setUserId(WendaUtil.ANONYMOUS_USERID);
                return WendaUtil.getJSONString(999);
            }else{
                question.setUserId(hostHolder.getUser().getId());
            }
            if(questionService.addQuestion(question) > 0){
                return WendaUtil.getJSONString(0);
            }
        }catch(Exception e){
            logger.error("增加题目失败"+e.getMessage());
        }
        return WendaUtil.getJSONString(1,"失败");
    }

    @RequestMapping(value = "/question/{qid}")
    public String questionDetail(Model model, @PathVariable("qid") int qid){
        Question question = questionService.selectByid(qid);
        model.addAttribute("question",question);

        List<Comment> commentList = commentService.getCommentsByEntity(qid, EntityType.ENTITY_QUESTION);
        //不只是评论的内容，还需要有评论的用户,所以需要使用View的东西
        List<ViewObject> comments = new ArrayList<>();
        for(Comment comment : commentList){
            ViewObject vo = new ViewObject();
            vo.set("comment",comment);
            vo.set("user",userservice.getUser(comment.getUserId()));//把评论的用户也加上
            if(hostHolder.getUser() == null){
                vo.set("liked",0);
            }else{
                //System.out.println("getstatus:"+likeService.getLikeStatus(comment.getUserId(),EntityType.ENTITY_COMMENT,comment.getId()));
                vo.set("liked",likeService.getLikeStatus(hostHolder.getUser().getId(),EntityType.ENTITY_COMMENT,comment.getId()));
            }
            vo.set("likeCount",likeService.getlikecount(EntityType.ENTITY_COMMENT,comment.getId()));
            comments.add(vo);
        }
        model.addAttribute("comments",comments);
        //model.addAttribute("user",userservice.getUser(question.getUserId()));
        return "detail";
    }
}
