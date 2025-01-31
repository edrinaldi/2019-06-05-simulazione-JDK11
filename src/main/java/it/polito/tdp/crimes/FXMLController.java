/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.crimes;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.crimes.model.Distretto;
import it.polito.tdp.crimes.model.DistrettoVicino;
import it.polito.tdp.crimes.model.Model;
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

    @FXML // fx:id="boxAnno"
    private ComboBox<Integer> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="boxMese"
    private ComboBox<Integer> boxMese; // Value injected by FXMLLoader

    @FXML // fx:id="boxGiorno"
    private ComboBox<Integer> boxGiorno; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaReteCittadina"
    private Button btnCreaReteCittadina; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaReteCittadina(ActionEvent event) {
    	// pulisco l'area di testo
    	this.txtResult.clear();
    	
    	// controllo l'anno
    	Integer anno = this.boxAnno.getValue();
    	if(anno == null) {
    		this.txtResult.setText("Errore: devi prima selezionare un anno.");
    		return;
    	}
    	
    	// creo il grafo
    	this.model.creaGrafo(anno);
    	
    	// stampo il risultato
    	this.txtResult.setText("Grafo creato!\n\n");
    	for(Distretto d : this.model.getVertici()) {
    		this.txtResult.appendText(String.format("VICINI DEL DISTRETTO: %s\n", d.getNome()));
    		for(DistrettoVicino v : this.model.getAdiacenti(d)) {
    			this.txtResult.appendText(v.toString() + "\n");
    		}
    		this.txtResult.appendText("\n");
    	}
    }

    @FXML
    void doSimula(ActionEvent event) {
    	// pulisco l'area di testo
    	this.txtResult.clear();
    	
    	// controllo il grafo
    	if(!this.model.isGrafoCreato()) {
    		this.txtResult.setText("Errore: devi prima creare il grafo.");
    		return;
    	}
    	
    	// controllo l'anno
    	Integer anno = this.boxAnno.getValue();
    	if(anno == null) {
    		this.txtResult.setText("Errore: devi prima selezionare un anno.");
    		return;
    	}
    	
    	// controllo il mese
    	Integer mese = this.boxMese.getValue();
    	if(mese == null) {
    		this.txtResult.setText("Errore: devi prima selezionare un mese.");
    		return;
    	}
    	
    	// controllo il giorno
    	Integer giorno = this.boxGiorno.getValue();
    	if(giorno == null) {
    		this.txtResult.setText("Errore: devi prima selezionare un giorno.");
    		return;
    	}
    	
    	// controllo N
    	int N = 0;
    	try {
    		N = Integer.parseInt(this.txtN.getText());
    	}
    	catch(NumberFormatException e) { 
    		e.printStackTrace();
    		this.txtResult.setText("Errore: devi inserire un valore intero per N.");
    		return;
    	}
    	
    	
    	// effettuo la simulazione
    	int nMalGestiti = this.model.simula(anno, mese, giorno, N);
    	
    	// stampo il risultato
    	this.txtResult.setText(String.format("Simulo con %d agenti\nCRIMINI MAL GESTITI: %d", N, nMalGestiti));
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxGiorno != null : "fx:id=\"boxGiorno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaReteCittadina != null : "fx:id=\"btnCreaReteCittadina\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	
    	// riempio la tendina con gli anni
    	this.boxAnno.getItems().clear();
    	for(int i=2014;i<=2017; i++) {
    		this.boxAnno.getItems().add(i);
    	}
    	
    	// riempio la tendina con i mesi
    	this.boxMese.getItems().clear();
    	for(int i=1;i<=12; i++) {
    		this.boxMese.getItems().add(i);
    	}
    	
    	// riempio la tendina con i giorni
    	this.boxGiorno.getItems().clear();
    	for(int i=1;i<=31; i++) {
    		this.boxGiorno.getItems().add(i);
    	}
    }
}
