package com.teamsix.filter;

import java.io.IOException;

import com.teamsix.model.bean.Member;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebFilter(urlPatterns = "/db/*")
public class AdministratorCheckFilter extends HttpFilter implements Filter {
       
	private static final long serialVersionUID = 1L;

	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)res;
		HttpSession session = request.getSession();
		Member member = null;
		if(session.getAttribute("member") != null) {
			member = (Member)session.getAttribute("member");
			if(!"Administrator".equals(member.getMemclass())) {
				response.sendRedirect("/rr/jumppage");
			}
		}else {
			response.sendRedirect("/rr/jumppage");
		}
		chain.doFilter(req, res);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
