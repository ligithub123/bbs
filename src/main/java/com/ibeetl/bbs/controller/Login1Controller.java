package com.ibeetl.bbs.controller;

import com.alibaba.fastjson.JSONObject;
import com.ibeetl.bbs.model.BbsUser;
import com.ibeetl.bbs.service.UserService;
import com.ibeetl.bbs.util.HashKit;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author ljy
 * @date 2018/12/27
 */
@Controller
@RequestMapping(value = "/bbs")
public class Login1Controller {

    private static final Logger logger = LoggerFactory.getLogger(Login1Controller.class);

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/login")
    public JSONObject login(@RequestParam("userName") String userName, @RequestParam("password") String password){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("err",1 );
        if(StringUtils.isEmpty(userName)){
            jsonObject.put("msg", "用户名不能为空");
            return jsonObject;
        }

        if(StringUtils.isEmpty(password)){
            jsonObject.put("msg", "密码不能为空");
            return jsonObject;
        }

        String pw = HashKit.md5(password);
        BbsUser bbsUser = userService.selectUserByName(userName);

        if(bbsUser == null){
            jsonObject.put("msg", "该用户不存在");
            return jsonObject;
        }

        if( !pw.equals(bbsUser.getPassword())){
            jsonObject.put("msg", "密码错误，请重新输入！");
            return jsonObject;
        }

        //进入新界面
        jsonObject.put("msg", "/bbs/index/1.html");
        jsonObject.put("err", 0);
        return jsonObject;
    }

}
