<template>
  <header class="bg-white shadow-sm border-b border-gray-200">
    <div class="container mx-auto px-4 py-6">
      <div class="flex items-center justify-between">
        <div class="flex items-center gap-3">
          <div class="p-2 bg-primary-100 rounded-lg">
            <Building2Icon class="text-primary-600" :size="28" />
          </div>
          <div>
            <h1 class="text-2xl font-bold text-gray-900">Bank API</h1>
            <p class="text-sm text-gray-600">Gestión de Clientes</p>
          </div>
        </div>

        <button
          @click="$emit('add-cliente')"
          class="flex items-center gap-2 bg-primary-600 hover:bg-primary-700 text-white px-4 py-2 rounded-lg font-medium transition-colors"
        >
          <PlusIcon :size="20" />
          Nuevo Cliente
        </button>
      </div>
    </div>
  </header>
</template>

<script setup lang="ts">
import { PlusIcon, Building2Icon } from 'lucide-vue-next'

defineEmits<{
  'add-cliente': []
}>()
</script>
