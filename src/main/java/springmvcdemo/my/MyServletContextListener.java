package springmvcdemo.my;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MyServletContextListener implements ServletContextListener {

	Logger log = LogManager.getLogger(MyServletContextListener.class);

	private ServletContext servletContext;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		this.servletContext = sce.getServletContext();
		servletContext.setAttribute("count", 1);
		Integer count=(Integer)servletContext.getAttribute("count");
		Object display_name=servletContext.getAttribute("display-name");
		String v = servletContext.getInitParameter("myParamName");
		log.info("MyServletContextListener contextInitialized:myParamName=" + v);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		log.info("MyServletContextListener contextDestroyed");
	}

}
