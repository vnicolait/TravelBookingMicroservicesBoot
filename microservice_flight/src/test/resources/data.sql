-- Crear la tabla vuelos
/*CREATE TABLE vuelos (
    id_vuelo INT PRIMARY KEY AUTO_INCREMENT,  -- Asigna un valor único automáticamente al id
    company VARCHAR(255),                      -- Nombre de la compañía
    origen VARCHAR(255),                       -- Ciudad de origen
    destino VARCHAR(255),                      -- Ciudad de destino
    fecha_hora_salida TIMESTAMP,               -- Fecha y hora de salida
    fecha_hora_llegada TIMESTAMP,              -- Fecha y hora de llegada
    precio DECIMAL(10, 2),                     -- Precio del vuelo
    asientos_disponibles INT,                  -- Asientos disponibles
    blocked_seats INT,                         -- Asientos bloqueados
    block_expiration TIMESTAMP                 -- Expiración del bloqueo de asientos
);

-- Crear la tabla itinerarios
CREATE TABLE itinerarios (
    id_itinerario INT AUTO_INCREMENT PRIMARY KEY,  -- ID autoincrementable
    origen VARCHAR(255),                          -- Ciudad de origen
    destino VARCHAR(255),                         -- Ciudad de destino
    fecha_hora_salida TIMESTAMP,                  -- Fecha y hora de salida
    fecha_hora_llegada TIMESTAMP,                 -- Fecha y hora de llegada
    id_vuelo INT,                                 -- Referencia al vuelo
    FOREIGN KEY (id_vuelo) REFERENCES vuelos(id_vuelo)  -- Relación con la tabla vuelos
);
*/
-- Inserción de datos en la tabla vuelos
INSERT INTO vuelos (id_vuelo, company, origen, destino, fecha_hora_salida, fecha_hora_llegada, precio, asientos_disponibles, blocked_seats, block_expiration)
VALUES (1, 'Iberia', 'Madrid', 'Barcelona', TIMESTAMP '2025-02-01 08:00:00', TIMESTAMP '2025-02-01 09:30:00', 120.50, 50, 5, TIMESTAMP '2025-02-01 07:50:00');

INSERT INTO vuelos (id_vuelo, company, origen, destino, fecha_hora_salida, fecha_hora_llegada, precio, asientos_disponibles, blocked_seats, block_expiration)
VALUES (2, 'Vueling', 'Madrid', 'Sevilla', TIMESTAMP '2025-02-02 10:00:00', TIMESTAMP '2025-02-02 11:30:00', 95.75, 60, 0, NULL);

INSERT INTO vuelos (id_vuelo, company, origen, destino, fecha_hora_salida, fecha_hora_llegada, precio, asientos_disponibles, blocked_seats, block_expiration) 
VALUES (3, 'Air Europa', 'Barcelona', 'Valencia', TIMESTAMP '2025-02-03 12:00:00', TIMESTAMP '2025-02-03 13:00:00', 80.00, 40, 10, TIMESTAMP '2025-02-03 11:50:00');
-- Inserción de datos en la tabla itinerarios
INSERT INTO itinerarios (origen, destino, fecha_hora_salida, fecha_hora_llegada, id_vuelo)
VALUES ('Madrid', 'Barcelona', TIMESTAMP '2025-02-01 08:00:00', TIMESTAMP '2025-02-01 09:30:00', 1);

INSERT INTO itinerarios (origen, destino, fecha_hora_salida, fecha_hora_llegada, id_vuelo)
VALUES ('Madrid', 'Sevilla', TIMESTAMP '2025-02-02 10:00:00', TIMESTAMP '2025-02-02 11:30:00', 2);

INSERT INTO itinerarios (origen, destino, fecha_hora_salida, fecha_hora_llegada, id_vuelo)
VALUES ('Barcelona', 'Valencia', TIMESTAMP '2025-02-03 12:00:00', TIMESTAMP '2025-02-03 13:00:00', 3);
