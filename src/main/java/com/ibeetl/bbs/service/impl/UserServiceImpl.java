package com.ibeetl.bbs.service.impl;

import com.ibeetl.bbs.bean.User;
import com.ibeetl.bbs.mapper.UserMapper;
import com.ibeetl.bbs.model.BbsUser;
import com.ibeetl.bbs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ljy
 * @date 2018/12/27
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public BbsUser selectUserByName(String userName) {

        BbsUser bbsUser = new BbsUser();
        bbsUser.setUserName(userName);
        BbsUser bbsUser1 = userMapper.selectOne(bbsUser);
        return bbsUser1;
    }
}
