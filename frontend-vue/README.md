# Bank API - Frontend Vue 3

Frontend moderno y responsivo para la API de gestión de clientes de Bank API, construido con Vue 3.

## 🚀 Tecnologías

- **Vue 3** - Librería UI progresiva
- **TypeScript** - Tipado estático
- **Vite** - Build tool ultrarrápido
- **Tailwind CSS** - Estilos utilities
- **Lucide Vue Next** - Iconos SVG
- **Axios** - Cliente HTTP

## 📋 Requisitos

- Node.js 18+
- npm o yarn
- Backend ejecutándose en `http://localhost:8080`

## 🔧 Instalación

```bash
# Instalar dependencias
npm install

# Crear archivo de variables de entorno
cp .env.example .env
```

## 🏃 Desarrollo

```bash
# Iniciar servidor de desarrollo
npm run dev
```

El frontend se abrirá automáticamente en `http://localhost:5173`

## 📦 Build para producción

```bash
# Compilar para producción
npm run build

# Vista previa del build
npm run preview
```

## 📁 Estructura del Proyecto

```
src/
├── components/          # Componentes Vue reutilizables
├── composables/         # Composables (lógica reutilizable)
├── services/           # Servicios API
├── types/              # Tipos TypeScript
├── App.vue             # Componente raíz
├── main.ts             # Punto de entrada
├── style.css           # Estilos globales
└── vite-env.d.ts       # Tipos de Vite
```

## 🎨 Características

- ✅ Listar clientes con búsqueda en tiempo real
- ✅ Crear nuevos clientes
- ✅ Editar clientes existentes
- ✅ Eliminar clientes con confirmación
- ✅ Validación de formularios completa
- ✅ Notificaciones toast (éxito, error, info)
- ✅ Manejo robusto de errores
- ✅ Interfaz responsiva y moderna
- ✅ Iconos con Lucide Vue
- ✅ Composables para lógica reutilizable

## 🔌 Configuración de la API

Por defecto, el frontend se conecta a `http://localhost:8080`. Para cambiar la URL:

### Opción 1: Variable de entorno
Edita el archivo `.env`:
```env
VITE_API_BASE_URL=http://tu-backend.com
VITE_API_TIMEOUT=5000
```

### Opción 2: Línea de comandos
```bash
VITE_API_BASE_URL=http://tu-backend.com npm run dev
```

## 📝 Endpoints de la API Integrados

- `GET /api/clientes` - Obtener todos los clientes
- `GET /api/clientes/{id}` - Obtener cliente por ID
- `POST /api/clientes` - Crear nuevo cliente
- `PUT /api/clientes/{id}` - Actualizar cliente
- `DELETE /api/clientes/{id}` - Eliminar cliente

## 🧩 Composables

### `useClientes()`
Gestiona la obtención y caché de la lista de clientes.

```typescript
const { clientes, isLoading, error, fetch, refetch } = useClientes()
```

### `useCreateCliente()`
Maneja la creación de nuevos clientes.

```typescript
const { isLoading, error, mutate } = useCreateCliente()
const result = await mutate(clienteData)
```

### `useUpdateCliente()`
Maneja la actualización de clientes.

```typescript
const { isLoading, error, mutate } = useUpdateCliente()
const result = await mutate(id, clienteData)
```

### `useDeleteCliente()`
Maneja la eliminación de clientes.

```typescript
const { isLoading, error, mutate } = useDeleteCliente()
const success = await mutate(id)
```

## 🐛 Solución de problemas

### CORS errors
Si obtienes errores de CORS, asegúrate de que tu backend tiene CORS habilitado:

```java
@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                    .allowedOrigins("http://localhost:5173")
                    .allowedMethods("GET", "POST", "PUT", "DELETE")
                    .allowedHeaders("*");
            }
        };
    }
}
```

### Backend no responde
- Verifica que el backend esté corriendo en el puerto 8080
- Confirma la URL en el archivo `.env`
- Revisa la consola del navegador (F12) para más detalles
- Comprueba que la API está retornando JSON válido

### Servidor de desarrollo no inicia
```bash
# Limpia node_modules y reinstala
rm -rf node_modules
npm install

# Intenta nuevamente
npm run dev
```

## 📄 Licencia

MIT

## 🤝 Contribución

Las contribuciones son bienvenidas. Por favor, abre un issue o envía un pull request.

## ✨ Cambios desde la versión React

Este proyecto es la versión Vue 3 del frontend original en React. Mantiene las mismas características pero aprovecha:

- **Composition API** para una lógica más limpia
- **Componentes .vue** con template, script y style en un archivo
- **Reactivity** nativa de Vue
- **Mejor rendimiento** en aplicaciones medianas a grandes
- **Mejor experiencia de desarrollo** con Vue DevTools
