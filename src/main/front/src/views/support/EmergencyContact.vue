<template>
  <div>
    <PageTitle/>
    <div class="page-box filter-box filtering">
      <h4>검 색</h4>
      <select v-model="search.selected">
        <option v-for="item in search.list"
                :value="item.value"
                :key="item.value"
                :label="item.name"/>
      </select>
      <input type="text"
             v-model="search.query"
             :placeholder="queryPlaceholder"
             @keyup.enter="queryList">
      <button @click="queryList">검 색</button>
    </div>
    <div class="page-box">
      <PanelTitle title="비상 연락망" :desc="total"/>

      <table class="default-table">
        <thead>
        <tr>
          <th class="w80">즐겨찾기</th>
          <th>조직</th>
          <th>소속</th>
          <th class="w200">이름</th>
          <th class="w200">연락처</th>
          <th class="w100"></th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(item, index) in emergencyContactList.list" :key="index">
          <td>*</td>
          <td>{{ item.dept_nm }}</td>
          <td>{{ item.blg_nm }}</td>
          <td>{{ item.name }}</td>
          <td>{{ item.contact }}</td>
          <td>
            <button @click="editContact(item)">수정</button>
          </td>
        </tr>
        </tbody>
      </table>

      <div class="button-group">
        <button @click="writeContact">등 록</button>
      </div>

      <Pagination :page-index="this.pageIndex"
                  :total-count="emergencyContactList.totalcount"
                  :page-size="this.pageSize"
                  @change="onChangePage"/>
    </div>

    <Dialog title="비상연락망" v-if="dialog.isShow" @close="hideDialog">
      <template v-slot:body>
        <div class="form-row">
          <label>조 직</label>
          <!--<select>
            <option>{{ dialog.activeItem.dept_nm }}</option>
          </select>-->
          <input type="text" v-model="dialog.activeItem.dept_nm">
          <label>소 속</label>
          <!--<select>
            <option>{{ dialog.activeItem.blg_nm }}</option>
          </select>-->
          <input type="text" v-model="dialog.activeItem.blg_nm">
        </div>
        <div class="form-row">
          <label>이 름</label>
          <input type="text" v-model="dialog.activeItem.name">
          <label>연락처</label>
          <input type="text" v-model="dialog.activeItem.contact">
        </div>
      </template>

      <template v-slot:footer>
        <button @click="deleteContact" class="btn-dark" v-if="dialog.isEdit">삭 제</button>
        <button @click="hideDialog">취 소</button>
        <button @click="saveContact">저 장</button>
      </template>
    </Dialog>
  </div>
</template>

<script>
import PanelTitle from "@/components/ui/PanelTitle";
import PageTitle from "@/components/panels/PageTitle";
import {mapState} from "vuex";
import Pagination from "@/components/table/Pagination";
import Dialog from "@/components/ui/Dialog";
export default {
  name: "EmergencyContact",
  components: {Dialog, Pagination, PageTitle, PanelTitle},

  computed: {
    ...mapState('common/emergencyContact',[
      'emergencyContactList'
    ]),

    total() {
      return (this.emergencyContactList.totalcount) ? '총 '+ this.emergencyContactList.totalcount + ' 개' : '아직 등록된 항목이 없습니다.'
    },

    queryPlaceholder(){
      let str = '검색어를 입력하십시오'
      if (this.search.selected === 'dept_nm'){
        str = '조직명 ' + str;
      } else if (this.search.selected === 'blg_nm'){
        str = '소속명 ' + str;
      }
      return str;
    }
  },

  created() {
    this.queryList();
  },

  data() {
    return {
      search: {
        selected: 'dept_nm',
        query: '',
        list: [
          {
            name: '조직',
            value: 'dept_nm'
          },
          {
            name: '소속',
            value: 'blg_nm'
          }
        ]
      },
      pageIndex: 1,
      pageSize: 20,
      isShowDialog: false,
      activeItem: {},
      dialog: {
        isShow: false,
        isEdit: false,
        activeItem: {
          emct_sid: null,
          dept_nm: '',
          blg_nm: '',
          name: '',
          contact: ''
        }
      }
    }
  },

  methods: {
    queryList(){
      console.log(this.search.selected, this.search.query)
      let qs = '?pagesize=' + this.pageSize + '&pageindex=' + this.pageIndex;
      qs += (this.search.query.trim() === '') ? '' : '&' + this.search.selected + '=' + this.search.query;
      this.$store.dispatch('common/emergencyContact/emergencyContactList', {queryString: qs});
    },

    onChangePage(index){
      this.pageIndex = index;
      this.queryList();
    },

    writeContact(){
      this.dialog.isEdit = false;
      this.showDialog({
        dept_nm: '',
        blg_nm: '',
        name: '',
        contact: ''
      });
    },

    editContact(info){
      this.dialog.isEdit = true;
      this.showDialog(info);
    },

    deleteContact(){
      let bool = confirm('이 연락처를 삭제하시겠습니까?')
      if (bool){
        let payload = { emct_sid: this.dialog.activeItem.emct_sid }
        this.$store.dispatch('common/emergencyContact/deleteEmergencyContact', payload)
            .then(() => {
              this.queryList();
              this.hideDialog();
            })
      }
    },

    saveContact(){
      // 수정/신규 가 서로 dispatch 명이 다름
      let dispatchName = (this.dialog.isEdit) ? 'common/emergencyContact/putEmergencyContact' : 'common/emergencyContact/postEmergencyContact';
      this.$store.dispatch(dispatchName, this.dialog.activeItem)
          .then(() => {
            this.queryList();
            this.hideDialog();
          });
    },

    showDialog(info){
      this.dialog.isShow = true;
      this.dialog.activeItem = info;
    },

    hideDialog(){
      this.dialog.isShow = false;
    }
  }
}
</script>

<style scoped lang="scss">
@import "src/assets/scss/table";
.filter-box{
  display: flex;
  align-items: center;

  > h4{
    margin-right: 30px;
  }

  select {
    height: 40px;
    min-width: 150px;
  }
  input[type=text]{
    height: 40px;
    margin-left: 30px;
  }
}

</style>