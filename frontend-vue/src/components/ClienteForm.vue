<template>
  <div class="bg-white rounded-lg shadow-lg p-6 border border-gray-200">
    <div class="flex items-center justify-between mb-6">
      <h2 class="text-2xl font-bold text-gray-900">
        {{ isEditing ? 'Editar Cliente' : 'Nuevo Cliente' }}
      </h2>
      <button
        @click="$emit('close')"
        class="text-gray-400 hover:text-gray-600 transition-colors"
      >
        <XIcon :size="24" />
      </button>
    </div>

    <form @submit.prevent="onSubmit" class="space-y-6">
      <!-- Tipo y Número de Identificación -->
      <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">
            Tipo de Identificación *
          </label>
          <select
            v-model="form.tipoIdentificacion"
            class="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-primary-500"
            :class="errors.tipoIdentificacion ? 'border-red-500' : 'border-gray-300'"
          >
            <option value="">Seleccionar...</option>
            <option value="CC">Cédula de Ciudadanía (CC)</option>
            <option value="CE">Cédula de Extranjería (CE)</option>
            <option value="PA">Pasaporte (PA)</option>
            <option value="NIT">NIT</option>
          </select>
          <p v-if="errors.tipoIdentificacion" class="text-red-500 text-sm mt-1">
            {{ errors.tipoIdentificacion }}
          </p>
        </div>

        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">
            Número de Identificación *
          </label>
          <input
            v-model="form.numeroIdentificacion"
            type="text"
            placeholder="1234567890"
            class="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-primary-500"
            :class="errors.numeroIdentificacion ? 'border-red-500' : 'border-gray-300'"
          />
          <p v-if="errors.numeroIdentificacion" class="text-red-500 text-sm mt-1">
            {{ errors.numeroIdentificacion }}
          </p>
        </div>
      </div>

      <!-- Nombres -->
      <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">
            Primer Nombre *
          </label>
          <input
            v-model="form.primerNombre"
            type="text"
            placeholder="Juan"
            class="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-primary-500"
            :class="errors.primerNombre ? 'border-red-500' : 'border-gray-300'"
          />
          <p v-if="errors.primerNombre" class="text-red-500 text-sm mt-1">
            {{ errors.primerNombre }}
          </p>
        </div>

        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">
            Segundo Nombre
          </label>
          <input
            v-model="form.segundoNombre"
            type="text"
            placeholder="Carlos"
            class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-primary-500"
          />
        </div>
      </div>

      <!-- Apellidos -->
      <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">
            Primer Apellido *
          </label>
          <input
            v-model="form.primerApellido"
            type="text"
            placeholder="Pérez"
            class="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-primary-500"
            :class="errors.primerApellido ? 'border-red-500' : 'border-gray-300'"
          />
          <p v-if="errors.primerApellido" class="text-red-500 text-sm mt-1">
            {{ errors.primerApellido }}
          </p>
        </div>

        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">
            Segundo Apellido
          </label>
          <input
            v-model="form.segundoApellido"
            type="text"
            placeholder="García"
            class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-primary-500"
          />
        </div>
      </div>

      <!-- Email -->
      <div>
        <label class="block text-sm font-medium text-gray-700 mb-1">
          Correo Electrónico *
        </label>
        <input
          v-model="form.correoElectronico"
          type="email"
          placeholder="juan@example.com"
          class="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-primary-500"
          :class="errors.correoElectronico ? 'border-red-500' : 'border-gray-300'"
        />
        <p v-if="errors.correoElectronico" class="text-red-500 text-sm mt-1">
          {{ errors.correoElectronico }}
        </p>
      </div>

      <!-- Fecha de Nacimiento -->
      <div>
        <label class="block text-sm font-medium text-gray-700 mb-1">
          Fecha de Nacimiento *
        </label>
        <input
          v-model="form.fechaNacimiento"
          type="date"
          class="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-primary-500"
          :class="errors.fechaNacimiento ? 'border-red-500' : 'border-gray-300'"
        />
        <p v-if="errors.fechaNacimiento" class="text-red-500 text-sm mt-1">
          {{ errors.fechaNacimiento }}
        </p>
      </div>

      <!-- Buttons -->
      <div class="flex gap-4 pt-6">
        <button
          type="submit"
          :disabled="isSubmitting"
          class="flex-1 bg-primary-600 hover:bg-primary-700 text-white px-6 py-2 rounded-lg font-medium transition-colors disabled:opacity-50 disabled:cursor-not-allowed"
        >
          {{ isSubmitting ? 'Guardando...' : isEditing ? 'Actualizar Cliente' : 'Crear Cliente' }}
        </button>
        <button
          type="button"
          @click="$emit('close')"
          class="flex-1 bg-gray-200 hover:bg-gray-300 text-gray-900 px-6 py-2 rounded-lg font-medium transition-colors"
        >
          Cancelar
        </button>
      </div>
    </form>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, computed, watch } from 'vue'
