package org.jbehave.core.configuration.spring;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
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
	 * Gets content of child elements with given tag from element.
	 * 
	 * @param node
	 *            "parent" root.
	 * @param tagName
	 *            tag name of required children.
	 * @return List of content of child elements with given tag name.
	 */
	public static final List<String> getElementsContentFromNode(Element node,
			String tagName) {
		@SuppressWarnings("unchecked")
		List<Element> elements = DomUtils.getChildElementsByTagName(node,
				tagName);
		return getStringValuesFromElementsList(elements);
	}

	/**
	 * Transforms Elements list to a list of node values.
	 * 
	 * @param elements
	 *            elements where getNodeValue method will be executed.
	 * @return list of texts containing each node value.
	 */
	private static final List<String> getStringValuesFromElementsList(
			List<Element> elements) {
		List<String> elementsText = new ArrayList<String>();
		for (Element element : elements) {
			elementsText.add(DomUtils.getTextValue(element));
		}
		return elementsText;
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

	public static final BeanDefinition findBeanDefinitionByName(
			String beanName, ParserContext parserContext) {

		BeanDefinitionRegistry registry = parserContext.getRegistry();
		return registry.getBeanDefinition(beanName);
	}

}
