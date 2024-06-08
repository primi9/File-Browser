
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.event.*;

public class Propertiespanel1 extends JFrame implements ItemListener{
    
    JLabel name;
    JLabel filepath;
    JLabel size;
    JCheckBox writePermission,readPermission,executePermission;
    
    GridBagConstraints gbc;
    File file;
    
    boolean wPermission;
    boolean rPermission;
    boolean exPermission;
        
    public Propertiespanel1(File theFile){
        
        super("Properties: " + theFile.getName());
        
        file = theFile;
        
        getContentPane().setLayout(
            new GridBagLayout()
        );
        
        gbc = new GridBagConstraints();
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0,0,10,0);
        gbc.weightx = 1;
        gbc.weighty = 1;
        
        writePermission = new JCheckBox("Write Permission: ");
        readPermission = new JCheckBox("Read Permission: ");
        executePermission = new JCheckBox("Execute Permission: ");
        executePermission.setEnabled(false);
        
        writePermission.addItemListener(this);
        readPermission.addItemListener(this);
        executePermission.addItemListener(this);
        
        if(theFile.canWrite()){
            
            writePermission.setSelected(true);
            wPermission = true;
        }
        
        if(theFile.canRead()){
            
            readPermission.setSelected(true);
            rPermission = true;
        }
        
        if(theFile.canExecute()){
            
            executePermission.setSelected(true);
            exPermission = true;
        }
        
        name = new JLabel("Name: " + theFile.getName());
        filepath = new JLabel("FilePath: " + theFile.getAbsolutePath());
        
        long sizeOfFile = 0;
        
        if(theFile.isFile())
            sizeOfFile = theFile.length();
        else
            sizeOfFile = folderSize(theFile);
        
        int sizeoffileBytes = (int)sizeOfFile/8;
        
        size = new JLabel("Size : " + sizeoffileBytes + " bytes");
        
        add(name,gbc);
        
        gbc.gridy++;
        
        add(filepath,gbc);
        
        gbc.gridy++;
        
        add(size,gbc);
        
        gbc.gridy++;
        
        add(writePermission,gbc);
        
        gbc.gridy++;
        
        add(readPermission,gbc);
        
        gbc.gridy++;
        
        add(executePermission,gbc);
        
        pack();
        setLocationRelativeTo(null); 
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        
    }
    
    public void itemStateChanged(ItemEvent e){
        
        JCheckBox checkbox = (JCheckBox)e.getSource();
        
        if(checkbox == writePermission){
            
            //change the permission of the file
            if(wPermission){
                
                file.setWritable(false);
                
                System.out.println("Change the permission of write to disabled");
                wPermission = false;
                return;
            }
            else{
                
                file.setWritable(true);
                
                System.out.println("Change the permission of write to enabled");
                wPermission = true;
                return;
            }
        }
        
         if(readPermission == checkbox){
            
            //change the permission of the file
            if(rPermission){
                
                file.setReadable(false);
                
                System.out.println("Change the permission of read to disabled");
                rPermission = false;
                return;
            }
            else{
                
                file.setReadable(true);
                
                System.out.println("Change the permission of read to enabled");
                rPermission = true;
                return;
            }
        }
    }
    
    private long folderSize(File directory) {
        
        long length = 0;
        
        File[] file = directory.listFiles();
        
        if(file == null)
            return 0;
        
        int i;
        int size = file.length;
        
        for (i = 0; i < size; i++) {
        
            if(file[i].isFile())
                length = length + file[i].length();
            else
                length = length + folderSize(file[i]);
        }
        
        return length;
    }
}
