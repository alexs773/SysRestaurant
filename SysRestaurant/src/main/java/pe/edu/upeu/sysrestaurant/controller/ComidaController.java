package pe.edu.upeu.sysrestaurant.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pe.edu.upeu.sysrestaurant.components.ColumnInfo;
import pe.edu.upeu.sysrestaurant.components.TableViewHelper;
import pe.edu.upeu.sysrestaurant.components.Toast;
import pe.edu.upeu.sysrestaurant.model.Comida;
import pe.edu.upeu.sysrestaurant.service.IComidaService;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Controller
public class ComidaController {

    private static final Logger logger = LoggerFactory.getLogger(ComidaController.class);

    @FXML
    TextField txtNombreComida, txtPrecio, txtStock, txtDescripcion, txtFiltroDato;
    @FXML
    CheckBox chkDisponible;
    @FXML
    private TableView<Comida> tableView;
    @FXML
    Label lbnMsg;
    @FXML
    private AnchorPane miContenedor;
    Stage stage;

    @Autowired
    IComidaService comidaService;

    private Validator validator;
    ObservableList<Comida> listarComida;
    Comida formulario;
    Long idComidaCE = 0L;

    private void filtrarComidas(String filtro) {
        if (filtro == null || filtro.isEmpty()) {
            tableView.getItems().clear();
            tableView.getItems().addAll(listarComida);
        } else {
            String lowerCaseFilter = filtro.toLowerCase();
            List<Comida> comidasFiltradas = listarComida.stream()
                    .filter(comida -> {
                        if (comida.getNombre().toLowerCase().contains(lowerCaseFilter)) {
                            return true;
                        }
                        if (String.valueOf(comida.getPrecio()).contains(lowerCaseFilter)) {
                            return true;
                        }
                        if (String.valueOf(comida.getStock()).contains(lowerCaseFilter)) {
                            return true;
                        }
                        if (comida.getDescripcion() != null && comida.getDescripcion().toLowerCase().contains(lowerCaseFilter)) {
                            return true;
                        }
                        return false;
                    })
                    .collect(Collectors.toList());
            tableView.getItems().clear();
            tableView.getItems().addAll(comidasFiltradas);
        }
    }

    public void listar() {
        try {
            tableView.getItems().clear();
            listarComida = FXCollections.observableArrayList(comidaService.findAll());
            tableView.getItems().addAll(listarComida);
            txtFiltroDato.textProperty().addListener((observable, oldValue, newValue) -> {
                filtrarComidas(newValue);
            });
        } catch (Exception e) {
            logger.error("Error al listar comidas", e);
        }
    }

