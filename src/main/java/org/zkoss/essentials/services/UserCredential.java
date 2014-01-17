/* 
	Description:
		ZK Essentials
	History:
		Created by dennis

Copyright (C) 2012 Potix Corporation. All Rights Reserved.
*/
package org.zkoss.essentials.services;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class UserCredential implements Serializable{
	private static final long serialVersionUID = 1L;
	
	String account;
	String name;
    String role;
	
	Set<String> roles = new HashSet<String>();

	public UserCredential(String account, String name, String role) {
		this.account = account;
		this.name = name;
        this.role = role;
	}

	public UserCredential() {
		this.account = "anonymous";
		this.name = "Anonymous";
		roles.add("anonymous");
	}

	public boolean isAnonymous() {
		return hasRole("anonymous") || "anonymous".equals(account);
	}

    //used to render menu items in side bar
    public boolean isAdmin(){
        return ("ADMIN".equalsIgnoreCase(role));
    }

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public boolean hasRole(String role){
		return roles.contains(role);
	}
	
	public void addRole(String role){
		roles.add(role);
	}

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
