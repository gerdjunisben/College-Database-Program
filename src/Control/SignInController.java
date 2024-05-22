package Control;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Util.Connect;
import Util.CurrentUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;



/**
 * The controller for sign in controller
 * @see <A href="C:/Users/green/Documents/CSE248/cse248/GerdjunisFinal248/src/Control/SignInController.java"> 
 * Java Source Code
 * </A/>
 * @author Benjamin Gerdjunis <A href="mailto:benjamingerdjunis@gmail.com"> (benjamingerdjunis@gmail.com)
 * </A/>
 * @version December 11th, 2022
 *
 */
public class SignInController implements Initializable {

    @FXML
    private PasswordField passwordBox;

    @FXML
    private Button signInButton;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField usernameBox;
    
    @FXML
    private Label label;

    /**
     * Detects when the signInButton is clicked and changes view to {@link CollegeSearchController} if the login is valid
     * @param event
     */
    @FXML
    void signInClick(ActionEvent event) {
    	int success = Connect.signIn(usernameBox.getText(), passwordBox.getText());
    	if(success==0)
    	{
    		label.setText("User doesn't exist");
    	}
    	else if(success==-1)
    	{
    		label.setText("Login failure");
    	}
    	else
    	{
    		Parent root;
			try {
				Connect.current = CurrentUser.makeUser(Connect.getUser(usernameBox.getText(),passwordBox.getText()));
			
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

    /**
     * Detects when the signUpButton is clicked and changes view to {@link SignUpController}
     * @param event
     */
    @FXML
    void signUpClick(ActionEvent event)  {
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
     * When the fxml connected to this is initialized the database connection is set
     */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Connect.setConnection("colleges.sqlite");
		
		
	}

}
