<template>
  <div v-if="message" :class="['alert', 'alert-dismissible', 'fade', 'show', alertClass]" role="alert">
    <i :class="['me-2', iconClass]"></i>
    <span>{{ message }}</span>
    <button type="button" class="btn-close" aria-label="Cerrar" @click="$emit('close')"></button>
  </div>
</template>

<script>
export default {
  name: 'AlertMessage',
  props: {
    type: {
      type: String,
      default: 'info',
    },
    message: {
      type: String,
      required: true,
    },
  },
  computed: {
    alertClass() {
      return `alert-${this.type}`
    },
    iconClass() {
      switch (this.type) {
        case 'success':
          return 'bi bi-check-circle-fill'
        case 'danger':
          return 'bi bi-exclamation-triangle-fill'
        case 'warning':
          return 'bi bi-exclamation-circle-fill'
        case 'info':
        default:
          return 'bi bi-info-circle-fill'
      }
    },
  },
}
</script>

<style scoped>
.alert {
  display: flex;
  align-items: center;
  gap: 0.75rem;
}
</style>
