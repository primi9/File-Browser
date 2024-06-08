
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.awt.event.*;
import java.util.ArrayList;

public class SearchResults extends JPopupMenu implements ActionListener{
    
    MainFrame mainFrame;
    ArrayList<File> theList;
    
    public SearchResults(MainFrame mainframe,ArrayList<File> theList){
        
        mainFrame = mainframe;
        this.theList = theList;
        
        int sizeoflist = theList.size();
        int i;
        
        File temp = null;
        JMenuItem tempItem = null;
        
        for(i = 0; i < sizeoflist; i++){
            
            temp = theList.get(i);
            
            tempItem = new JMenuItem(temp.getAbsolutePath()); 
            
            tempItem.addActionListener(this);
            
            add(tempItem);
            
        }
        
        show(mainFrame,mainFrame.getWidth() / 2,mainFrame.getHeight() / 2);
    }
    
    public void actionPerformed(ActionEvent event){
        
        JMenuItem itemClicked = (JMenuItem)event.getSource();
        
        String itemName = itemClicked.getText();
        
        File pickedFile = new File(itemName);
        
        mainFrame.showSearchResults(pickedFile);
        
    }
    
}
