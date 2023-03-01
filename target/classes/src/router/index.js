import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import LogIn from '@/views/LogIn'
import store from '../store'

Vue.use(VueRouter);


/**
 * 인증이 있는지 확인하고 없으면 로그인 화면으로 돌려보냄
 * @returns {function(*=, *=, *): (undefined)}
 */
const requireCheck = () => (from, to, next) => {
  // token 있는지 확인하고 없으면 login 으로 돌려보냄.
  if (store.getters.accessToken) {
    // console.log('<<<<< 인증있음 >> to, from:', to, from)
    next()
  } else {
    console.log('<<<<< 인증 실패 >> to, from:', to, from)
    next('/login')
    // location.href = '/login'
  }
}

/**
 * 가스 위험도 분석 대메뉴
 * @type {{redirect: string, path: string, component: {name: string}, children: [{path: string, component: {name: string}, name: string}, {path: string, component: function(): *, name: string}, {path: string, component: function(): *, name: string}], name: string}}
 */
const analysis = {
  path: '/analysis',
  // redirect: '/analysis/realtime',
  name: '가스 위험도 분석',
  icon: 'icon-type1.png',
  beforeEnter: requireCheck(),
  // route level code-splitting
  // this generates a separate chunk (about.[hash].js) for this route
  // which is lazy-loaded when the route is visited.
  component: () => import('../views/analysis/AnalysisHome'),
  children: [
    {
      path: '/analysis/realtime',
      name: '실시간 위험도',
      beforeEnter: requireCheck(),
      component: () => import('../views/analysis/RealtimeRisk')
    },
    {
      path: '/analysis/prediction',
      name: '위험도 예측',
      beforeEnter: requireCheck(),
      component: () => import('../views/analysis/RiskPrediction')
    },
    {
      path: '/analysis/history',
      name: '위험도 이력',
      beforeEnter: requireCheck(),
      component: () => import('../views/analysis/RiskHistory')
    },
    {
      hidden: true, // gnb 에서는 감춤
      props: true, // props 로 데이터 전달.
      path: '/analysis/process/:procs_id',
      name: '공정 상세',
      beforeEnter: requireCheck(),
      component: () => import('../views/analysis/RiskPredictionProcessDetail')
    },
    {
      hidden: true,
      props: true, // props 로 데이터 전달.
      path: '/analysis/equipment/:eqmt_id',
      name: '설비 상세',
      beforeEnter: requireCheck(),
      component: () => import('../views/analysis/RiskPredictionEquipmentDetail')
    }
  ]
}

/**
 * 가스 재난 지원 대메뉴
 * @type {{path: string, component: (function(): *), children: [{path: string, component: function(): *, name: string}, {path: string, component: function(): *, name: string}, {path: string, component: function(): *, name: string}, {path: string, component: function(): *, name: string}], name: string}}
 */
const support = {
  path: '/support',
  // redirect: '/support/simulation',
  name: '가스 재난 지원',
  icon: 'icon-type2.png',
  beforeEnter: requireCheck(),
  component: () => import('../views/support/SupportHome'),
  children: [
    {
      path: '/support/simulation',
      name: '사고 시뮬레이션',
      beforeEnter: requireCheck(),
      component: () => import('../views/support/IncidentSimulation')
    },
    {
      path: '/support/manual',
      name: '사고 대응 매뉴얼',
      beforeEnter: requireCheck(),
      component: () => import('../views/support/IncidentResponseManual')
    },
    {
      hidden: true, // gnb 에서는 감춤
      props: true, // props 로 데이터 전달.
      path: '/support/manual/view/:mual_sid',
      name: '사고 대응 매뉴얼 상세',
      beforeEnter: requireCheck(),
      component: () => import('../views/support/IncidentResponseManualView')
    },
    {
      hidden: true, // gnb 에서는 감춤
      props: true, // props 로 데이터 전달.
      path: '/support/manual/:mode',
      name: '사고 대응 매뉴얼 등록',
      beforeEnter: requireCheck(),
      component: () => import('../views/support/IncidentResponseManualEdit')
    },
    {
      hidden: true, // gnb 에서는 감춤
      props: true, // props 로 데이터 전달.
      path: '/support/manual/:mode/:mual_sid',
      name: '사고 대응 매뉴얼 수정',
      beforeEnter: requireCheck(),
      component: () => import('../views/support/IncidentResponseManualEdit')
    },
    {
      path: '/support/contact',
      name: '비상 연락망',
      beforeEnter: requireCheck(),
      component: () => import('../views/support/EmergencyContact')
    },
    {
      path: '/support/message',
      name: '비상대피 메시지 전송',
      beforeEnter: requireCheck(),
      component: () => import('../views/support/SendEmergencyMessage')
    }
  ]
}

