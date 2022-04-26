package il.cshaifasweng.OCSFMediatorExample.client;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
public class PrimaryController {
	@FXML
	Button updatePrice1;
	@FXML
	private TextField text1;
	@FXML
	private TextArea itemDesciption1;
	@FXML
	private TextField newPriceText1;
	@FXML
	private ImageView img1;
	//@FXML
	//private AnchorPane pane1;
	@FXML
	private Button updatePrice2;
	@FXML
	private TextField text2;
	@FXML
	private TextArea itemDesciption2;
	@FXML
	private TextField newPriceText2;
	@FXML
	private ImageView img2;
//	@FXML
	//private AnchorPane pane2;
	@FXML
	private Button updatePrice3;
	@FXML
	private TextArea itemDesciption3;
	@FXML
	private TextField text3;
	@FXML
	private TextField newPriceText3;
	@FXML
	private ImageView img3;
//	@FXML
	//private AnchorPane pane3;
/*	@FXML
	private Button updatePrice4;
	@FXML
	private TextField text4;
	@FXML
	private ImageView img4;
	@FXML
	private AnchorPane pane4;
	@FXML
	private Button updatePrice5;
	@FXML
	private TextField text5;
	@FXML
	private ImageView img5;
	//@FXML
	//private AnchorPane pane5;*/

	//text1.add(KeyEvent.KEY_TYPED, numeric_Validation(5));

//AutoControl.setOnMousePressed(new EventHandler<MouseEvent>() {
	@FXML
	public void updatePrice (ActionEvent event){
		try {
			if(event.getSource().equals(updatePrice1)){
				SimpleClient.getClient().sendToServer("#update  1 "+newPriceText1.getText());
				text1.setText("Enter New Price: ");
				newPriceText1.clear();
				itemDesciption1.setText("flower color: ");
			}
			else if(event.getSource().equals(updatePrice2)){
				SimpleClient.getClient().sendToServer("#update  2"+newPriceText2.getText());
				newPriceText2.clear();
			}
			else {
				SimpleClient.getClient().sendToServer("#update  3"+newPriceText3.getText());
				newPriceText3.clear();
			}
		}
		catch (Exception e){
		}
	}

    @FXML
    void sendWarning(ActionEvent event) {
    	try {
			SimpleClient.getClient().sendToServer("#warning");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
