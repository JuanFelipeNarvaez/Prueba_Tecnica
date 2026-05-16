<template>
  <div>
    <h1 class="mb-4">Gestión de Transacciones</h1>

    <div class="row">
      <div class="col-lg-6">
        <TransaccionForm @transaccion-completada="handleTransaccionCompletada" ref="transaccionForm"/>
      </div>
      <div class="col-lg-6">
        <TransaccionList ref="transaccionList" />
      </div>
    </div>
  </div>
</template>

<script>
import TransaccionForm from '@/components/TransaccionForm.vue'
import TransaccionList from '@/components/TransaccionList.vue'

export default {
  name: 'TransaccionesView',
  components: {
    TransaccionForm,
    TransaccionList,
  },
  methods: {
    handleTransaccionCompletada() {
      this.$refs.transaccionList.loadTransacciones()
    },
  },
}
</script>

<style scoped>
h1 {
  color: #0dcaf0;
}
</style>
