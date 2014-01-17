/* 
	Description:
		ZK Essentials
	History:
		Created by dennis

Copyright (C) 2012 Potix Corporation. All Rights Reserved.
*/
package org.zkoss.essentials.services.impl;

import java.io.Serializable;
import java.util.List;

import org.zkoss.essentials.entity.User;
import org.zkoss.essentials.services.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("userInfoService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class UserInfoServiceImpl implements UserInfoService,Serializable{
	private static final long serialVersionUID = 1L;
	
	@Autowired
	UserDao dao;
	
	public User findUser(String account){
		return dao.get(account);
	}
	
	public User updateUser(User user){
		return dao.update(user);
	}

    public String getUserRole(String account){
        User user = dao.get(account);
        return user.getRole();
    }

    public User saveUser(User user){
        return dao.save(user);
    }

    public List<User> getAllUsers() {
        List<User> userList = dao.queryAll();
        return userList;
    }
}
