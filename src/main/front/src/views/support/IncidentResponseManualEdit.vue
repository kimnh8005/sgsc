<template>
  <div>
    <PageTitle title="사고 대응 매뉴얼"/>

    <div class="page-box">
      <PanelTitle title="대응 매뉴얼 등록/수정"/>

      <div class="article-view">
        <div class="article-header edit-mode">
          <div class="item-box">
            <label>
              분류
              <span class="required">*</span>
            </label>
            <select v-model="article.category">
              <option value="all">전체 설비</option>
              <option v-for="item in MUAL_TYPE_CD"
                      :value="item.cd_id"
                      :key="item.cd_id">
                {{ item.cd_nm }}
              </option>
            </select>
          </div>
          <div class="item-box">
            <label>
              제목
              <span class="required">*</span>
            </label>
            <input type="text" v-model="article.title">
          </div>
        </div>

        <div class="article-body edit-mode">
          <vue-editor v-model="article.bodyText" :editor-toolbar="customToolbar" />
        </div>

        <div class="attach-file">
          <div class="attach-inner">
            첨부파일 :
            <input type="text" readonly v-model="article.attach.fileName">
            <input type="file" style="display: none" id="attach">
            <button class="btn-dark"
                    @click="onClickSearchAttach">
              파일 선택
            </button>
          </div>
        </div>
      </div>

      <div class="button-group center-type article-buttons">
        <button @click="goView">취 소</button>
        <button
            @click="doSave"
            :disabled="disableSave"
        >저 장</button>
      </div>

    </div>
    <LoadingSpinner v-if="isShowSpinner"/>
  </div>
</template>

<script>
import PanelTitle from "@/components/ui/PanelTitle";
import PageTitle from "@/components/panels/PageTitle";
import {mapState, mapGetters} from "vuex";
import dayjs from "dayjs";
import { VueEditor } from "vue2-editor/dist/vue2-editor.core.js";
import LoadingSpinner from "@/components/ui/LoadingSpinner";

export default {
  name: "IncidentResponseManualEdit",
  components: {LoadingSpinner, VueEditor, PageTitle, PanelTitle},

  props: {
    mode: {
      type: String,
      required: true
    },
    mual_sid: {
      type: String
    }
  },

  computed: {
    ...mapState('common/manual',[
      'incidentManual'
    ]),
    ...mapState('common/code',[
      'MUAL_TYPE_CD'
    ]),
    ...mapGetters([
      'userData'
    ]),

    disableSave() {
      return (this.article.category === 'all') || (this.article.title.trim() === '') || (this.article.bodyText.trim() === '')
    },
  },
  created() {
    this.queryList();
    this.$store.dispatch('common/code/query', {code: 'MUAL_TYPE_CD'})
  },

  data() {
    return {
      dayjs,

      article: {
        category: 'all',
        title: '',
        bodyText: '',
        attach: {
          fileName : null,
          file: null
        }
      },
      isShowSpinner: false,

      customToolbar: [
        [{ 'header': [false, 1, 2, 3, 4, 5, 6, ] }],
        ['bold', 'italic', 'underline', 'strike'],
        [{'align': ''}, {'align': 'center'}, {'align': 'right'}, {'align': 'justify'}],
        ['blockquote', 'code-block'],
        [{ 'list': 'ordered'}, { 'list': 'bullet' }],
        [{ 'indent': '-1'}, { 'indent': '+1' }],
        [{ 'color': [] }, { 'background': [] }],
        ['link', 'video'],
        ['clean'],
      ]
    }
  },

  methods: {
    queryList(){
      if(this.mode === 'edit'){
        this.$store.dispatch('common/manual/incidentManual', {mual_sid: this.mual_sid})
        .then(() => {
          this.article.title = this.incidentManual.current.mual_title;
          this.article.bodyText = this.incidentManual.current.mual_cnt;
          this.article.category = this.incidentManual.current.mual_type_cd;
          this.article.attach.fileName = this.incidentManual.current.file_nm;
          // this.article.attach.physicalFileName = this.incidentManual.current.file_phy_nm;
        })
      }
    },
    goView(){
      let url = (this.mual_sid) ? '/support/manual/view/' + this.mual_sid : '/support/manual'
      this.$router.push(url)
    },
    onClickSearchAttach(){
      let input = document.getElementById('attach');
      input.onchange = e => {
        this.article.attach.file = e.target.files[0];
        this.article.attach.fileName = this.article.attach.file.name;
      };
      input.click();
    },
    doSave(){
      this.isShowSpinner = true;
      let formData = new FormData();
      formData.append('Content-Type', 'multipart/form-data');
      formData.append('mual_type_cd', this.article.category);
      formData.append('mual_title', this.article.title);
      formData.append('mual_cnt', this.article.bodyText);
      if (this.article.attach.file){
        formData.append('file', this.article.attach.file);
      }

      let stateName;
      if (this.mode === 'edit'){
        stateName = 'common/manual/putIncidentManual';
        formData.append('mual_sid', this.mual_sid)
      } else if (this.mode === 'write'){
        stateName = 'common/manual/postIncidentManual';
      } else if (this.mode === 'delete'){
        stateName = 'common/manual/deleteIncidentManual';
        formData = {'mual_sid': this.mual_sid}
      }
      this.$store.dispatch(stateName, formData)
        .then((response) => {
          if(response.data.resultCode === 'NRL001'){
            this.goView();
          }
          this.isShowSpinner = false;
        })
        .catch(() => {
          this.isShowSpinner = false;
        })
    }
  }
}
</script>

<style scoped lang="scss">
@import '~quill/dist/quill.snow.css';
@import "src/assets/scss/table";

.article-view{

  .item-box{
    display: flex;
    align-items: center;
    padding: 12px 0;

    > h4{
      margin-right: 30px;
    }

    label{
      display: inline-flex;
      flex-shrink: 0;
      width: 80px;

      span.required{
        color: $warning-level-color;
        margin-left: 5px;
        padding-top: 5px;
        line-height: 10px;
        font-size: 20px;
      }
    }
    select {
      min-width: 150px;
      padding-left: 10px;
    }
    input[type=text]{
      width: 100%;
    }
  }

  .article-header{
    border-top: 2px solid $gray-line-color;
    border-bottom: 1px solid $gray-line-color;
    padding: 15px 30px;

    &.edit-mode{
      padding-right: 0;
    }
    
    .manual-type{
      color: $light-font-color;
      font-size: 16px;
      height: 30px;
    }
    h2{
      font-size: 20px;
      font-weight: 400;
      height: 40px;
    }
  }

  .article-body{
    min-height: 100px;
    padding: 30px;

    &.edit-mode{
      padding: 25px 0;

      textarea{
        min-height: 200px;
      }
    }
  }

  .attach-file{
    background-color: #f6f6f6;
    margin-bottom: 28px;
    padding: 2px 0;

    > .attach-inner{
      border: 1px solid $gray-line-color;
      padding: 0 30px;
      display: flex;
      align-items: center;
      border-left: 0;
      border-right: 0;
      min-height: 96px;
      box-sizing: border-box;

      input[type=text]{
        width: 300px;
        margin-left: 20px;
      }
      button{
        border-top-left-radius: 0;
        border-bottom-left-radius: 0;
      }
    }
  }
}
.article-buttons{
   margin-bottom: 40px;
 }
</style>