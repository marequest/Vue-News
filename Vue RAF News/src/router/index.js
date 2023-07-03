import Vue from 'vue'
import VueRouter from 'vue-router'
import HomePage from "@/components/HomePage";
import Najcitanije from "@/components/elements/Najcitanije";
import Login from "@/components/elements/Login";
import Vest from "@/components/elements/Vest";
import Kategorija from "@/components/elements/Kategorija";
import Vesti from "@/components/contentcreator/Vesti";
import Kategorije from "@/components/contentcreator/Kategorije";
import Users from "@/components/admin/Users";
import VestiByTag from "@/components/elements/VestiByTag";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'login',
    component: Login
  },
  {
    path: '/home-page',
    name: 'home-page',
    component: HomePage,
    meta: {
      authRequired: true
    }
  },
  {
    path: '/najcitanije',
    name: 'najcitanije',
    component: Najcitanije,
    meta: {
      authRequired: true
    }
  },
  {
    path: '/kategorija/:id',
    name: 'kategorija',
    component: Kategorija,
    meta: {
      authRequired: true
    },
    props: true
  },
  {
    path: '/tag/:id',
    name: 'tag',
    component: VestiByTag,
    meta: {
      authRequired: true
    },
    props: true
  },
  {
    path: '/vest/:id',
    name: 'vest',
    component: Vest,
    meta: {
      authRequired: true
    },
    props: true
  },
  {
    path: '/kategorije',
    name: 'kategorije',
    component: Kategorije,
    meta: {
      authRequired: true
    }
  },
  {
    path: '/vesti',
    name: 'vesti',
    component: Vesti,
    meta: {
      authRequired: true
    }
  },
  {
    path: '/users',
    name: 'users',
    component: Users,
    meta: {
      authRequired: true
    }
  },
]

const router = new VueRouter({
  routes
})

router.beforeEach((to, from, next) => {
  if (to.meta.authRequired) {
    const jwt = localStorage.getItem('jwt');

    if (!jwt) {
      next({name: 'login'});
      return;
    }

    const payload = JSON.parse(atob(jwt.split('.')[1]));
    const expDate = new Date(payload.exp * 1000);
    if (expDate < new Date()) {
      next({name: 'login'});
      return;
    }
  }

  next();
});

export default router
