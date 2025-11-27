package pe.edu.upeu.sysrestaurant.components;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;
import net.sf.jasperreports.engine.JasperPrint;
import win.zqxu.jrviewer.JRViewerFX;


public class ReportAlert {

    private JasperPrint jasperPrint;

    public ReportAlert(JasperPrint jasperPrint) {
        this.jasperPrint = jasperPrint;
    }

    public void show() {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Visualizar Reporte");
        alert.setHeaderText(null);

        JRViewerFX viewerFX = new JRViewerFX(jasperPrint);
        viewerFX.setPrefSize(800, 400);

        StackPane stackPane = new StackPane(viewerFX);
        alert.getDialogPane().setContent(stackPane);

        ButtonType closeButton = new ButtonType("Cerrar");
        alert.getButtonTypes().add(closeButton);

        alert.setOnCloseRequest(event -> alert.close());
        alert.showAndWait().ifPresent(response -> {
            if (response == closeButton) {
                alert.close();
            }
        });
    }
}