    @FXML
    public void initialize() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(2000),
                event -> {
                    stage = (Stage) miContenedor.getScene().getWindow();
                    if (stage != null) {
                        logger.debug("Stage inicializado: {}", stage.getTitle());
                    }
                }));
        timeline.setCycleCount(1);
        timeline.play();
        
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        TableViewHelper<Comida> tableViewHelper = new TableViewHelper<>();
        LinkedHashMap<String, ColumnInfo> columns = new LinkedHashMap<>();
        columns.put("ID", new ColumnInfo("idComida", 60.0));
        columns.put("Nombre Comida", new ColumnInfo("nombre", 250.0));
        columns.put("Precio", new ColumnInfo("precio", 100.0));
        columns.put("Stock", new ColumnInfo("stock", 80.0));
        columns.put("Disponible", new ColumnInfo("disponible", 80.0));
        columns.put("Descripción", new ColumnInfo("descripcion", 200.0));
        
        Consumer<Comida> updateAction = (Comida comida) -> {
            logger.debug("Actualizar comida: {}", comida);
            editForm(comida);
        };

        Consumer<Comida> deleteAction = (Comida comida) -> {
            logger.debug("Eliminar comida: {}", comida);
            comidaService.delete(comida.getIdComida());
            double with = stage.getWidth() / 1.5;
            double h = stage.getHeight() / 2;
            Toast.showToast(stage, "Se eliminó correctamente!!", 2000, with, h);
            listar();
        };
        
        tableViewHelper.addColumnsInOrderWithSize(tableView, columns, updateAction, deleteAction);
        tableView.setTableMenuButtonVisible(true);
        listar();
    }

    public void limpiarError() {
        List<Control> controles = List.of(
                txtNombreComida, txtPrecio, txtStock, txtDescripcion
        );
        controles.forEach(c -> c.getStyleClass().remove("text-field-error"));
    }

    public void clearForm() {
        txtNombreComida.clear();
        txtPrecio.clear();
        txtStock.clear();
        txtDescripcion.clear();
        chkDisponible.setSelected(true);
        idComidaCE = 0L;
        limpiarError();
    }

    public void editForm(Comida comida) {
        txtNombreComida.setText(comida.getNombre());
        txtPrecio.setText(comida.getPrecio().toString());
        txtStock.setText(comida.getStock().toString());
        txtDescripcion.setText(comida.getDescripcion() != null ? comida.getDescripcion() : "");
        chkDisponible.setSelected(comida.getDisponible() != null ? comida.getDisponible() : true);
        idComidaCE = comida.getIdComida();
        limpiarError();
    }

    private double parseDoubleSafe(String value) {
        if (value == null || value.trim().isEmpty()) return 0.0;
        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private void mostrarErroresValidacion(List<ConstraintViolation<Comida>> violaciones) {
        limpiarError();
        Map<String, Control> campos = new LinkedHashMap<>();
        campos.put("nombre", txtNombreComida);
        campos.put("precio", txtPrecio);
        campos.put("stock", txtStock);
        
        LinkedHashMap<String, String> erroresOrdenados = new LinkedHashMap<>();
        final Control[] primerControlConError = {null};
        for (String campo : campos.keySet()) {
            violaciones.stream()
                    .filter(v -> v.getPropertyPath().toString().equals(campo))
                    .findFirst()
                    .ifPresent(v -> {
                        erroresOrdenados.put(campo, v.getMessage());
                        Control control = campos.get(campo);
                        if (control != null && !control.getStyleClass().contains("text-field-error")) {
                            control.getStyleClass().add("text-field-error");
                        }
                        if (primerControlConError[0] == null) {
                            primerControlConError[0] = control;
                        }
                    });
        }
        if (!erroresOrdenados.isEmpty()) {
            var primerError = erroresOrdenados.entrySet().iterator().next();
            lbnMsg.setText(primerError.getValue());
            lbnMsg.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
            if (primerControlConError[0] != null) {
                Control finalPrimerControl = primerControlConError[0];
                Platform.runLater(finalPrimerControl::requestFocus);
            }
        }
    }

    private void procesarFormulario() {
        lbnMsg.setText("Formulario válido");
        lbnMsg.setStyle("-fx-text-fill: green; -fx-font-size: 16px;");
        limpiarError();
        double width = stage.getWidth() / 1.5;
        double height = stage.getHeight() / 2;
        if (idComidaCE > 0L) {
            formulario.setIdComida(idComidaCE);
            comidaService.update(formulario);
            Toast.showToast(stage, "Se actualizó correctamente!!", 2000, width, height);
        } else {
            comidaService.save(formulario);
            Toast.showToast(stage, "Se guardó correctamente!!", 2000, width, height);
        }
        clearForm();
        listar();
    }

    @FXML
    public void validarFormulario() {
        formulario = new Comida();
        formulario.setNombre(txtNombreComida.getText());
        formulario.setPrecio(parseDoubleSafe(txtPrecio.getText()));
        formulario.setStock(parseDoubleSafe(txtStock.getText()));
        formulario.setDescripcion(txtDescripcion.getText());
        formulario.setDisponible(chkDisponible.isSelected());
        
        Set<ConstraintViolation<Comida>> violaciones = validator.validate(formulario);
        List<ConstraintViolation<Comida>> violacionesOrdenadas = violaciones.stream()
                .sorted(Comparator.comparing(v -> v.getPropertyPath().toString()))
                .toList();
        if (violacionesOrdenadas.isEmpty()) {
            procesarFormulario();
        } else {
            mostrarErroresValidacion(violacionesOrdenadas);
        }
    }
}



