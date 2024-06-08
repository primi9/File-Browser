
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
//the test one
public class MainFrame extends JFrame{
    
    private hw3 program;
    private Toolbar toolbar;
    private FavouritesPanel favourites;
    private MainPanel mainpanel;
    private BorderLayout theLayout;
    private JScrollPane scrollpane;
    private File currentFile;
    private File selectedFile;
    private JButton selectedButton;    
    private File toCut;
    private File toCopy;
    private FileMenu fileMenu;
    private EditMenu editMenu;
    boolean searchEnabled;
    ArrayList <JButton> theButtons;
    private ArrayList<JButton> favouriteButtons;
    private ArrayList<File> buttonFiles;
    
    public MainFrame(){
        
        super("FileExplorer");
        
        this.program = program;
        
        theButtons = new ArrayList<JButton>();
        favouriteButtons = new ArrayList<JButton>();
        buttonFiles = new ArrayList<File>();
        
        String homdir = System.getProperty("user.home");
        
        currentFile = new File(homdir);
        
        this.selectedFile = null;
        this.selectedButton = null;
        
        if(!currentFile.exists()){
            System.out.print("fuck\n");
            return;
        }
        
        theLayout = new BorderLayout(5,5);
        
        setLayout(theLayout);
        
        fileMenu = new FileMenu(this);
        editMenu = new EditMenu(this);
        favourites = new FavouritesPanel(this,3);
        toolbar = new Toolbar(this);
        mainpanel = new MainPanel(this);
        
        scrollpane = new JScrollPane(favourites);
        
        add(mainpanel,BorderLayout.CENTER);
        
        add(toolbar,BorderLayout.NORTH);
        
        add(scrollpane,BorderLayout.WEST);
        
        pack();
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        setVisible(true);
        
    }
    
    public void gethelpFavourites(JButton abutton,File afile){
        
        favouriteButtons.add(abutton);
        buttonFiles.add(afile);
    }
    
    public File givehelpFavourites(JButton buttonClicked){
        
        int fb = favouriteButtons.size();
        
        for(int i = 0; i < fb; i++){
            
            if(buttonClicked == favouriteButtons.get(i)){
                
                return buttonFiles.get(i);
            }
        }
        
        return null;
    }
    
    public void flushList(){
        
        theButtons.removeAll(theButtons);
        
    }
    
    public void addindexofbreadcrumb(JButton newButton){
        
        theButtons.add(newButton);
    }
    
    public int indexOfbreadcrumb(JButton checkButton){
        
        int sizeoflist = theButtons.size();

        for(int i = 0; i < sizeoflist; i++){
            
            if(theButtons.get(i) == checkButton){

                return i;
            }
        }
        
        return -1;
        
    }
    
    public File getCurrentFolder(){
        
        return currentFile;
    }
    
    public void setSelectedFile(File selectedFile,JButton selectedButton){
        
        if(this.selectedFile != null){
            
            this.selectedButton.setBackground(Color.orange);
        }
        
        this.selectedFile = selectedFile;
        
        this.selectedButton = selectedButton;
        
        selectedButton.setBackground(Color.lightGray);
        
        toolbar.enableEditButton();
        
    }
    
    public void openNewWindow(){
        
        new MainFrame();
        
    }
    
    public void exit(){
        
        setVisible(false);
        dispose();
        
    }
    
    public void Cut(){
        
        toCopy = null;
        
        toCut = selectedFile;
        editMenu.setPaste(true);
    }
    
    public void setCurrentDir(File currentFile){
        
        if(currentFile.getAbsolutePath().equals(this.currentFile.getAbsolutePath()))
        
        if(!currentFile.canRead() || !currentFile.canWrite()){
            System.out.println("No read or file permission to open this folder");
            return;
        }
        
        
        this.currentFile = currentFile;
        
        this.selectedButton = null;
        this.selectedFile = null;
        
        toolbar.disableEditButton();//to theloume ayto?
        searchEnabled = false;
        
        updateMainPanel();
        
    }
    
    public void Copy(){
        
        toCut = null;
        
        toCopy = selectedFile;
        editMenu.setPaste(true);
    }
    
