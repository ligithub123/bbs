package com.ibeetl.bbs.service;

import com.ibeetl.bbs.model.BbsUser;

public interface UserService {

    BbsUser selectUserByName(String userName);
}
