package co.edu.uco.imexcol.negocio.casouso;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.negocio.dominio.LineaPedidoDominio;

public interface LineaPedidoNegocio {

    void registrar(LineaPedidoDominio dominio);

    void actualizar(LineaPedidoDominio dominio);

    void eliminar(UUID id);

    List<LineaPedidoDominio> consultar(LineaPedidoDominio filtros);
}
