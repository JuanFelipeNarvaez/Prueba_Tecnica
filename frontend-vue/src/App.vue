<template>
  <div class="min-h-screen bg-gray-50">
    <Header @add-cliente="openForm()" />

    <main class="container mx-auto px-4 py-8">
      <div v-if="showForm" class="mb-8">
        <ClienteForm
          :cliente="editingCliente"
          @close="closeForm"
          @success="handleFormSuccess"
          @error="handleFormError"
        />
      </div>

      <ClienteList 
        @edit="handleEdit"
        @delete="showToast('Cliente eliminado exitosamente', 'success')"
        @error="(msg) => showToast(msg, 'error')"
      />
    </main>

    <div class="fixed bottom-4 right-4 space-y-2 z-50">
      <Toast
        v-for="toast in toasts"
        :key="toast.id"
        :message="toast.message"
        :type="toast.type"
        @close="removeToast(toast.id)"
      />
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import Header from '@components/Header.vue'
import ClienteForm from '@components/ClienteForm.vue'
import ClienteList from '@components/ClienteList.vue'
import Toast from '@components/Toast.vue'
import type { Cliente } from '@types/cliente'

type ToastType = 'success' | 'error' | 'info'

interface ToastMessage {
  id: string
  message: string
  type: ToastType
}

const showForm = ref(false)
const editingCliente = ref<Cliente | null>(null)
const toasts = ref<ToastMessage[]>([])

const showToast = (message: string, type: ToastType = 'success') => {
  const id = Date.now().toString()
  toasts.value.push({ id, message, type })
  
  setTimeout(() => {
    removeToast(id)
  }, 4000)
}

const removeToast = (id: string) => {
  toasts.value = toasts.value.filter(t => t.id !== id)
}

const openForm = () => {
  editingCliente.value = null
  showForm.value = true
}

const closeForm = () => {
  showForm.value = false
  editingCliente.value = null
}

const handleEdit = (cliente: Cliente) => {
  editingCliente.value = cliente
  showForm.value = true
}

const handleFormSuccess = (message: string) => {
  showToast(message, 'success')
  closeForm()
}

const handleFormError = (message: string) => {
  showToast(message, 'error')
}
</script>
