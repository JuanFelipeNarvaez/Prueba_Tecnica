import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import ClientesView from '../views/ClientesView.vue'
import CuentasView from '../views/CuentasView.vue'
import TransaccionesView from '../views/TransaccionesView.vue'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: HomeView,
  },
  {
    path: '/clientes',
    name: 'Clientes',
    component: ClientesView,
  },
  {
    path: '/cuentas',
    name: 'Cuentas',
    component: CuentasView,
  },
  {
    path: '/transacciones',
    name: 'Transacciones',
    component: TransaccionesView,
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router
