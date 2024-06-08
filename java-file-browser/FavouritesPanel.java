
import javax.swing.*;
import java.awt.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.util.*;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.awt.event.*;
//import java.awt.List;

public class FavouritesPanel extends JPanel implements MouseListener{
    
    private int realgridx;
    private int realgridy;
    private MainFrame mainFrame;
    private ArrayList<String> favourites;
    private ArrayList<String> strings;
    private GridBagConstraints gbc;
    private GridBagLayout thelayout;
    int col_num;
    
    public FavouritesPanel(MainFrame theFrame,int col_num) {
                
        mainFrame = theFrame;
        this.col_num = col_num;
        /*
        setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        
        setBackground(Color.lightGray);
        */
        //load favourites
        
        
        //favourites = (mainFrame.getCurrentFolder()).listFiles();
        try{
        
            loadPanel();
        }catch(Exception e){
            System.out.println("Exception occured in constructor of FavouritesPanel");
        }
    }
    
    
    public void loadPanel() throws Exception{
        
        File xmlFile = new File("java-file-browser" + File.separator + "properties.xml");
        JAXBContext jc = JAXBContext.newInstance(FavouritesItems.class);
        strings = new ArrayList<String>();
        
        if(!xmlFile.exists()){
        
            try{
                xmlFile.createNewFile();
                FileWriter myWriter = new FileWriter("java-file-browser" + File.separator + "properties.xml");
                
                strings.add(System.getProperty("user.home"));
                
                FavouritesItems content = new FavouritesItems();
                content.setKeywords(strings);
                
                Marshaller marshaller = jc.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                marshaller.marshal(content, myWriter);
                
            }catch(IOException eox){
                System.out.println("Something is wrong in favourites");
                return ;
            }
        }
        
        JAXBContext jaxbContext;
        try
        {
            
            jaxbContext = JAXBContext.newInstance(FavouritesItems.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
             
            FavouritesItems fav = (FavouritesItems) jaxbUnmarshaller.unmarshal(xmlFile);
            //ArrayList<String> favourites;
            
            favourites = fav.getKeywords();
            
            if(favourites == null)System.out.println("yep");
        }
        catch (JAXBException e) 
        {
            System.out.println("Something is wrong in unmarshalling");
            e.printStackTrace();
            return ;
        }
        
        thelayout = new GridBagLayout();
        
        setLayout(thelayout);
        
        gbc = new GridBagConstraints();
        
        setBackground(Color.lightGray);
        
        int length = favourites.size();
        int counter = 0;
        
        JButton button;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(0,0,0,0); 
        
//         if(length <= col_num){
            gbc.weighty = 1;
            gbc.weightx = 1;
//         }
//         else{ 
//             gbc.weighty = 0;
//             gbc.weightx = 0;
//         }
        
        while(counter < length){
                
            File tempFile = new File(favourites.get(counter));    
            
            if(!tempFile.exists()){
                
                favourites.remove(counter);
                
            }else{
            
                addOneFile1(tempFile);
                
                counter++;
            }
        }
        
        length = favourites.size();
        
        realgridx = gbc.gridx;
        realgridy = gbc.gridy;
        
        int toFill = length / 3;
        
        while(toFill < 10){
             
             button = new JButton("");
             
             
             button.setBorderPainted(false);
             button.setEnabled(false);
             button.setBackground(Color.lightGray);
             button.setVerticalTextPosition(SwingConstants.BOTTOM);
             button.setHorizontalTextPosition(SwingConstants.CENTER);
             
             if(gbc.gridx == col_num){
                 
                 toFill++;
                 gbc.gridx = 0;
                 gbc.gridy++;
                 //System.out.println(gbc.gridy);
             }
             
             add(button,gbc);
             gbc.gridx++;
             
         }
         
        gbc.gridx = realgridx;
        gbc.gridy = realgridy;
         
    }
    
    private void addOneFile1(File fileToAdd){
        
        JButton button;
        
        if(gbc.gridx == col_num){
                 gbc.gridy++;
                 gbc.gridx = 0;
             }
            
        ImageIcon theIcon;
        String substr = null;
        String iconName = fileToAdd.getName();
        int indexofdot = iconName.lastIndexOf(".");
            
        if(indexofdot != -1)
            substr = iconName.substring(indexofdot + 1);
            
        if(fileToAdd.isDirectory()){
                
            theIcon = new ImageIcon("icons" + File.separator + "folder.png");

        }else if(substr != null){
                
            File searchName = new File("icons" + File.separator + substr + ".png");//meta me icons/
                
            if(searchName.exists()){
                    
                theIcon = new ImageIcon("icons" + File.separator + substr + ".png");
                    
            }else{
                theIcon = new ImageIcon("icons" + File.separator + "question.png");
            }
                
        }else{
            theIcon = new ImageIcon("icons" + File.separator + "question.png");
        }
            
        if(iconName.length() > 15){
                                
            StringBuffer finalName = new StringBuffer();
                
            finalName.append("<html>");
                
            while(iconName.length() > 15){
                    
                finalName.append(iconName.substring(0,15));
                finalName.append("<br>");
                    
                iconName = iconName.substring(15);
            }
                
            if(iconName.length() > 0)
                finalName.append(iconName);
                
            finalName.append("</html>");
                
            button = new JButton(finalName.toString(),theIcon);
                
        }else{
                
            button = new JButton(iconName,theIcon);
        }
            
        button.setBorderPainted(false);
        button.addMouseListener(this);
        button.setBackground(Color.lightGray);
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        add(button,gbc);
        
        mainFrame.gethelpFavourites(button,fileToAdd);
        
        gbc.gridx++;
    }
    
    public void addOneFile(File fileToAdd) throws Exception{
        
        JButton button;
        
        if(gbc.gridx == col_num){
                 gbc.gridy++;
                 gbc.gridx = 0;
             }
            
        ImageIcon theIcon;
        String substr = null;
        String iconName = fileToAdd.getName();
        int indexofdot = iconName.lastIndexOf(".");
            
        if(indexofdot != -1)
            substr = iconName.substring(indexofdot + 1);
            
        if(fileToAdd.isDirectory()){
                
            theIcon = new ImageIcon("icons" + File.separator +  "folder.png");

        }else if(substr != null){
                
            File searchName = new File("icons" + File.separator + substr + ".png");//meta me icons/
                
            if(searchName.exists()){
                    
                theIcon = new ImageIcon("icons" + File.separator + substr + ".png");
                    
            }else{
                theIcon = new ImageIcon("icons" + File.separator + "question.png");
            }
                
        }else{
            theIcon = new ImageIcon("icons" + File.separator + "question.png");
        }
            
        if(iconName.length() > 15){
                                
            StringBuffer finalName = new StringBuffer();
                
            finalName.append("<html>");
                
            while(iconName.length() > 15){
                    
                finalName.append(iconName.substring(0,15));
                finalName.append("<br>");
                    
                iconName = iconName.substring(15);
            }
                
            if(iconName.length() > 0)
                finalName.append(iconName);
                
            finalName.append("</html>");
                
            button = new JButton(finalName.toString(),theIcon);
                
        }else{
                
            button = new JButton(iconName,theIcon);
        }
            
        button.setBorderPainted(false);
        button.addMouseListener(this);
        button.setBackground(Color.lightGray);
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        add(button,gbc);
        mainFrame.gethelpFavourites(button,fileToAdd);
        gbc.gridx++;
        
        File xmlFile = new File("java-file-browser" + File.separator + "properties.xml");
        JAXBContext jc = JAXBContext.newInstance(FavouritesItems.class);
        
        try{
                //xmlFile.createNewFile();
                FileWriter myWriter = new FileWriter("java-file-browser" + File.separator + "properties.xml");
                
                favourites.add(fileToAdd.getAbsolutePath());
                
                FavouritesItems content = new FavouritesItems();
                content.setKeywords(favourites);
                
                Marshaller marshaller = jc.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                marshaller.marshal(content, myWriter);
                
            }catch(IOException eox){
                System.out.println("Something is wrong in favourites");
                return ;
            }
        
        
    }
    
    public void mouseClicked(MouseEvent event) {
        
        JButton buttonClicked = (JButton)event.getSource();
        //File currentFolder = mainFrame.getCurrentFolder();
        String buttonName = buttonClicked.getText();
        
        File clickedFile = mainFrame.givehelpFavourites(buttonClicked);
        
        if(!clickedFile.exists()){
            //System.out.println("exiting");
            return ;
        }
        if(event.getButton() == MouseEvent.BUTTON3){
            
            int res = JOptionPane.showConfirmDialog(this,"Are you sure you want to delete: " + clickedFile.getName(), "Delete File?",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
            if(res == 0){
                        
                int length = favourites.size();
                
                for(int i = 0; i < length; i++){
                    
                    if(favourites.get(i).equals(clickedFile.getAbsolutePath())){
                        
                        favourites.remove(i);
                        break;
                    }
                }
                
                try{
                
                    File xmlFile = new File("java-file-browser" + File.separator + "properties.xml");
                    JAXBContext jc = JAXBContext.newInstance(FavouritesItems.class);
                    FileWriter myWriter = new FileWriter("java-file-browser" + File.separator + "properties.xml");
                                    
                    FavouritesItems content = new FavouritesItems();
                    content.setKeywords(favourites);
                    
                    Marshaller marshaller = jc.createMarshaller();
                    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                    marshaller.marshal(content, myWriter);
                    
                    mainFrame.updateFavouritesPanel();
                    
                }catch(Exception ex){
                    System.out.println("yea right");
                    return ;
                }
                
            }else
                return;
                
            return ;
        }
        
        if(clickedFile.isDirectory()){
            
            mainFrame.setCurrentDir(clickedFile);
            return ;
        }
        
        try{
                
                if(clickedFile.canExecute()){
                    
                    //String filepath = mainFrame.getCurrentFolder().getAbsolutePath() + "/" + clickedFile.getName();
                    try{
                        //Process newProcess = Runtime.getRuntime().exec("/usr/bin/x-terminal-emulator --disable-factory -e cat README.txt");
                        //System.out.println(clickedFile.getAbsolutePath());
                        /*
                        Process newProcess = Runtime.getRuntime().exec(clickedFile.getAbsolutePath());
                        if(newProcess == null)System.out.println("its's null");
                        
                        Scanner scanner = new Scanner(newProcess.getInputStream());
                        
                        while (scanner.hasNext()) {
                            System.out.println(scanner.nextLine());
                        }*/
                        
                        ProcessBuilder pb = new ProcessBuilder(clickedFile.getAbsolutePath());
                        pb.inheritIO();
                        pb.directory(mainFrame.getCurrentFolder());
                        pb.start();
                        return ;
                    }catch(Exception ex){
                        System.out.println("I just donnoknow");return;
                    }
                    
                }
                
            }catch(SecurityException sex){
                
                System.out.println("SecurityException occured!! -_-");return ;
                
            }
            
            if (!Desktop.isDesktopSupported()) {
                System.out.println("Desktop is not supported");return ;
            }

            Desktop desktop = Desktop.getDesktop();
            if (!desktop.isSupported(Desktop.Action.OPEN)) {
                System.out.println("Something about desktop action open went wronng");return;
            }
            
            final File openFile = new File(clickedFile.getAbsolutePath());
            
            
            
            try {
                
               // if (isLinux()) {
                   //String command = "xdg-open " + clickedFile.getAbsolutePath();
                //} else if (isWindows()) {
                  //  command = "cmd /C start " + file;
                //}
                
                //Runtime.getRuntime().exec(command);
                desktop.open(openFile);
            } catch (IOException e) {
                //e.printStackTrace();
                System.out.println("Caught an IOException...the specified file has no associated application or the associated application fails to be launched  ");return ;
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











