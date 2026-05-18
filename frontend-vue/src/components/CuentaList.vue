<template>
  <div class="card">
    <div class="card-header d-flex justify-content-between align-items-center">
      <h5>Lista de Cuentas</h5>
      <button class="btn btn-sm btn-success" @click="loadCuentas">
        <i class="bi bi-arrow-clockwise"></i> Actualizar
      </button>
    </div>
    <div class="card-body">
      <!-- Mensajes de estado -->
      <AlertMessage
        v-if="successMessage"
        type="success"
        :message="successMessage"
        @close="successMessage = ''"
      />
      <AlertMessage
        v-if="errorMessage"
        :type="errorType"
        :message="errorMessage"
        @close="errorMessage = ''"
      />
      <div v-if="loading" class="text-center">
        <div class="spinner-border" role="status">
          <span class="visually-hidden">Cargando...</span>
        </div>
      </div>

      <div v-else-if="cuentas.length === 0" class="alert alert-info">
        No hay cuentas registradas
      </div>

      <div v-else class="table-responsive">
        <table class="table table-hover">
          <thead>
            <tr>
              <th>ID</th>
              <th>Número Cuenta</th>
              <th>Tipo</th>
              <th>Saldo</th>
              <th>Estado</th>
              <th>GMF</th>
              <th>Cliente</th>
              <th>Acciones</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="cuenta in cuentas" :key="cuenta.id">
              <td>{{ cuenta.id }}</td>
              <td>{{ cuenta.numeroCuenta }}</td>
              <td>{{ cuenta.tipoCuenta }}</td>
              <td class="text-success fw-bold">{{ formatCurrency(cuenta.saldo) }}</td>
              <td>
                <span :class="getEstadoBadgeClass(cuenta.estado)">{{ cuenta.estado }}</span>
              </td>
              <td>{{ cuenta.exentaGmf ? 'Sí' : 'No' }}</td>
              <td>{{ getClienteName(cuenta.clienteId) }}</td>
              <td>
                <button
                  class="btn btn-sm btn-primary me-2"
                  @click="editCuenta(cuenta.id)"
                  title="Editar"
                >
                  <i class="bi bi-pencil"></i>
                </button>
                <button
                  v-if="cuenta.estado !== 'CANCELADA'"
                  class="btn btn-sm btn-warning me-2"
                  @click="showStateModal(cuenta.id, cuenta.estado)"
                  title="Cambiar Estado"
                >
                  <i class="bi bi-arrow-repeat"></i>
                </button>
                <button
                  v-if="cuenta.estado !== 'CANCELADA'"
                  class="btn btn-sm btn-danger"
                  @click="cancelarCuenta(cuenta.id)"
                  title="Cancelar"
                >
                  <i class="bi bi-x-circle"></i>
                </button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>

      <!-- Modal para cambiar estado -->
      <div v-if="showStateModalFlag" class="modal d-block" style="background-color: rgba(0,0,0,0.5)">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title">Cambiar Estado de Cuenta</h5>
              <button type="button" class="btn-close" @click="showStateModalFlag = false"></button>
            </div>
            <div class="modal-body">
              <label class="form-label">Nuevo Estado:</label>
              <select v-model="newState" class="form-select">
                <option value="">Seleccionar estado</option>
                <option value="ACTIVA">Activa</option>
                <option value="INACTIVA">Inactiva</option>
              </select>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" @click="showStateModalFlag = false">
                <i class="bi bi-x-circle me-2"></i>Cancelar
              </button>
              <button type="button" class="btn btn-primary" @click="updateEstado">
                <i class="bi bi-check2-circle me-2"></i>Cambiar
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { cuentaService } from '@/services/cuentaService'
import { clienteService } from '@/services/clienteService'
import { getErrorMessage, getErrorType } from '@/services/errorHelper'
import AlertMessage from '@/components/AlertMessage.vue'

export default {
  components: { AlertMessage },
  name: 'CuentaList',
  emits: ['edit-cuenta'],
  data() {
    return {
      cuentas: [],
      clientes: [],
      loading: false,
      successMessage: '',
      errorMessage: '',
      errorType: 'danger',
      showStateModalFlag: false,
      selectedCuentaId: null,
      newState: '',
    }
  },
  mounted() {
    this.loadCuentas()
    this.loadClientes()
  },
  methods: {
    async loadCuentas() {
      try {
        this.loading = true
        this.errorMessage = ''
        const response = await cuentaService.listarCuentas()
        this.cuentas = response.data
      } catch (error) {
        this.errorMessage = getErrorMessage(error) || 'Error al cargar las cuentas'
        this.errorType = getErrorType(error)
      } finally {
        this.loading = false
      }
    },
    async loadClientes() {
      try {
        const response = await clienteService.listarClientes()
        this.clientes = response.data
      } catch (error) {
        console.error('Error cargando clientes')
      }
    },
    editCuenta(id) {
      this.$emit('edit-cuenta', id)
    },
    getClienteName(clienteId) {
      const cliente = this.clientes.find((c) => c.id === clienteId)
      return cliente ? `${cliente.primerNombre} ${cliente.primerApellido}` : '-'
    },
    formatCurrency(value) {
      return new Intl.NumberFormat('es-CO', {
        style: 'currency',
        currency: 'COP',
        minimumFractionDigits: 2,
      }).format(value)
    },
    getEstadoBadgeClass(estado) {
      const classes = 'badge'
      if (estado === 'ACTIVA') return classes + ' bg-success'
      if (estado === 'INACTIVA') return classes + ' bg-warning'
      if (estado === 'CANCELADA') return classes + ' bg-danger'
      return classes + ' bg-secondary'
    },
    showStateModal(id, estado) {
      this.selectedCuentaId = id
      this.newState = estado
      this.showStateModalFlag = true
    },
    async updateEstado() {
      try {
        this.errorMessage = ''
        await cuentaService.actualizarEstadoCuenta(this.selectedCuentaId, this.newState)
        this.successMessage = 'Estado actualizado correctamente'
        this.showStateModalFlag = false
        this.loadCuentas()
      } catch (error) {
        this.errorMessage = getErrorMessage(error) || 'Error al actualizar el estado'
        this.errorType = getErrorType(error)
      }
    },
    async cancelarCuenta(id) {
      if (confirm('¿Estás seguro de que deseas cancelar esta cuenta?')) {
        try {
          this.errorMessage = ''
          await cuentaService.cancelarCuenta(id)
          this.successMessage = 'Cuenta cancelada correctamente'
          this.loadCuentas()
        } catch (error) {
          this.errorMessage = getErrorMessage(error) || 'Error al cancelar la cuenta'
          this.errorType = getErrorType(error)
        }
      }
    },
  },
}
</script>

<style scoped>
.table-responsive {
  overflow-x: auto;
}

.modal.d-block {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1050;
}

.badge {
  padding: 0.35em 0.65em;
}
</style>
