import api from './api'

export const clienteService = {
  // Crear cliente
  crearCliente(clienteData) {
    return api.post('/clientes', clienteData)
  },

  // Listar todos los clientes
  listarClientes() {
    return api.get('/clientes')
  },

  // Obtener cliente por ID
  obtenerCliente(id) {
    return api.get(`/clientes/${id}`)
  },

  // Actualizar cliente
  actualizarCliente(id, clienteData) {
    return api.put(`/clientes/${id}`, clienteData)
  },

  // Eliminar cliente
  eliminarCliente(id) {
    return api.delete(`/clientes/${id}`)
  },
}
