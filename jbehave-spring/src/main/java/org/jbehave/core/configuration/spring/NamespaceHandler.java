package org.jbehave.core.configuration.spring;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class NamespaceHandler extends NamespaceHandlerSupport {

	private static final String REGEX_PREFIX_CAPTURING_PATTERN_PARSER = "regexPrefixCapturingPatternParser";
	private static final String STORY_PATH_RESOLVER = "storyPathResolver";
	private static final String STORY_CONTROLS = "storyControls";
	private static final String STORY_REPORTER_BUILDER = "storyReporterBuilder";
	private static final String STORY_LOADER = "storyLoader";
	private static final String PATH_CALCULATOR = "pathCalculator";
	private static final String PENDING_STEP = "pendingStep";
	private static final String FAILURE_STRATEGY = "failureStrategy";
	private static final String REGEX_STORY_PARSER = "regexStoryParser";
	private static final String PRINT_STREAM_STEP_DOC_REPORTER = "printStreamStepdocReporter";
	private static final String MARK_UNMATCHED_STEPS_AS_PENDING = "markUnmatchedStepsAsPending";
	private static final String STEP_FINDER = "stepFinder";
	private static final String STEP_MONITOR = "stepMonitor";
	private static final String LOCALIZED_KEYWORDS = "localizedKeywords";

	public void init() {

		registerBeanDefinitionParser(REGEX_PREFIX_CAPTURING_PATTERN_PARSER,
				new StepPatternParserBeanDefinitionParser());
		registerBeanDefinitionParser(STORY_PATH_RESOLVER,
				new StoryPathResolverBeanDefinitionParser());
		registerBeanDefinitionParser(STORY_CONTROLS,
				new StoryControlsBeanDefinitionParser());
		registerBeanDefinitionParser(STORY_REPORTER_BUILDER,
				new StoryReporterBuilderBeanDefinitionParser());
		registerBeanDefinitionParser(STORY_LOADER,
				new StoryLoaderBeanDefinitionParser());
		registerBeanDefinitionParser(PATH_CALCULATOR,
				new PathCalculatorBeanDefinitionParser());
		registerBeanDefinitionParser(PENDING_STEP,
				new PendingStepBeanDefinitionParser());
		registerBeanDefinitionParser(FAILURE_STRATEGY,
				new FailureStrategyBeanDefinitionParser());
		registerBeanDefinitionParser(STEP_FINDER,
				new StepFinderBeanDefinitionParser());
		registerBeanDefinitionParser(STEP_MONITOR,
				new StepMonitorBeanDefinitionParser());
		registerBeanDefinitionParser(LOCALIZED_KEYWORDS,
				new LocalizedKeywordsBeanDefinitionParser());

	}

}
