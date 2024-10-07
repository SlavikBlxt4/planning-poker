// Importa Express
const express = require('express');

// Crea una aplicación de Express
const app = express();

// Middleware para parsear JSON en el cuerpo de las peticiones
app.use(express.json());

// Define un puerto para el servidor
const port = 3000;

// Ruta para manejar peticiones GET
app.get('/', (req, res) => {
    res.send('¡Hola, mundo desde Express!');
});

// Ruta para manejar peticiones POST
app.post('/data', (req, res) => {
    const data = req.body;
    console.log('Datos recibidos:', data);
    res.send('Datos recibidos');
});

// Ruta para manejar peticiones PUT
app.put('/update', (req, res) => {
    const updatedData = req.body;
    console.log('Datos actualizados:', updatedData);
    res.send('Datos actualizados');
});

// Ruta para manejar peticiones DELETE
app.delete('/delete', (req, res) => {
    const id = req.body.id;
    console.log('Eliminando recurso con ID:', id);
    res.send(`Recurso con ID: ${id} eliminado`);
});

// Inicia el servidor
app.listen(port, () => {
    console.log(`Servidor escuchando en http://localhost:${port}`);
});
