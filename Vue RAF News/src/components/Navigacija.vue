<template>
  <div>
    <b-navbar toggleable="lg" type="dark" variant="info">
      <b-container>
        <b-navbar-brand><router-link to="/">Logo</router-link></b-navbar-brand>

        <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

        <b-collapse id="nav-collapse" is-nav>
          <b-navbar-nav>
            <b-nav-item ><router-link to="/home-page" :class="{active: this.$router.currentRoute.name === 'home-page'}">Home Page</router-link></b-nav-item>
            <b-nav-item ><router-link to="/najcitanije" :class="{active: this.$router.currentRoute.name === 'najcitanije'}">Najcitanije</router-link></b-nav-item>

            <b-nav-item v-for="kategorija in this.kategorije" :key="kategorija.id">
<!--              @click.native="$router.go()" je privremeno resenje za kesiranje rute koja nije dodata u index.js-->
              <router-link :to="{name:'kategorija', params: {id: kategorija.id }}" @click.native="$router.go()"> {{ kategorija.naziv }}</router-link>
            </b-nav-item>
          </b-navbar-nav>
        </b-collapse>

<!--          <b-navbar-nav class="ml-auto">-->
<!--            <b-nav-item ><router-link to="/profile" :class="{active: this.$router.currentRoute.name === 'profile'}">Profile</router-link></b-nav-item>-->
<!--          </b-navbar-nav>-->

          <b-navbar-nav>
            <form v-if="canLogout" class="d-flex" @submit.prevent="logout">
              <button class="btn btn-outline-secondary" type="submit">Logout</button>
            </form>
          </b-navbar-nav>
      </b-container>
    </b-navbar>

    <b-navbar toggleable="lg" type="dark" variant="success">
      <b-container>
        <b-navbar-brand>Content Creator</b-navbar-brand>
        <b-collapse id="nav-collapse" is-nav>
          <b-navbar-nav>
            <b-nav-item ><router-link to="/kategorije" :class="{active: this.$router.currentRoute.name === 'kategorije'}">Kategorije</router-link></b-nav-item>
            <b-nav-item ><router-link to="/vesti" :class="{active: this.$router.currentRoute.name === 'vesti'}">Vesti</router-link></b-nav-item>
          </b-navbar-nav>
        </b-collapse>

        <b-navbar-nav>
          <b-nav-item ><router-link to="/users" :class="{active: this.$router.currentRoute.name === 'users'}">Korisnici</router-link></b-nav-item>
        </b-navbar-nav>
      </b-container>
    </b-navbar>

  </div>
</template>

<script>

export default {
  name: "NavigacijaKomp",
  props: {
    selectedCategory: String,
  },
  emits: ['selektovana-kategorija'],
  data() {
    return {
      kategorije: []
    }
  },
  mounted() {
    this.$axios.get('http://localhost:8081/api/categories')
        .then(response => {
            for (const cate of response.data) {
              this.kategorije.push({id: cate.id, naziv: cate.naziv, opis: cate.opis})
            }
        }).catch(error => {
          console.log(error)
        })
  },
  computed: {
    canLogout() {
      return this.$route.name !== 'login';
    },
  },
  methods: {
    logout() {
      localStorage.removeItem('jwt');
      this.$router.push({name: 'login'});
    }
  }
}
</script>

<style scoped>

</style>