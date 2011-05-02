package org.jbehave.core.configuration.spring;

import static org.jbehave.core.configuration.spring.NamespaceHandlerUtils.isAttributeDefined;
import static org.jbehave.core.configuration.spring.NamespaceHandlerUtils.parseXSDLanguageValue;

import java.util.List;
import java.util.Locale;

import org.jbehave.core.io.CasePreservingResolver;
import org.jbehave.core.io.UnderscoredCamelCaseResolver;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

public class StoryPathResolverBeanDefinitionParser extends
		AbstractBeanDefinitionParser {

	/**
	 * casePreservingResolver tag name.
	 */
	private static final String CASE_PRESERVING_RESOLVER_TAG = "casePreservingResolver";
	/**
	 * underscoredCamelCaseResolver tag name.
	 */
	private static final String UNDERSCORED_CAMEL_CASE_RESOLVER_TAG = "underscoredCamelCaseResolver";

	/**
	 * extension attribute name.
	 */
	private static final String EXTENSION_ATTRIBUTE = "extension";

	/**
	 * resolutionPattern attribute name.
	 */
	private static final String RESOLUTION_PATTERN_ATTRIBUTE = "resolutionPattern";

	/**
	 * locale attribute name.
	 */
	private static final String LOCALE_ATTRIBUTE = "locale";

	/**
	 * Possible implementations of Story Path Resolver.
	 */
	private static final String[] STORY_PATH_RESOLVER_CHOICES = new String[] {
			CASE_PRESERVING_RESOLVER_TAG, UNDERSCORED_CAMEL_CASE_RESOLVER_TAG };

	@Override
	protected AbstractBeanDefinition parseInternal(
			Element storyPathResolverElement, ParserContext parserContext) {
		return registerStoryPathResolver(storyPathResolverElement,
				parserContext);
	}

	/**
	 * Registers required story path resolver.
	 * 
	 * @param storyPathResolverElement
	 *            dom element.
	 * @param parserContext
	 *            spring object.
	 * @return registered story path resolver.
	 */
	private AbstractBeanDefinition registerStoryPathResolver(
			Element storyPathResolverElement, ParserContext parserContext) {
		@SuppressWarnings("unchecked")
		List<Element> storyLoader = DomUtils.getChildElementsByTagName(
				storyPathResolverElement, STORY_PATH_RESOLVER_CHOICES);

		AbstractBeanDefinition beanDefinition = null;
		if (storyLoader.size() > 0) {
			Element definedElement = getFirstElement(storyLoader);
			beanDefinition = registerStoryPathResolverByTagName(definedElement,
					parserContext);
		} else {
			throw new IllegalArgumentException(
					String.format(
							"storyPathResolverType tag is present, but no implementation has been provided. Try providing %s or %s tag.",
							CASE_PRESERVING_RESOLVER_TAG,
							UNDERSCORED_CAMEL_CASE_RESOLVER_TAG));
		}
		return beanDefinition;

	}

	/**
	 * Method that resolves which story path resolver implementation is defined
	 * and registers it.
	 * 
	 * @param definedElement
	 *            with required attributes.
	 * @param parserContext
	 *            spring object.
	 * @return registered story path resolver.
	 */
	private AbstractBeanDefinition registerStoryPathResolverByTagName(
			Element definedElement, ParserContext parserContext) {

		if (CASE_PRESERVING_RESOLVER_TAG.equals(definedElement.getLocalName())) {
			return registerCasePreservingResolver(definedElement, parserContext);
		} else {
			if (UNDERSCORED_CAMEL_CASE_RESOLVER_TAG.equals(definedElement
					.getLocalName())) {
				return registerUnderscoredCamelCaseResolver(definedElement,
						parserContext);
			}
		}

		return null;
	}

	/**
	 * Register a Underscored Camel Case Resolver with defined attributes.
	 * 
	 * @param definedElement
	 *            dom element.
	 * @param parserContext
	 *            spring object.
	 * @return registered story path resolver.
	 */
	private AbstractBeanDefinition registerUnderscoredCamelCaseResolver(
			Element definedElement, ParserContext parserContext) {
		BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder
				.rootBeanDefinition(UnderscoredCamelCaseResolver.class);

		if (isAttributeDefined(definedElement, EXTENSION_ATTRIBUTE)) {
			String extension = getAttributeValue(definedElement,
					EXTENSION_ATTRIBUTE);
			beanDefinitionBuilder.addConstructorArgValue(extension);
		}

		if (isAttributeDefined(definedElement, RESOLUTION_PATTERN_ATTRIBUTE)) {
			String resolutionPattern = getAttributeValue(definedElement,
					RESOLUTION_PATTERN_ATTRIBUTE);
			beanDefinitionBuilder.addConstructorArgValue(resolutionPattern);
		}

		if (isAttributeDefined(definedElement, LOCALE_ATTRIBUTE)) {
			Locale locale = parseXSDLanguageValue(getAttributeValue(
					definedElement, LOCALE_ATTRIBUTE));
			beanDefinitionBuilder.addConstructorArgValue(locale);
		}

		AbstractBeanDefinition beanDefinition = beanDefinitionBuilder
				.getBeanDefinition();
		NamespaceHandlerUtils.registerBeanDefinition(parserContext,
				beanDefinition, UnderscoredCamelCaseResolver.class.getName());

		return beanDefinition;
	}

	/**
	 * Register a Case Preserving Resolver with defined attributes.
	 * 
	 * @param definedElement
	 *            dom element.
	 * @param parserContext
	 *            spring object.
	 * @return registered story path resolver.
	 */
	private AbstractBeanDefinition registerCasePreservingResolver(
			Element definedElement, ParserContext parserContext) {

		BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder
				.rootBeanDefinition(CasePreservingResolver.class);

		if (isAttributeDefined(definedElement, EXTENSION_ATTRIBUTE)) {
			String extension = getAttributeValue(definedElement,
					EXTENSION_ATTRIBUTE);
			beanDefinitionBuilder.addConstructorArgValue(extension);
		}

		AbstractBeanDefinition beanDefinition = beanDefinitionBuilder
				.getBeanDefinition();
		NamespaceHandlerUtils.registerBeanDefinition(parserContext,
				beanDefinition, CasePreservingResolver.class.getName());

		return beanDefinition;
	}

	/**
	 * Gets attribute value for given element.
	 * 
	 * @param element
	 *            element containing attribute.
	 * @param attributeName
	 *            attribute name.
	 * @return attribute value.
	 */
	private String getAttributeValue(Element element, String attributeName) {
		return element.getAttribute(attributeName);
	}

	/**
	 * Gets first tag name of given element list.
	 * 
	 * @param elements
	 *            that first tag name is returned.
	 * @return Tag name of first element.
	 */
	private Element getFirstElement(List<Element> elements) {
		return elements.get(0);
	}

}
