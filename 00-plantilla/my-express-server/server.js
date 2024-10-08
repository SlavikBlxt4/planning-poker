// Importa Express y pg para PostgreSQL
const express = require('express');
const { Pool } = require('pg');

// Crea una aplicación de Express
const app = express();

// Middleware para parsear JSON en el cuerpo de las peticiones
app.use(express.json());

const pool = new Pool({
    user: 'postgres',
    host: 'database-1.cdi8weoounkz.us-east-1.rds.amazonaws.com',
    database: 'postgres',
    password: 'postgres',
    port: 5432,
    ssl: {
        rejectUnauthorized: false, // Si es necesario, dependiendo de tu configuración SSL
    },
});


// Define un puerto para el servidor
const port = 3000;
pool.connect();
// Ruta para manejar peticiones GET (Ejemplo: Obtener todos los datos de una tabla)
app.get('/usuarios/login', async (req, res) => {
    const { ACTION, EMAIL, PASSWORD } = req.query;

    if (ACTION !== 'USER.LOGIN') {
        return res.status(400).send('Acción no válida');
    }

    try {
        if (!EMAIL || !PASSWORD) {
            return res.status(400).send('Faltan parámetros: EMAIL y PASSWORD');
        }

        const result = await pool.query('SELECT * FROM usuarios WHERE email = $1 AND contraseña = $2', [EMAIL, PASSWORD]);

        if (result.rows.length > 0) {
            res.json(result.rows);  // Devolver una lista de usuarios
        } else {
            res.status(401).send('Usuario no encontrado o credenciales incorrectas');
        }
    } catch (err) {
        console.error(err);
        res.status(500).send('Error en el servidor');
    }
});




// GET PARA PELICULAS
// Ruta para obtener todas las películas
app.get('/peliculas', async (req, res) => {
    try {
        const result = await pool.query('SELECT * FROM peliculas');
        res.json(result.rows);
    } catch (err) {
        console.error(err);
        res.status(500).send('Error al obtener las películas');
    }
});


// PUT PARA PELICULAS
// Ruta para manejar peticiones PUT (Actualizar una película por ID)
app.put('/peliculas/:id', async (req, res) => {
    const id = req.params.id;
    const { titulo, descripcion, director, anyo, url_imagen } = req.body;

    try {
        const result = await pool.query(
            'UPDATE peliculas SET titulo = $1, descripcion = $2, director = $3, anyo = $4, url_imagen = $5 WHERE id = $6 RETURNING *',
            [titulo, descripcion, director, anyo, url_imagen, id]
        );
        
        if (result.rowCount === 0) {
            res.status(404).send('Película no encontrada');
        } else {
            res.json(result.rows[0]); // Retorna la película actualizada
        }
    } catch (err) {
        console.error(err);
        res.status(500).send('Error al actualizar la película');
    }
});





// Ruta para manejar peticiones POST (Ejemplo: Insertar datos en una tabla)
app.post('/data', async (req, res) => {
    const { campo1, campo2 } = req.body; // Ajusta los campos según tu tabla
    try {
        const result = await pool.query(
            'INSERT INTO tu_tabla (campo1, campo2) VALUES ($1, $2) RETURNING *',
            [campo1, campo2]
        );
        res.json(result.rows[0]);
    } catch (err) {
        console.error(err);
        res.status(500).send('Error al insertar datos');
    }
});

// Ruta para manejar peticiones PUT (Ejemplo: Actualizar datos en una tabla)
app.put('/data/:id', async (req, res) => {
    const id = req.params.id;
    const { campo1, campo2 } = req.body;
    try {
        const result = await pool.query(
            'UPDATE tu_tabla SET campo1 = $1, campo2 = $2 WHERE id = $3 RETURNING *',
            [campo1, campo2, id]
        );
        if (result.rowCount === 0) {
            res.status(404).send('Recurso no encontrado');
        } else {
            res.json(result.rows[0]);
        }
    } catch (err) {
        console.error(err);
        res.status(500).send('Error al actualizar datos');
    }
});

// Ruta para manejar peticiones DELETE (Ejemplo: Eliminar un recurso por ID)
app.delete('/data/:id', async (req, res) => {
    const id = req.params.id;
    try {
        const result = await pool.query('DELETE FROM tu_tabla WHERE id = $1 RETURNING *', [id]);
        if (result.rowCount === 0) {
            res.status(404).send('Recurso no encontrado');
        } else {
            res.send(`Recurso con ID: ${id} eliminado`);
        }
    } catch (err) {
        console.error(err);
        res.status(500).send('Error al eliminar el recurso');
    }
});

// Inicia el servidor
app.listen(port, () => {
    console.log(`Servidor escuchando en http://localhost:${port}`);
});
