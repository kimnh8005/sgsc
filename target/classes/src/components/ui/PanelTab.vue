<template>
  <ul class="tab">
    <li v-for="(item, idx) in items"
        :class="{active : (index === idx)}"
        :key="'filled_' + idx"
        @click="onClick(idx, item)">
      {{ item[displayPropName] }}
    </li>
    <li v-for="(item, idx) in getBlankNum(items)"
        :key="'empty_' + idx"
        class="blank-item">
    </li>
  </ul>
</template>

<script>
export default {
  name: "PanelTab",
  props: {
    items: Array,
    displayPropName: String,
    numsInRow: {
      type: Number,
      default: 5
    },
    emitPropList: {
      type: Array,
      required: true
    }
  },

  computed: {
    cssVars(){
      return {
        '--numsInRow': this.numsInRow
      }
    }
  },

  data() {
    return {
      index: 0,
    }
  },

  methods: {
    onClick(idx, item) {
      this.index = idx;
      let d = {}
      // emitPropList 에 항목으로 구성된 Object 객체를 만들어 emit 에 payload 해준다.
      if (this.emitPropList && this.emitPropList.length){
        this.emitPropList.forEach(key => {
          d[key] = item[key];
        })
      } else {
        // 방어코드, emitPropList 가 없으면 표시명이라도 보내준다.
        d[this.displayPropName] = item[this.displayPropName];
      }
      this.$emit('change', d);
    },
    getBlankNum(items){
      let n = 0;
      if (items && items.length){
        n = (this.numsInRow - items.length % this.numsInRow) % this.numsInRow
      }
      return n;
    }
  }
}
</script>

<style scoped lang="scss">
@import "src/assets/scss/variables";
.tab{
  border: 1px solid $gray-line-color;
  border-radius: $border-radius;
  box-sizing: border-box;
  overflow: hidden;
  margin-bottom: 50px;
  display: flex;
  flex-wrap: wrap;
  justify-content: left;
  max-width: 100%;

  > li {
    font-size: 15px;
    font-weight: 700;
    background-color: #fff;
    line-height: 43px;
    height: 45px;
    text-align: center;
    box-sizing: border-box;
    transition: background-color 0.5s;
    flex: 0 1 auto;
    width: 20%;
    border-top: 1px solid $gray-line-color;

    &:nth-child(-n + 5){
      border-top: 0;
    }
    &:not(:nth-child(5n)){
      border-right: 1px solid $gray-line-color;
    }
    &.active{
      background-color: $turquoise-color;
      color: #fff;
      cursor: default;
      transition-duration: 0.1s;
    }
    &:not(.blank-item):not(.active):hover{
      background-color: #b0dcde;
      transition-duration: 0.1s;
      cursor: pointer;
    }
  }
}
</style>