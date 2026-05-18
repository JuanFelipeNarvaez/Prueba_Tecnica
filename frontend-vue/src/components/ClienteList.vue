<template>
  <div class="card">
    <div class="card-header d-flex justify-content-between align-items-center">
      <h5>Lista de Clientes</h5>
      <button class="btn btn-sm btn-success" @click="loadClientes">
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

      <div v-else-if="clientes.length === 0" class="alert alert-info">
        No hay clientes registrados
      </div>

      <div v-else class="table-responsive">
        <table class="table table-hover">
          <thead>
            <tr>
              <th>ID</th>
              <th>Nombre Completo</th>
              <th>Identificación</th>
              <th>Correo</th>
              <th>Fecha Nacimiento</th>
              <th>Acciones</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="cliente in clientes" :key="cliente.id">
              <td>{{ cliente.id }}</td>
              <td>{{ cliente.primerNombre }} {{ cliente.segundoNombre }} {{ cliente.primerApellido }} {{ cliente.segundoApellido }}</td>
              <td>{{ cliente.tipoIdentificacion }}: {{ cliente.numeroIdentificacion }}</td>
              <td>{{ cliente.correoElectronico }}</td>
              <td>{{ formatDate(cliente.fechaNacimiento) }}</td>
              <td>
                <button
                  class="btn btn-sm btn-primary me-2"
                  @click="editCliente(cliente.id)"
                  title="Editar"
                >
                  <i class="bi bi-pencil"></i>
                </button>
                <button
                  class="btn btn-sm btn-danger"
                  @click="deleteCliente(cliente.id)"
                  title="Eliminar"
                >
                  <i class="bi bi-trash"></i>
                </button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
import { clienteService } from '@/services/clienteService'
import { getErrorMessage, getErrorType } from '@/services/errorHelper'
import { cuentaService } from '@/services/cuentaService'
import AlertMessage from '@/components/AlertMessage.vue'

export default {
  components: { AlertMessage },
  name: 'ClienteList',
  emits: ['edit-cliente'],
  data() {
    return {
      clientes: [],
      loading: false,
      successMessage: '',
      errorMessage: '',
      errorType: 'danger',
    }
  },
  mounted() {
    this.loadClientes()
  },
  methods: {
    async loadClientes() {
      try {
        this.loading = true
        this.errorMessage = ''
        const response = await clienteService.listarClientes()
        this.clientes = response.data
      } catch (error) {
        this.errorMessage = getErrorMessage(error) || 'Error al cargar los clientes'
        this.errorType = getErrorType(error)
      } finally {
        this.loading = false
      }
    },
    editCliente(id) {
      this.$emit('edit-cliente', id)
    },
    async deleteCliente(id) {
      if (confirm('¿Estás seguro de que deseas eliminar este cliente?')) {
          try {
            this.errorMessage = ''

            // Comprobar si el cliente tiene cuentas asociadas en el frontend
            const cuentasResp = await cuentaService.listarCuentas()
            const tieneCuentas = cuentasResp.data.some(c => c.clienteId === id)
            if (tieneCuentas) {
              this.errorMessage = 'No se puede eliminar el cliente: tiene cuentas asociadas'
              this.errorType = 'warning'
              return
            }

            await clienteService.eliminarCliente(id)
            this.successMessage = 'Cliente eliminado correctamente'
            this.loadClientes()
          } catch (error) {
            this.errorMessage = getErrorMessage(error) || 'Error al eliminar el cliente'
            this.errorType = getErrorType(error)
          }
      }
    },
    formatDate(date) {
      if (!date) return '-'
      return new Date(date).toLocaleDateString('es-ES')
    },
  },
}
</script>

<style scoped>
.table-responsive {
  overflow-x: auto;
}
</style>
