package pe.edu.upeu.sysrestaurant.service;


import pe.edu.upeu.sysrestaurant.model.PedidoCarrito;

import java.util.List;

public interface IPedidoCarritoService extends  ICrudGenericoService<PedidoCarrito,Long>{
    List<PedidoCarrito> listaCarritoCliente(String dni);
    void deleteCarAll(String dniruc);
}



