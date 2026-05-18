<template>
  <div>
    <!-- Tabs para seleccionar tipo de transacción -->
    <ul class="nav nav-tabs mb-4" role="tablist">
      <li class="nav-item" role="presentation">
        <button
          :class="['nav-link', { active: tipoTransaccion === 'consignar' }]"
          @click="tipoTransaccion = 'consignar'"
          type="button"
          role="tab"
        >
          💰 Consignar
        </button>
      </li>
      <li class="nav-item" role="presentation">
        <button
          :class="['nav-link', { active: tipoTransaccion === 'retirar' }]"
          @click="tipoTransaccion = 'retirar'"
          type="button"
          role="tab"
        >
          🏦 Retirar
        </button>
      </li>
      <li class="nav-item" role="presentation">
        <button
          :class="['nav-link', { active: tipoTransaccion === 'transferir' }]"
          @click="tipoTransaccion = 'transferir'"
          type="button"
          role="tab"
        >
          💸 Transferir
        </button>
      </li>
    </ul>

    <div class="card">
      <div class="card-header">
        <h5>{{ getTituloTransaccion() }}</h5>
      </div>
      <div class="card-body">
        <!-- CONSIGNACIÓN -->
        <form v-if="tipoTransaccion === 'consignar'" @submit.prevent="submitConsignacion">
          <div class="mb-3">
            <label for="cuenta_consignar" class="form-label">Número de Cuenta *</label>
            <input
              id="cuenta_consignar"
              v-model="form.numeroCuenta"
              type="text"
              class="form-control"
              placeholder="Ej: 1234567890"
              required
            />
          </div>

          <div class="mb-3">
            <label for="monto_consignar" class="form-label">Monto ($) *</label>
            <input
              id="monto_consignar"
              v-model.number="form.monto"
              type="number"
              step="0.01"
              class="form-control"
              placeholder="0.00"
              required
            />
          </div>

          <AlertMessage
            v-if="successMessage"
            type="success"
            :message="successMessage"
            @close="successMessage = ''"
          />
          <AlertMessage
            v-if="errorMessage"
            type="danger"
            :message="errorMessage"
            @close="errorMessage = ''"
          />

          <button type="submit" class="btn btn-success">
            <i class="bi bi-cash-stack me-2"></i>Consignar Dinero
          </button>
        </form>

        <!-- RETIRO -->
        <form v-if="tipoTransaccion === 'retirar'" @submit.prevent="submitRetiro">
          <div class="mb-3">
            <label for="cuenta_retirar" class="form-label">Número de Cuenta *</label>
            <input
              id="cuenta_retirar"
              v-model="form.numeroCuenta"
              type="text"
              class="form-control"
              placeholder="Ej: 1234567890"
              required
            />
          </div>

          <div class="mb-3">
            <label for="monto_retirar" class="form-label">Monto ($) *</label>
            <input
              id="monto_retirar"
              v-model.number="form.monto"
              type="number"
              step="0.01"
              class="form-control"
              placeholder="0.00"
              required
            />
          </div>

          <AlertMessage
            v-if="successMessage"
            type="success"
            :message="successMessage"
            @close="successMessage = ''"
          />
          <AlertMessage
            v-if="errorMessage"
            type="danger"
            :message="errorMessage"
            @close="errorMessage = ''"
          />

          <button type="submit" class="btn btn-warning">
            <i class="bi bi-wallet2 me-2"></i>Retirar Dinero
          </button>
        </form>

        <!-- TRANSFERENCIA -->
        <form v-if="tipoTransaccion === 'transferir'" @submit.prevent="submitTransferencia">
          <div class="mb-3">
            <label for="cuenta_origen" class="form-label">Cuenta Origen *</label>
            <input
              id="cuenta_origen"
              v-model="form.cuentaOrigen"
              type="text"
              class="form-control"
              placeholder="Ej: 1234567890"
              required
            />
          </div>

          <div class="mb-3">
            <label for="cuenta_destino" class="form-label">Cuenta Destino *</label>
            <input
              id="cuenta_destino"
              v-model="form.cuentaDestino"
              type="text"
              class="form-control"
              placeholder="Ej: 0987654321"
              required
            />
          </div>

          <div class="mb-3">
            <label for="monto_transferir" class="form-label">Monto ($) *</label>
            <input
              id="monto_transferir"
              v-model.number="form.monto"
              type="number"
              step="0.01"
              class="form-control"
              placeholder="0.00"
              required
            />
          </div>

          <AlertMessage
            v-if="successMessage"
            type="success"
            :message="successMessage"
            @close="successMessage = ''"
          />
          <AlertMessage
            v-if="errorMessage"
            type="danger"
            :message="errorMessage"
            @close="errorMessage = ''"
          />

          <button type="submit" class="btn btn-info">
            <i class="bi bi-arrow-left-right me-2"></i>Transferir Dinero
          </button>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import { transaccionService } from '@/services/transaccionService'
