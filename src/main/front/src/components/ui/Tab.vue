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
        &nbsp;
    </li>
  </ul>
</template>

<script>
export default {
  name: "Tab",
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

  created() {
    this.onClick(0, this.items[0]);
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
  box-sizing: border-box;
  overflow: hidden;
  margin-bottom: 40px;
  display: flex;
  // flex-wrap: wrap;
  justify-content: left;
  max-width: 100%;

  > li {
    font-size: 18px;
    font-weight: 700;
    line-height: 53px;
    height: 54px;
    text-align: center;
    box-sizing: border-box;
    transition: background-color 0.5s;
    flex: 1;
    width: 192px;
    border-bottom: 1px solid $gray-line-color;

    &.active{
      border: 1px solid $gray-line-color;
      border-bottom: 0;
      border-top-left-radius: $border-radius;
      border-top-right-radius: $border-radius;
      background-color: #fff;
      color: $turquoise-color;
      cursor: default;
      transition-duration: 0.1s;
    }
    &:not(.blank-item):not(.active){
      border: 1px solid $gray-line-color;
      border-top-left-radius: $border-radius;
      border-top-right-radius: $border-radius;
      background-color: rgba(49, 49, 49, 0.2);
      color: #757575;
      transition-duration: 0.1s;
    }
    &:not(.blank-item):not(.active):hover{
      background-color: #fff;
      color: $turquoise-color;
      transition-duration: 0.1s;
      cursor: pointer;
    }
  }
}
</style>