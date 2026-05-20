/* ============================================================
   Imexcol Comercializadora — Frontend e-commerce
   API: /api/v1/{categorias,productos}
   Respuesta backend: { mensajes: string[], datos: T[], exitosa: bool }
   ============================================================ */

const API = {
    categorias: '/api/v1/categorias',
    productos: '/api/v1/productos'
};

// Estado de edición por entidad (null = modo creación)
const estado = {
    catEditandoId: null,
    proEditandoId: null
};

// ============ Utilidades ============

function mostrarNotificacion(mensaje, tipo) {
    const nodo = document.getElementById('notificacion');
    nodo.textContent = mensaje;
    nodo.className = 'notificacion notificacion-' + tipo;
    clearTimeout(mostrarNotificacion._timer);
    mostrarNotificacion._timer = setTimeout(() => {
        nodo.classList.add('oculto');
    }, 4500);
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
        minimumFractionDigits: 0,
        maximumFractionDigits: 0
    }).format(valor);
}

function etiquetaEstado(activo) {
    const clase = activo ? 'etiqueta-activa' : 'etiqueta-inactiva';
    const texto = activo ? 'Activo' : 'Inactivo';
    return `<span class="etiqueta ${clase}">${texto}</span>`;
}

function iniciales(nombre) {
    if (!nombre) return '—';
    return nombre.trim().split(/\s+/).slice(0, 2).map(p => p.charAt(0).toUpperCase()).join('');
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

async function peticionApi(url, opciones) {
    const respuesta = await fetch(url, opciones);
    let datos = null;
    try {
        datos = await respuesta.json();
    } catch (e) {
        datos = null;
    }
    if (!respuesta.ok || !datos || !datos.exitosa) {
        throw new Error(extraerMensaje(datos, 'Ocurrió un problema procesando la solicitud.'));
    }
    return datos;
}

// ============ Navegación entre vistas ============

function cambiarVista(vista) {
    document.querySelectorAll('.nav-enlace').forEach(b => {
        b.classList.toggle('activo', b.dataset.vista === vista);
    });
    document.querySelectorAll('.vista').forEach(v => {
        v.classList.toggle('oculto', v.id !== 'vista-' + vista);
    });
    window.scrollTo({ top: 0, behavior: 'smooth' });
}

function configurarNavegacion() {
    document.querySelectorAll('[data-vista]').forEach(btn => {
        btn.addEventListener('click', evento => {
            evento.preventDefault();
            cambiarVista(btn.dataset.vista);
        });
    });

    document.querySelectorAll('[data-ir]').forEach(btn => {
        btn.addEventListener('click', () => {
            const ancla = document.getElementById(btn.dataset.ir);
            if (ancla) ancla.scrollIntoView({ behavior: 'smooth' });
        });
    });
}

// ============ Catálogo (vista tienda) ============

async function cargarCatalogo() {
    const contenedor = document.getElementById('grilla-catalogo');
    contenedor.innerHTML = '<div class="catalogo-vacio">Cargando catálogo…</div>';
    try {
        const datos = await peticionApi(API.productos);
        renderizarCatalogo(datos.datos || []);
    } catch (error) {
        contenedor.innerHTML = `<div class="catalogo-vacio">${escapar(error.message)}</div>`;
    }
}

function renderizarCatalogo(productos) {
    const contenedor = document.getElementById('grilla-catalogo');
    const activos = productos.filter(p => p.estado);
    if (activos.length === 0) {
        contenedor.innerHTML = '<div class="catalogo-vacio">Aún no hay productos disponibles.</div>';
        return;
    }
    contenedor.innerHTML = activos.map(p => `
        <article class="tarjeta-producto">
            <div class="tarjeta-producto-imagen">
                <span class="iniciales">${escapar(iniciales(p.nombre))}</span>
            </div>
            <div class="tarjeta-producto-info">
                <span class="tarjeta-producto-categoria">${escapar((p.categoria && p.categoria.nombre) || 'Sin categoría')}</span>
                <h3 class="tarjeta-producto-nombre">${escapar(p.nombre)}</h3>
                <div class="tarjeta-producto-pie">
                    <p class="tarjeta-producto-precio">${formatoMoneda(p.precio)}</p>
                    <span class="tarjeta-producto-stock">Stock: ${escapar(p.stock)}</span>
                </div>
            </div>
        </article>
    `).join('');
}

// ============ CATEGORÍAS ============

async function cargarCategorias() {
    const tbody = document.getElementById('tabla-categorias-body');
    tbody.innerHTML = '<tr><td colspan="4" class="celda-vacia">Cargando…</td></tr>';
    try {
        const datos = await peticionApi(API.categorias);
        renderizarCategorias(datos.datos || []);
    } catch (error) {
        tbody.innerHTML = `<tr><td colspan="4" class="celda-vacia">${escapar(error.message)}</td></tr>`;
    }
}

function renderizarCategorias(categorias) {
    const tbody = document.getElementById('tabla-categorias-body');
    if (categorias.length === 0) {
        tbody.innerHTML = '<tr><td colspan="4" class="celda-vacia">No hay categorías registradas.</td></tr>';
        return;
    }
    tbody.innerHTML = categorias.map(c => `
        <tr>
            <td>${escapar(c.nombre)}</td>
            <td>${escapar(c.descripcion) || '—'}</td>
            <td>${etiquetaEstado(c.estado)}</td>
            <td class="celda-acciones">
                <button class="boton-fila" data-accion="editar-categoria"
                        data-id="${escapar(c.id)}"
                        data-nombre="${escapar(c.nombre)}"
                        data-descripcion="${escapar(c.descripcion || '')}"
                        data-estado="${c.estado}">Editar</button>
                <button class="boton-fila boton-fila-peligro" data-accion="eliminar-categoria"
                        data-id="${escapar(c.id)}"
                        data-nombre="${escapar(c.nombre)}">Eliminar</button>
            </td>
        </tr>
    `).join('');
}

function entrarModoEdicionCategoria(datos) {
    estado.catEditandoId = datos.id;
    document.getElementById('cat-id').value = datos.id;
    document.getElementById('cat-nombre').value = datos.nombre;
    document.getElementById('cat-descripcion').value = datos.descripcion;
    document.getElementById('cat-estado').checked = datos.estado === 'true' || datos.estado === true;
    document.getElementById('cat-titulo-form').textContent = 'Editar categoría';
    document.getElementById('cat-boton-submit').textContent = 'Guardar cambios';
    document.getElementById('cat-boton-cancelar').classList.remove('oculto');
    document.getElementById('form-categoria').scrollIntoView({ behavior: 'smooth', block: 'center' });
}

function salirModoEdicionCategoria() {
    estado.catEditandoId = null;
    const form = document.getElementById('form-categoria');
    form.reset();
    document.getElementById('cat-id').value = '';
    document.getElementById('cat-estado').checked = true;
    document.getElementById('cat-titulo-form').textContent = 'Nueva categoría';
    document.getElementById('cat-boton-submit').textContent = 'Registrar categoría';
    document.getElementById('cat-boton-cancelar').classList.add('oculto');
}

async function guardarCategoria(evento) {
    evento.preventDefault();
    const form = evento.target;
    const boton = document.getElementById('cat-boton-submit');
    boton.disabled = true;

    const cuerpo = {
        nombre: form.nombre.value.trim(),
        descripcion: form.descripcion.value.trim(),
        estado: form.estado.checked
    };

    const enEdicion = !!estado.catEditandoId;
    const url = enEdicion ? `${API.categorias}/${estado.catEditandoId}` : API.categorias;
    const metodo = enEdicion ? 'PUT' : 'POST';

    try {
        const datos = await peticionApi(url, {
            method: metodo,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(cuerpo)
        });
        mostrarNotificacion(
            extraerMensaje(datos, enEdicion
                ? 'Categoría actualizada satisfactoriamente.'
                : 'Categoría registrada satisfactoriamente.'),
            'exito'
        );
        salirModoEdicionCategoria();
        await cargarCategorias();
        await cargarOpcionesCategoria();
    } catch (error) {
        mostrarNotificacion(error.message, 'error');
    } finally {
        boton.disabled = false;
    }
}

async function eliminarCategoria(id, nombre) {
    if (!window.confirm(`¿Eliminar la categoría "${nombre}"? Esta acción no se puede deshacer.`)) return;
    try {
        const datos = await peticionApi(`${API.categorias}/${id}`, { method: 'DELETE' });
        mostrarNotificacion(extraerMensaje(datos, 'Categoría eliminada satisfactoriamente.'), 'exito');
        if (estado.catEditandoId === id) salirModoEdicionCategoria();
        await cargarCategorias();
        await cargarOpcionesCategoria();
    } catch (error) {
        mostrarNotificacion(error.message, 'error');
    }
}

// ============ PRODUCTOS ============

async function cargarOpcionesCategoria() {
    const select = document.getElementById('pro-categoria');
    const valorActual = select.value;
    try {
        const datos = await peticionApi(API.categorias);
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
    tbody.innerHTML = '<tr><td colspan="6" class="celda-vacia">Cargando…</td></tr>';
    try {
        const datos = await peticionApi(API.productos);
        renderizarProductos(datos.datos || []);
    } catch (error) {
        tbody.innerHTML = `<tr><td colspan="6" class="celda-vacia">${escapar(error.message)}</td></tr>`;
    }
}

function renderizarProductos(productos) {
    const tbody = document.getElementById('tabla-productos-body');
    if (productos.length === 0) {
        tbody.innerHTML = '<tr><td colspan="6" class="celda-vacia">No hay productos registrados.</td></tr>';
        return;
    }
    tbody.innerHTML = productos.map(p => {
        const categoriaId = (p.categoria && p.categoria.id) || '';
        const categoriaNombre = (p.categoria && p.categoria.nombre) || '';
        return `
            <tr>
                <td>${escapar(p.nombre)}</td>
                <td>${escapar(categoriaNombre) || '—'}</td>
                <td class="celda-num">${formatoMoneda(p.precio)}</td>
                <td class="celda-num">${escapar(p.stock)}</td>
                <td>${etiquetaEstado(p.estado)}</td>
                <td class="celda-acciones">
                    <button class="boton-fila" data-accion="editar-producto"
                            data-id="${escapar(p.id)}"
                            data-categoria-id="${escapar(categoriaId)}"
                            data-nombre="${escapar(p.nombre)}"
                            data-descripcion="${escapar(p.descripcion || '')}"
                            data-precio="${escapar(p.precio)}"
                            data-stock="${escapar(p.stock)}"
                            data-estado="${p.estado}">Editar</button>
                    <button class="boton-fila boton-fila-peligro" data-accion="eliminar-producto"
                            data-id="${escapar(p.id)}"
                            data-nombre="${escapar(p.nombre)}">Eliminar</button>
                </td>
            </tr>
        `;
    }).join('');
}

function entrarModoEdicionProducto(datos) {
    estado.proEditandoId = datos.id;
    document.getElementById('pro-id').value = datos.id;
    document.getElementById('pro-categoria').value = datos.categoriaId;
    document.getElementById('pro-nombre').value = datos.nombre;
    document.getElementById('pro-descripcion').value = datos.descripcion;
    document.getElementById('pro-precio').value = datos.precio;
    document.getElementById('pro-stock').value = datos.stock;
    document.getElementById('pro-estado').checked = datos.estado === 'true' || datos.estado === true;
    document.getElementById('pro-titulo-form').textContent = 'Editar producto';
    document.getElementById('pro-boton-submit').textContent = 'Guardar cambios';
    document.getElementById('pro-boton-cancelar').classList.remove('oculto');
    document.getElementById('form-producto').scrollIntoView({ behavior: 'smooth', block: 'center' });
}

function salirModoEdicionProducto() {
    estado.proEditandoId = null;
    const form = document.getElementById('form-producto');
    form.reset();
    document.getElementById('pro-id').value = '';
    document.getElementById('pro-estado').checked = true;
    document.getElementById('pro-titulo-form').textContent = 'Nuevo producto';
    document.getElementById('pro-boton-submit').textContent = 'Registrar producto';
    document.getElementById('pro-boton-cancelar').classList.add('oculto');
}

async function guardarProducto(evento) {
    evento.preventDefault();
    const form = evento.target;
    const boton = document.getElementById('pro-boton-submit');
    boton.disabled = true;

    const cuerpo = {
        categoria: { id: form.categoriaId.value },
        nombre: form.nombre.value.trim(),
        descripcion: form.descripcion.value.trim(),
        precio: parseFloat(form.precio.value),
        stock: parseInt(form.stock.value, 10),
        estado: form.estado.checked
    };

    const enEdicion = !!estado.proEditandoId;
    const url = enEdicion ? `${API.productos}/${estado.proEditandoId}` : API.productos;
    const metodo = enEdicion ? 'PUT' : 'POST';

    try {
        const datos = await peticionApi(url, {
            method: metodo,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(cuerpo)
        });
        mostrarNotificacion(
            extraerMensaje(datos, enEdicion
                ? 'Producto actualizado satisfactoriamente.'
                : 'Producto registrado satisfactoriamente.'),
            'exito'
        );
        salirModoEdicionProducto();
        await cargarProductos();
        await cargarCatalogo();
    } catch (error) {
        mostrarNotificacion(error.message, 'error');
    } finally {
        boton.disabled = false;
    }
}

async function eliminarProducto(id, nombre) {
    if (!window.confirm(`¿Eliminar el producto "${nombre}"? Esta acción no se puede deshacer.`)) return;
    try {
        const datos = await peticionApi(`${API.productos}/${id}`, { method: 'DELETE' });
        mostrarNotificacion(extraerMensaje(datos, 'Producto eliminado satisfactoriamente.'), 'exito');
        if (estado.proEditandoId === id) salirModoEdicionProducto();
        await cargarProductos();
        await cargarCatalogo();
    } catch (error) {
        mostrarNotificacion(error.message, 'error');
    }
}

// ============ Manejador delegado de acciones en filas ============

function manejarAccionFila(evento) {
    const boton = evento.target.closest('[data-accion]');
    if (!boton) return;
    const accion = boton.dataset.accion;

    if (accion === 'editar-categoria') {
        entrarModoEdicionCategoria({
            id: boton.dataset.id,
            nombre: boton.dataset.nombre,
            descripcion: boton.dataset.descripcion,
            estado: boton.dataset.estado
        });
    } else if (accion === 'eliminar-categoria') {
        eliminarCategoria(boton.dataset.id, boton.dataset.nombre);
    } else if (accion === 'editar-producto') {
        entrarModoEdicionProducto({
            id: boton.dataset.id,
            categoriaId: boton.dataset.categoriaId,
            nombre: boton.dataset.nombre,
            descripcion: boton.dataset.descripcion,
            precio: boton.dataset.precio,
            stock: boton.dataset.stock,
            estado: boton.dataset.estado
        });
    } else if (accion === 'eliminar-producto') {
        eliminarProducto(boton.dataset.id, boton.dataset.nombre);
    }
}

// ============ Inicialización ============

document.addEventListener('DOMContentLoaded', () => {
    configurarNavegacion();

    // Categorías
    document.getElementById('form-categoria').addEventListener('submit', guardarCategoria);
    document.getElementById('cat-boton-cancelar').addEventListener('click', salirModoEdicionCategoria);
    document.getElementById('btn-recargar-categorias').addEventListener('click', cargarCategorias);
    document.getElementById('tabla-categorias-body').addEventListener('click', manejarAccionFila);

    // Productos
    document.getElementById('form-producto').addEventListener('submit', guardarProducto);
    document.getElementById('pro-boton-cancelar').addEventListener('click', salirModoEdicionProducto);
    document.getElementById('btn-recargar-productos').addEventListener('click', () => {
        cargarProductos();
        cargarOpcionesCategoria();
    });
    document.getElementById('tabla-productos-body').addEventListener('click', manejarAccionFila);

    // Carga inicial
    cargarCatalogo();
    cargarCategorias();
    cargarProductos();
    cargarOpcionesCategoria();
});
