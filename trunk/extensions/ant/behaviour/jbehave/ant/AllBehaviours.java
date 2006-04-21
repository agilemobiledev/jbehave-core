/*
 * Created on 07-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.ant;

import jbehave.core.behaviour.Behaviours;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class AllBehaviours implements Behaviours {

    public Class[] getBehaviours() {
        return new Class[] {
                JBehaveTaskBehaviour.class
        };
    }
}