    public void Paste(){
        
        File createdFile;
        File markedFile;
        //boolean deleteMarkedFile;
        
        if(toCut == null){
            markedFile = toCopy;
            
        }else{
            markedFile = toCut;
            //deleteMarkedFile = true;
        }
        
        File toCheck = new File(currentFile.getAbsolutePath() +File.separator + markedFile.getName());
        
        if(toCheck.getAbsolutePath().equals(markedFile.getAbsolutePath())){
            System.out.println("Cant copy paste in the same folder");
            return ;
            
        }
        
        if(toCheck.exists()){
            
            int res = JOptionPane.showConfirmDialog(this,"File already exists...Want to overwrite? " + selectedFile.getName(), "Delete File?",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
            
            if(res != 0)return ;
            
            deleteFile(toCheck);
            
            //revalidate();
            //pack();
            
        }
        //ena teleytaio tsek
        if(markedFile.isDirectory()){
            
            //mono sto proto paste dir tsekareis an yparxei allo...
            //check gia to proto
            
            File checkFile = new File(currentFile.getAbsolutePath() + File.separator + markedFile.getName());
            /*
            if(checkFile.exists()){
            
                System.out.println("Folder already exists");
                return ;
            }
            */
            createdFile = pasteOneDir(markedFile,currentFile);
            
        }else{
            
            createdFile = pasteOneFile(markedFile,currentFile);
        }
        
        if(createdFile == null)return ;
        
        mainpanel.addFile(createdFile);
        
        if(toCut != null)
            deleteFile(toCut);
        
        updateMainPanel();
        
        toCut = null;
        toCopy = null;
        editMenu.setPaste(false);
        
        toolbar.disableEditButton();
        
    }
    
    public void Rename(){
        
        //show pop up menu
        //check if name already exists
        
        String newName = JOptionPane.showInputDialog(this, "Enter new name", selectedFile.getName());
        
        File newFileName = new File(currentFile,newName);
        
        selectedFile.renameTo(newFileName);
        
        mainpanel.addFile(newFileName);
        
        updateMainPanel();
        
    }
    
    public void Delete(){
        
        
        int res = JOptionPane.showConfirmDialog(this,"Are you sure you want to delete: " + selectedFile.getName(), "Delete File?",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.WARNING_MESSAGE);
        
        if(res == 0){
                        
            deleteFile(selectedFile);
            
            updateMainPanel();
        }
    }
    
    public static void deleteFile(File element) {
        
        if (element.isDirectory()) {
            for (File sub : element.listFiles()) {
                deleteFile(sub);
            }
        }
        element.delete();
    }
    
    public void addToFavourites() {
        
        try{
            favourites.addOneFile(selectedFile);
        }catch(Exception e){
            System.out.println("Exception occured");
        }
        revalidate();
        repaint();
        pack();
    }
    
    public void showProperties(){
        
        Propertiespanel1 FileProperties;
        
        FileProperties = new Propertiespanel1(selectedFile);
        
        //FileProperties.show();
        
    }
    
    public void Search(String keyWord,String suffix){
        
        //create thread
                
        ArrayList<File> words = new ArrayList<File>();
        
        words = searchDir(keyWord,suffix,currentFile,words);
        
        int length = words.size();
        int i;
        /*
        for(i = 0; i < length; i++){
            
            System.out.println(words.get(i).getName());
        }
        */
        
        SearchResults results = new SearchResults(this,words);
    }
    
    private ArrayList<File> searchDir(String keyWord,String suffix,File curr,ArrayList<File> theList){
        
        File current = curr;
        
        String fileName;
        
        if(!current.isDirectory()){
            
            if(suffix != null && (suffix.equals("DIR") || suffix.equals("dir")))
                return theList;
            
            fileName = current.getName().toUpperCase();
            
            if(suffix == null){
                
                if(fileName.indexOf(keyWord) != -1){
                    
                    theList.add(current);
                }
                
            }else{
                
                String substr;
                
                substr = fileName.substring(fileName.lastIndexOf(".") + 1);
                
                if(substr.equals(suffix)){
                    
                   if(fileName.indexOf(keyWord) != -1){
                    
                        theList.add(current);
                    
                    }
                }
            }
            
            return theList;
        }
        
        File[] listofFiles = curr.listFiles();
        int length = listofFiles.length;
        int i;
        
        for(i = 0; i < length; i++){
            
            fileName = listofFiles[i].getName().toUpperCase();

            if(suffix == null){
                
                if(fileName.indexOf(keyWord) != -1){
                    
                    theList.add(listofFiles[i]);
                }
                
                if(listofFiles[i].isDirectory()){
                    
                    theList = searchDir(keyWord,suffix,listofFiles[i],theList);
                }
                
            }else{
                
                String substr;
                //fileName = listofFiles[i].getName().toUpperCase();
                
                substr = fileName.substring(fileName.lastIndexOf(".") + 1);
                
                if(substr.equals(suffix)){
                    
                   if(fileName.indexOf(keyWord) != -1){
                    
                    theList.add(listofFiles[i]);
                    
                    }
                }
                
                if(listofFiles[i].isDirectory()){
                    
                    if(suffix.equals("DIR") && (fileName.indexOf(keyWord) != -1)){
                        
                        theList.add(listofFiles[i]);
                    }
                    
                    theList = searchDir(keyWord,suffix,listofFiles[i],theList);
                }
            }
        }
        
        return theList;
    }
    
    public void updateFavouritesPanel(){
        
        remove(theLayout.getLayoutComponent(BorderLayout.WEST));
        validate();
        repaint();
        
        favourites = new FavouritesPanel(this,3);
        
        add(favourites,BorderLayout.WEST);
        pack();
        revalidate();
        
    }
    
    public void updateMainPanel(){
        
        remove(theLayout.getLayoutComponent(BorderLayout.CENTER));
        
        validate();
        repaint();
        selectedFile = null;//tora to ebala!!!!!!!!!!!
        mainpanel = new MainPanel(this);
        
        add(mainpanel,BorderLayout.CENTER);
        pack();
        revalidate();
    }
    
    public void showFileMenu(Component component,int x,int y){
        
        fileMenu.show(component,x,y);
    }
    
    public void showEditMenu(Component component,int x,int y){
        
        editMenu.show(component,x,y);
    }
    
    public void showSearchResults(File clickedFile){
        
        if(clickedFile == null){
            System.out.println("Something is wrong is showSearchResults");
        }
        
        if(clickedFile.isDirectory()){
            
            setCurrentDir(clickedFile);
            return ;
            
        }else{
            
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
                        pb.directory(getCurrentFolder());
                        pb.start();
                        
                        return ;
                        
                    }catch(Exception ex){
                        System.out.println("I just donnoknow");return;
                    }
                    
                }
                
            }catch(SecurityException sex){
                
                System.out.println("SecurityException occured!! -_-");return ;
            }
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
    
