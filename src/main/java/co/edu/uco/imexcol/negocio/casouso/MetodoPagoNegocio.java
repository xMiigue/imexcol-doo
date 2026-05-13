package co.edu.uco.imexcol.negocio.casouso;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.negocio.dominio.MetodoPagoDominio;

public interface MetodoPagoNegocio {

    void registrar(MetodoPagoDominio dominio);

    void actualizar(MetodoPagoDominio dominio);

    void eliminar(UUID id);

    List<MetodoPagoDominio> consultar(MetodoPagoDominio filtros);
}
