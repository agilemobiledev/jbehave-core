package org.jbehave.core.configuration.spring;

import static org.jbehave.core.configuration.spring.NamespaceHandlerUtils.isAttributeDefined;

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

		if (isAttributeDefined(stepPatternElement, PREFIX_ATTRIBUTE)) {
			String prefix = getAttributeValue(stepPatternElement,
					PREFIX_ATTRIBUTE);
			beanDefinitionBuilder.addConstructorArgValue(prefix);
		}

		String id = isAttributeDefined(stepPatternElement, ID_ATTRIBUTE) ? stepPatternElement
				.getAttribute(PREFIX_ATTRIBUTE)
				: RegexPrefixCapturingPatternParser.class.getName();

		AbstractBeanDefinition beanDefinition = beanDefinitionBuilder
				.getBeanDefinition();
		NamespaceHandlerUtils.registerBeanDefinition(parserContext,
				beanDefinition, id);

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

}
