<template>
  <div class="card">
    <div class="card-header">
      <h5>{{ isEditing ? 'Editar Cuenta' : 'Crear Nueva Cuenta' }}</h5>
    </div>
    <div class="card-body">
      <form @submit.prevent="submitForm">
        <!-- Tipo de Cuenta -->
        <div class="mb-3">
          <label for="tipoCuenta" class="form-label">Tipo de Cuenta *</label>
          <select
            id="tipoCuenta"
            v-model="form.tipoCuenta"
            class="form-select"
            required
          >
            <option value="">Seleccionar tipo</option>
            <option value="AHORROS">Ahorros</option>
            <option value="CORRIENTE">Corriente</option>
          </select>
        </div>

        <!-- Saldo Inicial -->
        <div class="mb-3">
          <label for="saldo" class="form-label">Saldo Inicial *</label>
          <input
            id="saldo"
            v-model.number="form.saldo"
            type="number"
            step="0.01"
            class="form-control"
            placeholder="0.00"
            required
          />
        </div>

        <!-- Exenta GMF -->
        <div class="mb-3">
          <div class="form-check">
            <input
              id="exentaGmf"
              v-model="form.exentaGmf"
              type="checkbox"
              class="form-check-input"
            />
            <label for="exentaGmf" class="form-check-label">
              ¿Exenta de GMF? (Gravamen a Movimientos Financieros)
            </label>
          </div>
        </div>

        <!-- Cliente -->
        <div class="mb-3">
          <label for="clienteId" class="form-label">Cliente *</label>
          <select
            id="clienteId"
            v-model.number="form.clienteId"
            class="form-select"
            :disabled="isEditing"
            required
          >
            <option value="">Seleccionar cliente</option>
            <option v-for="cliente in clientes" :key="cliente.id" :value="cliente.id">
              {{ cliente.primerNombre }} {{ cliente.primerApellido }}
            </option>
          </select>
        </div>

        <!-- Mensajes -->
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
        <AlertMessage
          v-if="isEditing"
          type="warning"
          message="No se puede cambiar el cliente asociado a la cuenta al editar"
        />

        <!-- Botones -->
        <div class="d-flex gap-2">
          <button type="submit" class="btn btn-primary">
            <i class="bi bi-save me-2"></i>
            {{ isEditing ? 'Actualizar' : 'Crear Cuenta' }}
          </button>
          <button type="button" class="btn btn-secondary" @click="resetForm">
            <i class="bi bi-trash me-2"></i>Limpiar
          </button>
          <button v-if="isEditing" type="button" class="btn btn-secondary" @click="cancelEdit">
            <i class="bi bi-x-circle me-2"></i>Cancelar
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import { cuentaService } from '@/services/cuentaService'
import { clienteService } from '@/services/clienteService'
import { getErrorMessage, getErrorType } from '@/services/errorHelper'
import AlertMessage from '@/components/AlertMessage.vue'

export default {
  name: 'CuentaForm',
  components: { AlertMessage },
  props: {
    cuentaId: {
      type: [Number, null],
      default: null,
    },
  },
  data() {
    return {
      form: {
        tipoCuenta: '',
        saldo: '',
        exentaGmf: false,
        clienteId: '',
      },
      clientes: [],
      isEditing: false,
      successMessage: '',
      errorMessage: '',
      errorType: 'danger',
    }
  },
  watch: {
    cuentaId: {
      immediate: true,
      handler(newId) {
        if (newId) {
          this.loadCuenta(newId)
        } else {
          this.isEditing = false
          this.resetForm()
        }
      },
    },
  },
  mounted() {
    this.loadClientes()
  },
  methods: {
    async loadClientes() {
      try {
        const response = await clienteService.listarClientes()
        this.clientes = response.data
      } catch (error) {
        this.errorMessage = 'Error al cargar clientes'
      }
    },
    async loadCuenta(id) {
      try {
        this.errorMessage = ''
        const response = await cuentaService.obtenerCuenta(id)
        this.form = { ...response.data }
        this.isEditing = true
      } catch (error) {
        this.errorMessage = 'Error al cargar la cuenta'
      }
    },
    async submitForm() {
      try {
        this.errorMessage = ''
        this.successMessage = ''

        // Validación cliente-side: saldo no negativo
        if (this.form.saldo != null && Number(this.form.saldo) < 0) {
          this.errorMessage = 'El saldo no puede ser negativo'
          this.errorType = 'warning'
          return
        }

        if (this.isEditing) {
          await cuentaService.actualizarCuenta(this.cuentaId, this.form)
          this.successMessage = 'Cuenta actualizada correctamente'
        } else {
          await cuentaService.crearCuenta(this.form)
          this.successMessage = 'Cuenta creada correctamente'
        }

        this.$emit('cuenta-saved')
        this.resetForm()
      } catch (error) {
        this.errorMessage = getErrorMessage(error) || 'Error al guardar cuenta'
        this.errorType = getErrorType(error)
      }
    },
    resetForm() {
      this.form = {
        tipoCuenta: '',
        saldo: '',
        exentaGmf: false,
        clienteId: '',
      }
      this.successMessage = ''
      this.errorMessage = ''
    },
    cancelEdit() {
      this.isEditing = false
      this.resetForm()
      this.$emit('edit-cancelled')
    },
  },
}
</script>

<style scoped>
.form-label {
  font-weight: 500;
}
</style>
