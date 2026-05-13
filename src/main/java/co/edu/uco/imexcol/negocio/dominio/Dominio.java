package co.edu.uco.imexcol.negocio.dominio;

import java.util.UUID;

import co.edu.uco.imexcol.transversal.UtilUUID;

public abstract class Dominio {

    private UUID id;

    protected Dominio(final UUID id) {
        setId(id);
    }

    public final UUID getId() {
        return id;
    }

    public final void setId(final UUID id) {
        this.id = UtilUUID.obtenerValorDefecto(id);
    }
}
