package pe.edu.upeu.sysrestaurant.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upeu.sysrestaurant.dto.ModeloDataAutocomplet;
import pe.edu.upeu.sysrestaurant.model.Comida;
import pe.edu.upeu.sysrestaurant.repository.ComidaRepository;
import pe.edu.upeu.sysrestaurant.service.IComidaService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ComidaServiceImp implements IComidaService {
    private static final Logger logger = LoggerFactory.getLogger(ComidaServiceImp.class);
    @Autowired
    ComidaRepository comidaRepo;
    
    @Override
    public Comida save(Comida comida) {
        return comidaRepo.save(comida);
    }
    
    @Override
    public List<Comida> findAll() {
        return comidaRepo.findAll();
    }
    
    @Override
    public Comida update(Comida comida) {
        return comidaRepo.save(comida);
    }
    
    @Override
    public void delete(Long id) {
        comidaRepo.deleteById(id);
    }
    
    @Override
    public Comida findById(Long id) {
        return comidaRepo.findById(id).orElse(null);
    }
    
    @Override
    public List<ModeloDataAutocomplet> listAutoCompletComida(String nombre) {
        List<ModeloDataAutocomplet> listarComida = new ArrayList<>();
        try {
            for (Comida comida : comidaRepo.listAutoCompletComida(nombre + "%")) {
                ModeloDataAutocomplet data = new ModeloDataAutocomplet();
                data.setIdx(comida.getNombre());
                data.setNameDysplay(String.valueOf(comida.getIdComida()));
                data.setOtherData(comida.getPrecio() + ":" + comida.getStock());
                listarComida.add(data);
            }
        } catch (Exception e) {
            logger.error("Error al realizar la busqueda", e);
        }
        return listarComida;
    }
    
    @Override
    public List<ModeloDataAutocomplet> listAutoCompletComida() {
        List<ModeloDataAutocomplet> listarComida = new ArrayList<>();
        try {
            for (Comida comida : comidaRepo.findByDisponibleTrueAndStockGreaterThanOrderByNombreAsc(0.0)) {
                ModeloDataAutocomplet data = new ModeloDataAutocomplet();
                data.setIdx(String.valueOf(comida.getIdComida()));
                data.setNameDysplay(comida.getNombre());
                data.setOtherData(comida.getPrecio() + ":" + comida.getStock());
                listarComida.add(data);
            }
        } catch (Exception e) {
            logger.error("Error al realizar la busqueda", e);
        }
        return listarComida;
    }
    
    @Override
    public List<Comida> findComidasDisponibles() {
        return comidaRepo.findByDisponibleTrueAndStockGreaterThanOrderByNombreAsc(0.0);
    }
}



