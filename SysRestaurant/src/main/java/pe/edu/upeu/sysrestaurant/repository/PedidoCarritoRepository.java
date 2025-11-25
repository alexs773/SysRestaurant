package pe.edu.upeu.sysrestaurant.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.edu.upeu.sysrestaurant.model.PedidoCarrito;

import java.util.List;

public interface PedidoCarritoRepository extends ICrudGenericoRepository<PedidoCarrito, Long> {

    @Query(value = "SELECT c.* FROM upeu_pedido_carrito c WHERE c.dniruc=:dniruc", nativeQuery = true)
    List<PedidoCarrito> listaCarritoCliente(@Param("dniruc") String dniruc);

    void deleteByDniruc(String dniruc);
}



