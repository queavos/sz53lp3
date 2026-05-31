INSERT INTO public.categoria (nombre) VALUES
('Cuadernos y agendas'),
('Escritura'),
('Papeles y blocs'),
('Organización'),
('Arte y dibujo');


INSERT INTO public.producto (estado, nombre, precio, categoria_id) VALUES
-- Categoría 1: Cuadernos y agendas
(true, 'Cuaderno universitario rayado', 25000, 1),
(true, 'Agenda escolar 2026', 30000, 1),
(true, 'Cuaderno espiral cuadriculado', 22000, 1),

-- Categoría 2: Escritura
(true, 'Bolígrafo azul Bic', 5000, 2),
(true, 'Lápiz grafito HB', 3000, 2),
(true, 'Resaltador amarillo', 7000, 2),

-- Categoría 3: Papeles y blocs
(true, 'Resma A4 500 hojas', 45000, 3),
(true, 'Bloc de notas adhesivas', 12000, 3),
(true, 'Hojas de cartulina blanca', 15000, 3),

-- Categoría 4: Organización
(true, 'Carpeta anillada A4', 18000, 4),
(true, 'Archivador de palanca', 25000, 4),
(true, 'Separadores plásticos multicolor', 10000, 4),

-- Categoría 5: Arte y dibujo
(true, 'Caja de crayones 12 colores', 20000, 5),
(true, 'Set de acuarelas escolares', 28000, 5),
(true, 'Cartulina de colores surtidos', 15000, 5);

