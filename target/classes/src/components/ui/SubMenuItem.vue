<template>
  <li>
    <router-link
        :to="subMenu.path"
        exact-active-class="active">
      {{ subMenu.name }}
    </router-link>
  </li>
</template>

<script>
export default {
  name: "sub-menu-item",
  props: {
    subMenu: Object
  },
}
</script>

<style scoped lang="scss">
li{
  background-color: #64686c;
  height: 50px;
  display: flex;
  align-items: center;
  padding-left: 95px;

  > a{
    font-size: 15px;
    color: #a1a1a1;
    
    &.active{
      color: #fff;
    }
  }
}
</style>