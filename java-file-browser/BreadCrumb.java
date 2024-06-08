
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.awt.event.*;
import java.util.*;

public class BreadCrumb extends JPanel{
    
    private JLabel breadcrumb;
    private MainFrame mainFrame;
    private int tempslash;
    
    public BreadCrumb(File currentDir,MainFrame mainframe){
        
        mainFrame = mainframe;
        
        loadBreadCrumb(currentDir);
    }
    
    public void loadBreadCrumb(File currentDir){
        
        mainFrame.flushList();
        
        setLayout(new FlowLayout(FlowLayout.LEFT));
        
        setBackground(Color.orange);
        
        setBorder(BorderFactory.createLineBorder(Color.blue,1));
        
        
        //ArrayList <String> fileNames = new ArrayList<String>();
        
        JButton link;
        String mystr = currentDir.getAbsolutePath();
        String breadcrumbText = mystr.substring(1);
        JLabel temp;
        int indexofslash;
        int tempslash = 0;
        //int counter = 0;
        
        indexofslash = breadcrumbText.indexOf(File.separator);
        //tempslash = indexofslash;
        
        //fileNames.add(mystr.substring(0,indexofslash));
        
        while(indexofslash != -1){
            
            temp = new JLabel(">");
            
            add(temp);
            
            link = new JButton(breadcrumbText.substring(0,indexofslash));
            
            link.addActionListener(new ActionListener() {
            
                public void actionPerformed(ActionEvent e) { 
                    
                    int index = mainFrame.indexOfbreadcrumb((JButton)e.getSource()) + 1;
                    
                    int pos = ordinalIndexOf(mainFrame.getCurrentFolder().getAbsolutePath(),File.separator,index);

                    String finalstring = mainFrame.getCurrentFolder().getAbsolutePath().substring(0,pos);

                    File tempFile = new File(finalstring);
                    
                    if(!tempFile.exists()){
                        //System.out.println("Yea erorr");
                        return ;
                    }
                    
                    mainFrame.setCurrentDir(tempFile);
                } 
            } );
            
            mainFrame.addindexofbreadcrumb(link);
            //System.out.println("From bc:: ");
            add(link);
            
            breadcrumbText = breadcrumbText.substring(indexofslash + 1);
            
            tempslash = indexofslash;
            indexofslash = breadcrumbText.indexOf(File.separator);
           // tempslash = tempslash + indexofslash + 1;
        }
        
        temp = new JLabel(">");
            
            add(temp);
            
            link = new JButton(breadcrumbText);
            
            link.addActionListener(new ActionListener() {
            
                public void actionPerformed(ActionEvent e) { 
                    
                    int index = mainFrame.indexOfbreadcrumb((JButton)e.getSource()) + 1;
                    JButton occured = (JButton)e.getSource();
                    int pos = ordinalIndexOf(mainFrame.getCurrentFolder().getAbsolutePath(),File.separator,index);
                                   
                    if(pos == -1)
                        return ;
                    
                    String finalstring = mainFrame.getCurrentFolder().getAbsolutePath().substring(0,pos);
                    System.out.println(finalstring);
                    File tempFile = new File(finalstring);
                    
                    if(!tempFile.exists()){
                        //System.out.println("Yea erorr");
                        return ;
                    }
                    
                    mainFrame.setCurrentDir(tempFile);
                } 
            } );
            
            mainFrame.addindexofbreadcrumb(link);
            //System.out.println("From bc:: ");
            add(link);
        
        //add(breadcrumb);
    }
    
    public int ordinalIndexOf(String str, String substr, int n) {

        int pos = -1;
        do {
            pos = str.indexOf(substr, pos + 1);
        } while (n-- > 0 && pos != -1);
        return pos;
    }

    
}
