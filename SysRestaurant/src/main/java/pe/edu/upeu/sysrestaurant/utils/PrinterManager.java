package pe.edu.upeu.sysrestaurant.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.print.PrintService;
import com.github.anastaciocintra.output.PrinterOutputStream;
import java.io.IOException;

public class PrinterManager {
    private static final Logger logger = LoggerFactory.getLogger(PrinterManager.class);
    private static PrinterManager instance;
    private PrintService printService;
    private String printerNameR = "pos-80-series";

    private PrinterManager() throws IOException {
        initializePrinter();
    }

    public static synchronized PrinterManager getInstance() throws IOException {
        if (instance == null) {
            instance = new PrinterManager();
        }
        return instance;
    }

    private void initializePrinter() throws IOException {
        String[] printerNames = PrinterOutputStream.getListPrintServicesNames();
        logger.debug("Impresoras disponibles: {}", (Object) printerNames);
        String targetPrinter = null;
        for (String printerName : printerNames) {
            if (printerName.toLowerCase().contains(printerNameR.toLowerCase())) {
                targetPrinter = printerName;
                break;
            }
        }
        if (targetPrinter == null) {
            throw new IOException("No se encontro ninguna impresora 'POS-80-Series'.");
        }
        logger.info("Impresora seleccionada: {}", targetPrinter);
        this.printService = PrinterOutputStream.getPrintServiceByName(targetPrinter);
    }

    public PrintService getPrintService() {
        return printService;
    }

}

