
package it.polito.tdp.borders;

import java.net.URL;

import java.util.ResourceBundle;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtAnno"
    private TextField txtAnno; // Value injected by FXMLLoader
    
    @FXML // fx:id="cmbBoxCountry"
    private ComboBox<Country> cmbBoxCountry; // Value injected by FXMLLoader

    @FXML // fx:id="btnTrovaVicini"
    private Button btnTrovaVicini; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCalcolaConfini(ActionEvent event) {
    	
    	int year=0;
    	try {
    		year=Integer.parseInt(this.txtAnno.getText());
    	}catch(Exception e) {
    		this.txtResult.setText("ERRORE NELL'INSERIMENTO DELL'ANNO: va inserito un numero");
    		return;
    	}
    	
    	if(year>2016 || year<1816) {
    		this.txtResult.setText("ERRORE NELL'INSERIMENTO DELL'ANNO: gli anni possibili vanno dal 1816 al 2016");
    		return;
    	}
    	
    	this.model.creaGrafo(year);
    	this.txtResult.setText("Sono stati inseriti "+model.getNumVertex()+" vertici e "+model.getNumEdges()+" archi.\n"+model.getEdges());
    	
    		
    	this.cmbBoxCountry.getItems().addAll(this.model.getVertices());
    	

    }
    
    @FXML
    void doTrovaVicini(ActionEvent event) {
    	Country c=this.cmbBoxCountry.getValue();
    	
    	if(this.cmbBoxCountry.getItems().size()==0) {
    		this.txtResult.setText("ERRORE NELL'INSERIMENTO DELLO STATO: bisogna creare prima il grafo");
    		return;
    	}
    	
    	if(c==null) {
    		this.txtResult.setText("ERRORE NELL'INSERIMENTO DELLO STATO: va inserito un stato dalla tendina");
    		return;
    	}
    	
    	this.txtResult.setText("Le componenti connesse sono: \n");
    	for(Country k:this.model.getVicini1(c))
    		this.txtResult.appendText(k.getNome()+"\n");
    		
    	
    	
    }
    

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbBoxCountry != null : "fx:id=\"cmbBoxCountry\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnTrovaVicini != null : "fx:id=\"btnTrovaVicini\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
