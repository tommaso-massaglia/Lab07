package it.polito.tdp.poweroutages;

import it.polito.tdp.poweroutages.model.Model;
import it.polito.tdp.poweroutages.model.Nerc;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class PowerOutagesController {

	Model model = new Model();

	public void setModel(Model model) {
		this.model = model;
		ObservableList<Nerc> nerc = FXCollections.observableArrayList();
		for (Nerc n : model.getNercList()) {
			nerc.add(n);
		}
		nercChoicheBox.setItems(nerc);
	}

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private ComboBox<Nerc> nercChoicheBox;

	@FXML
	private TextField txtMaxYears;

	@FXML
	private TextField txtMaxHours;

	@FXML
	private Button btnWCA;

	@FXML
	private TextArea txtOutput;

	@FXML
	void doWCA(ActionEvent event) {
		this.txtOutput.setText(model.WCScenario(Integer.parseInt(this.txtMaxHours.getText()),
				Integer.parseInt(this.txtMaxYears.getText()), this.nercChoicheBox.getValue().getId()));
	}

	@FXML
	void initialize() {
		assert nercChoicheBox != null : "fx:id=\"nercChoicheBox\" was not injected: check your FXML file 'PowerOutages.fxml'.";
		assert txtMaxYears != null : "fx:id=\"txtMaxYears\" was not injected: check your FXML file 'PowerOutages.fxml'.";
		assert txtMaxHours != null : "fx:id=\"txtMaxHours\" was not injected: check your FXML file 'PowerOutages.fxml'.";
		assert btnWCA != null : "fx:id=\"btnWCA\" was not injected: check your FXML file 'PowerOutages.fxml'.";
		assert txtOutput != null : "fx:id=\"txtOutput\" was not injected: check your FXML file 'PowerOutages.fxml'.";
	}

}