package pe.edu.upeu.sysrestaurant.controller;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.StackPane;
import net.sf.jasperreports.engine.JasperPrint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pe.edu.upeu.sysrestaurant.service.IVentaService;
import win.zqxu.jrviewer.JRViewerFX;

import java.time.format.DateTimeFormatter;

@Controller
public class ReporteController {

    private static final Logger logger = LoggerFactory.getLogger(ReporteController.class);

    @FXML
    DatePicker txtFechaI, txtFechaF;
    private JasperPrint jasperPrint;
    @FXML
    StackPane paneRepo;
    DateTimeFormatter fechaFmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    @Autowired
    IVentaService daoV;

    @FXML
    void generarReporte(){
        if (txtFechaI.getValue() == null || txtFechaF.getValue() == null) {
            logger.warn("Debe seleccionar ambas fechas para generar el reporte");
            return;
        }
        String fechaI = txtFechaI.getValue().format(fechaFmt);
        String fechaF = txtFechaF.getValue().format(fechaFmt);
        try {
            jasperPrint = daoV.runReportVentas(fechaI, fechaF);
            JRViewerFX viewer = new JRViewerFX(jasperPrint);
            paneRepo.getChildren().clear();
            paneRepo.getChildren().add(viewer);
            StackPane.setAlignment(viewer, Pos.CENTER);
        } catch (Exception e) {
            logger.error("Error al generar reporte de ventas", e);
        }
    }
}


