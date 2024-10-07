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
    try {
        const result = await pool.query('SELECT * FROM usuarios'); // Cambia 'tu_tabla' al nombre de tu tabla
        res.json(result.rows);
    } catch (err) {
        console.error(err);
        res.status(500).send('Error al obtener los datos');
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
