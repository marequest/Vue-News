<template>
  <div>
    <b-form @submit="onSubmit"
            @reset="onReset"
            v-if="show" >

      <b-form-group
          id="input-group-1"
          label="Naslov:"
          label-for="input-1">
        <b-form-input
            id="input-1"
            v-model="form.naslov"
            type="text"
            size="lg"
            placeholder="Upisi naslov"
            required
            :state="validation"
        ></b-form-input>
        <b-form-valid-feedback :state="validation">
          Looks Good ✅
        </b-form-valid-feedback>
      </b-form-group>

      <b-form-group id="input-group-2" label="Tekst:" label-for="input-2">
        <b-form-textarea
            id="input-2"
            v-model="form.tekst"
            placeholder="Upisi tekst"
            required
        ></b-form-textarea>
      </b-form-group>


      <b-form-group id="input-group-3" label="Autor Vesti:" label-for="input-3">
        <b-form-input
            id="input-3"
            v-model="form.autorVesti"
            placeholder="Upisi autora vesti"
            required
        ></b-form-input>
      </b-form-group>

      <b-form-group id="input-group-4" label="Kategorija Vesti:" label-for="input-4">
        <b-form-input
            id="input-4"
            v-model="form.kategorijaVesti"
            placeholder="Upisi kategoriju vesti"
            required
        ></b-form-input>
      </b-form-group>

      <b-form-group id="input-group-5" label="Ime Autora:" label-for="input-5">
        <b-form-input
            id="input-5"
            v-model="form.ime_autora_vesti"
            placeholder="Upisi ime autora vesti"
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
  name: "VestiUpdateForma",
  props: ['vestProp'],
  mounted() {
    this.form.id = this.vestProp.id
    this.form.naslov = this.vestProp.naslov
    this.form.tekst = this.vestProp.tekst
    this.form.vremeKreiranja = this.vestProp.vremeKreiranja
    this.form.brojPoseta = this.vestProp.brojPoseta
    this.form.autorVesti = this.vestProp.autorVesti
    this.form.kategorijaVesti = this.vestProp.kategorijaVesti
    this.form.ime_autora_vesti = this.vestProp.ime_autora_vesti
  },
  data() {
    return {
      form: {
        id: '',
        naslov: '',
        tekst: '',
        vremeKreiranja: 0,
        brojPoseta: 0,
        autorVesti: 0,
        kategorijaVesti: 0,
        ime_autora_vesti: ''
      },
      show: true
    }
  },
  methods: {
    onSubmit(event) {
      event.preventDefault()
      alert(JSON.stringify(this.form))

      this.form.vremeKreiranja = new Date()

      // TODO
      this.$axios.put('http://localhost:8081/api/news/' + this.form.id, {
        id: this.form.id,
        naslov: this.form.naslov,
        tekst: this.form.tekst,
        vremeKreiranja: this.form.vremeKreiranja,
        brojPoseta: this.form.brojPoseta,
        autorVesti: this.form.autorVesti,
        kategorijaVesti: this.form.kategorijaVesti,
        ime_autora_vesti: this.form.ime_autora_vesti})
          .then(response => {
            console.log(response)
            alert("Azurirana vest!")
          }).catch(err => {
            console.log(err)
            alert("Greska pri azuriranju vesti!")
          })
    },
    onReset(event) {
      event.preventDefault()
      this.form.id = this.vestProp.id
      this.form.naslov = this.vestProp.naslov
      this.form.tekst = this.vestProp.tekst
      this.form.vremeKreiranja = this.vestProp.vremeKreiranja
      this.form.brojPoseta = this.vestProp.brojPoseta
      this.form.autorVesti = this.vestProp.autorVesti
      this.form.kategorijaVesti = this.vestProp.kategorijaVesti
      this.form.ime_autora_vesti = this.vestProp.ime_autora_vesti
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
      return this.form.naslov.length > 2;
    }
  }
}
</script>

<style scoped>

</style>