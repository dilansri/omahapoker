package pokergamefx;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Administrator
 */
public class TableCardController implements Initializable {
    
    @FXML
    private Group card;
    
    @FXML
    private Text TextSuit;
    
    @FXML
    private Text TextValue;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public TableCardController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TableCard.fxml"));
        //fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
    
    public Group getCardGroup(){
        return card;
    }
    
    public void setValue(String value){
        
    }
    
    public String getValue(){
      return TextValue.getText();
    }
    
    public void setSuit(String suit){
        
    }
    
    public String getSuit(){
        return null;
    }
    
    
    
}
