<template>
  <div class="bg-white rounded-lg shadow">
    <div class="p-6 border-b border-gray-200">
      <h2 class="text-xl font-semibold text-gray-900 mb-4">
        Clientes ({{ filteredClientes.length }})
      </h2>
      <input
        v-model="searchTerm"
        type="text"
        placeholder="Buscar por nombre, apellido, email o identificación..."
        class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-primary-500"
      />
    </div>

    <LoadingSpinner v-if="isLoading" />

    <ErrorAlert
      v-else-if="error"
      title="Error al cargar clientes"
      :message="error.message"
    />

    <div v-else-if="filteredClientes.length === 0" class="p-6 text-center text-gray-500">
      {{ searchTerm ? 'No se encontraron resultados' : 'No hay clientes registrados' }}
    </div>

    <ClienteTable
      v-else
      :clientes="filteredClientes"
      :is-deleting="isDeleting"
      @edit="handleEdit"
      @delete="handleDelete"
    />
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useClientes, useDeleteCliente } from '@composables/useClienteQueries'
import ClienteTable from './ClienteTable.vue'
import LoadingSpinner from './LoadingSpinner.vue'
import ErrorAlert from './ErrorAlert.vue'
import type { Cliente } from '@types/cliente'

const emit = defineEmits<{
  edit: [cliente: Cliente]
  delete: []
  error: [message: string]
}>()

const { clientes, isLoading, error, fetch } = useClientes()
const deleteCliente = useDeleteCliente()
const searchTerm = ref('')

const filteredClientes = computed(() => {
  return clientes.value.filter(cliente =>
    cliente.primerNombre.toLowerCase().includes(searchTerm.value.toLowerCase()) ||
    cliente.primerApellido.toLowerCase().includes(searchTerm.value.toLowerCase()) ||
    cliente.correoElectronico.toLowerCase().includes(searchTerm.value.toLowerCase()) ||
    cliente.numeroIdentificacion.includes(searchTerm.value)
  )
})

const isDeleting = computed(() => deleteCliente.isLoading.value)

const handleEdit = (cliente: Cliente) => {
  emit('edit', cliente)
}

const handleDelete = async (id: number) => {
  if (window.confirm('¿Estás seguro de que deseas eliminar este cliente?')) {
    const success = await deleteCliente.mutate(id)
    if (success) {
      await fetch()
      emit('delete')
    } else {
      emit('error', deleteCliente.error.value?.message || 'Error al eliminar')
    }
  }
}

onMounted(() => {
  fetch()
})
</script>
