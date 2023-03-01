<template>
  <div class="panel-title" :class="{underlined : hasUnderline}">
    <h4>{{ title }}</h4>
    <div class="title-desc"
         v-if="desc"
    >{{ desc }}</div>
  </div>
</template>

<script>
export default {
  name: "PanelTitle",

  props: {
    title: String,
    desc: String,
    hasUnderline: Boolean
  }
}
</script>

<style scoped lang="scss">
@import "src/assets/scss/variables";
.panel-title{
  display: flex;
  //margin-top: 10px;
  margin-bottom: 23px;

  &.underlined{
    padding-bottom: 23px;
    border-bottom: 1px solid $gray-line-color;
    margin-bottom: 13px;
  }

  > h4{
    font-size: 18px;
    color: $turquoise-color;
    margin-right: 25px;
  }

  > .title-desc{
    color: #787878;
    font-size: 15px;
    padding-left: 20px;
    border-left: 1px solid $gray-line-color;
  }
}
</style>