/* ============================================================
   Imexcol — Lógica del panel
   API: http://localhost:8080/api/v1/{categorias|productos}
   Formato de respuesta: { mensajes: string[], datos: T[], exitosa: bool }
   ============================================================ */

const API = {
    categorias: '/api/v1/categorias',
    productos: '/api/v1/productos'
};

// ============ Utilidades ============

function mostrarNotificacion(mensaje, tipo) {
    const nodo = document.getElementById('notificacion');
    nodo.textContent = mensaje;
    nodo.className = 'notificacion notificacion-' + tipo;
    setTimeout(() => {
        nodo.classList.add('oculto');
        nodo.classList.remove('notificacion-exito', 'notificacion-error');
    }, 4000);
}

function extraerMensaje(respuesta, predeterminado) {
    if (respuesta && Array.isArray(respuesta.mensajes) && respuesta.mensajes.length > 0) {
        return respuesta.mensajes.join(' ');
    }
    return predeterminado;
}

function formatoMoneda(valor) {
    if (valor === null || valor === undefined) return '—';
    return new Intl.NumberFormat('es-CO', {
        style: 'currency',
        currency: 'COP',
        minimumFractionDigits: 2,
        maximumFractionDigits: 2
    }).format(valor);
}

function etiquetaEstado(activo) {
    const clase = activo ? 'etiqueta-activa' : 'etiqueta-inactiva';
    const texto = activo ? 'Activo' : 'Inactivo';
    return `<span class="etiqueta ${clase}">${texto}</span>`;
}

