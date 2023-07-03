<template>
  <div>
    <b-form @submit="onSubmit"
            @reset="onReset"
            v-if="show" >
      <b-form-group
          id="input-group-1"
          label="Naziv Kategorije:"
          label-for="input-1"
      >
        <b-form-input
            id="input-1"
            v-model="form.naziv"
            type="text"
            size="lg"
            placeholder="Upisi naziv"
            required
            :state="validation"
        ></b-form-input>
<!--        <b-form-invalid-feedback :state="validation">-->
<!--          Short or existing category-->
<!--        </b-form-invalid-feedback>-->
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
  name: "KategorijaForma",
  props: ['kat'],
  data() {
    return {
      form: {
        id: '',
        naziv: '',
        opis: '',
      },
      show: true
    }
  },
  mounted() {
    this.form.id = this.kat.id
    this.form.naziv = this.kat.naziv
    this.form.opis = this.kat.opis
  },
  methods: {
    onSubmit(event) {
      event.preventDefault()

      this.$axios.put('http://localhost:8081/api/categories/' + this.form.id, {
        id: this.form.id,
        naziv: this.form.naziv,
        opis: this.form.opis})
          .then(response => {
            console.log(response)
            alert("Azurirana kategorija")
          }).catch(error => {
            console.log(error)
            alert("Greska pri azuriranju kategorije!")
          })
    },
    onReset(event) {
      event.preventDefault()
      this.form.naziv = this.kat.naziv
      this.form.opis = this.kat.opis
      // Trick to reset/clear native browser form validation state
      this.show = false
      this.$nextTick(() => {
        this.show = true
      })
    }
  },
  computed: {
    validation() {
      // Ovde validiraj da vec ne postoji kategorija sa tim nazivnom
      // I da je kategorija duza od 2 slova npr
      return this.form.naziv.length > 2;
    }
  }
}
</script>

<style scoped>

</style>