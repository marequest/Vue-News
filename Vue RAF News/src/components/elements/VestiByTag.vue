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
      <!--      <router-link to="/vest" :class="{active: this.$router.currentRoute.name === 'vest'}">-->
      <template #cell(ime)="data">
        <b-list-group-item @click="openNews(data.item.id)" href="#" class="flex-column align-items-start">
          <div class="d-flex w-100 justify-content-between">
            <h5 class="mb-1">{{ data.item.naslov }}</h5>
            <small>{{ new Date(data.item.datum) | formatDate}}</small>
          </div>

          <p class="mb-1">{{ data.item.tekst }}</p>

          <small>{{ data.item.kategorija }}</small>
        </b-list-group-item>
      </template>
      <!--      </router-link>-->
    </b-table>

    <b-row>
      <b-col sm="5" md="6" class="my-1">
        <b-form-group
            label="Per page"
            label-for="per-page-select"
            label-cols-sm="6"
            label-cols-md="4"
            label-cols-lg="3"
            label-align-sm="right"
            label-size="sm"
            class="mb-0"
        >
          <b-form-select
              id="per-page-select"
              v-model="perPage"
              :options="pageOptions"
              size="sm"
          ></b-form-select>
        </b-form-group>
      </b-col>
      <b-col sm="7" md="6" class="my-1">
        <b-pagination
            v-model="currentPage"
            :total-rows="totalRows"
            :per-page="perPage"
            align="fill"
            size="sm"
            class="my-0"
            pills
            ref="pagination"
        ></b-pagination>
      </b-col>
    </b-row>
  </div>

</template>

<script>
export default {

  name: "VestiByTag",
  mounted() {
    this.$axios.get('http://localhost:8081/api/tags/' + this.$route.params.id)
        .then(response => {
          this.home = response.data.tag
        }).catch(error => {
          console.log(error)
          alert("Greska u konekciji sa serverom!")
        })

    this.$axios.get('http://localhost:8081/api/news_tags')
        .then(response => {
          for (const data of response.data) {
            if(data.tag_id == this.$route.params.id){
              this.$axios.get('http://localhost:8081/api/news/' + data.news_id)
                .then(response => {
                  if (response.data.naslov == null || response.data.naslov === ""){
                    return;
                  }
                  this.vesti.push({
                    id: response.data.id,
                    naslov: response.data.naslov,
                    tekst: response.data.tekst,
                    kategorija: response.data.kategorijaVesti,
                    datum: response.data.vremeKreiranja})
                })
            }
          }
        }).catch(error => {
          console.log(error)
          alert("Greska sa serverom!")
        })
  },
  data() {
    return {
      tag: this.$route.params.id,
      home: "",
      totalRows: 1,
      currentPage: 1,
      perPage: 10,
      pageOptions: [2, 5, 10, { value: 100, text: "Show a lot" }],

      vesti: [
        // { id:"11", naslov: "NBA Finale sutra na RTS", tekst: "Donec id elit non mi porta gravida at eget metus. Maecenas sed diam eget risus varius blandit.", kategorija: "Sport", datum: "27.5.2023." },
        // { id:"22", naslov: "Novi rezim usluge radnika", tekst: "Donec id elit non mi porta gravida at eget metus. Maecenas sed diam eget risus varius blandit.", kategorija: "Politika", datum: "25.5.2023." },
        // { id:"33", naslov: "Vucic prodaje Kosovo za 2000 dinara!", tekst: "Donec id elit non mi porta gravida at eget metus. Maecenas sed diam eget risus varius blandit.", kategorija: "Politika", datum: "24.5.2023." },
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