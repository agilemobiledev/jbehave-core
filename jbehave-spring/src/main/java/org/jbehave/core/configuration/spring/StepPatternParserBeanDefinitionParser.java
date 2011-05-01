package org.jbehave.core.configuration.spring;

import org.jbehave.core.parsers.RegexPrefixCapturingPatternParser;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public class StepPatternParserBeanDefinitionParser extends
		AbstractBeanDefinitionParser {

	/**
	 * Prefix attribute name.
	 */
	private static final String PREFIX_ATTRIBUTE = "prefix";

	@Override
	protected AbstractBeanDefinition parseInternal(Element stepPatternElement,
			ParserContext parserContext) {
		return parseStepPatternElement(stepPatternElement, parserContext);
	}

	/**
	 * Parse regexPrefixCapturingPatternParser element.
	 * 
	 * @param element
	 *            regexPrefixCapturingPatternParser.
	 * @param parserContext
	 *            spring object.
	 * @return regexPrefixCapturingPatternParser created.
	 */
	private AbstractBeanDefinition parseStepPatternElement(
			Element stepPatternElement, ParserContext parserContext) {

		BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder
				.rootBeanDefinition(RegexPrefixCapturingPatternParser.class);

		if (isPrefixDefined(stepPatternElement)) {
			String prefix = getAttributeValue(stepPatternElement,
					PREFIX_ATTRIBUTE);
			beanDefinitionBuilder.addConstructorArgValue(prefix);
		}

		AbstractBeanDefinition beanDefinition = beanDefinitionBuilder
				.getBeanDefinition();
		NamespaceHandlerUtils.registerBeanDefinition(parserContext,
				beanDefinition,
				RegexPrefixCapturingPatternParser.class.getName());

		return beanDefinition;
	}

	/**
	 * Function that checks if prefix attribute is present in
	 * regexPrefixCapturingPatternParser.
	 * 
	 * @param stepPatternElement
	 *            dom element.
	 * @return True if it is defined, false otherwise.
	 */
	private boolean isPrefixDefined(Element stepPatternElement) {
		final String prefixAttributeValue = stepPatternElement
				.getAttribute(PREFIX_ATTRIBUTE);
		return prefixAttributeValue != null && !"".equals(prefixAttributeValue);
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

}
