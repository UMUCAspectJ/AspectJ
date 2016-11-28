/*
 * This file creates a menu option for generating and viewing the relationship
 * table.  It will call the action to open the window when the menu option is
 * selected.
 */
package org.nb.aspectj.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "Tools",
        id = "org.nb.aspectj.actions.OpenRelationshipTable"
)
@ActionRegistration(
        displayName = "#CTL_OpenRelationshipTable"
)
//Create menu option under Menu->Tools->AspectJ
@ActionReference(path = "Menu/Tools/AspectJ", position = 240)
@Messages("CTL_OpenRelationshipTable=Open Relationship Table")
public final class OpenRelationshipTableAction implements ActionListener {

    /**
     * Generate and open the Relationship Table (TBA)
     * @param e 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO implement action body
    }
}
