<template>
  <div class="screen">
    <div class="loader"></div>
  </div>
</template>

<script>
export default {
  name: "LoadingSpinner"
}
</script>

<style scoped lang="scss">
@import "src/assets/scss/variables";
// variables
$size: 100px !default;
$thickness: 6px !default;
$base-color: #fff !default;
$wheel-color: #cc3300 !default;
$speed: 800ms !default;


// styles
.screen {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  z-index: 99999;
  background-color: rgba(0, 0, 0, 0.1);
  display: flex;

  .loader {
    margin: auto;
    height: $size;
    width: $size;
    border: $thickness solid $base-color;
    border: {
      right-color: $turquoise-color;
      top-color: $turquoise-color;
      radius: 100%;
    }
    animation: spin $speed infinite linear;
  }
}

@keyframes spin {
  from { transform: rotate(0deg); }
  to { transform: rotate(359deg); }
}
</style>