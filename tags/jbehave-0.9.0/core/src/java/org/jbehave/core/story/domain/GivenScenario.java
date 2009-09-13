/*
 * Created on 27-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package org.jbehave.core.story.domain;

import org.jbehave.core.minimock.story.domain.GivenUsingMiniMock;





/**
 * Adapter to allow a {@link Scenario} to be used as the context
 * for another Scenario.
 * 
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class GivenScenario extends GivenUsingMiniMock {

    private final Scenario scenario;

    public GivenScenario(Scenario scenario) {
        this.scenario = scenario;
    }
    
    public Scenario getScenario() {
        return scenario;
    }

    public void setUp(World world) {
        scenario.run(world);
    }

    public void cleanUp(World world) {
        scenario.cleanUp(world);
    }
    
}