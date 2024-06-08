
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MainPanel extends JPanel{
    
    private MainFrame mainFrame;
    
    private File currentDir;
    
    private SearchBar searchbar;
    private BreadCrumb breadcrumb;
    private filepanel2 filePanel;
    private JScrollPane scrollpane;
    
    public MainPanel(MainFrame mainFrame){
        
        super();
        
        this.mainFrame = mainFrame;
        
        loadMainPanel();
    }
    
    public void loadMainPanel(){
        
        currentDir = mainFrame.getCurrentFolder();
        
        filePanel = new filepanel2(mainFrame,4);
        searchbar = new SearchBar(mainFrame);
        breadcrumb = new BreadCrumb(currentDir,mainFrame);
        //setBorder(BorderFactory.createTitledBorder("MainPanel:"));
        //setLayout(new BorderLayout(10,10));
        
        setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.gridx = gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(0,0,10,0);
        gbc.weighty = gbc.weightx = 1;
        
        add(searchbar,gbc);
        
        gbc.gridy = 1;
        
        searchbar.setVisible(false);
        
        add(breadcrumb,gbc);
        
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 30;
        
         JScrollPane scrollpane = new JScrollPane(filePanel,
             JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
             JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        //scrollpane = new JScrollPane(filePanel);
        //scrollpane = scrollpane.HORIZONTAL_SCROLLBAR_NEVER;
        //scrollpane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
        //add(scrollpane,BorderLayout.WEST);
        add(scrollpane,gbc);
        //add(bsb,BorderLayout.NORTH);
    }
    
    public void addFile(File fileToAdd){
        
        filePanel.addFile(fileToAdd);
        
    }
    
    public void enableSearch(){
        
        searchbar.setVisible(true);
    }
    
    
    public void disableSearch(){
        
        searchbar.setVisible(false);
    }
    
}







