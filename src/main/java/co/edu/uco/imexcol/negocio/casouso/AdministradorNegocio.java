package co.edu.uco.imexcol.negocio.casouso;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.negocio.dominio.AdministradorDominio;

public interface AdministradorNegocio {

    void registrar(AdministradorDominio dominio);

    void actualizar(AdministradorDominio dominio);

    void eliminar(UUID id);

    List<AdministradorDominio> consultar(AdministradorDominio filtros);
}
