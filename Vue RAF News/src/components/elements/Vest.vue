<template>
  <div>
    <h2>{{ naslov }}</h2>
    <small>Autor: {{ autor }} - {{ new Date(datumKreiranja) | formatDate}} - Broj poseta: <b-badge variant="info">{{ brojPoseta }}</b-badge></small>
    <p>{{ tekst }}</p>
    <span v-for="item in tagovi" :key="item.id">
      <b-badge variant="success" class="tag-margina" :to="{name:'tag', params: {id: item.id }}">
          {{ item.tag }}
      </b-badge>
    </span>

    <KomentarKomp :komentari="this.komentari" :vestId="this.id"/>
  </div>
</template>

<script>
import { dateFilter } from "vue-date-fns";
import KomentarKomp from "@/components/elements/Komentar";

export default {
  name: "VestKomp",
  components: {KomentarKomp},
  mounted() {
    this.$axios.get('http://localhost:8081/api/news/' + this.id)
        .then(response => {
          this.naslov = response.data.naslov
          this.tekst = response.data.tekst
          this.datumKreiranja = response.data.vremeKreiranja
          this.brojPoseta = response.data.brojPoseta
          this.autor = response.data.ime_autora_vesti

          this.$axios.put('http://localhost:8081/api/news/' + this.id, {
            id: response.data.id,
            naslov: response.data.naslov,
            tekst: response.data.tekst,
            vremeKreiranja: response.data.vremeKreiranja,
            brojPoseta: response.data.brojPoseta + 1,
            autorVesti: response.data.autorVesti,
            kategorijaVesti: response.data.kategorijaVesti,
            ime_autora_vesti: response.data.ime_autora_vesti
          })
              // .then(response => console.log(response))
              .catch(error => {
                console.log(error)
                alert("Greska pri povecavanju broja poseta!")
              })
        }).catch(error => {
          console.log(error)
          alert("Greska u konekciji sa serverom!")
        })
    this.$axios.get('http://localhost:8081/api/comments')
        .then(response => {
          response.data.forEach(element => {
            if (element.vestKomentara == this.id) {
              this.komentari.push({autor: element.autor, tekst: element.tekst, vremeKreiranja: element.vremeKreiranja})
            }
          })

        }).catch(error => {
          console.log(error)
          alert("Greska u konekciji sa serverom!")
        })
    this.$axios.get('http://localhost:8081/api/news_tags')
        .then(response => {
          response.data.forEach(element => {
            if (element.news_id == this.id) {
              this.$axios.get('http://localhost:8081/api/tags/' + element.tag_id)
                  .then(responseTag => {
                    this.tagovi.push({id:element.tag_id, tag: responseTag.data.tag})
                  })
            }
          })
        }).catch(error => {
          console.log(error)
          alert("Greska u konekciji sa serverom!")
        })
  },
  data() {
    return {
      id: this.$route.params.id,
      naslov: "",
      tekst: "",
      datumKreiranja: new Date(),
      brojPoseta: 1,
      autor: "",
      komentari: [
        // {
        //   autor: "Autor Komentara",
        //   tekst: "Tekst Komentara",
        //   vremeKreiranja: new Date()
        // },
      ],
      tagovi: [
        // {tag: "sport"},
        // {tag: "fudbal"},
        // {tag: "NBA"}
      ]
    }
  },
  filters: {
    date: dateFilter,
  }
}
</script>

<style scoped>
.tag-margina {
  margin-right: 8px;
}
</style>