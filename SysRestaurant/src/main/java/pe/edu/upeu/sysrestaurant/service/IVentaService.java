package pe.edu.upeu.sysrestaurant.service;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import pe.edu.upeu.sysrestaurant.model.Venta;

import java.io.File;
import java.sql.SQLException;

public interface IVentaService extends ICrudGenericoService<Venta,Long>{

    File getFile(String filex);
    JasperPrint runReport(Long idv) throws JRException, SQLException;

    JasperPrint runReportVentas(String fInicio, String ffinal) throws
            JRException, SQLException;

}


