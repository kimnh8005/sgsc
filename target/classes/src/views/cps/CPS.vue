<template>
  <div>
    <PageTitle/>
    <div class="page-box">
      <PanelTab display-prop-name="title"
                :emit-prop-list="['title', 'cps_id', 'url']"
                :nums-in-row="4"
                :items="tabData" @change="onTabChange"/>
      <div class="cps-image">
        <img :src="selected.url" :alt="selected.title">
      </div>
    </div>
  </div>
</template>

<script>
import PageTitle from "@/components/panels/PageTitle";
import PanelTab from "@/components/ui/PanelTab";
import cpsData from "@/modules/cps-data";

export default {
  name: "CPS",
  components: {PanelTab, PageTitle},

  mounted() {
    this.selected = cpsData[0];
  },

  data() {
    return {
      tabData: cpsData,
      selected: {
        title: '',
        cps_id: null,
        url: ''
      }
    }
  },

  methods: {
    onTabChange(info){
      this.selected = info;
    }
  }
}
</script>

<style scoped lang="scss">
@import "src/assets/scss/variables";
.tab{
  margin-bottom: 30px;
}
.cps-image{
  min-height: 400px;
  background-color: #efefef;
  border: 1px solid $gray-line-color;

  img{
    width: 100%;
  }
}
</style>