import { getErrorMessage, getErrorType } from '@/services/errorHelper'
import AlertMessage from '@/components/AlertMessage.vue'

export default {
  components: { AlertMessage },
  name: 'TransaccionForm',
  emits: ['transaccion-completada'],
  data() {
    return {
      tipoTransaccion: 'consignar',
      form: {
        numeroCuenta: '',
        cuentaOrigen: '',
        cuentaDestino: '',
        monto: '',
      },
      successMessage: '',
      errorMessage: '',
      errorType: 'danger',
    }
  },
  methods: {
    getTituloTransaccion() {
      if (this.tipoTransaccion === 'consignar') return 'Consignar Dinero'
      if (this.tipoTransaccion === 'retirar') return 'Retirar Dinero'
      if (this.tipoTransaccion === 'transferir') return 'Transferir Dinero'
      return 'Transacción'
    },
    async submitConsignacion() {
      try {
        this.errorMessage = ''
        this.successMessage = ''

        const data = {
          numeroCuenta: this.form.numeroCuenta,
          monto: this.form.monto,
        }

        await transaccionService.consignar(data)
        this.successMessage = 'Consignación realizada exitosamente'
        this.form.numeroCuenta = ''
        this.form.monto = ''
        this.$emit('transaccion-completada')
      } catch (error) {
        this.errorMessage = getErrorMessage(error) || 'Error al realizar consignación'
      }
    },
    async submitRetiro() {
      try {
        this.errorMessage = ''
        this.successMessage = ''

        const data = {
          numeroCuenta: this.form.numeroCuenta,
          monto: this.form.monto,
        }

        await transaccionService.retirar(data)
        this.successMessage = 'Retiro realizado exitosamente'
        this.form.numeroCuenta = ''
        this.form.monto = ''
        this.$emit('transaccion-completada')
      } catch (error) {
        this.errorMessage = getErrorMessage(error) || 'Error al realizar retiro'
      }
    },
    async submitTransferencia() {
      try {
        this.errorMessage = ''
        this.successMessage = ''

        const data = {
          cuentaOrigen: this.form.cuentaOrigen,
          cuentaDestino: this.form.cuentaDestino,
          monto: this.form.monto,
        }

        await transaccionService.transferir(data)
        this.successMessage = 'Transferencia realizada exitosamente'
        this.form.cuentaOrigen = ''
        this.form.cuentaDestino = ''
        this.form.monto = ''
        this.$emit('transaccion-completada')
      } catch (error) {
        this.errorMessage = getErrorMessage(error) || 'Error al realizar transferencia'
      }
    },
  },
}
</script>

<style scoped>
.nav-tabs {
  border-bottom: 2px solid #dee2e6;
}

.nav-link {
  color: #6c757d;
  border: none;
  border-bottom: 3px solid transparent;
  padding-bottom: 0.5rem;
}

.nav-link.active {
  color: #0d6efd;
  border-bottom: 3px solid #0d6efd;
  background-color: transparent;
}

.nav-link:hover {
  color: #495057;
}
</style>
