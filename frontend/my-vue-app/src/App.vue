<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: "App",
};
</script>

<style>
#app {
  width: 100vw;
  max-width: none; /* 解除宽度限制 */
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
}

body {
    margin: 0;
    padding: 0;
    width: 100%;
    max-width: none;
}
</style>
