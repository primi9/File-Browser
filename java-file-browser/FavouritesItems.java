
import java.util.*;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.*;

@XmlRootElement
public class FavouritesItems{
    
    ArrayList<String>theList;
    private ArrayList<String> keywords;
    
    public FavouritesItems(){
        
    }
    
    public void setKeywords(ArrayList<String> keywords) {
        this.keywords = keywords;
    }  
    
    public String getElement(int index){
        
        return keywords.get(index);
    }
    
    @XmlElementWrapper
    @XmlElement(name="keyword")
    public ArrayList<String> getKeywords() {
        return keywords;
    }
    /*
    @XmlElementWrapper
    @XmlElement(name="keyword")
    public void addElement(String filename){
        
        theList.add(filename);
        
        return ;
    }
    */
}