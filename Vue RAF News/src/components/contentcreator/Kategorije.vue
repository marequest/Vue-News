<template>
  <div>
    <b-modal v-model="newCatModal">
      <KategorijeNewForma/>
    </b-modal>
    <b-modal v-model="updateCatModal">
      <KategorijeUpdateForma :kat="selectedUpdate"/>
    </b-modal>
    <b-table hover head-variant="dark"
             id="pages-table"
             :items="kategorije"
             :fields="fields"
             :current-page="currentPage"
             :per-page="perPage"
             @row-clicked="otvoriKategoriju">
      <template
      v-slot:cell(actions)="{item}">
        <b-button-group>
          <b-button
              class="btn btn-primary"
              variant="secondary"
              @click="updateForm(item)">Update</b-button>
          <b-button
              class="btn btn-danger"
              variant="danger"
              @click="deleteRow(item)">Delete</b-button>
        </b-button-group>
      </template>
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
        ></b-pagination>
      </b-col>
    </b-row>
    <b-button
        class="btn btn-primary"
        variant="primary"
        @click="showForm()"
    >New Category</b-button>
  </div>
</template>

<script>
import KategorijeUpdateForma from "@/components/contentcreator/KategorijeUpdateForma";
import KategorijeNewForma from "@/components/contentcreator/KategorijeNewForma";

export default {
  name: "KategorijeCC",
  components: {KategorijeUpdateForma, KategorijeNewForma},
  mounted() {
    // Set the initial number of items
    this.totalRows = this.kategorije.length

    this.$axios.get('http://localhost:8081/api/categories')
        .then(response => {
          for (const data of response.data) {
            this.kategorije.push({ id: data.id, naziv: data.naziv, opis: data.opis})
          }
          this.totalRows = this.kategorije.length
        }).catch(error => {
          console.log(error)
          alert("Greska u konekciji sa serverom!")
        })

  },
  data() {
    return {
      selectedUpdate: {},
      updateCatModal: false,
      newCatModal: false,
      totalRows: 1,
      currentPage: 1,
      perPage: 10,
      pageOptions: [2, 5, 10, { value: 100, text: "Show a lot" }],

      kategorije: [
      ],
      fields: [
        { key: "naziv", sortable: true },
        { key: "opis", label: 'Kratki Opis' },
        { key: "actions" }
      ],

    }
  },
  methods: {
    showForm() {
      // this.selectedUpdate = {naziv: "", opis: ""}
      this.newCatModal = !this.newCatModal
    },
    otvoriKategoriju(record){
      this.$router.push({name:'kategorija', params: {id: record.id}})
    },
    updateForm(record){
      this.selectedUpdate = record
      this.updateCatModal = !this.updateCatModal
    },
    deleteRow(record){
      this.$axios.get('http://localhost:8081/api/news')
          .then((response) => {
            let neMozeDaSeObrise = false;
            for (const data of response.data) {
              // eslint-disable-next-line no-empty
              if(record.id == data.kategorijaVesti){
                neMozeDaSeObrise = true;
              }
            }
            if(!neMozeDaSeObrise){
              this.$axios.delete('http://localhost:8081/api/categories/' + record.id)
                  .then(() => {
                    alert("Izbirsana kategorija!")
                  }).catch(error => {
                    console.log(error)
                    alert("Greska pri brisanju kategorije!")
                  })
            }
            this.totalRows = this.kategorije.length
          }).catch(error => {
            console.log(error)
            alert("Greska u konekciji sa serverom!")
          })
    }
  }
}
</script>

<style scoped>

table#table-transition-example .flip-list-move {
  transition: transform 1s;
}

</style>
