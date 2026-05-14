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
