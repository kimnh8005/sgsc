<template>
  <div class="panel-title">
    <h5>{{ title }}</h5>
    <div class="title-desc"
         v-if="desc"
    >{{ desc }}</div>
    <div class ="filler"></div>
    <slot name="button"></slot>
  </div>
</template>

<script>
export default {
  name: "PanelSubTitle",

  props: {
    title: String,
    desc: String
  }
}
</script>

<style scoped lang="scss">
@import "src/assets/scss/variables";

.panel-title{
  display: flex;
  margin-top: 10px;
  margin-bottom: 25px;
  height: 16px;
  align-items: center;

  > h5{
    font-size: 16px;
    color: $default-font-color;
    margin-right: 25px;
  }

  > .title-desc{
    color: #787878;
    font-size: 15px;
    padding-left: 20px;
    border-left: 1px solid $gray-line-color;
  }
}

.filler {
  flex-grow: 1;
}

</style>