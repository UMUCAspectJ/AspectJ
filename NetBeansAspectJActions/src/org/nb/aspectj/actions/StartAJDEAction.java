/*
 * This file creates a menu and toolbar option to start the AJDE.  It will 
 * also generate the CrossRefs window.
 * TBA = To Be Added
 */
package org.nb.aspectj.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "Tools",
        id = "org.nb.aspectj.actions.StartAJDE"
)
@ActionRegistration(
        iconBase = "org/nb/aspectj/actions/AspectJIcon.GIF",
        displayName = "#CTL_StartAJDE"
)
//Creates menu option under Menu->Tools->AspectJ and a Toolbar option
@ActionReferences({
    @ActionReference(path = "Menu/Tools/AspectJ", position = 140),
    @ActionReference(path = "Toolbars/Build", position = 500)
})
@Messages("CTL_StartAJDE=Start AJDE")
public final class StartAJDEAction implements ActionListener{

    /**
     * Start the AspectJ compiler/etc and show CrossRefs window.
     * @param e 
     */
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        //Below line creates new instance of CrossRefsTopComponent and runs 
        //the code in the constructor, but initComponents is not displaying 
        //the content.  Hard to test because cannot change the initComponents
        //code.  initComponents works fine when called directly by the 
        //CrossRefsTopComponent ActionID
        CrossRefsTopComponent xRef = new CrossRefsTopComponent();        
    }
}
