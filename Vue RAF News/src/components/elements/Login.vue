<template>
  <div>
    <b-form inline @submit.prevent="login">
      <label class="sr-only" for="inline-form-input-name">Name</label>
      <b-form-input
          v-model="username"
          id="inline-form-input-name"
          class="mb-2 mr-sm-2 mb-sm-0"
          placeholder="username"
      ></b-form-input>

      <label class="sr-only" for="inline-form-input-username">Username</label>
      <b-input-group class="mb-2 mr-sm-2 mb-sm-0">
        <b-form-input
            type="password"
            v-model="password"
            id="inline-form-input-username"
            placeholder="password"
        ></b-form-input>
      </b-input-group>

      <b-form-checkbox class="mb-2 mr-sm-2 mb-sm-0">Remember me</b-form-checkbox>

      <b-button variant="primary" type="submit" :disabled="!validation">Save</b-button>
    </b-form>
  </div>
</template>

<script>

export default {
  name: "LoginKomp",
  data() {
    return {
      username: "",
      password: ""
    }
  },
  methods: {
    login() {
      this.$axios.post('http://localhost:8081/api/users/login', {
        username: this.username,
        password: this.password,
      }).then(response => {
        localStorage.setItem('jwt', response.data.jwt)
        this.$router.push({name: 'kategorije'});
      }).catch(error => {
        console.log(error)
        alert("Pogresan email ili lozinka!")
      })
    }
  },
  computed: {
    validation() {
      return this.username.length > 0 && this.password.length > 0;
    }
  }
}
</script>

<style scoped>

</style>