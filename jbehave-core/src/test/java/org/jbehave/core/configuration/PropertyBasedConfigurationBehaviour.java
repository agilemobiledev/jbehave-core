package org.jbehave.core.configuration;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.jbehave.core.configuration.PropertyBasedConfiguration.FAIL_ON_PENDING;
import static org.jbehave.core.configuration.PropertyBasedConfiguration.SILENT_SUCCESS;

import org.jbehave.core.failures.FailingUponPendingStep;
import org.jbehave.core.failures.PassingUponPendingStep;
import org.jbehave.core.reporters.ConsoleOutput;
import org.jbehave.core.reporters.SilentSuccessFilter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PropertyBasedConfigurationBehaviour {

    private String originalFailOnPending;
    private String originalSilentSuccess;

    @Before
    public void captureExistingEnvironment() {
        originalFailOnPending = System.getProperty(FAIL_ON_PENDING);
        originalSilentSuccess = System.getProperty(SILENT_SUCCESS);
    }
    
    @After
    public void resetEnvironment() {
        if (originalFailOnPending != null) {
            System.setProperty(FAIL_ON_PENDING, originalFailOnPending);
        } else {
            System.clearProperty(FAIL_ON_PENDING);
        }
        if (originalSilentSuccess != null) {
            System.setProperty(SILENT_SUCCESS, originalSilentSuccess);
        } else {
            System.clearProperty(SILENT_SUCCESS);
        }
    }
    
    @Test
    public void shouldUsePassingPendingStepStrategyByDefault() {
        System.clearProperty(FAIL_ON_PENDING);
        assertThat(new PropertyBasedConfiguration().pendingStepStrategy(), is(PassingUponPendingStep.class));
    }
    
    @Test
    public void shouldUseFailingPendingStepStrategyWhenConfiguredToDoSo() {
        System.setProperty(FAIL_ON_PENDING, "true");
        assertThat(new PropertyBasedConfiguration().pendingStepStrategy(), is(FailingUponPendingStep.class));
    }
    
    @Test
    public void shouldOuputToConsoleByDefault() {
        System.clearProperty(SILENT_SUCCESS);
        assertThat(new PropertyBasedConfiguration().defaultStoryReporter(), is(ConsoleOutput.class));
    }
    
    @Test
    public void shouldUseSilentSuccessFilterConfiguredToDoSo() {
        System.setProperty(SILENT_SUCCESS, "true");
        assertThat(new PropertyBasedConfiguration().defaultStoryReporter(), is(SilentSuccessFilter.class));
    }
    
}
