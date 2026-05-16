<template>
  <div class="card">
    <div class="card-header">
      <h5>{{ isEditing ? 'Editar Cliente' : 'Crear Nuevo Cliente' }}</h5>
    </div>
    <div class="card-body">
      <form @submit.prevent="submitForm">
        <!-- Tipo de Identificación -->
        <div class="mb-3">
          <label for="tipoIdentificacion" class="form-label">Tipo de Identificación *</label>
          <select
            id="tipoIdentificacion"
            v-model="form.tipoIdentificacion"
            class="form-select"
            required
          >
            <option value="">Seleccionar tipo</option>
            <option value="CC">Cédula de Ciudadanía (CC)</option>
            <option value="CE">Cédula de Extranjería (CE)</option>
            <option value="NIT">NIT</option>
            <option value="PASAPORTE">Pasaporte</option>
          </select>
        </div>

        <!-- Número de Identificación -->
        <div class="mb-3">
          <label for="numeroIdentificacion" class="form-label">Número de Identificación *</label>
          <input
            id="numeroIdentificacion"
            v-model="form.numeroIdentificacion"
            type="text"
            class="form-control"
            placeholder="Ej: 1234567890"
            required
          />
        </div>

        <!-- Primer Nombre -->
        <div class="mb-3">
          <label for="primerNombre" class="form-label">Primer Nombre *</label>
          <input
            id="primerNombre"
            v-model="form.primerNombre"
            type="text"
            class="form-control"
            placeholder="Ej: Juan"
            required
          />
        </div>

        <!-- Segundo Nombre -->
        <div class="mb-3">
          <label for="segundoNombre" class="form-label">Segundo Nombre</label>
          <input
            id="segundoNombre"
            v-model="form.segundoNombre"
            type="text"
            class="form-control"
            placeholder="Ej: Carlos"
          />
        </div>

        <!-- Primer Apellido -->
        <div class="mb-3">
          <label for="primerApellido" class="form-label">Primer Apellido *</label>
          <input
            id="primerApellido"
            v-model="form.primerApellido"
            type="text"
            class="form-control"
            placeholder="Ej: Pérez"
            required
          />
        </div>

        <!-- Segundo Apellido -->
        <div class="mb-3">
          <label for="segundoApellido" class="form-label">Segundo Apellido</label>
          <input
            id="segundoApellido"
            v-model="form.segundoApellido"
            type="text"
            class="form-control"
            placeholder="Ej: García"
          />
        </div>

        <!-- Email -->
        <div class="mb-3">
          <label for="correoElectronico" class="form-label">Correo Electrónico *</label>
          <input
            id="correoElectronico"
            v-model="form.correoElectronico"
            type="email"
            class="form-control"
            placeholder="Ej: juan@example.com"
            required
          />
        </div>

        <!-- Fecha de Nacimiento -->
        <div class="mb-3">
          <label for="fechaNacimiento" class="form-label">Fecha de Nacimiento</label>
          <input
            id="fechaNacimiento"
            v-model="form.fechaNacimiento"
            type="date"
            class="form-control"
          />
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

        <!-- Botones -->
        <div class="d-flex gap-2">
          <button type="submit" class="btn btn-primary">
            <i class="bi bi-save me-2"></i>
            {{ isEditing ? 'Actualizar' : 'Crear Cliente' }}
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
import { clienteService } from '@/services/clienteService'
import { getErrorMessage, getErrorType } from '@/services/errorHelper'
import AlertMessage from '@/components/AlertMessage.vue'

export default {
  name: 'ClienteForm',
  components: { AlertMessage },
  props: {
    clienteId: {
      type: [Number, null],
      default: null,
    },
  },
  data() {
    return {
      form: {
        tipoIdentificacion: '',
        numeroIdentificacion: '',
        primerNombre: '',
        segundoNombre: '',
        primerApellido: '',
        segundoApellido: '',
        correoElectronico: '',
        fechaNacimiento: '',
      },
      isEditing: false,
      successMessage: '',
      errorMessage: '',
      errorType: 'danger',
    }
  },
  watch: {
    clienteId: {
      immediate: true,
      handler(newId) {
        if (newId) {
          this.loadCliente(newId)
        } else {
          this.isEditing = false
          this.resetForm()
        }
      },
    },
  },
  methods: {
    async loadCliente(id) {
      try {
        this.errorMessage = ''
        const response = await clienteService.obtenerCliente(id)
        this.form = { ...response.data }
        this.isEditing = true
      } catch (error) {
        this.errorMessage = getErrorMessage(error) || 'Error al cargar el cliente'
      }
    },
    async submitForm() {
      try {
        this.errorMessage = ''
        this.successMessage = ''

        if (this.isEditing) {
          await clienteService.actualizarCliente(this.clienteId, this.form)
          this.successMessage = 'Cliente actualizado correctamente'
        } else {
          await clienteService.crearCliente(this.form)
          this.successMessage = 'Cliente creado correctamente'
        }

        this.$emit('cliente-saved')
        this.resetForm()
      } catch (error) {
        this.errorMessage = getErrorMessage(error) || 'Error al guardar cliente'
        this.errorType = getErrorType(error)
      }
    },
    resetForm() {
      this.form = {
        tipoIdentificacion: '',
        numeroIdentificacion: '',
        primerNombre: '',
        segundoNombre: '',
        primerApellido: '',
        segundoApellido: '',
        correoElectronico: '',
        fechaNacimiento: '',
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
