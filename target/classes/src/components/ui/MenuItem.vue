<template>
  <li>
    <div @click="show = !show">
      <router-link :to="menu.path"
              active-class="active">
        <span class="icon">
          <img
              :alt="menu.name + ' 아이콘'"
              :src="require(`@/assets/img/${menu.icon}`)">
        </span>
        <span class="menu-title">
          {{ menu.name }}
        </span>
        <span class="menu-caret" v-if="menu.children && children.length"></span>
      </router-link>
    </div>
    <transition name="slide-fade">
      <ul v-if="show && menu.children">
        <sub-menu-item
            v-for="(subMenu, index) in children"
            :key="index"
            :sub-menu="subMenu"/>
      </ul>
    </transition>
  </li>
</template>

<script>
import SubMenuItem from "@/components/ui/SubMenuItem";
import router from "@/router";

export default {
  name: "menu-item",
  components: {SubMenuItem},
  props: {
    menu: Object
  },

  computed: {
    children() {
      return this.menu.children.filter((item) => {
        return !item.hidden
      })
    }
  },

  created() {
    // console.log(this.menu.name, router.currentRoute.matched[0].name)
    // console.log(router.currentRoute.name)
  },

  data() {
    return {
      show: false
    }
  },

  methods: {
    test(){
      console.log('<<<<<---------------------', this.menu.path , router.currentRoute.matched[0].path)
    }
  }
};
</script>

<style lang="scss" scoped>
li {
  > div {
    height: 80px;
    background-color: #3a3e43;
    font-size: 16px;
    padding: 17px 40px 17px 30px;
    box-sizing: border-box;

    > a {
      display: flex;
      align-items: center;

      > span {
        display: inline-block;
        height: 45px;

        &.icon {
          display: flex;
          align-items: center;
          justify-content: center;
          width: 45px;
          text-align: center;
          border-radius: 45px;
          transition: background-color 1s;
        }

        &.menu-title {
          padding-left: 20px;
          height: 16px;
          line-height: 1em;
          color: #fff;
        }

        &.menu-caret {
          width: 17px;
          height: 16px;
          background: url("../../assets/img/caret-down.png") no-repeat center center;
          margin-left: auto;
        }
      }

      &.active > span.icon {
        background-color: #2d969b;
        transition-duration: 0.2s;
      }
    }
  }
}
</style>