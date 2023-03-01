<template>
  <div id="app">
    <LayoutHeader v-if="$route.path !== '/login'"
                  :auth-info="{hasAuth: $route.path !== '/login'}"/>
    <div class="wrapper" :class="{ 'login-wrapper': $route.path === '/login'}">
      <LayoutNavigation v-if="$route.path !== '/login'"/>
      <div class="contents-body">
        <router-view/>
        <LayoutFooter v-if="$route.path !== '/login'"/>
      </div>
    </div>
  </div>
</template>

<script>
import LayoutHeader from "@/components/LayoutHeader";
import LayoutNavigation from "@/components/LayoutNavigation";
import LayoutFooter from "@/components/LayoutFooter";
// import router from "@/router";

export default {
  components: {
    LayoutHeader,
    LayoutNavigation,
    LayoutFooter
  },

  created() {
    // console.log(router.currentRoute)
  }
}
</script>

<style lang="scss">
@import url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@400;700&display=swap');
@import "./assets/scss/common";
</style>
