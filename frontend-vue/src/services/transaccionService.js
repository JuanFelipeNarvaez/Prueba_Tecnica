import api from './api'

export const transaccionService = {
  // Consignar dinero
  consignar(consignacionData) {
    return api.post('/transacciones/consignar', consignacionData)
  },

  // Retirar dinero
  retirar(retiroData) {
    return api.post('/transacciones/retirar', retiroData)
  },

  // Transferir dinero
  transferir(transferenciaData) {
    return api.post('/transacciones/transferir', transferenciaData)
  },
}
