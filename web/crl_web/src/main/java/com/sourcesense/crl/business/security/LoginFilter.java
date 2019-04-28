package com.sourcesense.crl.business.security;

import java.io.IOException;
import java.security.Principal;

import javax.faces.context.FacesContext;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sourcesense.crl.web.ui.beans.UserBean;

public class LoginFilter implements Filter {

	private FilterConfig filterConfig;
	private static final String loginpage = "/authenticate.xhtml";
	private static final String homepage="/home";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	@Override
	public void destroy() {
		this.filterConfig = null;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession();
		httpRequest.setCharacterEncoding("UTF-8");
		Principal principal = httpRequest.getUserPrincipal();

		String username = principal!=null? principal.getName(): "";

		String path = httpRequest.getServletPath();
        
		UserBean userBean =(UserBean) session.getAttribute("userBean");

		if (userBean!=null){
			if ((path.equals("/") || path.equals(loginpage))) {
				httpResponse.sendRedirect(httpRequest.getContextPath()
						+ homepage);
			}
			else {
				filterChain.doFilter(request, response);
			}
		} else if ( !path.equals("/") && (!path.equals(loginpage)) && (filterConfig == null) && (!httpRequest.getServletPath().contains(
				"/javax.faces.resource")) && (!httpRequest.getServletPath().contains("/resources/"))){
			httpResponse.sendRedirect(httpRequest.getContextPath()
						+ loginpage);
		} else{
			filterChain.doFilter(request, response);
		}
	}

	public FilterConfig getFilterConfig() {
		return filterConfig;
	}

	public void setFilterConfig(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}

}