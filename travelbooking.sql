CREATE DATABASE usuarios;

CREATE TABLE usuarios (
    id_usuario SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    enabled BOOLEAN DEFAULT TRUE
);

CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    rol VARCHAR(50) NOT NULL
);

CREATE TABLE user_roles (
    id_usuario INT NOT NULL,
    id_rol INT NOT NULL,
    PRIMARY KEY (id_usuario, id_rol),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_rol) REFERENCES roles(id) ON DELETE CASCADE
);



CREATE DATABASE hoteles;

CREATE TABLE hoteles (
    id_hotel SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    ubicacion VARCHAR(100) NOT NULL,
    estrellas SMALLINT NOT NULL,
    categoria TEXT CHECK (categoria IN ('ECONOMICO', 'PREMIUM', 'LUJO')) NOT NULL,
    total_habitaciones INT NOT NULL -- Total de habitaciones del hotel
);

CREATE TABLE habitaciones (
    id_habitacion SERIAL PRIMARY KEY,
    id_hotel INT NOT NULL,
    descripcion VARCHAR(100) NOT NULL, -- Descripción de la habitación (opcional)
    precio_por_noche NUMERIC(10, 2) NOT NULL,
    cantidad INT NOT NULL, -- Número de habitaciones disponibles de este tipo
    FOREIGN KEY (id_hotel) REFERENCES hoteles(id_hotel) ON DELETE CASCADE
);

CREATE TABLE ocupaciones (
    id_ocupacion SERIAL PRIMARY KEY,
    id_habitacion INT NOT NULL,
    id_reserva_externa INT DEFAULT NULL, -- Referencia al microservicio de reservas
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    cantidad INT NOT NULL, -- Número de habitaciones ocupadas de este grupo
    estado TEXT CHECK (estado IN ('BLOQUEADO', 'RESERVADO', 'FINALIZADO', 'CANCELADO')) DEFAULT 'BLOQUEADO',
    ultima_modificacion TIMESTAMP DEFAULT NOW(),
    FOREIGN KEY (id_habitacion) REFERENCES habitaciones(id_habitacion) ON DELETE CASCADE
);

ADD COLUMN block_expiration TIMESTAMP DEFAULT NULL; -- Fecha y hora en que caduca el bloqueo



CREATE DATABASE vuelos;
\c vuelos;

CREATE TABLE vuelos (
    id_vuelo SERIAL PRIMARY KEY,
    company VARCHAR(100) NOT NULL,
    origen VARCHAR(100) NOT NULL,
    destino VARCHAR(100) NOT NULL,
    fecha_hora_salida TIMESTAMP NOT NULL,
    fecha_hora_llegada TIMESTAMP NOT NULL,
    precio NUMERIC(10, 2) NOT NULL,
    asientos_disponibles INT NOT NULL
);
//MODIFICADO PARA BLOQUEAR MIENTRAS USUARIO RESERVA: ALTER TABLE vuelos
ADD COLUMN blocked_seats INT DEFAULT 0, -- Número de asientos bloqueados
ADD COLUMN block_expiration TIMESTAMP DEFAULT NULL; -- Fecha y hora en que caduca el bloqueo

CREATE TABLE itinerarios (
    id_itinerario SERIAL PRIMARY KEY,
    id_vuelo INT NOT NULL,
    origen VARCHAR(100) NOT NULL,
    destino VARCHAR(100) NOT NULL,
    fecha_hora_salida TIMESTAMP NOT NULL,
    fecha_hora_llegada TIMESTAMP NOT NULL,
    FOREIGN KEY (id_vuelo) REFERENCES vuelos(id_vuelo) ON DELETE CASCADE
);


CREATE DATABASE reservas;
\c reservas;

CREATE TABLE reservas (
    id_reserva SERIAL PRIMARY KEY,
    id_usuario INT NOT NULL, -- Referencia lógica, no clave foránea
    tipo_reserva TEXT CHECK (tipo_reserva IN ('HOTEL', 'VUELO')) NOT NULL,
    fecha_reserva TIMESTAMP NOT NULL,
    estado TEXT CHECK (estado IN ('CONFIRMADA', 'CANCELADA')) NOT NULL
);

CREATE TABLE detalle_reserva (
    id_detalle SERIAL PRIMARY KEY,
    id_reserva INT NOT NULL,
    id_habitacion INT, -- Referencia lógica, no clave foránea
    id_vuelo INT, -- Referencia lógica, no clave foránea
    cantidad INT NOT NULL,
    subtotal NUMERIC(10, 2) NOT NULL
);
