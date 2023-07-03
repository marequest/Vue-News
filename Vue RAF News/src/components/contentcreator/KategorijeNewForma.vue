<template>
  <div>
    <b-form @submit="onSubmit"
            @reset="onReset"
            v-if="show" >
      <b-form-group
          id="input-group-1"
          label="Ime Kategorije:"
          label-for="input-1"
      >
        <b-form-input
            id="input-1"
            v-model="form.ime"
            type="text"
            size="lg"
            placeholder="Upisi ime"
            required
            :state="validation"
        ></b-form-input>
        <b-form-valid-feedback :state="validation">
          Looks Good ✅
        </b-form-valid-feedback>
      </b-form-group>

      <b-form-group id="input-group-2" label="Opis:" label-for="input-2">
        <b-form-input
            id="input-2"
            v-model="form.opis"
            placeholder="Upisi opis"
            required
        ></b-form-input>
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
export default {
  name: "KategorijaNewForma",
  data() {
    return {
      form: {
        ime: '',
        opis: '',
      },
      show: true
    }
  },
  methods: {
    onSubmit(event) {
      event.preventDefault()
      // console.log(0, this.form.ime, this.form.opis)
      this.$axios.post('http://localhost:8081/api/categories', {
        id: 0,
        naziv: this.form.ime,
        opis: this.form.opis})
          .then(response => {
            console.log(response)
            alert("Dodata nova kategorija" + JSON.stringify(this.form))
          }).catch(error => {
            console.log(error)
            alert("Greska u konekciji sa serverom!")
          })
    },
    onReset(event) {
      event.preventDefault()
      this.form.ime = ''
      this.form.opis = ''
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
      return this.form.ime.length > 2;
    }
  }
}
</script>

<style scoped>

</style>