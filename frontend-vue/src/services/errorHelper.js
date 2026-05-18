export function getErrorMessage(error) {
  const response = error?.response?.data
  if (!response) {
    return error?.message || 'Ocurrió un error inesperado'
  }

  if (typeof response === 'string') {
    return response
  }

  if (response.message) {
    return response.message
  }

  if (response.errors) {
    if (typeof response.errors === 'string') {
      return response.errors
    }
    return Object.values(response.errors).join(', ')
  }

  return 'Ocurrió un error inesperado'
}

export function getErrorType(error) {
  const response = error?.response?.data
  if (!response) return 'danger'

  const asString = (val) => {
    if (!val) return ''
    if (typeof val === 'string') return val
    if (typeof val === 'object') return Object.values(val).join(' ')
    return String(val)
  }

  const combined = [response.message, response.errors, asString(response)].filter(Boolean).join(' ').toLowerCase()

  // Conditions that should be presented as warnings instead of errors
  if (combined.includes('ya existe') || combined.includes('identificaci') || combined.includes('correo') || combined.includes('obligatoria') || combined.includes('no puede')) {
    return 'warning'
  }

  if (combined.includes('no encontrada') || combined.includes('saldo insuficiente') || combined.includes('activo') ) {
    return 'danger'
  }

  return 'danger'
}
