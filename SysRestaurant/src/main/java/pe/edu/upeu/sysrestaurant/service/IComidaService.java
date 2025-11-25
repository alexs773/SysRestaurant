package pe.edu.upeu.sysrestaurant.service;

import pe.edu.upeu.sysrestaurant.dto.ModeloDataAutocomplet;
import pe.edu.upeu.sysrestaurant.model.Comida;

import java.util.List;

public interface IComidaService {
    Comida save(Comida comida);
    List<Comida> findAll();
    Comida update(Comida comida);
    void delete(Long id);
    Comida findById(Long id);
    List<ModeloDataAutocomplet> listAutoCompletComida(String nombre);
    List<ModeloDataAutocomplet> listAutoCompletComida();
    List<Comida> findComidasDisponibles();
}



