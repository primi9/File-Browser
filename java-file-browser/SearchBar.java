
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.awt.event.*;

public class SearchBar extends JPanel implements MouseListener{
    
    private JButton searchButton;
    private JTextField searchField;
    MainFrame mainFrame;
    
    public SearchBar(MainFrame mainFrame){
        
        this.mainFrame = mainFrame;
        
        setLayout(new FlowLayout(FlowLayout.LEFT));
        
        searchField = new JTextField(50);
                
        add(searchField);
        
        searchButton = new JButton("Search");
        searchButton.addMouseListener(this);
        
        add(searchButton);
    }
    
    public void mouseClicked(MouseEvent event) {
        
        int val;
        
        String input = searchField.getText();
        String keyword;
        String suffix;
        //System.out.println("input is:" + input);
        
        int stringLength = input.length();
        
        val = input.indexOf(" ");
        
        if(val != -1){
            
            String testString;
            
            keyword = input.substring(0,val);

            if(stringLength > val + 6){
                
                testString = input.substring(val + 1,val + 6);
                
                if(!testString.equals("type:")){
                    
                    System.out.println("Format is <filename> type:<suffix>");
                    return;
                }
            }else{
                
                System.out.println("Format is <filename> type:<suffix>");
                return ;
            }
            suffix = input.substring(val + 6);
                
            keyword = keyword.toUpperCase();
            suffix = suffix.toUpperCase();
                
        }else{
                
            keyword = input.toUpperCase();
            suffix = null;
                
                //System.out.println("Something is wrong in search 2");
        }
            /*
            if(!testString.equals("type:"))
            
            val = testString.indexOf(":");
            
            
            suffix = input.substring(val + 1);
        
        }else{
        
            keyword = input;
        }
        //System.out.println("keyword is:" + keyword);
        
        val = input.lastIndexOf(" ");
                
        if(val == -1){
            
            //System.out.println("-1");
            suffix = null;
        }else{
            
            suffix = input.substring(val + 1);
            suffix = suffix.toUpperCase();
            //System.out.println("suffix is:" + suffix);
        }
        */
        mainFrame.Search(keyword,suffix);
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