function escapar(texto) {
    if (texto === null || texto === undefined) return '';
    return String(texto)
        .replace(/&/g, '&amp;')
        .replace(/</g, '&lt;')
        .replace(/>/g, '&gt;')
        .replace(/"/g, '&quot;')
        .replace(/'/g, '&#39;');
}

// ============ Navegación entre vistas ============

function configurarTabs() {
    const tabs = document.querySelectorAll('.tab');
    tabs.forEach(tab => {
        tab.addEventListener('click', () => {
            const vista = tab.dataset.vista;
            tabs.forEach(t => t.classList.toggle('activo', t === tab));
            document.querySelectorAll('.vista').forEach(v => {
                v.classList.toggle('oculto', v.id !== 'vista-' + vista);
            });
        });
    });
}

// ============ Categorías ============

async function cargarCategorias() {
    const tbody = document.getElementById('tabla-categorias-body');
    tbody.innerHTML = '<tr><td colspan="3" class="celda-vacia">Cargando…</td></tr>';
    try {
        const respuesta = await fetch(API.categorias);
        const datos = await respuesta.json();
        if (!respuesta.ok || !datos.exitosa) {
            throw new Error(extraerMensaje(datos, 'No fue posible consultar categorías.'));
        }
        renderizarCategorias(datos.datos || []);
    } catch (error) {
        tbody.innerHTML = `<tr><td colspan="3" class="celda-vacia">${escapar(error.message)}</td></tr>`;
    }
}

function renderizarCategorias(categorias) {
    const tbody = document.getElementById('tabla-categorias-body');
    if (categorias.length === 0) {
        tbody.innerHTML = '<tr><td colspan="3" class="celda-vacia">No hay categorías registradas.</td></tr>';
        return;
    }
    tbody.innerHTML = categorias.map(c => `
        <tr>
            <td>${escapar(c.nombre)}</td>
            <td>${escapar(c.descripcion) || '—'}</td>
            <td>${etiquetaEstado(c.estado)}</td>
        </tr>
    `).join('');
}

async function registrarCategoria(evento) {
    evento.preventDefault();
    const form = evento.target;
    const boton = form.querySelector('button[type="submit"]');
    boton.disabled = true;

    const cuerpo = {
        nombre: form.nombre.value.trim(),
        descripcion: form.descripcion.value.trim(),
        estado: form.estado.checked
    };

    try {
        const respuesta = await fetch(API.categorias, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(cuerpo)
        });
        const datos = await respuesta.json();
        if (!respuesta.ok || !datos.exitosa) {
            throw new Error(extraerMensaje(datos, 'No fue posible registrar la categoría.'));
        }
        mostrarNotificacion(extraerMensaje(datos, 'Categoría registrada satisfactoriamente.'), 'exito');
        form.reset();
        form.estado.checked = true;
        await cargarCategorias();
        await cargarOpcionesCategoria(); // refrescar combo en productos
    } catch (error) {
        mostrarNotificacion(error.message, 'error');
    } finally {
        boton.disabled = false;
    }
}

// ============ Productos ============

async function cargarOpcionesCategoria() {
    const select = document.getElementById('pro-categoria');
    const valorActual = select.value;
    try {
        const respuesta = await fetch(API.categorias);
        const datos = await respuesta.json();
        if (!respuesta.ok || !datos.exitosa) {
            throw new Error(extraerMensaje(datos, 'No fue posible cargar categorías.'));
        }
        const lista = (datos.datos || []).filter(c => c.estado);
        select.innerHTML = '<option value="">Seleccione una categoría…</option>' +
            lista.map(c => `<option value="${escapar(c.id)}">${escapar(c.nombre)}</option>`).join('');
        if (valorActual) select.value = valorActual;
    } catch (error) {
        select.innerHTML = '<option value="">No se pudieron cargar las categorías</option>';
    }
}

async function cargarProductos() {
    const tbody = document.getElementById('tabla-productos-body');
    tbody.innerHTML = '<tr><td colspan="5" class="celda-vacia">Cargando…</td></tr>';
    try {
        const respuesta = await fetch(API.productos);
        const datos = await respuesta.json();
        if (!respuesta.ok || !datos.exitosa) {
            throw new Error(extraerMensaje(datos, 'No fue posible consultar productos.'));
        }
        renderizarProductos(datos.datos || []);
    } catch (error) {
        tbody.innerHTML = `<tr><td colspan="5" class="celda-vacia">${escapar(error.message)}</td></tr>`;
    }
}

function renderizarProductos(productos) {
    const tbody = document.getElementById('tabla-productos-body');
    if (productos.length === 0) {
        tbody.innerHTML = '<tr><td colspan="5" class="celda-vacia">No hay productos registrados.</td></tr>';
        return;
    }
    tbody.innerHTML = productos.map(p => `
        <tr>
            <td>${escapar(p.nombre)}</td>
            <td>${escapar(p.categoria && p.categoria.nombre) || '—'}</td>
            <td class="celda-num">${formatoMoneda(p.precio)}</td>
            <td class="celda-num">${escapar(p.stock)}</td>
            <td>${etiquetaEstado(p.estado)}</td>
        </tr>
    `).join('');
}

async function registrarProducto(evento) {
    evento.preventDefault();
    const form = evento.target;
    const boton = form.querySelector('button[type="submit"]');
    boton.disabled = true;

    const cuerpo = {
        categoria: { id: form.categoriaId.value },
        nombre: form.nombre.value.trim(),
        descripcion: form.descripcion.value.trim(),
        precio: parseFloat(form.precio.value),
        stock: parseInt(form.stock.value, 10),
        estado: form.estado.checked
    };

    try {
        const respuesta = await fetch(API.productos, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(cuerpo)
        });
        const datos = await respuesta.json();
        if (!respuesta.ok || !datos.exitosa) {
            throw new Error(extraerMensaje(datos, 'No fue posible registrar el producto.'));
        }
        mostrarNotificacion(extraerMensaje(datos, 'Producto registrado satisfactoriamente.'), 'exito');
        form.reset();
        form.estado.checked = true;
        await cargarProductos();
    } catch (error) {
        mostrarNotificacion(error.message, 'error');
    } finally {
        boton.disabled = false;
    }
}

// ============ Inicialización ============

document.addEventListener('DOMContentLoaded', () => {
    configurarTabs();

    document.getElementById('form-categoria').addEventListener('submit', registrarCategoria);
    document.getElementById('btn-recargar-categorias').addEventListener('click', cargarCategorias);

    document.getElementById('form-producto').addEventListener('submit', registrarProducto);
    document.getElementById('btn-recargar-productos').addEventListener('click', () => {
        cargarProductos();
        cargarOpcionesCategoria();
    });

    cargarCategorias();
    cargarProductos();
    cargarOpcionesCategoria();
});
