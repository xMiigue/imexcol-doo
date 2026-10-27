package co.edu.uco.imexcol.negocio.casouso;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.negocio.dominio.FacturaDominio;

public interface FacturaNegocio {

    void registrar(FacturaDominio dominio);

    void registrarParaPedido(UUID idPedido, double totalFacturado);

    FacturaDominio consultarPorPedido(UUID idPedido);

    FacturaDominio consultarPorPedidoDelCliente(UUID idPedido, UUID idCliente);

    List<FacturaDominio> consultar(FacturaDominio filtros);
}
