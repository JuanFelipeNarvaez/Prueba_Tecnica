<template>
  <Teleport to="body">
    <div
      class="fixed bottom-4 right-4 bg-white rounded-lg shadow-lg p-4 flex items-start gap-3 animate-slideIn border-l-4"
      :class="{
        'border-green-500': type === 'success',
        'border-red-500': type === 'error',
        'border-blue-500': type === 'info',
      }"
    >
      <component
        :is="iconComponent"
        :class="iconClass"
        class="flex-shrink-0 mt-0.5"
        :size="20"
      />
      <p
        :class="textClass"
        class="flex-1 text-sm"
      >
        {{ message }}
      </p>
      <button
        @click="$emit('close')"
        :class="iconClass"
        class="hover:opacity-70 flex-shrink-0"
      >
        <XIcon :size="16" />
      </button>
    </div>
  </Teleport>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { CheckCircleIcon, AlertCircleIcon, InfoIcon, XIcon } from 'lucide-vue-next'

type ToastType = 'success' | 'error' | 'info'

const props = defineProps<{
  message: string
  type: ToastType
}>()

defineEmits<{
  close: []
}>()

const iconComponent = computed(() => {
  switch (props.type) {
    case 'success':
      return CheckCircleIcon
    case 'error':
      return AlertCircleIcon
    case 'info':
      return InfoIcon
    default:
      return InfoIcon
  }
})

const iconClass = computed(() => {
  switch (props.type) {
    case 'success':
      return 'text-green-600'
    case 'error':
      return 'text-red-600'
    case 'info':
      return 'text-blue-600'
    default:
      return 'text-blue-600'
  }
})

const textClass = computed(() => {
  switch (props.type) {
    case 'success':
      return 'text-green-800'
    case 'error':
      return 'text-red-800'
    case 'info':
      return 'text-blue-800'
    default:
      return 'text-blue-800'
  }
})
</script>
