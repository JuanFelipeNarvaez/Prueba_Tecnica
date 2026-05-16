# Bank Management System - Frontend Vue.js

Frontend moderno desarrollado con Vue 3 y Vite para el sistema de gestión bancaria.

## 🚀 Características

- ✅ **Gestión de Clientes** - CRUD completo de clientes
- ✅ **Gestión de Cuentas** - Crear, editar, cambiar estado y cancelar cuentas
- ✅ **Transacciones** - Consignaciones, retiros y transferencias
- ✅ **Interfaz Responsiva** - Diseño adaptable con Bootstrap 5
- ✅ **Validación de Formularios** - Validación en tiempo real
- ✅ **Manejo de Errores** - Mensajes informativos para el usuario

## 📋 Requisitos Previos

- Node.js 16+ 
- npm o yarn
- Backend de Bank API corriendo en `http://localhost:8080`

## 🛠️ Instalación

### 1. Instalar dependencias

```bash
npm install
# o con yarn
yarn install
```

### 2. Configurar el Backend

Asegúrate de que el backend está corriendo en `http://localhost:8080`. Si necesitas cambiar la URL base de la API, edita el archivo `src/services/api.js`:

```javascript
const API_BASE_URL = 'http://localhost:8080/api'
```

## 🚀 Ejecutar el Proyecto

### Modo Desarrollo

```bash
npm run dev
```

La aplicación estará disponible en: `http://localhost:5173`

### Build para Producción

```bash
npm run build
```

Los archivos compilados estarán en la carpeta `dist/`.

### Preview de Producción

```bash
npm run preview
```

## 📁 Estructura del Proyecto

```
frontend-vue/
├── src/
│   ├── components/
│   │   ├── ClienteForm.vue          # Formulario para crear/editar clientes
│   │   ├── ClienteList.vue          # Lista de clientes con CRUD
│   │   ├── CuentaForm.vue           # Formulario para crear/editar cuentas
│   │   ├── CuentaList.vue           # Lista de cuentas con acciones
│   │   ├── TransaccionForm.vue      # Formulario para transacciones
│   │   └── TransaccionList.vue      # Historial de transacciones
│   ├── views/
│   │   ├── HomeView.vue             # Página de inicio
│   │   ├── ClientesView.vue         # Vista de gestión de clientes
│   │   ├── CuentasView.vue          # Vista de gestión de cuentas
│   │   └── TransaccionesView.vue    # Vista de transacciones
│   ├── services/
│   │   ├── api.js                   # Configuración de Axios
│   │   ├── clienteService.js        # Servicios para clientes
│   │   ├── cuentaService.js         # Servicios para cuentas
│   │   └── transaccionService.js    # Servicios para transacciones
│   ├── router/
│   │   └── index.js                 # Configuración de Vue Router
│   ├── assets/
│   │   └── style.css                # Estilos personalizados
│   ├── App.vue                      # Componente raíz con navbar
│   └── main.js                      # Punto de entrada
├── public/
├── index.html
├── vite.config.js
├── package.json
└── README.md
```

## 🔌 Integración con Backend

El frontend consume los siguientes endpoints del backend:

### Clientes
- `GET /api/clientes` - Listar clientes
- `POST /api/clientes` - Crear cliente
- `GET /api/clientes/{id}` - Obtener cliente
- `PUT /api/clientes/{id}` - Actualizar cliente
- `DELETE /api/clientes/{id}` - Eliminar cliente

### Cuentas
- `GET /api/cuentas` - Listar cuentas
- `POST /api/cuentas` - Crear cuenta
- `GET /api/cuentas/{id}` - Obtener cuenta
- `PATCH /api/cuentas/{id}/estado` - Cambiar estado
- `PATCH /api/cuentas/{id}/cancelar` - Cancelar cuenta

### Transacciones
- `POST /api/transacciones/consignar` - Consignar dinero
- `POST /api/transacciones/retirar` - Retirar dinero
- `POST /api/transacciones/transferir` - Transferir dinero

## 🎨 Tecnologías Utilizadas

- **Vue 3** - Framework progresivo
- **Vite** - Herramienta de build ultrarrápida
- **Vue Router** - Enrutamiento
- **Axios** - Cliente HTTP
- **Bootstrap 5** - Framework CSS

## 📝 Variables de Entorno

Si es necesario configurar variables de entorno, crea un archivo `.env` en la raíz del proyecto:

```
VITE_API_BASE_URL=http://localhost:8080/api
```

Luego actualiza `src/services/api.js`:

```javascript
const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api'
```

## 🤝 Flujo de Uso

### 1. Crear un Cliente
- Ve a la sección "Clientes"
- Completa el formulario con los datos del cliente
- Haz clic en "Crear Cliente"
- El cliente aparecerá en la lista

### 2. Crear una Cuenta
- Ve a la sección "Cuentas"
- Selecciona el tipo de cuenta (Ahorros, Corriente, Nómina)
- Establece el saldo inicial
- Selecciona el cliente propietario
- Haz clic en "Crear Cuenta"

### 3. Realizar Transacciones
- Ve a la sección "Transacciones"
- Selecciona el tipo de transacción:
  - **Consignar**: Ingresa número de cuenta y monto
  - **Retirar**: Ingresa número de cuenta y monto
  - **Transferir**: Ingresa cuenta origen, destino y monto
- Haz clic en el botón correspondiente

## 🐛 Solución de Problemas

### Error: "Cannot reach backend"
- Verifica que el backend está ejecutándose en `http://localhost:8080`
- Comprueba que no hay conflictos de CORS en el backend

### Error: "CORS error"
- Asegúrate de que el backend tiene CORS habilitado
- En Spring Boot, añade la configuración CORS en tu controlador o configuración global

### Puerto 5173 ya está en uso
```bash
npm run dev -- --port 3000
```

## 📞 Soporte

Para reportar problemas o sugerencias, por favor contacta al equipo de desarrollo.

## 📄 Licencia

Este proyecto está bajo la licencia MIT.
