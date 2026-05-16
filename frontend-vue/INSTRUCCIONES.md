# 🏦 Sistema de Gestión Bancaria - Frontend Vue.js

## 📌 Descripción

Este es el frontend para el sistema de gestión bancaria desarrollado con **Vue.js 3**, **Vite** y **Bootstrap 5**. Proporciona una interfaz moderna y responsiva para gestionar clientes, cuentas bancarias y realizar transacciones financieras.

---

## ⚙️ Instalación y Configuración

### 1️⃣ Instalar Dependencias

Navega a la carpeta `frontend-vue` y ejecuta:

```bash
cd frontend-vue
npm install
```

Esto instalará todas las dependencias necesarias incluyendo:
- Vue 3
- Vue Router
- Axios
- Bootstrap 5

### 2️⃣ Verificar que el Backend está Corriendo

Asegúrate de que el backend Spring Boot esté ejecutándose en:
```
http://localhost:8080
```

Si está corriendo en otro puerto, actualiza la URL en el archivo:
```
src/services/api.js
```

---

## 🚀 Ejecutar el Frontend

### Iniciar el Servidor de Desarrollo

```bash
npm run dev
```

La aplicación abrirá automáticamente en:
```
http://localhost:5173
```

### Detener el Servidor

Presiona `Ctrl + C` en la terminal.

---

## 📱 Interfaz de Usuario

### 🏠 Página de Inicio
Dashboard con acceso rápido a las tres secciones principales:
- Gestión de Clientes
- Gestión de Cuentas
- Gestión de Transacciones

### 👥 Gestión de Clientes

**Panel Izquierdo - Formulario:**
- Crear nuevos clientes
- Editar clientes existentes
- Campos requeridos: Nombre, Apellido, Email, Número ID

**Panel Derecho - Lista:**
- Ver todos los clientes registrados
- Editar cliente (click en el botón ✏️)
- Eliminar cliente (click en el botón 🗑️)
- Actualizar lista (click en 🔄)

### 💳 Gestión de Cuentas

**Panel Izquierdo - Formulario:**
- Crear nuevas cuentas bancarias
- Tipos de cuenta: Ahorros, Corriente, Nómina
- Establece el saldo inicial
- Asigna la cuenta a un cliente
- Opción de exención de GMF

**Panel Derecho - Lista:**
- Ver todas las cuentas con su estado y saldo
- Cambiar estado de la cuenta (Activa/Inactiva)
- Cancelar cuenta (cambia estado a CANCELADA)
- Estados mostrados con colores:
  - 🟢 ACTIVA
  - 🟡 INACTIVA
  - 🔴 CANCELADA

### 💰 Gestión de Transacciones

**Tres tipos de transacciones:**

1. **💰 Consignar (Depósito)**
   - Ingresa el número de cuenta
   - Ingresa el monto a consignar
   - Click en "Consignar Dinero"

2. **🏦 Retirar (Extracción)**
   - Ingresa el número de cuenta
   - Ingresa el monto a retirar
   - Click en "Retirar Dinero"

3. **💸 Transferir (Entre Cuentas)**
   - Ingresa la cuenta de origen
   - Ingresa la cuenta de destino
   - Ingresa el monto
   - Click en "Transferir Dinero"

---

## 🛠️ Estructura del Proyecto

```
frontend-vue/
├── src/
│   ├── components/           # Componentes reutilizables
│   │   ├── ClienteForm.vue   # Formulario de clientes
│   │   ├── ClienteList.vue   # Lista de clientes
│   │   ├── CuentaForm.vue    # Formulario de cuentas
│   │   ├── CuentaList.vue    # Lista de cuentas
│   │   ├── TransaccionForm.vue  # Formulario de transacciones
│   │   └── TransaccionList.vue  # Historial de transacciones
│   ├── views/                # Vistas de páginas completas
│   │   ├── HomeView.vue
│   │   ├── ClientesView.vue
│   │   ├── CuentasView.vue
│   │   └── TransaccionesView.vue
│   ├── services/             # Servicios API
│   │   ├── api.js            # Configuración Axios
│   │   ├── clienteService.js
│   │   ├── cuentaService.js
│   │   └── transaccionService.js
│   ├── router/               # Vue Router
│   ├── assets/               # Estilos CSS
│   ├── App.vue              # Componente principal
│   └── main.js              # Punto de entrada
├── package.json
├── vite.config.js
└── README.md
```

