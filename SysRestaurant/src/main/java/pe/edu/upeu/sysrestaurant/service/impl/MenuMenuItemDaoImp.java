package pe.edu.upeu.sysrestaurant.service.impl;

import org.springframework.stereotype.Service;
import pe.edu.upeu.sysrestaurant.dto.MenuMenuItenTO;
import pe.edu.upeu.sysrestaurant.service.IMenuMenuItemDao;

import java.util.*;

@Service
public class MenuMenuItemDaoImp implements IMenuMenuItemDao {
    @Override
    public List<MenuMenuItenTO> listaAccesos(String perfil, Properties idioma) {

        List<MenuMenuItenTO> lista = new ArrayList<>();

        lista.add(new MenuMenuItenTO("miprincipal", "/view/login.fxml", idioma.getProperty("menu.nombre.principal"), idioma.getProperty("menuitem.nombre.salir"), "Salir", "S"));
        lista.add(new MenuMenuItenTO("micomida", "/view/main_comida.fxml", "Restaurante", "GestiÃ³n Comidas", "Gestionar Comidas", "T"));
        lista.add(new MenuMenuItenTO("mipedido", "/view/main_venta.fxml", "Restaurante", "Reg. Pedido", "Gestionar Pedidos", "T"));
        lista.add(new MenuMenuItenTO("mireporte", "/view/main_reporte.fxml", "Restaurante", "Reportes", "Gestionar Reportes", "T"));

        List<MenuMenuItenTO> accesoReal = new ArrayList<>();

        accesoReal.add(lista.get(0));

        String rol = perfil == null ? "" : perfil.trim().toLowerCase();

        switch (rol) {
            case "admin":
            case "administrador":
                accesoReal.add(lista.get(1)); // Comidas
                accesoReal.add(lista.get(2)); // Pedidos
                accesoReal.add(lista.get(3)); // Reportes
                break;
            case "cocinero":
                accesoReal.add(lista.get(1)); // Solo Comidas
                break;
            case "mesero":
                accesoReal.add(lista.get(2)); // Solo Pedidos
                break;
            default:
                accesoReal.add(lista.get(2)); // Por defecto solo pedidos
                break;
        }
        return accesoReal;
    }

    @Override
    public Map<String, String[]> accesosAutorizados(List<MenuMenuItenTO> accesos) {

        Map<String, String[]> menuConfig = new HashMap<>();

        for (MenuMenuItenTO menu : accesos) {
            menuConfig.put("mi"+menu.getIdNombreObj(), new String[]{menu.getRutaFile(), menu.getNombreTab(),menu.getTipoTab()});
        }

        return menuConfig;
    }

}


