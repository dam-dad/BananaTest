package dad.proyectos.banana_test.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.proyectos.banana_test.App;
import dad.proyectos.banana_test.utils.Preferencias;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PreferenciasController implements Initializable {
	
	private Preferencias.TEMAS temaOriginal;
	private Preferencias.TEMAS temaNuevo;
	private Preferencias.IDIOMAS idiomaOriginal;
	private Preferencias.IDIOMAS idiomaNuevo;
	private Stage stage;

	@FXML
	private VBox view;

	@FXML
	private ComboBox<String> cbIdioma;

	@FXML
	private ComboBox<String> cbTema;
	
	@FXML
    private ImageView ivTema;

	public PreferenciasController(Stage stage) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PreferenciasView.fxml"), App.resourceBundle);
		loader.setController(this);
		loader.load();
		idiomaOriginal = Preferencias.getIdioma();
		temaOriginal = Preferencias.getTema();
		idiomaNuevo = idiomaOriginal;
		temaNuevo = temaOriginal;
		this.stage = stage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cbIdioma.getItems().addAll(Preferencias.IDIOMAS.getAll());
		cbTema.getItems().addAll(Preferencias.TEMAS.getAll());
		
		cbIdioma.getSelectionModel().selectedIndexProperty().addListener((o, ov, nv) -> actualizarIdioma(o, ov, nv));
		cbTema.getSelectionModel().selectedIndexProperty().addListener((o, ov, nv) -> actualizarTema(o, ov, nv));
		
		ivTema.setImage(new Image(getClass().getResource("/images/previewTemas/" + Preferencias.getTema().toString() + ".png").toExternalForm()));
	}
	
	private void actualizarIdioma(ObservableValue<? extends Number> o, Number ov, Number nv) {
		idiomaNuevo = Preferencias.IDIOMAS.values()[nv.intValue()];
	}

	private void actualizarTema(ObservableValue<? extends Number> o, Number ov, Number nv) {
		temaNuevo = Preferencias.TEMAS.values()[nv.intValue()];
		ivTema.setImage(new Image(getClass().getResource("/images/previewTemas/" + Preferencias.TEMAS.values()[nv.intValue()] + ".png").toExternalForm()));
	}
	
	@FXML
    void onCancelarAction(ActionEvent event) {
		Preferencias.setIdioma(idiomaOriginal);
		Preferencias.setTema(temaOriginal);
		stage.close();
    }
	
	@FXML
    void onGuardarAction(ActionEvent event) {
		Preferencias.setIdioma(idiomaNuevo);
		Preferencias.setTema(temaNuevo);
		stage.close();
		App.primaryStage.getScene().getStylesheets().clear();
		if (Preferencias.getTema() != Preferencias.TEMAS.DEFAULT)
			App.primaryStage.getScene().getStylesheets().add(Preferencias.cargarTema());
    }

	public VBox getView() {
		return view;
	}

	public void setView(VBox view) {
		this.view = view;
	}

}
