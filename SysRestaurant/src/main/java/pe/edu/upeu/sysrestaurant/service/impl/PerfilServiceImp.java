package pe.edu.upeu.sysrestaurant.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.upeu.sysrestaurant.model.Perfil;
import pe.edu.upeu.sysrestaurant.repository.ICrudGenericoRepository;
import pe.edu.upeu.sysrestaurant.repository.PerfilRepository;
import pe.edu.upeu.sysrestaurant.service.IPerfilService;

@RequiredArgsConstructor
@Service
public class PerfilServiceImp extends CrudGenericoServiceImp<Perfil, Long> implements IPerfilService {

    private final PerfilRepository perfilRepository;

    @Override
    protected ICrudGenericoRepository<Perfil, Long> getRepo() {
        return perfilRepository;
    }
}


