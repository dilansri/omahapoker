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
import javafx.scene.shape.SVGPath;
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
    private SVGPath suit;
    
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
                this.suit.setContent("M24.588 12.274c-1.845 0-3.503 0.769-4.683 2.022-0.5 0.531-1.368 1.16-2.306 1.713 0.441-1.683 1.834-3.803 2.801-4.733 1.239-1.193 2-2.87 2-4.734 0-3.59-2.859-6.503-6.4-6.541-3.541 0.038-6.4 2.951-6.4 6.541 0 1.865 0.761 3.542 2 4.734 0.967 0.93 2.36 3.050 2.801 4.733-0.939-0.553-1.806-1.182-2.306-1.713-1.18-1.253-2.838-2.022-4.683-2.022-3.575 0-6.471 2.927-6.471 6.541s2.897 6.542 6.471 6.542c1.845 0 3.503-0.792 4.683-2.045 0.525-0.558 1.451-1.254 2.447-1.832-0.094 4.615-2.298 8.005-4.541 9.341v1.179h12v-1.179c-2.244-1.335-4.448-4.726-4.541-9.341 0.995 0.578 1.922 1.274 2.447 1.832 1.18 1.253 2.838 2.045 4.683 2.045 3.575 0 6.471-2.928 6.471-6.542s-2.897-6.541-6.471-6.541z");
                this.suit.setFill(Color.BLACK);
                this.value.setFill(Color.BLACK);
                break;
            case DIAMONDS:
                this.suit.setContent("M16 0l-13 16 13 16 13-16z");
                this.suit.setFill(Color.RED);
                this.value.setFill(Color.RED);
                break;
            case HEARTS:
                this.suit.setContent("M32 11.192c0 2.699-1.163 5.126-3.015 6.808h0.015l-10 10c-1 1-2 2-3 2s-2-1-3-2l-9.985-10c-1.852-1.682-3.015-4.109-3.015-6.808 0-5.077 4.116-9.192 9.192-9.192 2.699 0 5.126 1.163 6.808 3.015 1.682-1.852 4.109-3.015 6.808-3.015 5.077 0 9.192 4.116 9.192 9.192z");
                this.suit.setFill(Color.RED);
                this.value.setFill(Color.RED);
                break;
            case SPADES:
                this.suit.setContent("M25.549 10.88c-6.049-4.496-8.133-8.094-9.549-10.88v0c-0 0-0-0-0-0v0c-1.415 2.785-3.5 6.384-9.549 10.88-10.314 7.665-0.606 18.365 7.93 12.476-0.556 3.654-2.454 6.318-4.381 7.465v1.179h12.001v-1.179c-1.928-1.147-3.825-3.811-4.382-7.465 8.535 5.889 18.244-4.811 7.93-12.476z");
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
        originalSuit = suit.getContent();
        originalColor = suit.getFill();
        value.setText("");
        suit.setContent("M32 18v-2h-6.040c-0.183-2.271-0.993-4.345-2.24-6.008h5.061l2.189-8.758-1.94-0.485-1.811 7.242h-5.459c-0.028-0.022-0.056-0.043-0.084-0.064 0.21-0.609 0.324-1.263 0.324-1.944 0-3.305-2.686-5.984-6-5.984s-6 2.679-6 5.984c0 0.68 0.114 1.334 0.324 1.944-0.028 0.021-0.056 0.043-0.084 0.064h-5.459l-1.811-7.242-1.94 0.485 2.189 8.758h5.061c-1.246 1.663-2.056 3.736-2.24 6.008h-6.040v2h6.043c0.119 1.427 0.485 2.775 1.051 3.992h-3.875l-2.189 8.757 1.94 0.485 1.811-7.243h3.511c1.834 2.439 4.606 3.992 7.708 3.992s5.874-1.554 7.708-3.992h3.511l1.811 7.243 1.94-0.485-2.189-8.757h-3.875c0.567-1.217 0.932-2.565 1.051-3.992h6.043z");
        suit.setFill(Paint.valueOf("116db8"));
        suit.setLayoutX(suit.getLayoutX());
        suit.setLayoutY(suit.getLayoutY()-5);
        suit.setScaleX(1);
        suit.setScaleY(1);
        faceDown = true;
    }
    
    public void faceUp(){
        if(faceDown){
            value.setText(originalValue);
            suit.setContent(originalSuit);
            suit.setFill(originalColor);

            suit.setLayoutX(suit.getLayoutX()+5);
            suit.setLayoutY(suit.getLayoutY()+5);
            suit.setScaleX(1);
            suit.setScaleY(1);
        }
    }
    
    public SVGPath getSuitText(){
        return suit;
    }
    
}
