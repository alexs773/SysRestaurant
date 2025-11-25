package pe.edu.upeu.sysrestaurant.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.sysrestaurant.model.PedidoCarrito;
import pe.edu.upeu.sysrestaurant.repository.ICrudGenericoRepository;
import pe.edu.upeu.sysrestaurant.repository.PedidoCarritoRepository;
import pe.edu.upeu.sysrestaurant.service.IPedidoCarritoService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PedidoCarritoServiceImp extends CrudGenericoServiceImp<PedidoCarrito, Long> implements IPedidoCarritoService {

    private final PedidoCarritoRepository carritoRepository;

    @Override
    protected ICrudGenericoRepository<PedidoCarrito, Long> getRepo() {
        return carritoRepository;
    }
    @Override
    public List<PedidoCarrito> listaCarritoCliente(String dni) {
        return carritoRepository.listaCarritoCliente(dni);
    }
    @Transactional
    @Override
    public void deleteCarAll(String dniruc) {
        carritoRepository.deleteByDniruc(dniruc);
    }
}



