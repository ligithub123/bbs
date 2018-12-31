package com.ibeetl.bbs.controller;


import com.alibaba.fastjson.JSONObject;
import com.ibeetl.bbs.bean.BBSTopic;
import com.ibeetl.bbs.model.BbsTopic;
import com.ibeetl.bbs.model.BbsUser;
import com.ibeetl.bbs.service.BBSTopicService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
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

    /** 首页 */
    @RequestMapping(value = "/index")
    public ModelAndView index(){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index.html");

        //把置顶帖子先查询出来
        List<BbsTopic> bbsTopics = bbsTopicService.selectTopic();
        modelAndView.addObject("bbsTopics", bbsTopics);
        modelAndView.addObject("pagename", "首页综合");
        return modelAndView;
    }

    /**
     *
     * @param p
     * @return
     */
    @RequestMapping(value = "/index/{p}.html")
    public ModelAndView index1(@PathVariable("p") int p){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index.html");
        //todo p还没有利用到
        //Example example = new Example(BbsTopic.class);
        //example.setOrderByClause("CREATE_TIME DESC");
        //List<BbsTopic> bbsTopics = bbsTopicService.selectByExample(example);
        List<BbsTopic> bbsTopics = bbsTopicService.selectTopic();
        modelAndView.addObject("bbsTopics", bbsTopics);
        modelAndView.addObject("pagename", "首页综合");
        return modelAndView;
    }

    /** 跳转到添加帖子的界面*/
    @RequestMapping(value = "/add")
    public ModelAndView add(){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("post.html");
        return modelAndView;
    }

    /**
     * 保存帖子
     * @param moduleId
     * @param title
     * @param postContent
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/saveTopic")
    public JSONObject saveTopic(@RequestParam("moduleId") Integer moduleId,
                                @RequestParam("title") String title,
                                @RequestParam("postContent") String postContent,
                                HttpServletRequest request){

        BbsUser user = (BbsUser) request.getSession().getAttribute("user");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("err",1 );
        if(StringUtils.isEmpty(title)){
            jsonObject.put("msg", "标题不能为空");
            return jsonObject;
        }

        if(StringUtils.isEmpty(postContent)){
            jsonObject.put("msg", "内容不能为空");
            return jsonObject;
        }

        try {
            bbsTopicService.saveTopic(moduleId,title,postContent,user);

            jsonObject.put("msg", "/bbs/index/1.html");
            jsonObject.put("err", 0);
        } catch (Exception e) {
            jsonObject.put("msg", "系统错误，请联系管理员");
            return jsonObject;
        }

        return jsonObject;
    }

    /**
     * 改变帖子状态是否置顶
     * @param id
     * @return
     */
    @RequestMapping(value = "/topic/up/{id}")
    @ResponseBody
    public ModelAndView isUp(@PathVariable("id") Integer id){

        ModelAndView modelAndView = new ModelAndView();
        if(id == null){
            modelAndView.addObject("err",1 );
            modelAndView.addObject("msg","请选择要更改置顶状态的帖子" );
            return modelAndView;
        }
        bbsTopicService.changeIsUpStatus(id);
        modelAndView.addObject("err",0 );
        return modelAndView;
    }

    /**
     * 改变帖子状态是否为精华
     * @param id
     * @return
     */
    @RequestMapping(value = "/topic/nice/{id}")
    @ResponseBody
    public ModelAndView isNice(@PathVariable("id") Integer id){

        ModelAndView modelAndView = new ModelAndView();
        if(id == null){
            modelAndView.addObject("err",1 );
            modelAndView.addObject("msg","请选择要更改精华状态的帖子" );
            return modelAndView;
        }
        bbsTopicService.changeIsNiceStatus(id);
        modelAndView.addObject("err",0 );
        return modelAndView;
    }

    /**
     * 删除id
     * @param id
     * @return
     */
    @RequestMapping(value = "/topic/delete/{id}")
    @ResponseBody
    public ModelAndView delete(@PathVariable("id") Integer id){

        ModelAndView modelAndView = new ModelAndView();
        if(id == null){
            modelAndView.addObject("err",1 );
            modelAndView.addObject("msg","请选择要删除的帖子" );
            return modelAndView;
        }
        bbsTopicService.deleteTopic(id);
        modelAndView.addObject("err",0 );
        return modelAndView;
    }

    @RequestMapping(value = "/topic/{id}-1.html")
    @ResponseBody
    public ModelAndView topicDetail(@PathVariable("id") Integer id){

        ModelAndView modelAndView = new ModelAndView();
        if(id == null){
            modelAndView.addObject("err",1 );
            modelAndView.addObject("msg","请选择要查看的帖子" );
            return modelAndView;
        }
        modelAndView.setViewName("/detail.html");
        modelAndView.addObject("err",0 );
        return modelAndView;
    }
}
