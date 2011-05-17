package org.jbehave.core.configuration.spring;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import org.jbehave.core.io.LoadFromURL;
import org.jbehave.core.io.UnderscoredCamelCaseResolver;
import org.jbehave.core.parsers.RegexPrefixCapturingPatternParser;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.steps.spring.SpringApplicationContextFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;

public class NamespaceHandlerBehaviour {

	private static ConfigurableApplicationContext context;

	@BeforeClass
	public static void initContext() {
		context = new SpringApplicationContextFactory(
				"org/jbehave/core/configuration/spring/configuration-namespace.xml")
				.createApplicationContext();
	}

	@Test
	public void shouldBuildRegexPrefixCapturingPatternParserFromNamespaceSchema() {

		Object regexPrefixCapturingPatternParserObject = context
				.getBean("regex");
		assertThat(regexPrefixCapturingPatternParserObject, notNullValue());
		assertThat(regexPrefixCapturingPatternParserObject,
				instanceOf(RegexPrefixCapturingPatternParser.class));

		RegexPrefixCapturingPatternParser regexPrefixCapturingPatternParser = (RegexPrefixCapturingPatternParser) regexPrefixCapturingPatternParserObject;
		assertThat("#", is(regexPrefixCapturingPatternParser.getPrefix()));

	}

	@Test
	public void shouldBuildStoryPathResolver() {

		Object storyPathResolverObject = context.getBean("storyPathResolver");
		assertThat(storyPathResolverObject, notNullValue());
		assertThat(storyPathResolverObject,
				instanceOf(UnderscoredCamelCaseResolver.class));
	}

	@Test
	public void shouldBuildStoryControls() {
		Object storyControlsObject = context.getBean("storyControls");
		assertThat(storyControlsObject, notNullValue());
		assertThat(storyControlsObject, instanceOf(SpringStoryControls.class));
		SpringStoryControls springStoryControls = (SpringStoryControls) storyControlsObject;
		assertThat(springStoryControls.dryRun(), is(true));
		assertThat(
				springStoryControls
						.isSkipBeforeAndAfterScenarioStepsIfGivenStory(),
				is(true));
		assertThat(springStoryControls.isSkipScenariosAfterFailure(), is(true));
	}

	@Test
	public void shouldBuildStoryReporter() {
		Object storyReporterObject = context.getBean("storyReporter");
		assertThat(storyReporterObject, notNullValue());
		assertThat(storyReporterObject,
				instanceOf(SpringStoryReporterBuilder.class));

		SpringStoryReporterBuilder springStoryReporterBuilder = (SpringStoryReporterBuilder) storyReporterObject;

		assertThat(springStoryReporterBuilder.formats(), hasItem(Format.TXT));
		assertThat(springStoryReporterBuilder.getRelativeDirectory(),
				is("target/classes"));

	}

	@Test
	public void shouldBuildStoryLoader() {
		Object storyReporterObject = context.getBean("storyLoader");
		assertThat(storyReporterObject, notNullValue());
		assertThat(storyReporterObject, instanceOf(LoadFromURL.class));
	}

}
