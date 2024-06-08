
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.awt.event.*;

 public class Toolbar extends JPanel implements MouseListener{
    
    private MainFrame myFrame;
    private JButton FileButton;
    private JButton EditButton;
    private JButton SearchButton;
        
    public Toolbar(MainFrame theFrame){
    
        super();
        
        myFrame = theFrame;
        
        FileButton = new JButton("File");
        EditButton = new JButton("Edit");
        SearchButton = new JButton("Search");
        
        EditButton.setEnabled(false);
        
        super.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        setBackground(Color.darkGray);
        
        FileButton.addMouseListener(this);
        EditButton.addMouseListener(this);
        SearchButton.addMouseListener(this);
        
        super.add(FileButton);
        super.add(EditButton);
        super.add(SearchButton);
    }
    
    public void disableEditButton(){
        
        EditButton.setEnabled(false);
    }
    
    public void enableEditButton(){
        
        EditButton.setEnabled(true);
    }
    
    public void mouseClicked(MouseEvent event) {
        
        JButton buttonClicked = (JButton)event.getSource();
        //File currentFolder = mainFrame.getCurrentFolder();
        String buttonName = buttonClicked.getText();
 
        //File clickedFile = new File(currentFolder.getAbsolutePath() + "/" + buttonName);
        
        
        //if(event.getClickCount() == 1){
        
            //mainFrame.setSelectedFile(clickedFile,buttonClicked);
            if(buttonName.equals("File")){
                
                myFrame.showFileMenu(event.getComponent(),event.getX(),event.getY());
                
            }else if(buttonName.equals("Edit")){
                
                myFrame.showEditMenu(event.getComponent(),event.getX(),event.getY());
                
            }else{
                
                myFrame.manageSearch();
            }
    }
  
    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }
    
} 
