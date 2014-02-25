package fxmlstuff;
import javafx.scene.Node;

public interface DockletListener {
    //Dock item being added
    void added(Node n);
    //Dock item being removed
    void removed(String id);
    
}