/**
 * 가스 위험성 제안 대메뉴
 * @type {{path: string, component: (function(): *), children: [{path: string, component: function(): *, name: string}, {path: string, component: function(): *, name: string}, {path: string, component: function(): *, name: string}], name: string}}
 */
const proposition = {
  path: '/proposition',
  // redirect: '/proposition/risk',
  name: '가스 위험성 제안',
  icon: 'icon-type3.png',
  beforeEnter: requireCheck(),
  component: () => import('../views/proposition/PropositionHome'),
  children: [
    {
      path: '/proposition/risk',
      name: '밸브 제어 알림',
      beforeEnter: requireCheck(),
      component: () => import('../views/proposition/GasRiskProposition'),
    },
    {
      path: '/proposition/worker-error',
      name: '작업 오류 제안',
      beforeEnter: requireCheck(),
      component: () => import('../views/proposition/WorkerErrorProposition'),
    },
    {
      path: '/proposition/warning-history',
      name: '위험도 알림 이력',
      beforeEnter: requireCheck(),
      component: () => import('../views/proposition/RiskWaringHistory'),
    }
  ]
}

/**
 * CPS 대메뉴
 * @type {{path: string, component: (function(): *), name: string}}
 */
const cps = {
  path: '/cps',
  name: 'CPS',
  icon: 'icon-type4.png',
  beforeEnter: requireCheck(),
  component: () => import('../views/cps/CPS')
}

/**
 *
 * @type {{path: string, component: (function(): Promise<*>), beforeEnter: (function(*=, *=, *): undefined), children: [{path: string, component: function(): Promise<*>, beforeEnter: function(*=, *=, *): undefined, name: string}, {path: string, component: function(): Promise<*>, beforeEnter: function(*=, *=, *): undefined, name: string}], name: string, icon: string}}
 */
const monitoring = {
  path: '/monitoring',
  name: '모니터링',
  icon: 'icon-type4.png',
  beforeEnter: requireCheck(),
  component: () => import('../views/monitoring/MonitoringHome'),
  children: [
    {
      path: '/monitoring/server',
      name: '서버 모니터링',
      beforeEnter: requireCheck(),
      component: () => import('../views/monitoring/ServerMonitoring')
    },
    {
      path: '/monitoring/sensor',
      name: '측정센서 모니터링',
      beforeEnter: requireCheck(),
      component: () => import('../views/monitoring/SensorMonitoring')
    }
  ]
}

/**
 * 작업 관리 대메뉴
 * @type {{path: string, component: (function(): *), children: [{path: string, component: function(): *, name: string}], name: string}}
 */
const journal = {
  path: '/journal',
  name: '작업 일지 관리',
  icon: 'icon-type5.png',
  beforeEnter: requireCheck(),
  component: () => import('../views/work/WorkJournal'),
  /*children: [
    {
      hidden: true, // gnb 에서는 감춤
      props: true, // props 로 데이터 전달.
      path: '/journal/view/:jnl_sid',
      name: '작업 일지 상세',
      beforeEnter: requireCheck(),
      component: () => import('../views/work/WorkJournalView')
    },
    {
      hidden: true, // gnb 에서는 감춤
      props: true, // props 로 데이터 전달.
      path: '/journal/:mode/:jnl_sid',
      name: '작업 일지 수정',
      beforeEnter: requireCheck(),
      component: () => import('../views/work/WorkJournalEdit')
    }
  ]*/
}
const journalView = {
  hidden: true, // gnb 에서는 감춤
  props: true, // props 로 데이터 전달.
  path: '/journal/view/:jnl_sid',
  name: '작업 일지 상세',
  beforeEnter: requireCheck(),
  component: () => import('../views/work/WorkJournalView')
}
const journalEdit = {
  hidden: true, // gnb 에서는 감춤
  props: true, // props 로 데이터 전달.
  path: '/journal/:mode/:jnl_sid',
  name: '작업 일지 수정',
  beforeEnter: requireCheck(),
  component: () => import('../views/work/WorkJournalEdit')
}

