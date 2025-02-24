<template>
  <div class="product-detail-container" v-if="product">
    <div class="product-card">
      <h2 class="product-name">{{ product.name }}</h2>
      <p class="product-info">📦 Available: {{ product.quantity }}</p>
      <p class="product-info">💰 Price: ${{ product.price }}</p>
      <p class="product-info">📍 Description: {{ product.description }}</p>

      <!-- 只有当 availableNumber > 0 时才显示购买按钮 -->
      <div v-if="product.quantity > 0">
        <div class="quantity-container">
          <form class="product-form" @submit.prevent="handlePurchase">
            <div class="form-row">
              <input
                type="number"
                v-model="quantity"
                class="quantity-input"
                min="1"
                :max="product ? product.quantity : 0"
              />
              <button type="submit" class="purchase-button">Purchase</button>
            </div>
          </form>
        </div>
      </div>
      <p v-else class="out-of-stock">🚫 Out of Stock</p>

      <button @click="handleBackToProductList" class="back-button">Back to Product List</button>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      userId: null,
      productId: null,
      sellerName: null,
      product: null,
      quantity: 1,
    };
  },
  created() {
    console.log("当前路由参数:", this.$route.params);
    this.userId = this.$route.params.userId;
    this.productId = this.$route.params.productId;
    this.sellerName = this.$route.params.sellerName;

    console.log("解析的 userId:", this.userId);
    console.log("解析的 productId:", this.productId);
    console.log("解析的 sellerName:", this.sellerName);

    const apiUrl = `http://localhost:8080/buyer/${this.userId}/products/${this.productId}/${this.sellerName}`;
    console.log("请求的 API URL:", apiUrl);

    this.fetchProductDetail();
  },

  methods: {
    async fetchProductDetail() {
      try {
        const response = await axios.get(
          `http://localhost:8080/buyer/${this.userId}/products/${this.productId}/${this.sellerName}`
        );
        console.log("Product Detail:", response.data); // 查看后端返回的数据
        this.product = response.data;
      } catch (error) {
        console.error("Error fetching product details:", error);
      }
    },

    async handlePurchase() {
      try {
        await axios.post(
          `http://localhost:8080/buyer/${this.userId}/products/${this.productId}/${this.sellerName}`,
          null,
          {
            params: { amount: this.quantity },
          }
        );
        alert("Order made successfully");
        this.$router.push(`/buyer/${this.userId}/orders`);
      } catch (error) {
        console.error("Error making an order:", error);
        alert("Failed to make an order");
      }
    },
    handleBackToProductList() {
      this.$router.push(`/buyer/${this.userId}/products`);
    },
  },
};
</script>

<style>
@import "../assets/styles/ProductDetailCSS.css";
</style>