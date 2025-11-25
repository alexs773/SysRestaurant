package pe.edu.upeu.sysrestaurant.service;

import pe.edu.upeu.sysrestaurant.model.Usuario;

public interface IUsuarioService extends ICrudGenericoService<Usuario,Long>{
    Usuario loginUsuario(String user, String clave);
}


