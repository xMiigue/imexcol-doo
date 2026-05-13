package co.edu.uco.imexcol.negocio.casouso;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.negocio.dominio.CarritoComprasDominio;

public interface CarritoComprasNegocio {

    void registrar(CarritoComprasDominio dominio);

    void actualizar(CarritoComprasDominio dominio);

    void eliminar(UUID id);

    List<CarritoComprasDominio> consultar(CarritoComprasDominio filtros);
}
