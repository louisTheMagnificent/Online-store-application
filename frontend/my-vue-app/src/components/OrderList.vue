<template>
  <div class="order-list-container">
    <h1 class="order-title">Order List</h1>
    <button @click="handleGoToProducts" class="go-to-product-button">Go to Product List</button>
    <div v-if="orders.length > 0" class="order-list">
      <div v-for="order in sortedOrders" :key="order.id" class="order-card">
        <h2 class="order-info">User name: {{ order.username }}</h2>
        <p class="order-info">Order ID: {{ order.id }}</p>
        <p class="order-info">Product name: {{ order.productName }}</p>
        <p class="order-info">Item quantity: {{ order.itemQuantity }}</p>
        <button @click="handleViewDetails(order.id)" class="view-order-button">View Details</button>
      </div>
    </div>
    <p v-else class="no-orders">No orders available</p>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      orders: [],
    };
  },
  computed: {
    // 按 ID 升序排列订单
    sortedOrders() {
      return [...this.orders].sort((a, b) => a.id - b.id);
    }
  },
  async mounted() {
    try {
      const { userId } = this.$route.params;
      const response = await axios.get(`http://localhost:8080/buyer/${userId}/orders`);
      this.orders = response.data;
    } catch (error) {
      console.error("Error fetching orders:", error);
    }
  },
  methods: {
    handleViewDetails(orderId) {
      this.$router.push(`/buyer/${this.$route.params.userId}/orders/${orderId}`);
    },
    handleGoToProducts() {
      this.$router.push(`/buyer/${this.$route.params.userId}/products`);
    },
  },
};
</script>

<style>
@import "../assets/styles/OrderListCSS.css";
</style>