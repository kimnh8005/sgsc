<template>
  <aside>
    <ul>
      <menu-item
          v-for='(menu, index) in gnb'
          :key="index"
          :menu="menu"
      />
    </ul>
  </aside>
</template>

<script>
import router from "@/router";
import MenuItem from "@/components/ui/MenuItem";

export default {
  name: "layout-navigation",

  components: {
    MenuItem
  },

  computed: {
    gnb() {
      return router.options.gnb;
    }
  }
}
</script>
