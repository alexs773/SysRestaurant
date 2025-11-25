package pe.edu.upeu.sysrestaurant.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.upeu.sysrestaurant.model.VentaDetalle;
import pe.edu.upeu.sysrestaurant.repository.ICrudGenericoRepository;
import pe.edu.upeu.sysrestaurant.repository.VentaDetalleRepository;
import pe.edu.upeu.sysrestaurant.service.IVentaDetalleService;

@RequiredArgsConstructor
@Service
public class VentaDetalleServiceImp extends CrudGenericoServiceImp<VentaDetalle, Long> implements IVentaDetalleService {
    private final VentaDetalleRepository ventaDetalleRepository;
    @Override
    protected ICrudGenericoRepository<VentaDetalle, Long> getRepo() {
        return ventaDetalleRepository;
    }
}


