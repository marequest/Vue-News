<template>
  <div>
    <b-form @submit="onSubmit"
            @reset="onReset"
            v-if="show" >

      <b-form-group id="input-group-1" label="Ime:" label-for="input-1">
        <b-form-input
            id="input-1"
            v-model="form.ime"
            placeholder="Upisi ime"
            required
        ></b-form-input>
      </b-form-group>

      <b-form-group id="input-group-2" label="Prezime:" label-for="input-2">
        <b-form-input
            id="input-2"
            v-model="form.prezime"
            placeholder="Upisi prezime"
            required
        ></b-form-input>
      </b-form-group>

      <b-form-group
          id="input-group-3"
          label="Username:"
          label-for="input-3">
        <b-form-input
            id="input-3"
            v-model="form.username"
            type="text"
            size="lg"
            placeholder="Upisi username"
            required
            :state="validation"
        ></b-form-input>
        <b-form-valid-feedback :state="validation">
          Looks Good ✅
        </b-form-valid-feedback>
      </b-form-group>

      <b-form-group id="input-group-4" label="Tip:" label-for="input-4">
        <b-form-input
            id="input-4"
            v-model="form.tip"
            placeholder="Upisi tip"
            required
        ></b-form-input>
      </b-form-group>

      <b-form-group id="input-group-5" label="Lozinka:" label-for="input-5">
        <b-form-input
            id="input-5"
            v-model="form.password"
            placeholder="Upisi lozinku"
            required
            type="password"
        ></b-form-input>
      </b-form-group>

      <b-form-group>
        <input type="checkbox" id="checkbox" v-model="form.active">
        <label for="checkbox">is active user</label>
      </b-form-group>

      <b-button type="submit" variant="primary" :disabled="!validation">Submit</b-button>
      <b-button type="reset" variant="danger">Reset</b-button>
    </b-form>
    <b-card class="mt-3" header="Form Data Result">
      <pre class="m-0">{{ form }}</pre>
    </b-card>
  </div>
</template>

<script>
import { getSHA256Hash } from "boring-webcrypto-sha256";
export default {
  name: "UserNewForma",
  data() {
    return {
      form: {
        ime: '',
        prezime: '',
        username: '',
        tip: '',
        password: '',
        active: true,
      },
      show: true
    }
  },
  methods: {
    async onSubmit(event) {
      event.preventDefault()

      this.$axios.post('http://localhost:8081/api/users', {
        id: 0,
        ime: this.form.ime,
        prezime: this.form.prezime,
        username: this.form.username,
        tip: this.form.tip,
        password: await getSHA256Hash(this.form.password),
        active: this.form.active
      })
          .then(response => {
            console.log(response)
            alert("Dodata novi korisnik" + JSON.stringify(this.form))
          }).catch(error => {
            console.log(error)
            alert("Greska u dodavanju korisnika!")
          })
    },
    onReset(event) {
      event.preventDefault()

      this.form.ime = ''
      this.form.prezime = ''
      this.form.username = ''
      this.form.tip = ''
      this.form.password = ''
      this.form.active = true
      // Trick to reset/clear native browser form validation state
      this.show = false
      this.$nextTick(() => {
        this.show = true
      })
    }
  },
  computed: {
    validation() {
      // Ovde validiraj da vec ne postoji kategorija sa tim imenom
      // I da je kategorija duza od 2 slova npr
      return this.form.username.length > 3;
    }
  }
}
</script>

<style scoped>

</style>