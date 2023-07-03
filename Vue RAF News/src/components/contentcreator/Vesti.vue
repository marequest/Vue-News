<template>
  <div>
    <b-modal v-model="newNewsModal">
      <VestiNewForma/>
    </b-modal>
    <b-modal v-model="updateNewsModal">
      <VestiUpdateForma :vestProp="selectedUpdate"/>
    </b-modal>
    <b-table hover head-variant="dark"
             id="pages-table"
             :items="vesti"
             :fields="fields"
             :current-page="currentPage"
             :per-page="perPage"
             @row-clicked="otvoriKategoriju">

<!--      <template #cell(autorVesti)="data">-->
<!--        {{ this.$axios.get('http://localhost:8081/api/news/' + data.item.autorVesti) }}-->
<!--      </template>-->

      <template #cell(vremeKreiranja)="data">
        {{ new Date(data.item.vremeKreiranja) | formatDate}}
      </template>

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
    >New News</b-button>
  </div>
</template>

<script>

import VestiNewForma from "@/components/contentcreator/VestiNewForma";
import VestiUpdateForma from "@/components/contentcreator/VestiUpdateForma";
export default {
  name: "VestiCC",
  components: {VestiUpdateForma, VestiNewForma},
  // components: {KategorijaUpdateForma, KategorijaNewForma},
  mounted() {
    // Set the initial number of items
    this.totalRows = this.vesti.length

    this.$axios.get('http://localhost:8081/api/news')
        .then(response => {
          for (const data of response.data) {
            this.vesti.push({
              id:data.id,
              naslov: data.naslov,
              tekst: data.tekst,
              vremeKreiranja: data.vremeKreiranja,
              brojPoseta: data.brojPoseta,
              autorVesti: data.autorVesti,
              kategorijaVesti: data.kategorijaVesti,
              ime_autora_vesti: data.ime_autora_vesti})
          }
          this.totalRows = this.vesti.length
        }).catch(error => {
          console.log(error)
          alert("Greska u konekciji sa serverom!")
        })

  },
  data() {
    return {
      selectedUpdate: {},
      updateNewsModal: false,
      newNewsModal: false,
      totalRows: 1,
      currentPage: 1,
      perPage: 10,
      pageOptions: [2, 5, 10, { value: 100, text: "Show a lot" }],

      vesti: [
      ],
      fields: [
        { key: "naslov", label: 'Naslov', sortable: true },
        { key: "autorVesti", label: 'Autor' },
        { key: "vremeKreiranja", label: 'Datum' },
        { key: "actions" }
      ],

    }
  },
  methods: {
    showForm() {
      this.newNewsModal = !this.newNewsModal
    },
    otvoriKategoriju(record){
      this.$router.push({name:'vest', params: {id: record.id}})
    },
    updateForm(record){
      this.updateNewsModal = !this.updateNewsModal
      this.selectedUpdate = record
    },
    deleteRow(record){
      this.$axios.delete('http://localhost:8081/api/news/' + record.id)
          .then(() => {
            alert("Izbrisana vest!")
          }).catch(error => {
            console.log(error)
            alert("Greska pri brisanju vesti!")
          })

      this.totalRows = this.vesti.length
    }
  }
}
</script>

<style scoped>

table#table-transition-example .flip-list-move {
  transition: transform 1s;
}

</style>
