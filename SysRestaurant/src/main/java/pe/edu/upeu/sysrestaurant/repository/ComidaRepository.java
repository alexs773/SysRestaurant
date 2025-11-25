package pe.edu.upeu.sysrestaurant.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.edu.upeu.sysrestaurant.model.Comida;

import java.util.List;

public interface ComidaRepository extends ICrudGenericoRepository<Comida, Long> {
    
    @Query(value = "SELECT c.* FROM upeu_comida c WHERE c.nombre LIKE :nombre AND c.disponible = 1 AND c.stock > 0", nativeQuery = true)
    List<Comida> listAutoCompletComida(@Param("nombre") String nombre);
    
    List<Comida> findByDisponibleTrueAndStockGreaterThanOrderByNombreAsc(Double stock);
}



