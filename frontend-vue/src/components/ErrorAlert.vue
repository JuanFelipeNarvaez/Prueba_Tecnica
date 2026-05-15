<template>
  <div class="bg-red-50 border border-red-200 rounded-lg p-6 flex gap-4">
    <AlertCircleIcon class="text-red-600 flex-shrink-0" :size="24" />
    <div>
      <h3 class="font-semibold text-red-900">{{ title }}</h3>
      <p class="text-red-700 text-sm mt-1">{{ message }}</p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { AlertCircleIcon } from 'lucide-vue-next'

defineProps<{
  title: string
  message: string
}>()
</script>
