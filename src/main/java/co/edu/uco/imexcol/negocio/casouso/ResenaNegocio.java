package co.edu.uco.imexcol.negocio.casouso;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.negocio.dominio.ResenaDominio;

public interface ResenaNegocio {

    void registrar(ResenaDominio dominio);

    void eliminarPorCliente(UUID idResena, UUID idCliente);

    void eliminarPorAdministrador(UUID idResena, UUID idAdministrador);

    List<ResenaDominio> consultarPorProducto(UUID idProducto);

    List<ResenaDominio> consultar(ResenaDominio filtros);
}
