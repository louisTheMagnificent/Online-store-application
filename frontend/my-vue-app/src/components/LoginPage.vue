<template>
  <div class="login-container">
    <h2>Login</h2>
    <form @submit.prevent="handleLogin">
      <div class="input-group">
        <input v-model="username" type="text" placeholder="Username" />
      </div>
      <div class="input-group">
        <input v-model="password" type="password" placeholder="Password" />
      </div>
      <button class="login-button" type="submit">Login</button>
      <p v-if="userId">User ID: {{ userId }}</p>
    </form>
  </div>
</template>


<style>
  @import "../assets/styles/LoginPageCSS.css";
</style>
  
  <script>


  
  import axios from "axios";
  
  export default {
    data() {
      return {
        username: "",
        password: "",
        userId: null,
      };
    },
    methods: {
      async handleLogin(event) {
        event.preventDefault(); // ✅ 防止表单默认提交导致页面刷新
        console.log("handleLogin called");

        try {
          const params = new URLSearchParams();
          params.append("username", this.username);
          params.append("password", this.password);

          const response = await axios.post("http://localhost:8080/login", params);
          
          console.log("Login response:", response.data);

          if (response.data && response.data.userId) {
            this.userId = response.data.userId; // ✅ 确保 userId 赋值
            console.log("Redirecting to:", `/buyer/${this.userId}/products`);

            // ✅ 让 Vue 在 userId 更新后跳转，避免数据未更新的问题
            this.$nextTick(() => {
              this.$router.push(`/buyer/${this.userId}/products`);
            });
          } else {
            alert("Login failed: No userId returned.");
          }
        } catch (error) {
          console.error("Login failed:", error);
          alert("Username or password is incorrect");
        }
      }
    },
  };
  </script>

  