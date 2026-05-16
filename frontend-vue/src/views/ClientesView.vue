<template>
  <div>
    <h1 class="mb-4">Gestión de Clientes</h1>

    <div class="row">
      <div class="col-lg-6">
        <ClienteForm :cliente-id="editingClienteId" @cliente-saved="handleClienteSaved" @edit-cancelled="handleEditCancelled"/>
      </div>
      <div class="col-lg-6">
        <ClienteList @edit-cliente="handleEditCliente" ref="clienteList" />
      </div>
    </div>
  </div>
</template>

<script>
import ClienteForm from '@/components/ClienteForm.vue'
import ClienteList from '@/components/ClienteList.vue'

export default {
  name: 'ClientesView',
  components: {
    ClienteForm,
    ClienteList,
  },
  data() {
    return {
      editingClienteId: null,
    }
  },
  methods: {
    handleEditCliente(id) {
      this.editingClienteId = id
    },
    handleClienteSaved() {
      this.editingClienteId = null
      this.$refs.clienteList.loadClientes()
    },
    handleEditCancelled() {
      this.editingClienteId = null
    },
  },
}
</script>

<style scoped>
h1 {
  color: #0d6efd;
}
</style>
