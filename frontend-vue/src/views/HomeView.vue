<template>
  <div class="row">
    <div class="col-12">
      <h1 class="mb-4">Bienvenido al Sistema de Gestión Bancaria</h1>
      <p class="lead">Administra clientes, cuentas y transacciones de forma segura y eficiente.</p>
    </div>
  </div>

  <div class="row mt-5">
    <div class="col-md-4 mb-3">
      <div class="card h-100">
        <div class="card-body text-center">
          <i class="bi bi-people" style="font-size: 3rem; color: #0d6efd"></i>
          <h5 class="card-title mt-3">Gestión de Clientes</h5>
          <p class="card-text">Crea, edita y elimina clientes de tu banco.</p>
          <router-link to="/clientes" class="btn btn-primary">
            <i class="bi bi-people-fill me-2"></i>Ir a Clientes
          </router-link>
        </div>
      </div>
    </div>

    <div class="col-md-4 mb-3">
      <div class="card h-100">
        <div class="card-body text-center">
          <i class="bi bi-credit-card" style="font-size: 3rem; color: #198754"></i>
          <h5 class="card-title mt-3">Gestión de Cuentas</h5>
          <p class="card-text">Crea y administra cuentas bancarias.</p>
          <router-link to="/cuentas" class="btn btn-success">
            <i class="bi bi-credit-card-fill me-2"></i>Ir a Cuentas
          </router-link>
        </div>
      </div>
    </div>

    <div class="col-md-4 mb-3">
      <div class="card h-100">
        <div class="card-body text-center">
          <i class="bi bi-arrow-left-right" style="font-size: 3rem; color: #0dcaf0"></i>
          <h5 class="card-title mt-3">Transacciones</h5>
          <p class="card-text">Realiza consignaciones, retiros y transferencias.</p>
          <router-link to="/transacciones" class="btn btn-info">
            <i class="bi bi-arrow-left-right me-2"></i>Ir a Transacciones
          </router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'HomeView',
}
</script>

<style scoped>
.card {
  transition: transform 0.2s;
}

.card:hover {
  transform: translateY(-5px);
}
</style>
