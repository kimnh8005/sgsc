<template>
  <div>
    <PageTitle/>

    <div class="page-box filtering">
      <ProcessEquipmentFiltering has-date-range-picker
                                 process-title="검 색"
                                 :equipment-title="null"
                                 @filtering="onFiltering"/>
    </div>

    <div class="page-box">
      <div class="simulation-list">
        <div v-for="(item, index) in pagedList"
             :key="index"
             class="simulation-item"
             @click="showAerialPhoto(item)">

          <div class="item-header">
            <div class="item-info">
              <h4>{{ ( item.gasFacId ) ? item.gasFacId : '설비명 없음' }}</h4>
              <div class="item-date">{{ dayjs(item.analysisDt).format('YYYY-MM-DD HH:mm:ss') }}</div>
            </div>
            <div class="item-badge" v-if="item.stability === 'D'">
              누출 위험
            </div>
          </div>
          <div class="item-img">
            <iframe :src="item.diffsMapUrl" frameborder="0"></iframe>
            <div class="prevent-click-screen"></div>
          </div>
        </div>
      </div>

      <Pagination :page-index="this.page.index"
                  :total-count="gasDiffusionList.length"
                  :page-size="this.page.size"
                  @change="onChangePage"/>
    </div>

    <AerialPhotoViewer v-if="aerialPhoto.isShow"
                       :display-data="aerialPhoto.data"
                       @close="hideAerialPhoto"/>
  </div>
</template>

<script>
import PageTitle from "@/components/panels/PageTitle";
import ProcessEquipmentFiltering from "@/components/ui/ProcessEquipmentFiltering";
import {mapState} from "vuex";
import dayjs from 'dayjs';
import Pagination from "@/components/table/Pagination";
import AerialPhotoViewer from "@/components/ui/AerialPhotoViewer";

export default {
  name: "IncidentSimulation",
  components: {AerialPhotoViewer, Pagination, ProcessEquipmentFiltering, PageTitle},

  computed: {
    ...mapState([
      'gasDiffusionList'
    ]),

    pagedList(){
      return this.gasDiffusionList.filter((item, index) => {
        return index >= ((this.page.index - 1) * this.page.size) && index < (this.page.index * this.page.size)
      })
    }
  },

  created() {
    this.queryList();
  },

  data(){
    return {
      dayjs,

      page: {
        index: 1,
        size: 6,
      },

      payload: {
        "gasFacId": '',//TK006
        "fromAnalyDt": '2020-11-05',
        "toAnalyDt": '2020-11-05'
      },

      aerialPhoto: {
        isShow: false,
        data: null
      }
    }
  },

  methods: {
    queryList(){
      this.$store.dispatch('gasDiffusionList', this.payload);
    },

    onFiltering(options){
      this.payload = {
        // gasFacId: 'TK006',
        // fromAnalyDt: '2020-11-05',
        // toAnalyDt: '2020-11-05'

        gasFacId: options.equipmentId,
        fromAnalyDt: dayjs(options.startDate).format('YYYY-MM-DD'),
        toAnalyDt: dayjs(options.endDate).format('YYYY-MM-DD')
      };
      console.log('onFiltering()', options, this.payload)
      // this.page.index = 1;// 필터링 하면 페이지 1로 설정
      this.queryList();
    },

    onChangePage(index){
      this.page.index = index;
      this.queryList();
    },

    showAerialPhoto(info){
      this.aerialPhoto.data = info;
      this.aerialPhoto.isShow = true;
    },

    hideAerialPhoto(){
      this.aerialPhoto.isShow = false;
    }
  }
}
</script>

<style scoped lang="scss">
@import "src/assets/scss/variables";
.simulation-list{
  display: flex;
  flex-wrap: wrap;
  //justify-content: space-between;
  //align-items: flex-start;

  .simulation-item{
    width: 31.4%;
    min-height: 200px;
    flex-shrink: 1;
    border: 1px solid $gray-line-color;
    margin-right: 2.6%;
    margin-bottom: 30px;
    align-self: flex-start;
    border-radius: $half-border-radius;
    padding: 20px 40px 30px 40px;
    box-sizing: border-box;

    &:nth-child(3n){
      margin-right: 0;
    }

    .item-header{
      display: flex;
      align-items: center;
      margin-bottom: 20px;

      h4{
        margin-bottom: 10px;
      }
      .item-date{
        font-size: 14px;
        color: $light-font-color;
      }
      .item-badge{
        margin-left: auto;
        color: $warning-level-color;
        border: 2px solid $warning-level-color;
        border-radius: $border-radius;
        font-size: 14px;
        font-weight: bold;
        height: 40px;
        box-sizing: border-box;
        padding: 9px 10px 10px 35px;
        background: url("~@/assets/img/icon-exclamtion.png") no-repeat 10px center;

      }
    }
    > .item-img{
      position: relative;
      background-color: #eee;
      height: 250px;
      display: flex;

      > img{
        margin: auto;
        width: 100%;
        height: auto;
      }
      > iframe{
        width: 100%;
        height: 100%;
      }
      > .prevent-click-screen{
        position: absolute;
        width: 100%;
        height: 100%;
        top: 0;
        left: 0;
        background-color: rgba(150, 150, 150, 0.3);
        transition: background-color 0.4s;
      }
      &:hover{
        > .prevent-click-screen{
          background-color: transparent;
        }
      }
    }
  }
}

</style>