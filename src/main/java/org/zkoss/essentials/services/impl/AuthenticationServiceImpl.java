package org.zkoss.essentials.services.impl;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.zkoss.essentials.entity.User;
import org.zkoss.essentials.services.AuthenticationService;
import org.zkoss.essentials.services.UserInfoService;
import org.zkoss.essentials.services.UserCredential;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

@Service("authService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class AuthenticationServiceImpl implements AuthenticationService,Serializable{
	private static final long serialVersionUID = 1L;
	
	@Autowired
    UserInfoService userInfoService;

	public UserCredential getUserCredential(){
		Session sess = Sessions.getCurrent();
		UserCredential cre = (UserCredential)sess.getAttribute("userCredential");
		if(cre==null){
			cre = new UserCredential();//new a anonymous user and set to session
			sess.setAttribute("userCredential",cre);
		}
		return cre;
	}

	public boolean login(String nm, String pd) {
		User user = userInfoService.findUser(nm);
		//a simple plan text password verification
		if(user==null || !user.getPassword().equals(pd)){
			return false;
		}
		
		Session sess = Sessions.getCurrent();
        //user role added to userCredential session attribute
		UserCredential cre = new UserCredential(user.getAccount(),user.getFullName(),user.getRole());
		sess.setAttribute("userCredential",cre);

        //System.out.println(" Login info (Role)-------->>>>>>  "+user.getRole());
		
		//handle the role here for authorization
		return true;
	}
	
	public void logout() {
		Session sess = Sessions.getCurrent();
		sess.removeAttribute("userCredential");
	}
}
