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

      <b-form-group id="input-group-6" label="Tagovi:" label-for="input-6">
        <div>
          <b-form-group label-for="tags-validation" :state="state">
            <b-form-tags
                input-id="tags-validation"
                v-model="tags"
                :input-attrs="{ 'aria-describedby': 'tags-validation-help' }"
                :tag-validator="tagValidator"
                :state="state"
                separator=" "
                :limit="limit"
                tag-variant="success"
            ></b-form-tags>

            <template #invalid-feedback>
              You must provide at least one tag and no more than 8
            </template>

            <template #description>
              <div id="tags-validation-help">
                Tags must be 3 to 10 characters in length and all lower
                case. Enter tags separated by spaces or press enter.
              </div>
            </template>
          </b-form-group>
        </div>
      </b-form-group>


      <b-button type="submit" variant="primary" :disabled="!validation">Submit</b-button>
      <b-button type="reset" variant="danger">Reset</b-button>
    </b-form>
    <b-card class="mt-3" header="Form Data Result">
      <pre class="m-0">{{ form }}</pre>
      <pre>{{tags}}</pre>
    </b-card>
  </div>
</template>

<script>

export default {
  name: "VestiNewForma",
  data() {
    return {
      form: {
        naslov: '',
        tekst: '',
        vremeKreiranja: 0,
        brojPoseta: 0,
        autorVesti: 0,
        kategorijaVesti: 0,
        ime_autora_vesti: '',
      },
      show: true,
      // Tags
      tags: [],
      dirty: false,
      limit: 8,
    }
  },
  methods: {
    onSubmit(event) {
      event.preventDefault()
      this.form.vremeKreiranja = new Date()
      this.$axios.post('http://localhost:8081/api/news', {
        id: 0,
        naslov: this.form.naslov,
        tekst: this.form.tekst,
        vremeKreiranja: this.form.vremeKreiranja,
        brojPoseta: this.form.brojPoseta,
        autorVesti: this.form.autorVesti,
        kategorijaVesti: this.form.kategorijaVesti,
        ime_autora_vesti: this.form.ime_autora_vesti
      })
          .then(responseNews => {
            console.log(responseNews)
            alert("Dodata nova vest!")

            this.$axios.get('http://localhost:8081/api/tags')
                .then(responseTag => {
                  for (const tag of this.tags){
                    let zapamtiTagId = 0
                    let tagPostoji = false
                    for (const data of responseTag.data) {
                      if(data.tag == tag){
                        zapamtiTagId = data.id
                        tagPostoji = true
                      }
                    }
                    if(!tagPostoji){
                      this.$axios.post('http://localhost:8081/api/tags', {
                        id: 0,
                        tag: tag
                      }).then(response => {
                        this.$axios.post('http://localhost:8081/api/news_tags', {
                          id: 0,
                          news_id: responseNews.data.id,
                          tag_id: response.data.id
                        })
                      }).catch(error => console.log(error))
                    } else {
                      this.$axios.post('http://localhost:8081/api/news_tags', {
                        id: 0,
                        news_id: responseNews.data.id,
                        tag_id: zapamtiTagId
                      }).catch(error => console.log(error))
                    }
                  }
                })
            .catch(error => {
              console.log(error)
              alert("Greska u konekciji sa serverom!")
            })
          })
          .catch(err => {
            console.log(err)
            alert("Greska pri dodavanju nove vesti!")
          })
    },
    onReset(event) {
      event.preventDefault()

      this.form.naslov = '',
      this.form.tekst = '',
      this.form.vremeKreiranja = 0,
      this.form.brojPoseta = 0,
      this.form.autorVesti = 0,
      this.form.kategorijaVesti = 0,
      this.form.ime_autora_vesti = ''
      this.tags = []
      // Trick to reset/clear native browser form validation state
      this.show = false
      this.$nextTick(() => {
        this.show = true
      })
    },
    tagValidator(tag) {
      // Individual tag validator function
      return tag === tag.toLowerCase() && tag.length > 2 && tag.length < 11
    }
  },
  watch: {
    tags() {
      // Set the dirty flag on first change to the tags array
      this.dirty = true
    }
  },
  computed: {
    state() {
      // Overall component validation state
      return this.dirty ? (this.tags.length > 0 && this.tags.length < 9) : null
    },
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