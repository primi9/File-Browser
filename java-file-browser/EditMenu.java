
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.awt.event.*;

public class EditMenu extends JPopupMenu implements ActionListener{
    
    MainFrame mainFrame;
    
    private JMenuItem cut; 
    private JMenuItem copy;
    private JMenuItem paste;
    private JMenuItem rename;
    private JMenuItem delete;
    private JMenuItem addToFavourites;
    private JMenuItem properties;
    
    public EditMenu(MainFrame mainframe){
        
        mainFrame = mainframe;
        
        cut = new JMenuItem("Cut");
        copy = new JMenuItem("Copy");
        paste = new JMenuItem("Paste");
        rename = new JMenuItem("Rename");
        delete = new JMenuItem("Delete");
        addToFavourites = new JMenuItem("Add to Favourites");
        properties = new JMenuItem("Properties");
        
        cut.addActionListener(this);
        copy.addActionListener(this);
        paste.addActionListener(this);
        rename.addActionListener(this);
        delete.addActionListener(this);
        addToFavourites.addActionListener(this);
        properties.addActionListener(this);
        
        add(cut);
        add(copy);
        add(paste);
        paste.setEnabled(false);
        add(rename);
        add(delete);
        add(addToFavourites);
        add(properties);
    }
    
    public void setPaste(boolean active){
        
        paste.setEnabled(active);
    }
    
    public void actionPerformed(ActionEvent event){
        
        JMenuItem itemClicked = (JMenuItem)event.getSource();
        
        String itemName = itemClicked.getText();
        
        if(itemName.equals("Cut")){
            
            mainFrame.Cut();
            
        }else if(itemName.equals("Copy")){
            
            mainFrame.Copy();
            
        }else if(itemName.equals("Paste")){
            
            mainFrame.Paste();
            
        }else if(itemName.equals("Rename")){
            
            mainFrame.Rename();
            
        }else if(itemName.equals("Delete")){
            
            mainFrame.Delete();
            
        }else if(itemName.equals("Add to Favourites")){
            
            mainFrame.addToFavourites();
            
        }else if(itemName.equals("Properties")){
            
            mainFrame.showProperties();
        }
    }
}







