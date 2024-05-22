package Control;


import Util.Connect;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * The demo which starts the gui
 * @see <A href="C:/Users/green/Documents/CSE248/cse248/GerdjunisFinal248/src/Control/FXDemo.java"> 
 * Java Source Code
 * </A/>
 * @author Benjamin Gerdjunis <A href="mailto:benjamingerdjunis@gmail.com"> (benjamingerdjunis@gmail.com)
 * </A/>
 * @version December 11th, 2022
 *
 */
public class FXDemo extends Application {
	
	/**
	 * main method the executes when ran
	 * @param args
	 */
	public static void main(String[] args)
	{
		launch(args);
	}

	/**
	 * when called opens login.fxml
	 */
	@Override
	public void start(Stage stage) throws Exception {

		//--module-path "C:\Program Files\Java\Javafx-sdk-17.0.1\lib" --add-modules=javafx.controls,javafx.fxml
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../view/login.fxml"));
			
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	/**
	 * on app close saves CurrentUser's save list to the sqlite database and closes the connection
	 */
	 @Override
	 public void stop()
	 {
		 
		 Connect.saveCurrent();
		 Connect.closeConnection();
	 }

	
	


}
