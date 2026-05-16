<template>
  <div>
    <h1 class="mb-4">Gestión de Cuentas</h1>

    <div class="row">
      <div class="col-lg-6">
        <CuentaForm :cuenta-id="editingCuentaId" @cuenta-saved="handleCuentaSaved" @edit-cancelled="handleEditCancelled" />
      </div>
      <div class="col-lg-6">
        <CuentaList @edit-cuenta="handleEditCuenta" ref="cuentaList" />
      </div>
    </div>
  </div>
</template>

<script>
import CuentaForm from '@/components/CuentaForm.vue'
import CuentaList from '@/components/CuentaList.vue'

export default {
  name: 'CuentasView',
  components: {
    CuentaForm,
    CuentaList,
  },
  data() {
    return {
      editingCuentaId: null,
    }
  },
  methods: {
    handleEditCuenta(id) {
      this.editingCuentaId = id
    },
    handleCuentaSaved() {
      this.editingCuentaId = null
      this.$refs.cuentaList.loadCuentas()
    },
    handleEditCancelled() {
      this.editingCuentaId = null
    },
  },
}
</script>

<style scoped>
h1 {
  color: #198754;
}
</style>
