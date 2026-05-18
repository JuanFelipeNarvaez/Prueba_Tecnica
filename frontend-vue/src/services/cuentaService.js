import api from './api'

export const cuentaService = {
  // Crear cuenta
  crearCuenta(cuentaData) {
    return api.post('/cuentas', cuentaData)
  },

  // Listar todas las cuentas
  listarCuentas() {
    return api.get('/cuentas')
  },

  // Obtener cuenta por ID
  obtenerCuenta(id) {
    return api.get(`/cuentas/${id}`)
  },

  // Actualizar estado de la cuenta
  actualizarEstadoCuenta(id, estado) {
    return api.patch(`/cuentas/${id}/estado`, null, {
      params: { estado },
    })
  },

  // Cancelar cuenta
  cancelarCuenta(id) {
    return api.patch(`/cuentas/${id}/cancelar`)
  },
}
