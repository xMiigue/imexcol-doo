package co.edu.uco.imexcol.datos.dao;

import java.util.UUID;

import co.edu.uco.imexcol.entidad.PagoEntidad;

public interface PagoDAO
        extends AccederDAO<PagoEntidad>,
                RecuperarDAO<PagoEntidad, UUID>,
                ActualizarDAO<PagoEntidad>,
                EliminarDAO<UUID> {
}
