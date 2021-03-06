package org.jbehave.core.steps;

import java.beans.IntrospectionException;
import java.lang.reflect.Method;

import org.jbehave.core.failures.BeforeOrAfterFailed;
import org.jbehave.core.failures.UUIDExceptionWrapper;
import org.jbehave.core.parsers.StepMatcher;
import org.jbehave.core.steps.AbstractStepResult.Failed;
import org.jbehave.core.steps.AbstractStepResult.Ignorable;
import org.jbehave.core.steps.AbstractStepResult.Pending;
import org.jbehave.core.steps.StepCreator.ParameterNotFound;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.instanceOf;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class StepCreatorBehaviour {
    
    @Test
    public void shouldHandleTargetInvocationFailureInBeforeOrAfterStep() throws IntrospectionException {
        // Given
        SomeSteps stepsInstance = new SomeSteps();
        StepCreator stepCreator = new StepCreator(stepsInstance, new SilentStepMonitor());

        // When
        Method method = SomeSteps.methodFor("aFailingMethod");
        StepResult stepResult = stepCreator.createBeforeOrAfterStep(method).perform(null);

        // Then
        assertThat(stepResult, instanceOf(Failed.class));
        assertThat(stepResult.getFailure(), instanceOf(UUIDExceptionWrapper.class));
        assertThat(stepResult.getFailure().getCause(), instanceOf(BeforeOrAfterFailed.class));

    }

    @Test
    public void shouldHandleFailureInBeforeOrAfterStep() throws IntrospectionException {
        // Given
        SomeSteps stepsInstance = new SomeSteps();
        StepCreator stepCreator = new StepCreator(stepsInstance, new SilentStepMonitor());

        // When
        Method method = null;
        StepResult stepResult = stepCreator.createBeforeOrAfterStep(method).perform(null);

        // Then
        assertThat(stepResult, instanceOf(Failed.class));
        assertThat(stepResult.getFailure(), instanceOf(UUIDExceptionWrapper.class));
        assertThat(stepResult.getFailure().getCause(), instanceOf(BeforeOrAfterFailed.class));

    }

    @Test
    public void shouldHandleTargetInvocationFailureInParametrisedStep() throws IntrospectionException {
        // Given
        SomeSteps stepsInstance = new SomeSteps();
        StepCreator stepCreator = new StepCreator(stepsInstance, new SilentStepMonitor());

        // When
        Method method = SomeSteps.methodFor("aFailingMethod");
        StepResult stepResult = stepCreator.createParametrisedStep(method, "When I fail", "I fail", null).perform(null);

        // Then
        assertThat(stepResult, instanceOf(Failed.class));
    }

    @Test
    public void shouldHandleFailureInParametrisedStep() throws IntrospectionException {
        // Given
        SomeSteps stepsInstance = new SomeSteps();
        StepCreator stepCreator = new StepCreator(stepsInstance, new SilentStepMonitor());

        // When
        Method method = null;
        StepResult stepResult = stepCreator.createParametrisedStep(method, "When I fail", "I fail", null).perform(null);

        // Then
        assertThat(stepResult, instanceOf(Failed.class));
    }

    @Test(expected = ParameterNotFound.class )
    public void shouldFailIfMatchedParametersAreNotFound() throws IntrospectionException{
        // Given
        SomeSteps stepsInstance = new SomeSteps();
        StepMatcher stepMatcher = mock(StepMatcher.class);        
        StepCreator stepCreator = new StepCreator(stepsInstance, new ParameterConverters(), stepMatcher, new SilentStepMonitor());

        // When
        when(stepMatcher.parameterNames()).thenReturn(new String[]{});
        stepCreator.matchedParameter("unknown");

        // Then .. fail as expected
    }
    
    @Test
    public void shouldCreatePendingAndIgnorableAsStepResults() throws IntrospectionException {
        // When
        Step ignorableStep = StepCreator.createIgnorableStep("!-- ignore me");
        Step pendingStep = StepCreator.createPendingStep("When I'm pending", null);

        // Then
        assertThat(ignorableStep.perform(null), instanceOf(Ignorable.class));
        assertThat(ignorableStep.doNotPerform(null), instanceOf(Ignorable.class));
        assertThat(pendingStep.perform(null), instanceOf(Pending.class));
        assertThat(pendingStep.doNotPerform(null), instanceOf(Pending.class));
    }

}
