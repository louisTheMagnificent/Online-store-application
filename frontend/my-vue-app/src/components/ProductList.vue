<template>
  <div class="product-list-container">
    <h1 class="product-title">Product List</h1>
    <button @click="handleGoToOrders" class="order-button">Go to Order List</button>

    <div v-if="Object.keys(groupedProducts).length > 0" class="seller-list">
      <div v-for="(products, sellerName) in groupedProducts" :key="sellerName" class="seller-section">
        <h2 class="seller-name">👤 Seller: {{ sellerName }}</h2>
        <div class="product-list">
          <div v-for="product in products" :key="product.productId" class="product-card">
            <h3 class="product-name">{{ product.productName }}</h3>
            <p class="product-info">📦 Available: {{ product.quantity }}</p>
            <button @click="handleViewDetails(product.productId, sellerName)" class="view-button">
              View Details
            </button>
          </div>
        </div>
      </div>
    </div>

    <p v-else class="no-products">No products available</p>
  </div>
</template>
  
  <script>
  import axios from "axios";
  
  export default {
    data() {
      return {
        products: [],
      };
    },
    async mounted() {
      try {
        const { userId } = this.$route.params;
        const response = await axios.get(`http://localhost:8080/buyer/${userId}/products`);
        this.products = response.data;
      } catch (error) {
        console.error("Error fetching products:", error);
      }
    },
    methods: {
      handleViewDetails(productId, sellerName) {
        this.$router.push(`/buyer/${this.$route.params.userId}/products/${productId}/${sellerName}`);
      },
      handleGoToOrders() {
        this.$router.push(`/buyer/${this.$route.params.userId}/orders`);
      },
    },
    computed:{
      groupedProducts() {
        // 按 sellerName 分组
        const grouped = {};
        this.products.forEach(product => {
          if (!grouped[product.sellerName]) {
            grouped[product.sellerName] = [];
          }
          grouped[product.sellerName].push(product);
        });
        return grouped;
      }
    }
  };
  </script>

<style>
@import "../assets/styles/ProductListCSS.css"; /* 引入外部 CSS */
</style>
  