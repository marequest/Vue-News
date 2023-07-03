<template>
  <div>
    <p>{{this.autor}}</p>
    <b-form-textarea
        id="textarea"
        v-model="tekst"
        placeholder="Enter something..."
        rows="3"
        max-rows="6"
    ></b-form-textarea>
    <b-button @click="submitComment()" variant="primary">Comment</b-button>
  </div>
</template>

<script>



export default {
  name: "KomentarForma",
  props: ['autor', 'vestId'],
  data() {
    return {
      tekst: "Komentar",
    }
  },
  methods: {
    submitComment() {
      this.$axios.post('http://localhost:8081/api/comments', {
        autor: this.autor,
        tekst: this.tekst,
        vremeKreiranja: new Date(),
        vestKomentara: this.vestId
      }).then(() => {
        alert("Dodat komentar")
      }).catch(error => {
        console.log(error)
        alert("Greska pri komentarisanju!")
      })
    }
  }
}
</script>

<style scoped>

</style>