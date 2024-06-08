
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.event.*;
import java.util.*;

public class filepanel2 extends JPanel implements MouseListener{//allagi
    
    private MainFrame mainFrame;    
    
    private int numberofcolumns;
    
    private GridBagLayout theLayout;
    private GridBagConstraints gbc;
    
    private int realgridx;
    private int realgridy;
    
    public filepanel2(MainFrame mainFrame,int numberofcolumns){
        
        this.mainFrame = mainFrame;
        
        this.numberofcolumns = numberofcolumns;
                
        loadPanel();
    }
    
    public void loadPanel(){
                
        File currentDir = mainFrame.getCurrentFolder();
        File[] theFiles = currentDir.listFiles();
        theLayout = new GridBagLayout();
        JButton button = null;
        
        theFiles = sortFiles(theFiles);
        
        int counter = 0;
        int length = theFiles.length;
        
        setLayout(theLayout);
        
        setBackground(Color.orange);
        
        gbc = new GridBagConstraints();
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(0,0,0,0); 
        gbc.weightx = 1;
        gbc.weighty = 1;
        
        while(counter < length){
            
            if(!theFiles[counter].canWrite() || !theFiles[counter].canRead()){
                
                System.out.println("No write or read permission");
            }else{
            
            addFile1(theFiles[counter]);
            
            }
            counter++;
            
        }
        
         realgridx = gbc.gridx;
         realgridy = gbc.gridy;
         
        int toFill = length / 4;
        
        /*
        if(toFill < 10){
            
            button.setBorderPainted(false);
            button.setBackground(Color.orange);
            button.setVerticalTextPosition(SwingConstants.BOTTOM);
            button.setHorizontalTextPosition(SwingConstants.CENTER);
        }
        */
         while(toFill < 10){
             
             button = new JButton("");
             
             
             button.setBorderPainted(false);
             button.setEnabled(false);
             button.setBackground(Color.orange);
             button.setVerticalTextPosition(SwingConstants.BOTTOM);
             button.setHorizontalTextPosition(SwingConstants.CENTER);
             
             if(gbc.gridx == numberofcolumns){
                 
                 toFill++;
                 gbc.gridx = 0;
                 gbc.gridy++;
             }
             
             add(button,gbc);
             gbc.gridx++;
             
         }
         
        gbc.gridx = realgridx;
        gbc.gridy = realgridy;
         
    }
    
    public void mouseClicked(MouseEvent event) {
        
        JButton buttonClicked = (JButton)event.getSource();
        File currentFolder = mainFrame.getCurrentFolder();
        String buttonName = buttonClicked.getText();
 
        File clickedFile = new File(currentFolder.getAbsolutePath() + File.separator + buttonName);
        
        if(event.getButton() == MouseEvent.BUTTON3){
            
            mainFrame.setSelectedFile(clickedFile,buttonClicked);
            
            mainFrame.showEditMenu(event.getComponent(),event.getX(),event.getY());
            
            return;
        }
        
        if(event.getClickCount() == 2) {
            
            if(clickedFile.isDirectory()){
                
                mainFrame.setCurrentDir(clickedFile);
                return;
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

            
            
        }else if(event.getClickCount() == 1){
        
            mainFrame.setSelectedFile(clickedFile,buttonClicked);
            
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
    
    private void addFile1(File fileToAdd){
        
        JButton button;
        
        if(gbc.gridx == numberofcolumns){
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
        button.setBackground(Color.orange);
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        add(button,gbc);
            
        gbc.gridx++;
    }
    
    public void addFile(File fileToAdd){
        
        JButton button;
        
        //gbc.gridx = realgridx;
        //gbc.gridy = realgridy;
        
        if(gbc.gridx == numberofcolumns){
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
        button.setBackground(Color.orange);
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        add(button,gbc);
            
        gbc.gridx++;
        
    }
    
    
    private File[] sortFiles(File[] theFiles){
        
        ArrayList<File> sortedFiles = new ArrayList<File>();
        
        int size = theFiles.length;
        int arraysize = 0;
        int j;
        int i;
        int k;
        
        for(i = 0; i < size; i++){

            if(theFiles[i].isDirectory()){
                
                j = 0;
                
                while(j < arraysize){
                    
                    if(!sortedFiles.get(j).isDirectory()){
                        
                        break;
                    }
                        
                    if(theFiles[i].getName().compareTo(sortedFiles.get(j).getName()) < 0){
                        
                        break;
                    }
                
                    j++;
                }
                
                
                sortedFiles.add(j,theFiles[i]);
                
                arraysize++;
                
            }else{
                
                j = 0;
                
                while(j < arraysize){
                    
                    if(!sortedFiles.get(j).isDirectory()){
                        
                        if(theFiles[i].getName().compareTo(sortedFiles.get(j).getName()) < 0){

                            break;
                        }
                    }
                    
                    j++;
                }
                
                sortedFiles.add(j,theFiles[i]);

                arraysize++;
            }
        }

        File[] sortedArray = new File[sortedFiles.size()]; 
        
        sortedArray = sortedFiles.toArray(sortedArray);
        
        return sortedArray;
    }
    
}
 

 
 
 
 
 
 
 
 