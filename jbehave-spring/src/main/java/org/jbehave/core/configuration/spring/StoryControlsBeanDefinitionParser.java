package org.jbehave.core.configuration.spring;

import static org.jbehave.core.configuration.spring.NamespaceHandlerUtils.isAttributeDefined;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public class StoryControlsBeanDefinitionParser extends
		AbstractBeanDefinitionParser {

	/**
	 * skip Scenarios After Failure setter method name.
	 */
	private static final String SKIP_SCENARIOS_AFTER_FAILURE_SETTER = "skipScenariosAfterFailure";
	/**
	 * dry Run setter method name.
	 */
	private static final String DRY_RUN_SETTER = "dryRun";
	/**
	 * skip Before And After Scenario Steps If Given Story setter method.
	 */
	private static final String SKIP_BEFORE_AND_AFTER_SCENARIO_STEPS_IF_GIVEN_STORY_SETTER = "skipBeforeAndAfterScenarioStepsIfGivenStory";
	/**
	 * doSkipScenariosAfterFailure attribute name.
	 */
	private static final String SKIP_SCENARIOS_AFTER_FAILURE_SPRING_STORY_CONTROLS_SETTER_ATTRIBUTE = "doSkipScenariosAfterFailure";
	/**
	 * doDryRun attribute name.
	 */
	private static final String DRY_RUN_SPRING_STORY_CONTROLS_ATTRIBUTE = "doDryRun";
	/**
	 * doSkipBeforeAndAfterScenarioStepsIfGivenStory attribute name.
	 */
	private static final String DO_SKIP_BEFORE_AND_AFTER_SCENARIO_STEPS_IF_GIVEN_STORY_ATTRIBUTE = "doSkipBeforeAndAfterScenarioStepsIfGivenStory";

	@Override
	protected AbstractBeanDefinition parseInternal(Element storyControls,
			ParserContext parserContext) {
		return registerSpringStoryControls(storyControls, parserContext);
	}

	/**
	 * Registers an Spring Story Controls with defined attributes.
	 * 
	 * @param storyControls
	 *            dom element.
	 * @param parserContext
	 *            spring object.
	 * @return registered story controls.
	 */
	private AbstractBeanDefinition registerSpringStoryControls(
			Element storyControls, ParserContext parserContext) {

		BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder
				.rootBeanDefinition(SpringStoryControls.class);

		if (isAttributeDefined(storyControls,
				DO_SKIP_BEFORE_AND_AFTER_SCENARIO_STEPS_IF_GIVEN_STORY_ATTRIBUTE)) {
			Boolean doSkipBeforeAndAfter = Boolean
					.valueOf(getAttributeValue(storyControls,
							DO_SKIP_BEFORE_AND_AFTER_SCENARIO_STEPS_IF_GIVEN_STORY_ATTRIBUTE));
			beanDefinitionBuilder.addPropertyValue(
					SKIP_BEFORE_AND_AFTER_SCENARIO_STEPS_IF_GIVEN_STORY_SETTER,
					doSkipBeforeAndAfter);
		}

		if (isAttributeDefined(storyControls,
				DRY_RUN_SPRING_STORY_CONTROLS_ATTRIBUTE)) {
			Boolean dryRun = Boolean.valueOf(getAttributeValue(storyControls,
					DRY_RUN_SPRING_STORY_CONTROLS_ATTRIBUTE));
			beanDefinitionBuilder.addPropertyValue(DRY_RUN_SETTER, dryRun);
		}

		if (isAttributeDefined(storyControls,
				SKIP_SCENARIOS_AFTER_FAILURE_SPRING_STORY_CONTROLS_SETTER_ATTRIBUTE)) {
			Boolean skipAfterFailure = Boolean
					.valueOf(getAttributeValue(storyControls,
							SKIP_SCENARIOS_AFTER_FAILURE_SPRING_STORY_CONTROLS_SETTER_ATTRIBUTE));
			beanDefinitionBuilder.addPropertyValue(
					SKIP_SCENARIOS_AFTER_FAILURE_SETTER, skipAfterFailure);
		}

		AbstractBeanDefinition beanDefinition = beanDefinitionBuilder
				.getBeanDefinition();
		NamespaceHandlerUtils.registerBeanDefinition(parserContext,
				beanDefinition, SpringStoryControls.class.getName());

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
