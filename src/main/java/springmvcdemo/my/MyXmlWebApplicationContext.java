package springmvcdemo.my;

import java.io.IOException;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.ResourceEntityResolver;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

public class MyXmlWebApplicationContext extends XmlWebApplicationContext {

	@Override
	protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws BeansException, IOException {
		System.out.println("MyXmlWebApplicationContext.loadBeanDefinitions");
		String temp1 = ObjectUtils.identityToString(this);
		String temp2 = ((ConfigurableWebApplicationContext)this).getId();
		super.loadBeanDefinitions(beanFactory);
	}

	/**
	 * Initialize the bean definition reader used for loading the bean
	 * definitions of this context. Default implementation is empty.
	 * <p>
	 * Can be overridden in subclasses, e.g. for turning off XML validation or
	 * using a different XmlBeanDefinitionParser implementation.
	 * 
	 * @param beanDefinitionReader
	 *            the bean definition reader used by this context
	 * @see org.springframework.beans.factory.xml.XmlBeanDefinitionReader#setValidationMode
	 * @see org.springframework.beans.factory.xml.XmlBeanDefinitionReader#setDocumentReaderClass
	 */
	protected void initBeanDefinitionReader(XmlBeanDefinitionReader beanDefinitionReader) {
		System.out.println("MyXmlWebApplicationContext.initBeanDefinitionReader");
		super.initBeanDefinitionReader(beanDefinitionReader);
	}

	/**
	 * Load the bean definitions with the given XmlBeanDefinitionReader.
	 * <p>
	 * The lifecycle of the bean factory is handled by the refreshBeanFactory
	 * method; therefore this method is just supposed to load and/or register
	 * bean definitions.
	 * <p>
	 * Delegates to a ResourcePatternResolver for resolving location patterns
	 * into Resource instances.
	 * 
	 * @throws IOException
	 *             if the required XML document isn't found
	 * @see #refreshBeanFactory
	 * @see #getConfigLocations
	 * @see #getResources
	 * @see #getResourcePatternResolver
	 */
	protected void loadBeanDefinitions(XmlBeanDefinitionReader reader) throws IOException {
		System.out.println("MyXmlWebApplicationContext.loadBeanDefinitions");
		super.loadBeanDefinitions(reader);
	}

	/**
	 * The default location for the root context is
	 * "/WEB-INF/applicationContext.xml", and "/WEB-INF/test-servlet.xml" for a
	 * context with the namespace "test-servlet" (like for a DispatcherServlet
	 * instance with the servlet-name "test").
	 */
	@Override
	protected String[] getDefaultConfigLocations() {
		System.out.println("MyXmlWebApplicationContext.getDefaultConfigLocations");
		return super.getDefaultConfigLocations();
	}
	
	@Override
    protected void initPropertySources() {
        super.initPropertySources();
        //把"MYSQL_HOST"作为启动的时候必须验证的环境变量
        getEnvironment().setRequiredProperties("JAVA_HOME");
    }

	
}
