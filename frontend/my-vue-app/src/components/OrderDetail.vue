<template>
    <div v-if="order" class="order-detail-container">
      <div class="order-card">
        <h1 class="order-title">Order ID: {{ order.id }}</h1>
        <p class="order-info">Customer Name: {{ order.customerName }}</p>
        <p class="order-info">Product Name: {{ order.productName }}</p>
        <p class="order-info">Item Quantities: {{ order.itemQuantities }}</p>
        <p class="order-info">Total Price: ${{ order.totalPrice.toFixed(2) }}</p>
        <p class="order-info">Time: {{ new Date(order.time).toLocaleString() }}</p>
        <p class="order-status">Status: {{ order.status }}</p>
        <div class="order-action-buttons">
          <button v-if="order.showCancelButton" @click="handleCancelOrder" class="back-button">Cancel Order</button>
          <button @click="handleBackToOrderList" class="back-button">Back to Order List</button>
        </div>
      </div>
      
    </div>
    <div v-else>Loading...</div>
  </template>
  
  <script>
  import axios from "axios";
  
  export default {
    data() {
      return {
        order: null,
      };
    },
    async mounted() {
      try {
        const { userId, id } = this.$route.params;
        const response = await axios.get(`http://localhost:8080/buyer/${userId}/orders/${id}`);
        this.order = response.data;
      } catch (error) {
        console.error("Error fetching order details:", error);
      }
    },
    methods: {
      async handleCancelOrder() {
        try {
          const { userId, id } = this.$route.params;
          await axios.post(`http://localhost:8080/buyer/${userId}/orders/${id}`);
          alert("Order cancelled successfully");
          window.location.reload(); // 重新加载页面
        } catch (error) {
          console.error("Error cancelling order:", error);
          alert("Failed to cancel order");
          window.location.reload(); // 重新加载页面
        }
      },
      handleBackToOrderList() {
        this.$router.push(`/buyer/${this.$route.params.userId}/orders`);
      },
    },
  };
  </script>
  