---

## 🔗 Endpoints del Backend Utilizados

### Clientes (`/api/clientes`)
- `POST` - Crear cliente
- `GET` - Listar todos
- `GET /{id}` - Obtener uno
- `PUT /{id}` - Actualizar
- `DELETE /{id}` - Eliminar

### Cuentas (`/api/cuentas`)
- `POST` - Crear cuenta
- `GET` - Listar todas
- `GET /{id}` - Obtener una
- `PATCH /{id}/estado` - Cambiar estado
- `PATCH /{id}/cancelar` - Cancelar

### Transacciones (`/api/transacciones`)
- `POST /consignar` - Consignar dinero
- `POST /retirar` - Retirar dinero
- `POST /transferir` - Transferir entre cuentas

---

## ⚡ Scripts Disponibles

```bash
# Desarrollo
npm run dev          # Inicia servidor de desarrollo

# Producción
npm run build        # Compila para producción
npm run preview      # Vista previa de build
```

---

## 🎯 Validaciones Implementadas

✅ **Clientes:**
- Nombre y apellido mínimo 2 caracteres
- Email válido
- Tipo de identificación requerido
- Número de identificación único

✅ **Cuentas:**
- Saldo no puede ser negativo
- Tipo de cuenta seleccionado
- Cliente asociado requerido

✅ **Transacciones:**
- Monto debe ser mayor a 0
- Número de cuenta requerido
- Validación de saldo disponible (backend)
- Cuentas deben existir

---

## 🐛 Troubleshooting

### ❌ "Cannot connect to backend"
**Solución:** Asegúrate que el backend corre en `http://localhost:8080`

```bash
# En terminal del backend
cd backend/bankapi
./mvnw spring-boot:run
```

### ❌ "Port 5173 already in use"
**Solución:** Usa otro puerto:
```bash
npm run dev -- --port 3000
```

### ❌ "Module not found"
**Solución:** Reinstala dependencias:
```bash
rm -rf node_modules package-lock.json
npm install
```

### ❌ Error de CORS
**Solución:** Verifica que el backend tiene CORS habilitado. Añade esto en tu backend:

```java
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
            .allowedOrigins("http://localhost:5173")
            .allowedMethods("*")
            .allowCredentials(true);
    }
}
```

---

## 📊 Formato de Datos

### Cliente Response
```json
{
  "id": 1,
  "tipoIdentificacion": "CC",
  "numeroIdentificacion": "123456789",
  "primerNombre": "Juan",
  "primerApellido": "Pérez",
  "correoElectronico": "juan@example.com",
  "fechaNacimiento": "1990-01-15",
  "fechaCreacion": "2024-01-15T10:30:00"
}
```

### Cuenta Response
```json
{
  "id": 1,
  "tipoCuenta": "AHORROS",
  "numeroCuenta": "1234567890",
  "estado": "ACTIVA",
  "saldo": 5000.00,
  "exentaGmf": false,
  "clienteId": 1,
  "fechaCreacion": "2024-01-15T10:30:00"
}
```

### Transacción Response
```json
{
  "id": 1,
  "tipoTransaccion": "CONSIGNACION",
  "monto": 1000.00,
  "fecha": "2024-01-15T10:35:00",
  "cuentaOrigen": "1234567890",
  "cuentaDestino": null
}
```

---

## 🎨 Personalización

### Cambiar colores
Edita `src/assets/style.css`:
```css
:root {
  --primary-color: #0d6efd;
  --success-color: #198754;
  --danger-color: #dc3545;
}
```

### Cambiar título
Edita `index.html`:
```html
<title>Tu Título Personalizado</title>
```

---

## 📞 Notas Importantes

- ✅ El frontend está completamente integrado con la lógica de negocio del backend
- ✅ Todos los formularios incluyen validación
- ✅ Los mensajes de error provienen del backend
- ✅ La interfaz es responsiva (funciona en móvil, tablet y desktop)
- ✅ Los datos se almacenan en la base de datos PostgreSQL del backend

---

## 🚀 Próximos Pasos

1. Instala las dependencias: `npm install`
2. Asegúrate que el backend corre: `http://localhost:8080`
3. Inicia el frontend: `npm run dev`
4. Abre `http://localhost:5173` en tu navegador
5. ¡Comienza a gestionar tus clientes y cuentas!

---

¡Disfruta usando el Sistema de Gestión Bancaria! 🎉
