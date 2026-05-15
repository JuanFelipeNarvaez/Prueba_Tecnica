import axios, { AxiosInstance, AxiosError } from 'axios'
import type { Cliente, ClienteFormData } from '@types/cliente'

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || ''
const API_TIMEOUT = import.meta.env.VITE_API_TIMEOUT || 5000

const axiosInstance: AxiosInstance = axios.create({
  baseURL: API_BASE_URL,
  timeout: Number(API_TIMEOUT),
  headers: {
    'Content-Type': 'application/json',
  },
})

// Add response interceptor for error handling
axiosInstance.interceptors.response.use(
  (response) => response,
  (error: AxiosError) => {
    if (error.response?.status === 404) {
      throw new Error('Cliente no encontrado')
    }
    if (error.response?.status === 400) {
      const data = error.response.data as Record<string, unknown>
      throw new Error(data?.message || 'Error en los datos enviados')
    }
    if (error.response?.status === 409) {
      throw new Error('El número de identificación o correo ya están registrados')
    }
    throw error
  }
)

export const clienteApi = {
  // Get all clientes
  getAll: async (): Promise<Cliente[]> => {
    const response = await axiosInstance.get<Cliente[]>('/api/clientes')
    return response.data
  },

  // Get cliente by id
  getById: async (id: number): Promise<Cliente> => {
    const response = await axiosInstance.get<Cliente>(`/api/clientes/${id}`)
    return response.data
  },

  // Create cliente
  create: async (data: ClienteFormData): Promise<Cliente> => {
    const response = await axiosInstance.post<Cliente>('/api/clientes', data)
    return response.data
  },

  // Update cliente
  update: async (id: number, data: ClienteFormData): Promise<Cliente> => {
    const response = await axiosInstance.put<Cliente>(`/api/clientes/${id}`, data)
    return response.data
  },

  // Delete cliente
  delete: async (id: number): Promise<void> => {
    await axiosInstance.delete(`/api/clientes/${id}`)
  },
}