import { XIcon } from 'lucide-vue-next'
import { useCreateCliente, useUpdateCliente } from '@composables/useClienteQueries'
import type { Cliente, ClienteFormData } from '@types/cliente'

interface Props {
  cliente?: Cliente | null
}

const props = defineProps<Props>()

const emit = defineEmits<{
  close: []
  success: [message: string]
  error: [message: string]
}>()

const isEditing = computed(() => !!props.cliente)
const createCliente = useCreateCliente()
const updateCliente = useUpdateCliente()
const isSubmitting = ref(false)

const form = reactive<ClienteFormData>({
  tipoIdentificacion: '',
  numeroIdentificacion: '',
  primerNombre: '',
  segundoNombre: '',
  primerApellido: '',
  segundoApellido: '',
  correoElectronico: '',
  fechaNacimiento: '',
})

const errors = reactive<Partial<Record<keyof ClienteFormData, string>>>({})

watch(
  () => props.cliente,
  (newCliente) => {
    if (newCliente) {
      form.tipoIdentificacion = newCliente.tipoIdentificacion
      form.numeroIdentificacion = newCliente.numeroIdentificacion
      form.primerNombre = newCliente.primerNombre
      form.segundoNombre = newCliente.segundoNombre || ''
      form.primerApellido = newCliente.primerApellido
      form.segundoApellido = newCliente.segundoApellido || ''
      form.correoElectronico = newCliente.correoElectronico
      form.fechaNacimiento = newCliente.fechaNacimiento
    }
  },
  { immediate: true }
)

const validateForm = (): boolean => {
  const newErrors: Partial<Record<keyof ClienteFormData, string>> = {}

  if (!form.tipoIdentificacion) {
    newErrors.tipoIdentificacion = 'El tipo de identificación es obligatorio'
  }
  if (!form.numeroIdentificacion) {
    newErrors.numeroIdentificacion = 'El número de identificación es obligatorio'
  }
  if (!form.primerNombre) {
    newErrors.primerNombre = 'El primer nombre es obligatorio'
  } else if (form.primerNombre.length < 2) {
    newErrors.primerNombre = 'El primer nombre debe tener mínimo 2 caracteres'
  }
  if (!form.primerApellido) {
    newErrors.primerApellido = 'El primer apellido es obligatorio'
  } else if (form.primerApellido.length < 2) {
    newErrors.primerApellido = 'El primer apellido debe tener mínimo 2 caracteres'
  }
  if (!form.correoElectronico) {
    newErrors.correoElectronico = 'El correo electrónico es obligatorio'
  } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(form.correoElectronico)) {
    newErrors.correoElectronico = 'El correo electrónico no es válido'
  }
  if (!form.fechaNacimiento) {
    newErrors.fechaNacimiento = 'La fecha de nacimiento es obligatoria'
  }

  Object.assign(errors, newErrors)
  return Object.keys(newErrors).length === 0
}

const onSubmit = async () => {
  if (!validateForm()) return

  try {
    isSubmitting.value = true

    if (isEditing.value && props.cliente) {
      await updateCliente.mutate(props.cliente.id, form)
      emit('success', 'Cliente actualizado exitosamente')
    } else {
      await createCliente.mutate(form)
      emit('success', 'Cliente creado exitosamente')
    }

    // Reset form
    Object.assign(form, {
      tipoIdentificacion: '',
      numeroIdentificacion: '',
      primerNombre: '',
      segundoNombre: '',
      primerApellido: '',
      segundoApellido: '',
      correoElectronico: '',
      fechaNacimiento: '',
    })
  } catch (err) {
    const message = err instanceof Error ? err.message : 'Error desconocido'
    emit('error', message)
  } finally {
    isSubmitting.value = false
  }
}
</script>
