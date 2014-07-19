/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pokergamefx;

import com.xfinity.poker.Suit;
import com.xfinity.poker.Suit.SuitType;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Administrator
 */
public class CardControl implements Initializable {
    
    @FXML
    private Pane card;
    
    @FXML
    private Text suit;
    
    @FXML
    private Text value;
    
    private String originalValue;    
    private String originalSuit;
    private Paint originalColor;
    private boolean faceDown = false;

    public CardControl(Pane card){
        this.card = card;
    }   
    public CardControl(String suit,int value){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Card.fxml"));
        //fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
        Suit cardSuit = Suit.getSuit(suit);
        
        switch(cardSuit.getSuitType()){
            case CLUBS:
                this.suit.setText("♣");
                this.suit.setFill(Color.BLACK);
                this.value.setFill(Color.BLACK);
                break;
            case DIAMONDS:
                this.suit.setText("♦");
                this.suit.setFill(Color.RED);
                this.value.setFill(Color.RED);
                break;
            case HEARTS:
                this.suit.setText("♥");
                this.suit.setFill(Color.RED);
                this.value.setFill(Color.RED);
                break;
            case SPADES:
                this.suit.setText("♠");
                this.suit.setFill(Color.BLACK);
                this.value.setFill(Color.BLACK);
                break;
            default:
                throw new AssertionError(cardSuit.getSuitType().name());
            
        }
        String valueText = "";
        switch(value){
            case 11:
                valueText = "J";
                break;
            case 12:
                valueText = "Q";
                break;
            case 13:
                valueText = "K";
                break;
            case 14:
                valueText = "A";
                break;
            default:
                valueText = value+"";
                break;
        }
        
        this.value.setText(valueText);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public Pane getCard(){
        return card;
    }
    
    public void faceDown(){
        originalValue = value.getText();  
        originalSuit = suit.getText();
        originalColor = suit.getFill();
        value.setText("");
        suit.setText("❀");
        suit.setFill(Paint.valueOf("454dc2"));
        suit.setLayoutX(suit.getLayoutX()-5);
        suit.setLayoutY(suit.getLayoutY()-5);
        suit.setScaleX(1.5);
        suit.setScaleY(1.5);
        faceDown = true;
    }
    
    public void faceUp(){
        if(faceDown){
            value.setText(originalValue);
            suit.setText(originalSuit);
            suit.setFill(originalColor);

            suit.setLayoutX(suit.getLayoutX()+5);
            suit.setLayoutY(suit.getLayoutY()+5);
            suit.setScaleX(1);
            suit.setScaleY(1);
        }
    }
    
    public Text getSuitText(){
        return suit;
    }
    
}
