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

            <small>{{ data.item.kategorijaVesti }}</small>
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

  name: "HomePage",
  mounted() {
    this.$axios.get('http://localhost:8081/api/news')
        .then(response => {
          for (const data of response.data) {
            this.vesti.push({ id: data.id, naslov: data.naslov, tekst: data.tekst, kategorijaVesti: data.kategorijaVesti, datum: data.vremeKreiranja})
          }
          this.totalRows = this.vesti.length
        }).catch(error => {
          console.log(error)
          alert("Greska u konekciji sa serverom!")
        })
  },
  data() {
    return {
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