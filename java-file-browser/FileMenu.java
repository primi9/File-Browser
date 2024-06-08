import javax.swing.*;
import java.awt.*;
import java.io.File;

import java.awt.event.*;

public class FileMenu extends JPopupMenu implements ActionListener{
    
    MainFrame mainFrame;
    
    private JMenuItem newWindow; 
    private JMenuItem exit;
    
    public FileMenu(MainFrame mainframe){
        
        mainFrame = mainframe;
        
        newWindow = new JMenuItem("New Window");
        exit = new JMenuItem("Exit");
        
        newWindow.addActionListener(this);
        exit.addActionListener(this);
        
        add(newWindow);
        add(exit);
        
    }
    
    public void actionPerformed(ActionEvent event){
        
        JMenuItem itemClicked = (JMenuItem)event.getSource();
        
        String itemName = itemClicked.getText();
        
        if(itemName.equals("New Window")){
            
            mainFrame.openNewWindow();
            
        }else if(itemName.equals("Exit")){
            
            mainFrame.exit();
        }
    }
    
}



















