package com.ibeetl.bbs.controller;


import com.alibaba.fastjson.JSONObject;
import com.ibeetl.bbs.bean.BBSTopic;
import com.ibeetl.bbs.common.WebUtils;
import com.ibeetl.bbs.model.BbsPost;
import com.ibeetl.bbs.model.BbsReply;
import com.ibeetl.bbs.model.BbsTopic;
import com.ibeetl.bbs.model.BbsUser;
import com.ibeetl.bbs.service.BBSPostService;
import com.ibeetl.bbs.service.BBSReplyService;
import com.ibeetl.bbs.service.BBSTopicService;
import com.sun.imageio.plugins.common.I18N;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ljy
 * @date 2018/12/24
 */
@Controller
@RequestMapping(value = "/bbs")
public class BBS1Controller {

    private static final Logger logger = LoggerFactory.getLogger(BBS1Controller.class);

    @Autowired
    private BBSTopicService bbsTopicService;

    @Autowired
    private BBSPostService bbsPostService;

    @Autowired
    private BBSReplyService bbsReplyService;

    @Autowired
    WebUtils webUtils;

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

    /**
     * 查看帖子的详细内容
     * @param id 帖子的id
     * @return
     */
    @RequestMapping(value = "/topic/{id}-1.html",headers = {})
    @ResponseBody
    public ModelAndView topicDetail(@PathVariable("id") Integer id){

        ModelAndView modelAndView = new ModelAndView();
        List<BbsPost> upPosts = new ArrayList<>();
        if(id == null){
            modelAndView.addObject("err",1 );
            modelAndView.addObject("msg","请选择要查看的帖子" );
            return modelAndView;
        }
        modelAndView.setViewName("/detail.html");
        BbsTopic bbsTopic = bbsTopicService.selectTopicById(id);
        modelAndView.addObject("topic", bbsTopic);

        if(bbsTopic.getPostCount() > 10){
            //如果多于10条评论，前面5条是按点赞的排序显示，后面的按照时间倒叙排列
            upPosts = bbsPostService.selectPostByIdLimit5(id);
        }

        List<BbsPost> bbsPosts = getPosts(id);

        modelAndView.addObject("upPosts",upPosts );
        modelAndView.addObject("postPage",bbsPosts );
        //修改帖子的浏览次数
        bbsTopicService.changPVcount(id);
        modelAndView.addObject("err",0 );
        return modelAndView;
    }

    /** 获取评论*/
    private List<BbsPost> getPosts(Integer id) {
        List<BbsPost> bbsPosts = bbsPostService.selectPostsByTopicId(id);
        for (BbsPost bbsPost : bbsPosts) {
            List<BbsReply> bbsReplies = bbsReplyService.seletPostReply(id,bbsPost.getId());

            if(bbsReplies != null && !bbsReplies.isEmpty()){
                bbsPost.setReplys(bbsReplies);
            }
        }
        return bbsPosts;
    }

