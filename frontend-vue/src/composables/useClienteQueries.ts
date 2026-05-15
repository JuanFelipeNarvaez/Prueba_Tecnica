import { ref, computed } from 'vue'
import { clienteApi } from '@services/api'
import type { Cliente, ClienteFormData } from '@types/cliente'

export const useClientes = () => {
  const clientes = ref<Cliente[]>([])
  const isLoading = ref(false)
  const error = ref<Error | null>(null)

  const fetch = async () => {
    isLoading.value = true
    error.value = null
    try {
      clientes.value = await clienteApi.getAll()
    } catch (err) {
      error.value = err instanceof Error ? err : new Error('Error desconocido')
    } finally {
      isLoading.value = false
    }
  }

  const refetch = () => fetch()

  return {
    clientes: computed(() => clientes.value),
    isLoading: computed(() => isLoading.value),
    error: computed(() => error.value),
    fetch,
    refetch,
  }
}

export const useCliente = (id: number) => {
  const cliente = ref<Cliente | null>(null)
  const isLoading = ref(false)
  const error = ref<Error | null>(null)

  const fetch = async () => {
    isLoading.value = true
    error.value = null
    try {
      cliente.value = await clienteApi.getById(id)
    } catch (err) {
      error.value = err instanceof Error ? err : new Error('Error desconocido')
    } finally {
      isLoading.value = false
    }
  }

  return {
    cliente: computed(() => cliente.value),
    isLoading: computed(() => isLoading.value),
    error: computed(() => error.value),
    fetch,
  }
}

export const useCreateCliente = () => {
  const isLoading = ref(false)
  const error = ref<Error | null>(null)

  const mutate = async (data: ClienteFormData): Promise<Cliente | null> => {
    isLoading.value = true
    error.value = null
    try {
      const result = await clienteApi.create(data)
      return result
    } catch (err) {
      error.value = err instanceof Error ? err : new Error('Error desconocido')
      return null
    } finally {
      isLoading.value = false
    }
  }

  return {
    isLoading: computed(() => isLoading.value),
    error: computed(() => error.value),
    mutate,
  }
}

export const useUpdateCliente = () => {
  const isLoading = ref(false)
  const error = ref<Error | null>(null)

  const mutate = async (id: number, data: ClienteFormData): Promise<Cliente | null> => {
    isLoading.value = true
    error.value = null
    try {
      const result = await clienteApi.update(id, data)
      return result
    } catch (err) {
      error.value = err instanceof Error ? err : new Error('Error desconocido')
      return null
    } finally {
      isLoading.value = false
    }
  }

  return {
    isLoading: computed(() => isLoading.value),
    error: computed(() => error.value),
    mutate,
  }
}

export const useDeleteCliente = () => {
  const isLoading = ref(false)
  const error = ref<Error | null>(null)

  const mutate = async (id: number): Promise<boolean> => {
    isLoading.value = true
    error.value = null
    try {
      await clienteApi.delete(id)
      return true
    } catch (err) {
      error.value = err instanceof Error ? err : new Error('Error desconocido')
      return false
    } finally {
      isLoading.value = false
    }
  }

  return {
    isLoading: computed(() => isLoading.value),
    error: computed(() => error.value),
    mutate,
  }
}
