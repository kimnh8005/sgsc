<template>
  <div class="monitoring-container">
    <PageTitle/>
    <div class="iframe-container">
      <iframe src="http://222.233.76.214:5601/app/kibana#/dashboard/Metricbeat-system-overview-ecs?embed=true&_g=()"></iframe>
    </div>
  </div>
</template>

<script>
import PageTitle from "@/components/panels/PageTitle";

export default {
  name: "ServerMonitoring",
  components: {PageTitle}
};
</script>

<style scoped lang="scss">
.monitoring-container{
  height: 100%;
  flex-basis: auto;
  display: flex;
  flex-direction: column;

  .iframe-container{
    height: 100%;
    flex-grow: 0;
    margin-bottom: 30px;

    iframe{
      border: 0;
      height: 100%;
      width: 100%;
    }
  }
}
</style>