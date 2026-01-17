import { createRouter, createWebHistory } from 'vue-router'
import PkIndexView from '@/views/pk/PkIndexView.vue'
import RanklistIndexView from '@/views/ranklist/RanklistIndexView.vue'
import RecordIndexView from '@/views/record/RecordIndexView.vue'
import UserBotIndexView from '@/views/user/bot/UserBotIndexView.vue'
import NotFound from '@/views/error/NotFound.vue'
import UserAccountRegisterView from '@/views/user/account/UserAccountRegisterView.vue'
import UserAccountLoginView from '@/views/user/account/UserAccountLoginView.vue'
import store from '../store/index'

const routes = [
  {
    path: "/",
    name: "home",
    redirect: "/pk/",
    meta: { needAuth: true },
  },
  {
    path: "/pk/",
    name: "pk_index",
    component: PkIndexView,
    meta: { needAuth: true },
  },
  {
    path: "/record/",
    name: "record_index",
    component: RecordIndexView,
    meta: { needAuth: true },
  },
  {
    path: "/ranklist/",
    name: "ranklist_index",
    component: RanklistIndexView,
    meta: { needAuth: true },
  },
  {
    path: "/user/bot/",
    name: "user_bot_index",
    component: UserBotIndexView,
    meta: { needAuth: true },
  },
  {
    path: "/user/account/register/",
    name: "user_account_register",
    component: UserAccountRegisterView,
    meta: { needAuth: false },
  },
  {
    path: "/user/account/login/",
    name: "user_account_login",
    component: UserAccountLoginView,
    meta: { needAuth: false },
  },
  {
    path: "/404/",
    name: "not_found",
    component: NotFound,
    meta: { needAuth: false },
  },
  {
    path: "/:catchAll(.*)",
    redirect: "/404/"
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  if (to.meta.needAuth && !store.state.user.is_login) {
    next({ name: 'user_account_login' });
  } else {
    next();
  }
})

export default router
