<template>
  <div class="home-container">
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: "MonitoringHome"
}
</script>

<style scoped lang="scss">
.home-container{
  height: 100%;
  display: flex;
  flex-direction: column;
}
</style>