<template>
  <div class="overflow-x-auto">
    <table class="w-full">
      <thead class="bg-gray-50 border-b border-gray-200">
        <tr>
          <th class="px-6 py-3 text-left text-xs font-medium text-gray-700 uppercase">
            Identificación
          </th>
          <th class="px-6 py-3 text-left text-xs font-medium text-gray-700 uppercase">
            Nombre
          </th>
          <th class="px-6 py-3 text-left text-xs font-medium text-gray-700 uppercase">
            Apellido
          </th>
          <th class="px-6 py-3 text-left text-xs font-medium text-gray-700 uppercase">
            Email
          </th>
          <th class="px-6 py-3 text-left text-xs font-medium text-gray-700 uppercase">
            Nacimiento
          </th>
          <th class="px-6 py-3 text-left text-xs font-medium text-gray-700 uppercase">
            Creación
          </th>
          <th class="px-6 py-3 text-left text-xs font-medium text-gray-700 uppercase">
            Modificación
          </th>
          <th class="px-6 py-3 text-left text-xs font-medium text-gray-700 uppercase">
            Acciones
          </th>
        </tr>
      </thead>
      <tbody class="divide-y divide-gray-200">
        <tr v-for="cliente in clientes" :key="cliente.id" class="hover:bg-gray-50 transition-colors">
          <td class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">
            {{ cliente.tipoIdentificacion }}: {{ cliente.numeroIdentificacion }}
          </td>
          <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
            {{ cliente.primerNombre }} {{ cliente.segundoNombre || '' }}
          </td>
          <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
            {{ cliente.primerApellido }} {{ cliente.segundoApellido || '' }}
          </td>
          <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-600">
            {{ cliente.correoElectronico }}
          </td>
          <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-600">
            {{ formatDate(cliente.fechaNacimiento) }}
          </td>
          <td class="px-6 py-4 whitespace-nowrap text-xs text-gray-500">
            {{ formatDateTime(cliente.fechaCreacion) }}
          </td>
          <td class="px-6 py-4 whitespace-nowrap text-xs text-gray-500">
            {{ formatDateTime(cliente.fechaModificacion) }}
          </td>
          <td class="px-6 py-4 whitespace-nowrap text-sm space-x-2">
            <button
              @click="$emit('edit', cliente)"
              class="inline-flex items-center gap-1 text-blue-600 hover:text-blue-900 transition-colors"
            >
              <Edit2Icon :size="18" />
            </button>
            <button
              @click="$emit('delete', cliente.id)"
              :disabled="isDeleting"
              class="inline-flex items-center gap-1 text-red-600 hover:text-red-900 transition-colors disabled:opacity-50"
            >
              <Trash2Icon :size="18" />
            </button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup lang="ts">
import { Edit2Icon, Trash2Icon } from 'lucide-vue-next'
import type { Cliente } from '@types/cliente'

defineProps<{
  clientes: Cliente[]
  isDeleting: boolean
}>()

defineEmits<{
  edit: [cliente: Cliente]
  delete: [id: number]
}>()

const formatDate = (dateString?: string) => {
  if (!dateString) return '-'
  return new Date(dateString).toLocaleDateString('es-CO')
}

const formatDateTime = (dateString?: string) => {
  if (!dateString) return '-'
  const date = new Date(dateString)
  return date.toLocaleDateString('es-CO') + ' ' + date.toLocaleTimeString('es-CO', { hour: '2-digit', minute: '2-digit' })
}
</script>
