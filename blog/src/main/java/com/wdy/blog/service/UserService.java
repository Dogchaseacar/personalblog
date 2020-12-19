package com.wdy.blog.service;

import com.wdy.blog.po.User;

public interface UserService {
    User checkUser(String username, String password);
}
