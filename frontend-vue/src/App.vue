<template>
  <div class="d-flex flex-column min-vh-100">
    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark sticky-top">
      <div class="container-fluid">
        <router-link to="/" class="navbar-brand">
          <i class="bi bi-bank"></i> Bank Management
        </router-link>
        <button
          class="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarNav"
          aria-controls="navbarNav"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
          <ul class="navbar-nav ms-auto">
            <li class="nav-item">
              <router-link to="/clientes" class="nav-link">Clientes</router-link>
            </li>
            <li class="nav-item">
              <router-link to="/cuentas" class="nav-link">Cuentas</router-link>
            </li>
            <li class="nav-item">
              <router-link to="/transacciones" class="nav-link">Transacciones</router-link>
            </li>
          </ul>
        </div>
      </div>
    </nav>

    <!-- Main Content -->
    <main class="flex-grow-1 py-4">
      <div class="container">
        <router-view></router-view>
      </div>
    </main>

    <!-- Footer -->
    <footer class="bg-dark text-white text-center py-3 mt-4">
      <p class="mb-0">&copy; 2024 Bank Management System. All rights reserved.</p>
    </footer>
  </div>
</template>

<script>
export default {
  name: 'App',
}
</script>

<style scoped>
.min-vh-100 {
  min-height: 100vh;
}

.navbar-brand {
  font-size: 1.5rem;
  font-weight: bold;
}

main {
  flex: 1;
}
</style>
