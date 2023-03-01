<template>
  <ul class="pagination">
    <li @click="prev"
        :class="{disabled: (pageIndex === 1) || (totalPages === 0)}">
      &lt; 이전
    </li>
    <li v-for="(item, index) in pageList"
        :key="index"
        :class="{active: pageIndex === item}"
        @click="onChange(item)">
      {{ item }}
    </li>
    <li @click="next" :class="{disabled: (pageIndex === totalPages) || (totalPages === 0)}">다음 &gt;</li>
  </ul>
</template>

<script>
export default {
  name: "Pagination",

  props:{
    totalCount: {
      type: Number,
      default: 0
    },
    maxDisplayPageCount: {
      type: Number,
      default: 5
    },
    pageIndex: {
      type: Number,
      default: 1
    },
    pageSize: {
      type: Number,
      default: 100
    }
  },

  computed: {
    totalPages(){
      return Math.ceil(this.totalCount / this.pageSize);
    },
    pageCount() {
      return ( this.totalPages > this.maxDisplayPageCount ) ? this.maxDisplayPageCount : this.totalPages;
    },
    pageList() {
      let start = (this.pageIndex > 3) ? this.pageIndex - 2 : 1;
      let diff = this.totalPages - this.pageIndex;
      start = (this.totalPages > this.maxDisplayPageCount && diff < 3) ? start - (this.maxDisplayPageCount - diff - 3) : start;
      let list = [];
      for (let i = 0; i < this.pageCount; ++i){
        list.push(start + i)
      }
      return list;
    }
  },

  methods: {
    onChange(index){
      if (this.pageIndex === index)
        return

      this.$emit('change', index);
    },
    prev(){
      if (this.pageIndex === 1)
        return

      this.onChange(this.pageIndex - 1);
    },
    next(){
      if (this.pageIndex === this.totalPages)
        return

      this.onChange(this.pageIndex + 1);
    }
  }
}
</script>

<style scoped lang="scss">
@import "src/assets/scss/variables";

.pagination{
  display: flex;
  justify-content: center;

  > li{
    padding: 12px;
    border-radius: $half-border-radius;
    font-size: 15px;
    margin: 0 1px;
    cursor: pointer;
    box-sizing: border-box;
    border: 1px solid transparent;

    &:hover:not(.disabled){
      border-color: $gray-line-color;
    }
    &.disabled{
      opacity: 0.3;
      cursor: default;;
    }
    &.active{
      border-color: $gray-line-color;
      color: $turquoise-text-color;
      font-weight: 700;
      cursor: default;
    }
  }
}
</style>