    public void manageSearch(){
        
        if(searchEnabled){
            
            mainpanel.disableSearch();
            searchEnabled = false;
        }else{
            
            mainpanel.enableSearch();
            searchEnabled = true;
        }
        
        
        pack();
        
    }
    
    public void disableSearch(){
        
        mainpanel.disableSearch();
        
        pack();
    }
    
    
    private File pasteOneFile(File source,File destination){//to source einai file kai oxi dir,to destination einai directory
        
        File createdFile;
        
        try{
            
            createdFile = new File(destination.getAbsolutePath() + File.separator + source.getName());
            
            if(!createdFile.createNewFile()){
                
                //show menu
                System.out.println("File already exists");
                return null;
            }
            
            FileInputStream in = new FileInputStream(source);
            FileOutputStream out = new FileOutputStream(createdFile);
            
            byte []array = new byte[512];
            int length;
            
            while ((length = in.read(array)) != -1) {
            
                out.write(array, 0, length);
            }
        }catch(IOException e){
            
            return null;
        }
        
        return createdFile;
    }
    
    private File pasteOneDir(File source,File destination){
       
        File createdFile; 
        File pasteFile = null;
        
        if(source.isDirectory()){
            
            pasteFile = new File(destination.getAbsolutePath() + File.separator + source.getName());
            
            pasteFile.mkdir();
            
            File[] fileNames = source.listFiles();
            int length = fileNames.length;
            
            int counter;
            
            for(counter = 0; counter < length; counter++){
                
                pasteOneDir(fileNames[counter],pasteFile);
            }
            
        }else{
            
            pasteOneFile(source,destination);
        }
        
        return pasteFile;
    }
}

















