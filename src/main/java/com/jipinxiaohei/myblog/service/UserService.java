package com.jipinxiaohei.myblog.service;

import com.jipinxiaohei.myblog.po.User;

public interface UserService {
    User checkUser(String username,String password);
}
