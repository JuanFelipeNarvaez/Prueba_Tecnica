export interface Cliente {
  id: number
  tipoIdentificacion: string
  numeroIdentificacion: string
  primerNombre: string
  segundoNombre?: string
  primerApellido: string
  segundoApellido?: string
  correoElectronico: string
  fechaNacimiento: string
  fechaCreacion?: string
  fechaModificacion?: string
}

export interface ClienteFormData {
  tipoIdentificacion: string
  numeroIdentificacion: string
  primerNombre: string
  segundoNombre: string
  primerApellido: string
  segundoApellido: string
  correoElectronico: string
  fechaNacimiento: string
}

export interface ApiResponse<T> {
  data: T
  message?: string
  error?: string
}

export interface PaginatedResponse<T> {
  content: T[]
  totalElements: number
  totalPages: number
  currentPage: number
}
