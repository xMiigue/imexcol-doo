package co.edu.uco.imexcol.datos.dao;

import java.util.UUID;

import co.edu.uco.imexcol.entidad.EnvioEntidad;

public interface EnvioDAO
        extends AccederDAO<EnvioEntidad>,
                RecuperarDAO<EnvioEntidad, UUID>,
                ActualizarDAO<EnvioEntidad>,
                EliminarDAO<UUID> {
}
