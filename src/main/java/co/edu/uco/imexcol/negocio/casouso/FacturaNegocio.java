package co.edu.uco.imexcol.negocio.casouso;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.negocio.dominio.FacturaDominio;

public interface FacturaNegocio {

    void registrar(FacturaDominio dominio);

    void actualizar(FacturaDominio dominio);

    void eliminar(UUID id);

    List<FacturaDominio> consultar(FacturaDominio filtros);
}
