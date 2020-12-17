package springmvcdemo.my;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class MyFirstServlet implements Servlet {

	@Override
	public void init(ServletConfig config) throws ServletException {
		System.out.print("init");
	}

	@Override
	public ServletConfig getServletConfig() {
		System.out.print("getServletConfig");
		return null;
	}

	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		System.out.print("service");
	}

	@Override
	public String getServletInfo() {
		System.out.print("getServletInfo");
		return null;
	}

	@Override
	public void destroy() {
		System.out.print("destroy");
	}

}
