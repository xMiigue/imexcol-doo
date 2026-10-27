package co.edu.uco.imexcol.datos.dao;

import java.util.UUID;

import co.edu.uco.imexcol.entidad.EventoRastreoEntidad;

public interface EventoRastreoDAO
        extends AccederDAO<EventoRastreoEntidad>,
                RecuperarDAO<EventoRastreoEntidad, UUID> {

    void eliminarPorEnvio(UUID idEnvio);
}
