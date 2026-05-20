package co.edu.uco.imexcol.datos.dao;

import java.util.UUID;

import co.edu.uco.imexcol.entidad.MetodoPagoEntidad;

public interface MetodoPagoDAO
        extends AccederDAO<MetodoPagoEntidad>,
                RecuperarDAO<MetodoPagoEntidad, UUID>,
                ActualizarDAO<MetodoPagoEntidad>,
                EliminarDAO<UUID> {
}
