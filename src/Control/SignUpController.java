package Control;

import java.io.IOException;

import Util.Connect;
import Util.CurrentUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
/**
 * The controller for sign up controller
 * @see <A href="C:/Users/green/Documents/CSE248/cse248/GerdjunisFinal248/src/Control/SignUpController.java"> 
 * Java Source Code
 * </A/>
 * @author Benjamin Gerdjunis <A href="mailto:benjamingerdjunis@gmail.com"> (benjamingerdjunis@gmail.com)
 * </A/>
 * @version December 11th, 2022
 *
 */
public class SignUpController {

    @FXML
    private PasswordField passwordBox1;

    @FXML
    private PasswordField passwordBox2;

    @FXML
    private Button signInButton;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField usernameBox;
    
    @FXML
    private Label label;

    /**
     * Detects if signInButton is clicked and switches the view to {@link SignInController}
     * @param event
     */
    @FXML
    void signInClick(ActionEvent event) {
    	Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("../view/signup.fxml"));
			Scene scene = new Scene(root);
			Stage window = (Stage) (((Node) event.getSource()).getScene().getWindow());
			window.setScene(scene);
			window.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    /**
     * Detects if signUpButton is clicked and switches the view to {@link CollegeSearchController} if the signUp is valid
     * @param event
     */
    @FXML
    void signUpClick(ActionEvent event) {
    	if(usernameBox.getText()!=null || !usernameBox.getText().equals(""))
    	{
    		if(!passwordBox1.getText().equals("") && passwordBox1.getText().equals(passwordBox2.getText()))
    		{
    			int success = Connect.addUser(usernameBox.getText(), passwordBox1.getText());
    			if(success==0)
    			{
    				label.setText("User already exists");
    			}
    			else if(success==-1)
    			{
    				label.setText("Sign up failure");
    			}
    			else
    			{
    				Connect.current = CurrentUser.makeUser(Connect.getUser(usernameBox.getText(),passwordBox1.getText()));
    				
    				Parent root;
    				try {
    					root = FXMLLoader.load(getClass().getResource("../view/collegeSearch.fxml"));
    					Scene scene = new Scene(root);
    					Stage window = (Stage) (((Node) event.getSource()).getScene().getWindow());
    					window.setScene(scene);
    					window.show();
    				} catch (IOException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    			}
    		}
    	}
    }

}
