package pe.edu.upeu.sysrestaurant.service.impl;


import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upeu.sysrestaurant.model.Venta;
import pe.edu.upeu.sysrestaurant.repository.ICrudGenericoRepository;
import pe.edu.upeu.sysrestaurant.repository.VentaRepository;
import pe.edu.upeu.sysrestaurant.service.IVentaService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

@RequiredArgsConstructor
@Service
public class VentaServiceImp extends CrudGenericoServiceImp<Venta, Long> implements IVentaService {

    private static final Logger logger = LoggerFactory.getLogger(VentaServiceImp.class);

    @Autowired
    private DataSource dataSource;

    private final VentaRepository ventaRepository;

    @Override
    protected ICrudGenericoRepository<Venta, Long> getRepo() {
        return ventaRepository;
    }

    @Override
    public File getFile(String filex) {
        File newFolder = new File("jasper");
        String ruta = newFolder.getAbsolutePath();
        Path CAMINO = Paths.get(ruta + "/" + filex);
        logger.debug("Ruta del archivo jasper: {}", CAMINO.toAbsolutePath());
        return CAMINO.toFile();
    }
    @Override
    public JasperPrint runReport(Long idv) throws JRException, SQLException
    {
        if (!ventaRepository.existsById(idv)) {
            throw new IllegalArgumentException("La venta con id " + idv + " no existe");
        }
        HashMap<String, Object> param = new HashMap<>();
        String imgen = getFile("logoupeu.png").getAbsolutePath();
        String urljasper = compileSubreport("detallev.jrxml");
        param.put("idventa", idv);
        param.put("imagenurl", imgen);
        param.put("urljasper", urljasper);
        JasperDesign jdesign =
                JRXmlLoader.load(getFile("comprobante.jrxml"));
        JasperReport jreport = JasperCompileManager.compileReport(jdesign);
        try (Connection conn = dataSource.getConnection()) {
            return JasperFillManager.fillReport(jreport, param, conn);
        }
    }

    @Override
    public JasperPrint runReportVentas(String fInicio, String ffinal) throws
            JRException, SQLException {
        HashMap<String, Object> param = new HashMap<>();
        String imgen = getFile("logoupeu.png").getAbsolutePath();
        param.put("fechaI", fInicio);
        param.put("imagenurl", imgen);
        param.put("fechaF", ffinal);
        JasperDesign jdesign =
                JRXmlLoader.load(getFile("reporte_ventas.jrxml"));
        JasperReport jreport = JasperCompileManager.compileReport(jdesign);
        try (Connection conn = dataSource.getConnection()) {
            return JasperFillManager.fillReport(jreport, param, conn);
        }
    }

    private String compileSubreport(String jrxmlName) throws JRException {
        File jrxml = getFile(jrxmlName);
        String jasperName = jrxmlName.replace(".jrxml", ".jasper");
        File jasperFile = new File(jrxml.getParentFile(), jasperName);
        JasperCompileManager.compileReportToFile(jrxml.getAbsolutePath(), jasperFile.getAbsolutePath());
        return jasperFile.getAbsolutePath();
    }




}


