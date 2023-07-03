<template>
  <div>
    <KomentarForma :autor="'Ime Korisnika'" :vestId="vestId"/>
    <div v-for="komentar in sortedArray" :key="komentar.id">
      <h4>{{ komentar.autor }} - <small>{{ new Date(komentar.vremeKreiranja) | formatDate}}</small></h4>
      <p>{{ komentar.tekst }}</p>
    </div>

  </div>
</template>

<script>

import {dateFilter} from "vue-date-fns";
import KomentarForma from "@/components/elements/KomentarForma";

export default {
  name: "KomentarKomp",
  components: {KomentarForma},
  props: ['komentari', 'vestId'],
  data() {
    return {

    }
  },
  computed: {
    sortedArray() {
      let array = this.komentari
      console.log(array)
      return array.sort((a, b) => {
        if (a.vremeKreiranja < b.vremeKreiranja) {
          return 1
        }
        if (a.vremeKreiranja > b.vremeKreiranja) {
          return -1
        }
        return 0
      });
    }
  },
  filters: {
    date: dateFilter,
  }
}
</script>

<style scoped>

</style>