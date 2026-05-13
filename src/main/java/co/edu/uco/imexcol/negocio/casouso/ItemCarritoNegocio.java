package co.edu.uco.imexcol.negocio.casouso;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.negocio.dominio.ItemCarritoDominio;

public interface ItemCarritoNegocio {

    void registrar(ItemCarritoDominio dominio);

    void actualizar(ItemCarritoDominio dominio);

    void eliminar(UUID id);

    List<ItemCarritoDominio> consultar(ItemCarritoDominio filtros);
}
