package co.edu.uco.imexcol.datos.dao;

import java.util.UUID;

import co.edu.uco.imexcol.entidad.ResenaEntidad;

public interface ResenaDAO
        extends AccederDAO<ResenaEntidad>,
                RecuperarDAO<ResenaEntidad, UUID>,
                EliminarDAO<UUID> {
}