/**
 * 공지사항 대메뉴
 * @type {{path: string, component: (function(): *), name: string}}
 */
const notice = {
  path: '/notice',
  name: '공지사항',
  icon: 'icon-type6.png',
  beforeEnter: requireCheck(),
  component: () => import('../views/notice/NoticeList'),
  /*children: [
    {
      hidden: true, // gnb 에서는 감춤
      props: true, // props 로 데이터 전달.
      path: '/notice/view/:noti_sid',
      name: '공지사항 보기',
      beforeEnter: requireCheck(),
      component: () => import('../views/notice/NoticeView')
    },
    {
      hidden: true, // gnb 에서는 감춤
      props: true, // props 로 데이터 전달.
      path: '/notice/:mode',
      name: '공지사항 작성',
      beforeEnter: requireCheck(),
      component: () => import('../views/notice/NoticeEdit')
    },
    {
      hidden: true, // gnb 에서는 감춤
      props: true, // props 로 데이터 전달.
      path: '/notice/:mode/:noti_sid',
      name: '공지사항 수정',
      beforeEnter: requireCheck(),
      component: () => import('../views/notice/NoticeEdit')
    }
  ]*/
}
const noticeView = {
  hidden: true, // gnb 에서는 감춤
  props: true, // props 로 데이터 전달.
  path: '/notice/view/:noti_sid',
  name: '공지사항 보기',
  beforeEnter: requireCheck(),
  component: () => import('../views/notice/NoticeView')
}
const noticeWrite = {
  hidden: true, // gnb 에서는 감춤
  props: true, // props 로 데이터 전달.
  path: '/notice/:mode',
  name: '공지사항 작성',
  beforeEnter: requireCheck(),
  component: () => import('../views/notice/NoticeEdit')
}
const noticeEdit = {
  hidden: true, // gnb 에서는 감춤
  props: true, // props 로 데이터 전달.
  path: '/notice/:mode/:noti_sid',
  name: '공지사항 수정',
  beforeEnter: requireCheck(),
  component: () => import('../views/notice/NoticeEdit')
}


const gnb = [
  analysis,
  proposition,
  support,
  // cps,
  monitoring,
  journal,
  notice
]


/**
 * routes
 * @type {({redirect: string, path: string, component: {components: {HelloWorld: {name: string, props: {msg: StringConstructor}}}, name: string}, name: string}|{redirect: string, path: string, component: {name: string}, children: ({path: string, component: {name: string}, name: string}|{path: string, component: (function(): *), name: string})[], name: string}|{path: string, component: (function(): *), children: ({path: string, component: (function(): *), name: string})[], name: string}|{path: string, component: (function(): *), children: ({path: string, component: (function(): *), name: string})[], name: string}|{path: string, component: (function(): *), name: string})[]}
 */
const routes = [
  {
    path: '*',
    name: 'index.html',
    component: () => import('../views/NotFound'),
    beforeEnter: requireCheck()
  },
  {
    path: '/',
    name: 'Home',
    component: Home,
    redirect: '/analysis/realtime',
    beforeEnter: requireCheck()
  },
  {
    path: '/login',
    name: '로그인',
    component: LogIn
  },
  {
    path: '/mypage',
    name: '마이 페이지',
    beforeEnter: requireCheck(),
    component: () => import('../views/MyPage')
  },
  analysis,
  support,
  proposition,
  cps,
  monitoring,
  journal,
  journalView,
  journalEdit,
  notice,
  noticeView,
  noticeWrite,
  noticeEdit
]

const router = new VueRouter({
  base: '/sgsc',
  mode: 'history',
  routes,
  gnb
})

router.beforeEach((to, from, next) => {
  // 대메뉴 화면이 없는 대메뉴는 이동 금지.
  // 아래의 대메뉴는 그냥 중단. (대 메뉴 자체의 화면이 없음)
  if (from.path === to.path || to.path === analysis.path || to.path === support.path || to.path === proposition.path || to.path === monitoring.path)
    return;

  next();
})

export default router
