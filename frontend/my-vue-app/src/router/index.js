import Vue from "vue";
import VueRouter from "vue-router";
import ProductList from "@/components/ProductList.vue";
import ProductDetail from "@/components/ProductDetail.vue";
import OrderList from "@/components/OrderList.vue";
import OrderDetail from "@/components/OrderDetail.vue";
import LoginPage from "@/components/LoginPage.vue";

Vue.use(VueRouter);

const routes = [
  { path: "/login", component: LoginPage },
  { path: "/buyer/:userId/products", component: ProductList },
  { path: "/buyer/:userId/products/:productId/:sellerName", component: ProductDetail },
  { path: "/buyer/:userId/orders", component: OrderList },
  { path: "/buyer/:userId/orders/:id", component: OrderDetail },
  { path: "/", component: LoginPage }
];

const router = new VueRouter({
  mode: "history",
  routes
});

export default router;
