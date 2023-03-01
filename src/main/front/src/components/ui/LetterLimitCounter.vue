<template>
  <span :class="{ 'overflowed' : isOverflow }">
    <span>{{ currentText.length }}</span>
    /
    <span>{{ limit }}</span>
    <span>{{ description }}</span>
  </span>
</template>

<script>
export default {
  name: "LetterLimitCounter",

  props: {
    limit: {
      type: Number,
      required: true
    },
    currentText: {
      type : String,
      required: true
    },
    description: String
  },

  computed: {
    isOverflow(){
      return this.currentText.trim().length > this.limit
    }
  }
}
</script>

<style scoped lang="scss">
@import "src/assets/scss/variables";
.overflowed{
  color: $warning-level-font-color;
}
</style>