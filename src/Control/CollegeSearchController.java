package Control;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import Model.BasicQuery;
import Model.College;
import Model.NumericQuery;
import Model.Query;
import Model.QueryDecorator;
import Model.StringQuery;
import Util.Connect;
import Util.CurrentUser;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
/**
 * The College search controller controls the college search FXML
 * @see <A href="C:/Users/green/Documents/CSE248/cse248/GerdjunisFinal248/src/Control/CollegeSearchController.java"> 
 * Java Source Code
 * </A/>
 * @author Benjamin Gerdjunis <A href="mailto:benjamingerdjunis@gmail.com"> (benjamingerdjunis@gmail.com)
 * </A/>
 * @version December 11th, 2022
 *
 */
public class CollegeSearchController implements Initializable {
	
	@FXML
	private ComboBox<String> signBox;

	@FXML
	private Button unSaveButton;
	
	@FXML
    private Button saveButton;

    @FXML
    private TextField saveText;
    
    @FXML
    private Button addButton;

    @FXML
    private TableColumn<Query, String> attribute;

    @FXML
    private ComboBox<String> attributeBox;

    @FXML
    private ListView<College> listArea;

    @FXML
    private TableColumn<Query, String> query;

    @FXML
    private TextField queryText;

    @FXML
    private Button removeButton;

    @FXML
    private TableView<Query> searchTable;
    
    @FXML
    private Button showButton;

    /**
     * detects if the addButton is clicked and then adds a query made from the attributeBox and queryText
     * to the list of queries
     * @param event
     */
    @FXML
    void addClick(ActionEvent event) {
    	if(attributeBox.getValue() != null && (queryText.getText()!= null || !queryText.getText().equals("")))
    	{
    		boolean invalidSign = false;
    		boolean isNumeric = true;
    		BasicQuery query = new BasicQuery(attributeBox.getValue(),queryText.getText());
    		QueryDecorator decoratedQuery = null;
    		if(query.getAttribute().equals("Id") || query.getAttribute().equals("Admission") 
    				|| query.getAttribute().equals("Completion") || query.getAttribute().equals("InState")||
    				query.getAttribute().equals("OutState") || query.getAttribute().equals("Population"))
    		{
    			String sign = signBox.getValue();
    			try {
    				Double.parseDouble(query.getQuery());
    				isNumeric=true;
    			}
    			catch(Exception e)
    			{
    				isNumeric = false;
    			}
    			if(sign!=null && isNumeric)
    			{
    				decoratedQuery = new NumericQuery(query,sign);
    			}
    			else if(!isNumeric)
    			{
    				queryText.setText("Invalid query");
    			}
    			else
    			{
    				queryText.setText("Invalid sign");
    				invalidSign=true;
    			}
    		}
    		else
    		{
    			decoratedQuery = new StringQuery(query);
    		}
    		ObservableList<Query> list = searchTable.getItems();
    		boolean exists = false;
    		for(int i = 0;i<list.size();i++)
    		{
    			if(list.get(i).getAttribute().equals(attributeBox.getValue()))
    			{
    				exists=true;
    			}
    		}
    		if(!exists && !invalidSign && isNumeric)
    		{
    			list.add(decoratedQuery);
    			searchTable.setItems(list);
    		}
    	}
    	searchTable.refresh();
    	if(searchTable.getItems().size()!=0)
    		updateSearch();
    	else
    		listArea.getItems().clear();
    }

    /**
     * detects if the removeButton is clicked and removes a query depending on the 
     * @param event
     */
    @FXML
    void removeClick(ActionEvent event) {
    	if(attributeBox.getValue() != null)
    	{
    		
    		ObservableList<Query> list = searchTable.getItems();
    		for(int i = 0;i<list.size();i++)
    		{
    			if(list.get(i).getAttribute().equals(attributeBox.getValue()))
    			{
    				list.remove(i);
    				i--;
    			}
    		}
    		searchTable.setItems(list);
    	}
    	searchTable.refresh();
    	if(searchTable.getItems().size()!=0)
    		updateSearch();
    	else
    		listArea.getItems().clear();
    }
    
    /**
     * updates the list area with the new query
     */
    public void updateSearch()
    {
    	listArea.getItems().clear();
    	listArea.getItems().addAll(Connect.getSomeColleges(searchTable.getItems()));
    }

    /**
     * When this fxml is initialized the searchTable is set,
     * the attributesBox is set with the possible queries and
     * the event listener for when something on listArea is clicked is set
     */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		attribute.setCellValueFactory(
                new PropertyValueFactory<Query, String>("attribute"));
		query.setCellValueFactory(
                new PropertyValueFactory<Query, String>("query"));
		String[] attributes = {"Id","Name","Zip","City","Admission","Completion","InState","OutState","Population","Ownership"};
		String[] signs = {">=","<=","="};
		attributeBox.getItems().addAll(attributes);
		signBox.getItems().addAll(signs);
		
	
		listArea.setOnMouseClicked((e)->{
				try
				{
					saveText.setText(listArea.getSelectionModel().getSelectedItem().getId());
				}
				catch(Exception ex)
				{
					saveText.setText("Invalid cell");
				}
	        });
		
		attributeBox.valueProperty().addListener((e)->{
			if(attributeBox.getValue().equals("Id") || attributeBox.getValue().equals("Admission") 
    				|| attributeBox.getValue().equals("Completion") || attributeBox.getValue().equals("InState")||
    				attributeBox.getValue().equals("OutState") || attributeBox.getValue().equals("Population"))
    		{
    			signBox.setVisible(true);
    		}
    		else
    		{
    			signBox.setVisible(false);
    		}
		});
	    
		
	}
	
	/**
     * detects if the saveButton is clicked and then adds the id from saveText to the list of saved colleges
     * in CurrentUser
     * @param event
     */
	@FXML
    void saveClick(ActionEvent event) {
		int id;
		try {
			id=Integer.parseInt(saveText.getText());;
		}
		catch(Exception e)
		{
			id=0;
	    	saveText.setText("invalid");
		}
		int success = Connect.current.addCollege(id);
		System.out.println(success);
		if(success == 1)
			saveText.setText("saved");
		else if(success==-2)
			saveText.setText("id doesn't exist");
		else if(success==-1)
			saveText.setText("saves full");
		else
			saveText.setText("already saved");
    }
	
	/**
     * detects if the showButton is clicked and then updates the listView with the colleges saved in
     * CurrentUser
     * @param event
     */
	 @FXML
	 void showClick(ActionEvent event) {
		 listArea.getItems().clear();
		 LinkedList<College> colleges = Connect.getSavedColleges();
		 if(colleges!=null)
		 {
			 
			 listArea.getItems().addAll(colleges);
		 }
		 
	 }
	 
	 /**
	     * detects if the app is closed, if it's closed it executes stop from FXDemo
	     * {@link FXDemo.stop}
	     * @param event
	     */
	 @FXML
	 public void exitApplication(ActionEvent event)
	 {
		 Platform.exit();
	 }
	 
	 /**
	     * detects if the unSaveButton is clicked and then removes the id from the save list in CurrentUser
	     * @param event
	     */
	 @FXML
	 void unSaveClick(ActionEvent event) {
		 int id;
			try {
				id=Integer.parseInt(saveText.getText());;
			}
			catch(Exception e)
			{
				id=0;
				saveText.setText("invalid");
			}
			Connect.current.removeCollege(id);
			saveText.setText("unsaved");
	 }
	 
	 

}