    /**
     * 赞或者踩
     * @param num 赞还是踩的区分
     * @param postId 具体评论
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/post/support/{postId}")
    public JSONObject support(@RequestParam("num") Integer num, @PathVariable("postId")Integer postId,
                              HttpServletRequest request, HttpServletResponse response){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("err",1 );
        BbsUser user = webUtils.currentUser(request, response);
        if(user == null){
            jsonObject.put("msg", "未登录用户！");
            return jsonObject;
        }

        BbsPost bbsPost = bbsPostService.getPostById(postId);
        if(bbsPost == null){
            jsonObject.put("msg","该记录不存在" );
            return jsonObject;
        }

        if(num == 0){
            Integer cons = bbsPost.getCons() == null?0:bbsPost.getCons();
            cons++;
            bbsPost.setCons(cons);
            jsonObject.put("data",cons );
        }else {
            Integer pros = bbsPost.getPros() == null?0:bbsPost.getPros();
            pros++;
            bbsPost.setPros(pros);
            jsonObject.put("data",pros );
        }

        bbsPostService.saveSupport(bbsPost);
        jsonObject.put("err",0 );

        return jsonObject;
    }

    /**
     * 保存评论
     * @param topicId
     * @param content
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/post/save")
    public JSONObject savePost(@RequestParam("topicId") Integer topicId,
                                @RequestParam("content") String content,
                                HttpServletRequest request,HttpServletResponse response){

        JSONObject jsonObject = new JSONObject();
        BbsUser user = webUtils.currentUser(request, response);
        if(user == null){
            jsonObject.put("msg", "未登录用户！");
            return jsonObject;
        }

        jsonObject.put("err",1 );
        if(topicId == null){
            jsonObject.put("msg", "系统错误，请联系管理员");
            return jsonObject;
        }

        if(StringUtils.isEmpty(content)){
            jsonObject.put("msg", "内容不能为空");
            return jsonObject;
        }

        try {
            bbsPostService.savePost(topicId,content,user);
            bbsTopicService.changePostCount(topicId);
            jsonObject.put("err", 0);
            jsonObject.put("msg","bbs/topic/${"+topicId+"}-1.html" );
        } catch (Exception e) {
            jsonObject.put("msg", "评论保存出错，请联系管理员");
            return jsonObject;
        }

        return jsonObject;
    }

    /**
     * 保存评论的评论
     * @param topicId
     * @param postId
     * @param content
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/reply/save")
    public JSONObject saveReply(@RequestParam("topicId") Integer topicId,
                                @RequestParam("postId") Integer postId,
                               @RequestParam("content") String content,
                               HttpServletRequest request,HttpServletResponse response){

        JSONObject jsonObject = new JSONObject();
        BbsUser user = webUtils.currentUser(request, response);
        if(user == null){
            jsonObject.put("msg", "未登录用户！");
            return jsonObject;
        }

        jsonObject.put("err",1 );
        if(topicId == null){
            jsonObject.put("msg", "系统错误，请联系管理员");
            return jsonObject;
        }

        if(StringUtils.isEmpty(content)){
            jsonObject.put("msg", "内容不能为空");
            return jsonObject;
        }

        try {
            bbsReplyService.saveReply(postId,topicId,content,user);
            //修改post为有回复的状态
            bbsPostService.changeHasReplyStatus(postId,topicId);

            jsonObject.put("err", 0);
            jsonObject.put("msg","bbs/topic/"+topicId+"-1.html" );
        } catch (Exception e) {
            jsonObject.put("msg", "评论的回复保存出错，请联系管理员");
            return jsonObject;
        }

        return jsonObject;
    }

    @ResponseBody
    @RequestMapping(value = "/post/edit/{id}.html")
    public ModelAndView postEdit(@PathVariable("id") Integer id,
                                HttpServletRequest request,HttpServletResponse response){

        ModelAndView modelAndView = new ModelAndView();
        BbsUser user = webUtils.currentUser(request, response);
        modelAndView.addObject("err",1 );
        if(user == null){
            modelAndView.addObject("msg", "未登录用户！");
            return modelAndView;
        }

        if(id == null){
            modelAndView.addObject("msg", "系统错误，请联系管理员");
            return modelAndView;
        }

        try {
            modelAndView.setViewName("/postEdit.html");
            BbsPost post = bbsPostService.getPostById(id);
            if(post != null){
                modelAndView.addObject("post", post);
            }else {
                logger.info("获取的评论对象为空");
            }
            BbsTopic bbsTopic = bbsTopicService.selectTopicById(post.getTopicId());
            if(bbsTopic != null){
                modelAndView.addObject("topic", bbsTopic);
            }else {
                logger.info("编辑评论时获取帖子对象为空");
            }
        } catch (Exception e) {
            modelAndView.addObject("msg", "评论编辑出错，请联系管理员");
        }

        return modelAndView;
    }

    /**
     * 帖子中评论内容进行更新
     * @param id
     * @param content
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/post/update")
    public JSONObject postUpdate(@RequestParam("id") Integer id,
                               @RequestParam("content") String content,
                               HttpServletRequest request,HttpServletResponse response){

        JSONObject jsonObject = new JSONObject();
        BbsUser user = webUtils.currentUser(request, response);
        if(user == null){
            jsonObject.put("msg", "未登录用户！");
            return jsonObject;
        }

        jsonObject.put("err",1 );
        if(id == null){
            jsonObject.put("msg", "系统错误，请联系管理员");
            return jsonObject;
        }

        if(StringUtils.isEmpty(content)){
            jsonObject.put("msg", "内容不能为空");
            return jsonObject;
        }

        try {
            bbsPostService.updatePost(id,content);
            BbsPost post = bbsPostService.getPostById(id);
            Integer topicId = post.getTopicId();
            jsonObject.put("err", 0);
            jsonObject.put("msg","/bbs/topic/"+topicId+"-1.html" );
            jsonObject.put("id",post.getId() );
        } catch (Exception e) {
            jsonObject.put("msg", "评论更新出错，请联系管理员");
            return jsonObject;
        }

        return jsonObject;
    }


    @ResponseBody
    @RequestMapping(value = "//post/delete/{id}")
    public JSONObject deletePost(@PathVariable("id") Integer id,
                                 HttpServletRequest request,HttpServletResponse response){

        JSONObject jsonObject = new JSONObject();
        BbsUser user = webUtils.currentUser(request, response);
        if(user == null){
            jsonObject.put("msg", "未登录用户！");
            return jsonObject;
        }

        jsonObject.put("err",1 );
        if(id == null){
            jsonObject.put("msg", "请选择需要删除的评论");
            return jsonObject;
        }

        try {
            bbsPostService.deletePostById(id);
            BbsPost post = bbsPostService.getPostById(id);
            Integer topicId = post.getTopicId();
            jsonObject.put("err", 0);
            jsonObject.put("msg","/bbs/topic/"+topicId+"-1.html" );
        } catch (Exception e) {
            jsonObject.put("msg", "评论删除出错，请联系管理员");
            return jsonObject;
        }

        return jsonObject;
    }
}
