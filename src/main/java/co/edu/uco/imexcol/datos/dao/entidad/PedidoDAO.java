package co.edu.uco.imexcol.datos.dao.entidad;

import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.AccederDAO;
import co.edu.uco.imexcol.datos.dao.ActualizarDAO;
import co.edu.uco.imexcol.datos.dao.EliminarDAO;
import co.edu.uco.imexcol.datos.dao.RecuperarDAO;
import co.edu.uco.imexcol.entidad.PedidoEntidad;

public interface PedidoDAO
        extends AccederDAO<PedidoEntidad>,
                RecuperarDAO<PedidoEntidad, UUID>,
                ActualizarDAO<PedidoEntidad>,
                EliminarDAO<UUID> {
}
