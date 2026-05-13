package co.edu.uco.imexcol.negocio.casouso;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.negocio.dominio.PedidoDominio;

public interface PedidoNegocio {

    void registrar(PedidoDominio dominio);

    void actualizar(PedidoDominio dominio);

    void eliminar(UUID id);

    List<PedidoDominio> consultar(PedidoDominio filtros);
}
