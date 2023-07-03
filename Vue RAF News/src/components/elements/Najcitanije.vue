<template>
  <div>
    <h1>{{ home }}</h1>

    <b-table hover head-variant="dark"
             id="pages-table"
             :items="vesti"
             :fields="fields"
             :current-page="currentPage"
             :per-page="perPage"
             thead-class="hidden_header">
      <template #cell(ime)="data">
        <b-list-group-item @click="openNews(data.item.id)" href="#" class="flex-column align-items-start">
          <div class="d-flex w-100 justify-content-between">
            <h5 class="mb-1">{{ data.item.naslov }}</h5>
            <small>
              <b-badge variant="info" class="tag-margina">{{ data.item.brojPoseta }}</b-badge>
              {{ new Date(data.item.datum) | formatDate}}
            </small>
          </div>

          <p class="mb-1">{{ data.item.tekst }}</p>

          <small>{{ data.item.kategorija }}</small>
        </b-list-group-item>
      </template>
    </b-table>

  </div>

</template>

<script>
export default {

  name: "NajcitanijeKomp",
  mounted() {
    this.$axios.get('http://localhost:8081/api/news')
        .then(response => {
          const thirtyDays = 1000 * 60 * 60 * 24 * 30;
          for (const data of response.data) {
            if((new Date()) - (new Date(data.vremeKreiranja)) < thirtyDays)
              this.vesti.push({ id: data.id, naslov: data.naslov, tekst: data.tekst, kategorija: data.kategorijaVesti, datum: data.vremeKreiranja, brojPoseta: data.brojPoseta})
          }
          this.vesti.sort((a, b) => {
            if(a.brojPoseta > b.brojPoseta)
              return -1;
            if(a.brojPoseta < b.brojPoseta)
              return 1;
            return 0;
          })
          this.totalRows = this.vesti.length
        }).catch(error => {
          console.log(error)
          alert("Greska u konekciji sa serverom!")
        })
  },
  data() {
    return {
      home: "10 najcitanijih u poslednjih mesec dana",
      totalRows: 1,
      currentPage: 1,
      perPage: 10,
      pageOptions: [2, 5, 10, { value: 100, text: "Show a lot" }],

      vesti: [
        // { id:"11", naslov: "NBA Finale sutra na RTS", tekst: "Donec id elit non mi porta gravida at eget metus. Maecenas sed diam eget risus varius blandit.", kategorija: "Sport", datum: "27.5.2023.", brojPoseta: 42 },
        // { id:"22", naslov: "Novi rezim usluge radnika", tekst: "Donec id elit non mi porta gravida at eget metus. Maecenas sed diam eget risus varius blandit.", kategorija: "Politika", datum: "25.5.2023.", brojPoseta: 2 },
        // { id:"33", naslov: "Vucic prodaje Kosovo za 2000 dinara!", tekst: "Donec id elit non mi porta gravida at eget metus. Maecenas sed diam eget risus varius blandit.", kategorija: "Politika", datum: "24.5.2023.", brojPoseta: 5 },
      ],
      fields: [
        { key: "ime", sortable: true },
      ],
      methods: {
        update() {

        }
      }
    }
  },
  methods: {
    openNews(id) {
      this.$router.push({ name: 'vest', params: { id } })
    }
  },
}
</script>

<style scoped>

</style>

<style>
.hidden_header {
  display: none;
}
</style>