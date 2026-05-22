-- ============================================================
-- schema.sql — Imexcol (SQL Server)
--
-- Script idempotente para crear el esquema de las 5 entidades principales.
-- Los nombres de tablas y columnas coinciden exactamente con los definidos
-- en datos/dao/entidad/sql/*.java
--
-- Ejecucion manual:
--   sqlcmd -S localhost,1433 -U sa -P 'Imexcol2026!' -d imexcol -i schema.sql
--
-- O activar la ejecucion automatica de Spring Boot estableciendo
-- spring.sql.init.mode=always en application.properties.
-- ============================================================

-- ------------------------------------------------------------
-- Tabla: imex_categoria
-- ------------------------------------------------------------
IF OBJECT_ID(N'dbo.imex_categoria', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.imex_categoria (
        id          UNIQUEIDENTIFIER NOT NULL,
        nombre      NVARCHAR(100)    NOT NULL,
        descripcion NVARCHAR(255)    NULL,
        estado      BIT              NOT NULL,
        CONSTRAINT pk_imex_categoria PRIMARY KEY (id)
    );
END;

-- ------------------------------------------------------------
-- Tabla: imex_administrador
-- ------------------------------------------------------------
IF OBJECT_ID(N'dbo.imex_administrador', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.imex_administrador (
        id                  UNIQUEIDENTIFIER NOT NULL,
        nombre_usuario      NVARCHAR(50)     NOT NULL,
        correo_electronico  NVARCHAR(150)    NOT NULL,
        contrasena          NVARCHAR(255)    NOT NULL,
        estado              BIT              NOT NULL,
        CONSTRAINT pk_imex_administrador PRIMARY KEY (id),
        CONSTRAINT uq_imex_administrador_nombre_usuario UNIQUE (nombre_usuario),
        CONSTRAINT uq_imex_administrador_correo UNIQUE (correo_electronico)
    );
END;

-- ------------------------------------------------------------
-- Tabla: imex_cliente
-- ------------------------------------------------------------
IF OBJECT_ID(N'dbo.imex_cliente', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.imex_cliente (
        id                  UNIQUEIDENTIFIER NOT NULL,
        nombre              NVARCHAR(100)    NOT NULL,
        apellido            NVARCHAR(100)    NOT NULL,
        correo_electronico  NVARCHAR(150)    NOT NULL,
        contrasena          NVARCHAR(255)    NOT NULL,
        telefono            NVARCHAR(20)     NULL,
        estado              BIT              NOT NULL,
        CONSTRAINT pk_imex_cliente PRIMARY KEY (id),
        CONSTRAINT uq_imex_cliente_correo UNIQUE (correo_electronico)
    );
END;

-- ------------------------------------------------------------
-- Tabla: imex_producto (FK -> imex_categoria)
-- ------------------------------------------------------------
IF OBJECT_ID(N'dbo.imex_producto', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.imex_producto (
        id           UNIQUEIDENTIFIER NOT NULL,
        categoria_id UNIQUEIDENTIFIER NOT NULL,
        nombre       NVARCHAR(100)    NOT NULL,
        descripcion  NVARCHAR(1000)   NULL,
        precio       DECIMAL(12, 2)   NOT NULL,
        stock        INT              NOT NULL,
        estado       BIT              NOT NULL,
        CONSTRAINT pk_imex_producto PRIMARY KEY (id),
        CONSTRAINT fk_imex_producto_categoria
            FOREIGN KEY (categoria_id) REFERENCES dbo.imex_categoria (id)
    );
END;

-- ------------------------------------------------------------
-- Tabla: imex_pedido (FK -> imex_cliente)
-- ------------------------------------------------------------
IF OBJECT_ID(N'dbo.imex_pedido', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.imex_pedido (
        id           UNIQUEIDENTIFIER NOT NULL,
        cliente_id   UNIQUEIDENTIFIER NOT NULL,
        fecha_pedido DATE             NOT NULL,
        total        DECIMAL(12, 2)   NOT NULL,
        estado       NVARCHAR(50)     NOT NULL,
        CONSTRAINT pk_imex_pedido PRIMARY KEY (id),
        CONSTRAINT fk_imex_pedido_cliente
            FOREIGN KEY (cliente_id) REFERENCES dbo.imex_cliente (id)
    );
END;

-- ------------------------------------------------------------
-- Tabla: imex_metodo_pago
-- ------------------------------------------------------------
IF OBJECT_ID(N'dbo.imex_metodo_pago', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.imex_metodo_pago (
        id          UNIQUEIDENTIFIER NOT NULL,
        nombre      NVARCHAR(100)    NOT NULL,
        descripcion NVARCHAR(255)    NULL,
        estado      BIT              NOT NULL,
        CONSTRAINT pk_imex_metodo_pago PRIMARY KEY (id)
    );
END;

-- ------------------------------------------------------------
-- Tabla: imex_direccion (FK -> imex_cliente)
-- ------------------------------------------------------------
IF OBJECT_ID(N'dbo.imex_direccion', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.imex_direccion (
        id            UNIQUEIDENTIFIER NOT NULL,
        cliente_id    UNIQUEIDENTIFIER NOT NULL,
        calle         NVARCHAR(200)    NOT NULL,
        ciudad        NVARCHAR(100)    NOT NULL,
        departamento  NVARCHAR(100)    NOT NULL,
        codigo_postal NVARCHAR(10)     NULL,
        pais          NVARCHAR(100)    NOT NULL,
        es_principal  BIT              NOT NULL,
        CONSTRAINT pk_imex_direccion PRIMARY KEY (id),
        CONSTRAINT fk_imex_direccion_cliente
            FOREIGN KEY (cliente_id) REFERENCES dbo.imex_cliente (id)
    );
END;

-- ------------------------------------------------------------
-- Tabla: imex_carrito_compras (FK -> imex_cliente)
-- ------------------------------------------------------------
IF OBJECT_ID(N'dbo.imex_carrito_compras', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.imex_carrito_compras (
        id              UNIQUEIDENTIFIER NOT NULL,
        cliente_id      UNIQUEIDENTIFIER NOT NULL,
        fecha_creacion  DATE             NOT NULL,
        estado          NVARCHAR(20)     NOT NULL,
        CONSTRAINT pk_imex_carrito_compras PRIMARY KEY (id),
        CONSTRAINT fk_imex_carrito_compras_cliente
            FOREIGN KEY (cliente_id) REFERENCES dbo.imex_cliente (id)
    );
END;

-- ------------------------------------------------------------
-- Tabla: imex_item_carrito (FK -> imex_carrito_compras, imex_producto)
-- ------------------------------------------------------------
IF OBJECT_ID(N'dbo.imex_item_carrito', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.imex_item_carrito (
        id              UNIQUEIDENTIFIER NOT NULL,
        carrito_id      UNIQUEIDENTIFIER NOT NULL,
        producto_id     UNIQUEIDENTIFIER NOT NULL,
        cantidad        INT              NOT NULL,
        precio_unitario DECIMAL(12, 2)   NOT NULL,
        CONSTRAINT pk_imex_item_carrito PRIMARY KEY (id),
        CONSTRAINT fk_imex_item_carrito_carrito
            FOREIGN KEY (carrito_id) REFERENCES dbo.imex_carrito_compras (id),
        CONSTRAINT fk_imex_item_carrito_producto
            FOREIGN KEY (producto_id) REFERENCES dbo.imex_producto (id)
    );
END;

-- ------------------------------------------------------------
-- Alteracion: imex_producto — agrega imagen_url (opcional)
-- ------------------------------------------------------------
IF NOT EXISTS (
    SELECT 1 FROM sys.columns
    WHERE name = 'imagen_url' AND object_id = OBJECT_ID(N'dbo.imex_producto')
)
BEGIN
    ALTER TABLE dbo.imex_producto
        ADD imagen_url NVARCHAR(255) NULL;
END;

-- ------------------------------------------------------------
-- Alteracion: imex_pedido — agrega direccion_id (FK -> imex_direccion)
-- ------------------------------------------------------------
IF NOT EXISTS (
    SELECT 1 FROM sys.columns
    WHERE name = 'direccion_id' AND object_id = OBJECT_ID(N'dbo.imex_pedido')
)
BEGIN
    ALTER TABLE dbo.imex_pedido
        ADD direccion_id UNIQUEIDENTIFIER NULL
            CONSTRAINT fk_imex_pedido_direccion REFERENCES dbo.imex_direccion (id);
END;

-- ------------------------------------------------------------
-- Tabla: imex_linea_pedido (FK -> imex_pedido, imex_producto)
-- ------------------------------------------------------------
IF OBJECT_ID(N'dbo.imex_linea_pedido', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.imex_linea_pedido (
        id              UNIQUEIDENTIFIER NOT NULL,
        pedido_id       UNIQUEIDENTIFIER NOT NULL,
        producto_id     UNIQUEIDENTIFIER NOT NULL,
        cantidad        INT              NOT NULL,
        precio_unitario DECIMAL(12, 2)   NOT NULL,
        subtotal        DECIMAL(12, 2)   NOT NULL,
        CONSTRAINT pk_imex_linea_pedido PRIMARY KEY (id),
        CONSTRAINT fk_imex_linea_pedido_pedido
            FOREIGN KEY (pedido_id) REFERENCES dbo.imex_pedido (id),
        CONSTRAINT fk_imex_linea_pedido_producto
            FOREIGN KEY (producto_id) REFERENCES dbo.imex_producto (id)
    );
END;

-- ------------------------------------------------------------
-- Tabla: imex_pago (FK -> imex_pedido, imex_metodo_pago)
-- ------------------------------------------------------------
IF OBJECT_ID(N'dbo.imex_pago', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.imex_pago (
        id             UNIQUEIDENTIFIER NOT NULL,
        pedido_id      UNIQUEIDENTIFIER NOT NULL,
        metodo_pago_id UNIQUEIDENTIFIER NOT NULL,
        monto          DECIMAL(12, 2)   NOT NULL,
        fecha_pago     DATE             NOT NULL,
        estado         NVARCHAR(50)     NOT NULL,
        CONSTRAINT pk_imex_pago PRIMARY KEY (id),
        CONSTRAINT fk_imex_pago_pedido
            FOREIGN KEY (pedido_id) REFERENCES dbo.imex_pedido (id),
        CONSTRAINT fk_imex_pago_metodo_pago
            FOREIGN KEY (metodo_pago_id) REFERENCES dbo.imex_metodo_pago (id)
    );
END;

-- ------------------------------------------------------------
-- Tabla: imex_envio (FK -> imex_pedido)
-- ------------------------------------------------------------
IF OBJECT_ID(N'dbo.imex_envio', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.imex_envio (
        id             UNIQUEIDENTIFIER NOT NULL,
        pedido_id      UNIQUEIDENTIFIER NOT NULL,
        fecha_envio    DATE             NOT NULL,
        fecha_entrega  DATE             NULL,
        transportadora NVARCHAR(100)    NOT NULL,
        numero_guia    NVARCHAR(100)    NOT NULL,
        estado         NVARCHAR(50)     NOT NULL,
        CONSTRAINT pk_imex_envio PRIMARY KEY (id),
        CONSTRAINT fk_imex_envio_pedido
            FOREIGN KEY (pedido_id) REFERENCES dbo.imex_pedido (id)
    );
END;

-- ------------------------------------------------------------
-- Indices auxiliares para acelerar consultas por filtro
-- ------------------------------------------------------------
IF NOT EXISTS (
    SELECT 1 FROM sys.indexes
    WHERE name = 'ix_imex_producto_categoria_id' AND object_id = OBJECT_ID(N'dbo.imex_producto')
)
BEGIN
    CREATE INDEX ix_imex_producto_categoria_id ON dbo.imex_producto (categoria_id);
END;

IF NOT EXISTS (
    SELECT 1 FROM sys.indexes
    WHERE name = 'ix_imex_pedido_cliente_id' AND object_id = OBJECT_ID(N'dbo.imex_pedido')
)
BEGIN
    CREATE INDEX ix_imex_pedido_cliente_id ON dbo.imex_pedido (cliente_id);
END;

IF NOT EXISTS (
    SELECT 1 FROM sys.indexes
    WHERE name = 'ix_imex_pedido_estado' AND object_id = OBJECT_ID(N'dbo.imex_pedido')
)
BEGIN
    CREATE INDEX ix_imex_pedido_estado ON dbo.imex_pedido (estado);
END;
