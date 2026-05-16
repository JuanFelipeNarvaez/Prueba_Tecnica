<template>
  <div class="card">
    <div class="card-header d-flex justify-content-between align-items-center">
      <h5>Historial de Transacciones</h5>
      <button class="btn btn-sm btn-success" @click="loadTransacciones">
        <i class="bi bi-arrow-clockwise"></i> Actualizar
      </button>
    </div>
    <div class="card-body">
      <!-- Filtros -->
      <div class="row mb-3">
        <div class="col-md-4">
          <input
            v-model="filtros.numeroCuenta"
            type="text"
            class="form-control"
            placeholder="Filtrar por número de cuenta"
          />
        </div>
        <div class="col-md-4">
          <select v-model="filtros.tipo" class="form-select">
            <option value="">Todos los tipos</option>
            <option value="CONSIGNACION">Consignación</option>
            <option value="RETIRO">Retiro</option>
            <option value="TRANSFERENCIA">Transferencia</option>
          </select>
        </div>
      </div>

      <!-- Mensajes de estado -->
      <AlertMessage
        v-if="successMessage"
        type="success"
        :message="successMessage"
        @close="successMessage = ''"
      />
      <AlertMessage
        v-if="errorMessage"
        type="danger"
        :message="errorMessage"
        @close="errorMessage = ''"
      />

      <!-- Tabla de transacciones -->
      <div v-if="loading" class="text-center">
        <div class="spinner-border" role="status">
          <span class="visually-hidden">Cargando...</span>
        </div>
      </div>

      <div v-else-if="transaccionesFiltradas.length === 0" class="alert alert-info">
        No hay transacciones registradas
      </div>

      <div v-else class="table-responsive">
        <table class="table table-hover table-sm">
          <thead>
            <tr>
              <th>ID</th>
              <th>Tipo</th>
              <th>Monto</th>
              <th>Fecha</th>
              <th>Cuenta Origen</th>
              <th>Cuenta Destino</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="transaccion in transaccionesFiltradas" :key="transaccion.id">
              <td>{{ transaccion.id }}</td>
              <td>
                <span :class="getTipoTransaccionBadge(transaccion.tipoTransaccion)">
                  {{ transaccion.tipoTransaccion }}
                </span>
              </td>
              <td class="text-success fw-bold">{{ formatCurrency(transaccion.monto) }}</td>
              <td>{{ formatDate(transaccion.fecha) }}</td>
              <td>{{ transaccion.cuentaOrigen || '-' }}</td>
              <td>{{ transaccion.cuentaDestino || '-' }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
import { transaccionService } from '@/services/transaccionService'
import { getErrorMessage } from '@/services/errorHelper'
import AlertMessage from '@/components/AlertMessage.vue'

export default {
  components: { AlertMessage },
  name: 'TransaccionList',
  data() {
    return {
      transacciones: [],
      loading: false,
      successMessage: '',
      errorMessage: '',
      filtros: {
        numeroCuenta: '',
        tipo: '',
      },
    }
  },
  computed: {
    transaccionesFiltradas() {
      return this.transacciones.filter((t) => {
        const coincideCuenta =
          !this.filtros.numeroCuenta ||
          (t.cuentaOrigen && t.cuentaOrigen.includes(this.filtros.numeroCuenta)) ||
          (t.cuentaDestino && t.cuentaDestino.includes(this.filtros.numeroCuenta))

        const coincideTipo = !this.filtros.tipo || t.tipoTransaccion === this.filtros.tipo

        return coincideCuenta && coincideTipo
      })
    },
  },
  mounted() {
    this.loadTransacciones()
  },
  methods: {
    async loadTransacciones() {
      try {
        this.loading = true
        this.errorMessage = ''
        // Como el backend no tiene endpoint para listar transacciones,
        // mostraremos un mensaje informativo
        // En producción, se conectaría a un endpoint real
        this.transacciones = []
      } catch (error) {
        this.errorMessage = getErrorMessage(error) || 'Error al cargar las transacciones'
      } finally {
        this.loading = false
      }
    },
    getTipoTransaccionBadge(tipo) {
      const classes = 'badge'
      if (tipo === 'CONSIGNACION') return classes + ' bg-success'
      if (tipo === 'RETIRO') return classes + ' bg-warning'
      if (tipo === 'TRANSFERENCIA') return classes + ' bg-info'
      return classes + ' bg-secondary'
    },
    formatCurrency(value) {
      return new Intl.NumberFormat('es-CO', {
        style: 'currency',
        currency: 'COP',
        minimumFractionDigits: 2,
      }).format(value)
    },
    formatDate(date) {
      if (!date) return '-'
      const options = {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
        hour: '2-digit',
        minute: '2-digit',
        second: '2-digit',
      }
      return new Date(date).toLocaleDateString('es-ES', options)
    },
  },
}
</script>

<style scoped>
.table-responsive {
  overflow-x: auto;
}

.badge {
  padding: 0.35em 0.65em;
}
</style>
