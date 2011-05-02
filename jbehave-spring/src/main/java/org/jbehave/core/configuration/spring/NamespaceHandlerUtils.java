package org.jbehave.core.configuration.spring;

import java.util.Locale;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public class NamespaceHandlerUtils {

	private static final char LANGUAGE_SEPARATOR = '-';

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
	public static final void registerBeanDefinition(
			ParserContext parserContext, BeanDefinition beanDefinition,
			String springBeanId) {
		BeanDefinitionHolder beanDefinitionHolder = new BeanDefinitionHolder(
				beanDefinition, springBeanId);
		BeanDefinitionReaderUtils.registerBeanDefinition(beanDefinitionHolder,
				parserContext.getRegistry());
		parserContext.getReaderContext().fireComponentRegistered(
				new BeanComponentDefinition(beanDefinitionHolder));
	}

	/**
	 * Function that checks if attribute is defined in given element.
	 * 
	 * @param definedElement
	 *            dom element.
	 * @param attributeName
	 * @return True if extension attribute is defined, false otherwise.
	 */
	public static final boolean isAttributeDefined(Element definedElement,
			String attributeName) {
		final String attributeValue = definedElement
				.getAttribute(attributeName);
		return attributeValue != null && !"".equals(attributeValue);
	}

	/**
	 * Method that parses an xsd:language XML Schema element to a Locale object.
	 * 
	 * @param xsdLanguageValue
	 *            in xsd:language format.
	 * @return Java Locale object.
	 */
	public static final Locale parseXSDLanguageValue(String xsdLanguageValue) {

		int separator = xsdLanguageValue.indexOf(LANGUAGE_SEPARATOR);
		Locale locale = null;
		if (separator > 0) {
			String language = xsdLanguageValue.substring(0, separator);
			String country = xsdLanguageValue.substring(separator + 1,
					xsdLanguageValue.length());
			locale = new Locale(language, country);
		} else {
			locale = new Locale(xsdLanguageValue);
		}

		return locale;

	}

}
