/*
 * This file handles the creation/opening of the configuration screen as well
 * as the updating of the manifest file.  Could be broken into two files.
 * TBA = To Be Added
 */
package org.nb.aspectj.actions;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "Tools",
        id = "org.nb.aspectj.actions.OpenConfigurationScreen"
)
@ActionRegistration(
        displayName = "#CTL_OpenConfigurationScreen"
)
//Create menu option under Menu->Tools->AspectJ
@ActionReference(path = "Menu/Tools/AspectJ", position = 340)
@Messages("CTL_OpenConfigurationScreen=Configure")

public final class OpenConfigurationScreen implements ActionListener {

    private String aJPath;
    private String javaPath;    
    
    /*
     * Generates the visual components of the configuration screen. 
    */
    public void createGUI()
    {
        //This will need to be read from the MANIFEST file to allow 
        //for persistence between sessions (TBA)
        aJPath = ""; 
        //This will need to be read from the MANIFEST file to allow 
        //for persistence between sessions (TBA)
        javaPath = ""; 
        
        final JFrame mainFrame = new JFrame("AspectJ Configuration");
        JLayeredPane contentPane = new JLayeredPane();
        JPanel content = new JPanel();
        Font label = new Font("Arial", Font.PLAIN, 14);
        Dimension labelDim = new Dimension(130, 35);
        Dimension textFieldDim = new Dimension(375, 30);
        Dimension buttonDim = new Dimension(100, 35);
        
        //Set the size and layout of the main configuration window
        mainFrame.setSize(700,200);
        contentPane.setSize(700,200);
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame.setLocation((int)((dimension.getWidth() - mainFrame.getWidth()) / 2), (int)((dimension.getHeight() - mainFrame.getHeight()) / 2));
        content.setLayout(new FlowLayout());
        content.setBounds(0, 0, 700, 200);
        
        //Set the text/size/etc for the label at the top of the window
        JLabel headerLabel = new JLabel("Please select install locations.  Default uses the components that came with the plugin.");
        headerLabel.setPreferredSize(new Dimension(700, 40));
        headerLabel.setFont(label);
        headerLabel.setHorizontalAlignment(JLabel.CENTER);
        
        //Set the label for the first text field
        JLabel aJLocLabel = new JLabel("AspectJ Jar File");
        aJLocLabel.setPreferredSize(labelDim);
        aJLocLabel.setFont(label);
        aJLocLabel.setHorizontalAlignment(JLabel.RIGHT);
        
        //Create first text field.  Will need to have a setText with the 
        //AspectJ location from the MANIFEST file (TBA)
        final JTextField aJLocField = new JTextField();
        aJLocField.setPreferredSize(textFieldDim);
        aJLocField.setFont(label);
        aJLocField.setEditable(false);
        
        //Creates checkbox for the user to select whether to use the AspectJ
        //that came with the plugin or their own
        final JCheckBox aJLocDef = new JCheckBox();
        aJLocDef.setText("Default");
        aJLocDef.setFont(label);
        aJLocDef.setSelected(true);
        
        //Adds button that will allow user to browse to their own AspectJ
        //jar location (TBA)
        JButton aJLocButton = new JButton("Browse...");
        aJLocButton.setSize(buttonDim);
        aJLocButton.setFont(label);
        
        //Creates label for second TextField
        JLabel javaLocLabel = new JLabel("Java Version");
        javaLocLabel.setPreferredSize(labelDim);
        javaLocLabel.setFont(label);
        javaLocLabel.setHorizontalAlignment(JLabel.RIGHT);
        
        //Create first text field.  Will need to have a setText with the 
        //Java version from the MANIFEST file (TBA)
        final JTextField javaLocField = new JTextField();
        javaLocField.setPreferredSize(textFieldDim);
        javaLocField.setFont(label);
        javaLocField.setEditable(false);
        
        //Creates checkbox for the user to select whether to use the default
        //Java version that the plugin was configured for or their own
        final JCheckBox javaLocDef = new JCheckBox();
        javaLocDef.setText("Default");
        javaLocDef.setFont(label);
        javaLocDef.setSelected(true);
        
        //Creates a button that allows the user to browse to the Java install
        //location to determine version
        JButton javaLocButton = new JButton("Browse...");
        javaLocButton.setSize(buttonDim);
        javaLocButton.setFont(label);
        
        //Creates button that the user will click to confirm the information
        //entered in the TextFields and update the MANIFEST.MF file
        JButton submitButton = new JButton("OK");
        submitButton.setSize(buttonDim);
        submitButton.setFont(label);
        
        //Creates a button that closes the configuration screen without 
        //updating the manifest file
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setSize(buttonDim);
        cancelButton.setFont(label);
        
        //Add all components to the configuration pane
        content.add(headerLabel);
        content.add(aJLocLabel);
        content.add(aJLocField);
        content.add(aJLocDef);
        content.add(aJLocButton);
        content.add(javaLocLabel);
        content.add(javaLocField);
        content.add(javaLocDef);
        content.add(javaLocButton);
        content.add(submitButton);
        content.add(cancelButton);
        
        //Add the configuration pane to the configuration window
        mainFrame.add(content);
        
        //Add ActionListener to AspectJ Default checkbox so when it is clicked
        //it will set the corresponding TextField to editable or not.  If 
        //default is selected, TextField is not editable
        ActionListener aJLocDefActionListener = new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
              AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
              boolean selected = abstractButton.getModel().isSelected();
              if(selected)
              {
                    aJLocField.setEditable(false);
              }
              else
              {
                    aJLocField.setEditable(true);
              }
            }
        };
        aJLocDef.addActionListener(aJLocDefActionListener);
        
        
        //Add ActionListener to Java Default checkbox so when it is clicked
        //it will set the corresponding TextField to editable or not.  If 
        //default is selected, TextField is not editable
        ActionListener javaLocDefActionListener = new ActionListener() 
        {
            public void actionPerformed(ActionEvent actionEvent) {
              AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
              boolean selected = abstractButton.getModel().isSelected();
              if(selected)
              {
                    javaLocField.setEditable(false);
              }
              else
              {
                    javaLocField.setEditable(true);
              }
            }
        };
        javaLocDef.addActionListener(javaLocDefActionListener);
        
        //Add a listener to the configuration window so if the user clicks
        //"X" the window disappears
        mainFrame.addWindowListener(new WindowAdapter() 
            {
                @Override
                public void windowClosing(WindowEvent windowEvent)
                {
                    mainFrame.setVisible(false);
                }        
            }); 
        
        //Add a listener to the Submit button so when clicked, it will update
        //the MANIFEST.MF file
        ActionListener submitButtonActionListener = new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent) 
            {
                if(aJLocDef.isSelected())
                {
                    setAJPath("TBD"); //Once we know the default, update this
                }
                else
                {
                    setAJPath(aJLocField.getText());
                }
                if(javaLocDef.isSelected())
                {
                    setJavaPath("TBD"); //Once we know the default, update this
                }
                else
                {
                    setJavaPath(javaLocField.getText());
                }
                try {
                    updateManifest();
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                }
                mainFrame.setVisible(false);
            }
        };
        submitButton.addActionListener(submitButtonActionListener);
        
        //Add listener to the Cancel button so when it is clicked, the window
        //disappears
        ActionListener cancelButtonActionListener = new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent) 
            {
                mainFrame.setVisible(false);
            }
        };
        cancelButton.addActionListener(cancelButtonActionListener);
              
        //Add listener to the AspectJ Location button so when it is clicked, 
        //the user selects the AspectJ Jar file they want to use instead
        ActionListener aJLocButtonActionListener = new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent) 
            {
                if(aJLocDef.isSelected())
                {
                    JOptionPane.showMessageDialog(null, "Please uncheck the default box first");
                }
                else
                {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                    FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "JAR Files", "jar");
                    fileChooser.setFileFilter(filter);
                    int result = fileChooser.showOpenDialog(fileChooser);
                    if (result == JFileChooser.APPROVE_OPTION) 
                    {
                        aJLocField.setText(fileChooser.getSelectedFile().getAbsolutePath());
                    }  
                }
                
                
            }
        };
        aJLocButton.addActionListener(aJLocButtonActionListener);
        
        //Add listener to the Java Location button so when it is clicked, 
        //the user selects the location of their Java install (TBA)
        ActionListener javaLocButtonActionListener = new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent) 
            {
                
            }
        };
        javaLocButton.addActionListener(javaLocButtonActionListener);
        
    }
    
    
    /**
     * Responsible for updating the manifest itself.  Involves creating a new 
     * jar, copying files from the old one to the new one minus the manifest,
     * copying the manifest to a new file, removing the Class-Path line, 
     * adding the new Class-Path line, adding the new manifest to the new jar,
     * and renaming the new jar to replace the original jar.  Should probably 
     * be broken into multiple methods.  Needs to have JavaPath functionality
     * added (TBA)
     * @throws IOException 
     */
    private void updateManifest() throws IOException
    {
        //Temporary until we have the project built and have the final jar to update
        JOptionPane.showMessageDialog(null, "Please select the Jar file to update");
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(fileChooser);
        if (result == JFileChooser.APPROVE_OPTION) 
        {
            File selectedFile = fileChooser.getSelectedFile();
        }
        //End Temporary
        
        File tmpJarFile = File.createTempFile("tempJar", ".tmp");
        File srcJarFile = fileChooser.getSelectedFile(); //Will be set to the jar created by installing plugin
        String manifestName = "";
        JarFile jarFile = new JarFile(srcJarFile);
        boolean jarUpdated = false;
        JarEntry manifestJar;

        try {
            JarOutputStream tempJarOutputStream = new JarOutputStream(new FileOutputStream(tmpJarFile));

            try {
                //Copy original jar file to the temporary one.
                Enumeration jarEntries = jarFile.entries();
                while(jarEntries.hasMoreElements()) {
                    JarEntry entry = (JarEntry)jarEntries.nextElement();
                    if(!entry.getName().contains("MANIFEST.MF")) //If the file is not MANIFEST.MF,copy file
                    {
                        InputStream entryInputStream = jarFile.getInputStream(entry);
                        tempJarOutputStream.putNextEntry(entry);
                        byte[] buffer = new byte[1024];
                        int bytesRead = 0;
                        while ((bytesRead = entryInputStream.read(buffer)) != -1) 
                        {
                            tempJarOutputStream.write(buffer, 0, bytesRead);
                        }
                    }
                    else //If the file is MANIFEST.MF, update the file with the new Class-Path (OpenIDE-Module-Java-Dependencies [Java version control] to be added)
                    {
                        manifestJar = entry;
                        manifestName = manifestJar.getName();
                        String path = manifestJar.getName().substring(0, manifestJar.getName().lastIndexOf("/")+1);
                        File manifestFile = new File(fileChooser.getSelectedFile().getParent()+"/OLDMANIFEST.MF"); //Will be set to the path that the installed jar is located
                        
                        InputStream is = jarFile.getInputStream(manifestJar); // get the input stream
                        FileOutputStream fos = new FileOutputStream(manifestFile);
                        while (is.available() > 0) {  // write contents of 'is' to 'fos'
                            fos.write(is.read());
                        }
                        fos.close();
                        is.close();
                        //MANIFEST file has been copied, now to parse through and update
                        
                        //Create temp document to replace MANIFEST.MF and copy
                        //all lines except the Class-Path line.  NOTE: Deletes
                        //the line and any other items included.  Needs to be 
                        //updated to keep non AspectJ imports (TBA)
                        File tempFile = new File(fileChooser.getSelectedFile().getParent()+"/MANIFEST.MF"); //Will need to be set to the jar location /MANIFEST.MF
                        tempFile.createNewFile();
                        BufferedReader reader = new BufferedReader(new FileReader(manifestFile));
                        Writer output;
                        output = new BufferedWriter(new FileWriter(tempFile, true));
                        String currentLine = reader.readLine();
                        boolean trigger = false;
                        while(currentLine != null && !trigger)
                        {
                            if(!currentLine.contains("Class-Path:")){
                                output.append(currentLine + System.getProperty("line.separator"));
                            }
                            else
                            {
                                trigger = true;
                            }
                            currentLine = reader.readLine();
                        }
                        output.append("Class-Path: " + this.getAJPath() + "\n");
                        output.close();
                        reader.close();
                        
                        //Add the new MANIFEST.MF to the new Jar file
                        FileInputStream fis = new FileInputStream(tempFile);
                        try {
                            byte[] buffer = new byte[1024];
                            int bytesRead = 0;
                            
                            JarEntry entryManifest = new JarEntry(manifestName);
                            tempJarOutputStream.putNextEntry(entryManifest);
                            while((bytesRead = fis.read(buffer)) != -1) 
                            {
                                tempJarOutputStream.write(buffer, 0, bytesRead);
                            }
                        }
                        finally 
                        {
                            fis.close();
                        }
                        manifestFile.delete();
                        tempFile.delete();
                    }
                }
                jarUpdated = true;
            }
            catch(Exception ex) 
            {
                ex.printStackTrace();
                tempJarOutputStream.putNextEntry(new JarEntry("stub"));
            }
            finally 
            {
                tempJarOutputStream.close();
            }

        }
        finally 
        {
            jarFile.close();
            //If Jar not updated, delete the temp jar file
            if (!jarUpdated) 
            {
                tmpJarFile.delete();
            }
        }
        
        //If Jar file updated, delete the old Jar and rename the temp to 
        //replace it
        if (jarUpdated) 
        {
            srcJarFile.delete();
            tmpJarFile.renameTo(srcJarFile);
        }
    }
    
    /**
     * Sets the AJPath variable value
     * @param ajp 
     */
    private void setAJPath(String ajp)
    {
        aJPath = ajp;
    }
    
    /**
     * Returns the AJPath variable value
     * @return 
     */
    private String getAJPath()
    {
        return aJPath;
    }
    
    /**
     * Sets the JavaPath variable value
     * @param jp 
     */
    private void setJavaPath(String jp)
    {
        javaPath = jp;
    }
    
    /**
     * Returns the JavaPath variable value
     * @return 
     */
    private String getJavaPath()
    {
        return javaPath;
    }
    
    /**
     * Generates the configuration window when the tool bar/menu option selected. 
     * @param e 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        createGUI();
    }
}
