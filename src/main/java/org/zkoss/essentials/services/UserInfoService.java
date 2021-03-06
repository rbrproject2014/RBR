/* 
	Description:
		ZK Essentials
	History:
		Created by dennis

Copyright (C) 2012 Potix Corporation. All Rights Reserved.
*/
package org.zkoss.essentials.services;

import org.zkoss.essentials.entity.User;

import java.util.List;

public interface UserInfoService {

	/** find user by account **/
	public User findUser(String account);
	
	/** update user **/
	public User updateUser(User user);

    public String getUserRole(String account);
    public User saveUser(User user);
    public List<User> getAllUsers();
    public void deleteUser(User user);
}
