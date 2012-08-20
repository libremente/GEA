package com.sourcesense.crl.web.ui.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.sourcesense.crl.business.model.User;
import com.sourcesense.crl.business.security.AlfrescoSessionTicket;
import com.sourcesense.crl.business.service.AttoServiceManager;
import com.sourcesense.crl.business.service.UserServiceManager;
import com.sourcesense.crl.util.CRLMessage;
import com.sourcesense.crl.web.ui.beans.AttoBean;
import com.sourcesense.crl.web.ui.beans.UserBean;

@ManagedBean(name = "loginController")
@RequestScoped
public class LoginController {

	
	@ManagedProperty(value = "#{userServiceManager}")
	private UserServiceManager userServiceManager;
	
	private User user = new User();

	private String username;

	private String password;

	

	public String login() {
		
		User sessionUser = userServiceManager.authenticate(user);
		
		if (sessionUser!=null) {

			FacesContext context = FacesContext.getCurrentInstance();
			UserBean userBean = (UserBean) context
					.getApplication()
					.getExpressionFactory()
					.createValueExpression(context.getELContext(),
							"#{userBean}", UserBean.class)
					.getValue(context.getELContext());

			userBean.setUser(sessionUser);
			
			return "pretty:Home";

		} else {

			
			return null;

		}

	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUsername() {
		return user.getUsername();
	}

	public void setUsername(String username) {
		this.user.setUsername(username);
	}

	public String getPassword() {
		return user.getPassword();
	}

	public void setPassword(String password) {
		this.user.setPassword(password);
	}

	
	public UserServiceManager getUserServiceManager() {
		return userServiceManager;
	}

	public void setUserServiceManager(UserServiceManager userServiceManager) {
		this.userServiceManager = userServiceManager;
	}

	
}
