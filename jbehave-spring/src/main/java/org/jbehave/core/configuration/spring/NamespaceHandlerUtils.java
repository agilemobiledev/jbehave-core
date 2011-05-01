package org.jbehave.core.configuration.spring;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.xml.ParserContext;

public class NamespaceHandlerUtils {

	/**
	 * Register a new bean into Spring context.
	 * 
	 * @param parserContext
	 *            parser Context Object.
	 * @param beanDefinition
	 *            bean Definition to register.
	 * @param springBeanId
	 *            name that will be used as Spring Bean Id field.
	 */
	public static final void registerBeanDefinition(ParserContext parserContext,
			BeanDefinition beanDefinition, String springBeanId) {
		BeanDefinitionHolder beanDefinitionHolder = new BeanDefinitionHolder(
				beanDefinition, springBeanId);
		BeanDefinitionReaderUtils.registerBeanDefinition(beanDefinitionHolder,
				parserContext.getRegistry());
		parserContext.getReaderContext().fireComponentRegistered(
				new BeanComponentDefinition(beanDefinitionHolder));
	}
	
}
