package com.ibeetl.bbs.controller;


import com.ibeetl.bbs.bean.BBSTopic;
import com.ibeetl.bbs.model.BbsTopic;
import com.ibeetl.bbs.service.BBSTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author ljy
 * @date 2018/12/24
 */
@Controller
@RequestMapping(value = "/bbs")
public class BBS1Controller {

    @Autowired
    private BBSTopicService bbsTopicService;

    @RequestMapping(value = "/index")
    public ModelAndView index(){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index.html");
        Example example = new Example(BbsTopic.class);
        example.setOrderByClause("CREATE_TIME DESC");
        //List<BbsTopic> bbsTopics = bbsTopicService.findAll();
        List<BbsTopic> bbsTopics = bbsTopicService.selectByExample(example);
        modelAndView.addObject("bbsTopics", bbsTopics);
        modelAndView.addObject("pagename", "首页综合");
        return modelAndView;
    }

    @RequestMapping(value = "/index{p}.html")
    public ModelAndView index1(@PathVariable("p") String p){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index.html");
        Example example = new Example(BbsTopic.class);
        example.setOrderByClause("CREATE_TIME DESC");
        //List<BbsTopic> bbsTopics = bbsTopicService.findAll();
        List<BbsTopic> bbsTopics = bbsTopicService.selectByExample(example);
        modelAndView.addObject("bbsTopics", bbsTopics);
        modelAndView.addObject("pagename", "首页综合");
        return modelAndView;
    }

}
