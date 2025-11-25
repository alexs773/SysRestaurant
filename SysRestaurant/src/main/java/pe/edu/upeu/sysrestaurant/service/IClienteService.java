package pe.edu.upeu.sysrestaurant.service;

import pe.edu.upeu.sysrestaurant.dto.ModeloDataAutocomplet;
import pe.edu.upeu.sysrestaurant.model.Cliente;

import java.util.List;

public interface IClienteService extends ICrudGenericoService<Cliente,String> {
    List<ModeloDataAutocomplet> listAutoCompletCliente();
}


