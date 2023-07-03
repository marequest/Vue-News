<template>
  <div>
    <b-modal v-model="newUserModal">
      <UserNewForma/>
    </b-modal>
    <b-modal v-model="updateUserModal">
      <UserUpdateForma :userProp="selectedUpdate"/>
    </b-modal>
    <b-table hover head-variant="dark"
             id="pages-table"
             :items="users"
             :fields="fields"
             :current-page="currentPage"
             :per-page="perPage">
      <template
      v-slot:cell(actions)="{item}">
        <b-button-group>
          <b-button
              class="btn btn-primary"
              variant="secondary"
              @click="updateForm(item)">Update</b-button>
        </b-button-group>
        <b-form-group v-if="item.tip === 'content creator'">
          <input type="checkbox" id="checkbox" v-model="item.isActive" @change="updateIsActive(item)">
          <label for="checkbox">is active user</label>
        </b-form-group>
<!--        <b-form-checkbox-->
<!--            v-if="item.tip === 'content creator'"-->
<!--            v-model="item.isActive"-->
<!--            @change="updateIsActive(item)">-->
<!--          is active-->
<!--        </b-form-checkbox>-->
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
    >New User</b-button>
  </div>
</template>

<script>

import UserNewForma from "@/components/admin/UserNewForma";
import UserUpdateForma from "@/components/admin/UserUpdateForma";
export default {
  name: "UsersAdmin",
  components: {UserUpdateForma, UserNewForma},
  mounted() {
    // Set the initial number of items
    this.totalRows = this.users.length

    this.$axios.get('http://localhost:8081/api/users')
        .then(response => {
          for (const data of response.data) {
            this.users.push({ id: data.id, ime: data.ime, prezime: data.prezime, username: data.username, tip: data.tip, isActive: data.active})
          }
          this.totalRows = this.users.length
        }).catch(error => {
          console.log(error)
          alert("Greska u konekciji sa serverom!")
        })

  },
  data() {
    return {
      selectedUpdate: {},
      updateUserModal: false,
      newUserModal: false,
      totalRows: 1,
      currentPage: 1,
      perPage: 10,
      pageOptions: [2, 5, 10, { value: 100, text: "Show a lot" }],

      users: [
      ],
      fields: [
        { key: "ime", label: 'ime' },
        { key: "prezime", label: 'prezime' },
        { key: "username", label: 'email' },
        { key: "tip", label: 'tip korisnika' },
        { key: "actions" }
      ],

    }
  },
  methods: {
    updateIsActive(item){
      console.log(item.ime,
          item.prezime,
          item.username,
          item.tip,
          item.isActive)

      this.$axios.put('http://localhost:8081/api/users/' + item.username, {
        ime: item.ime,
        prezime: item.prezime,
        username: item.username,
        tip: item.tip,
        active: item.isActive
      })
          .then(response => {
            console.log(response)
            alert("Azuriran korisnik!")
          }).catch(error => {
            console.log(error)
            alert("Greska u azuriranju aktivnosti korisnika!")
          })
    },
    showForm() {
      // this.selectedUpdate = {naziv: "", opis: ""}
      this.newUserModal = !this.newUserModal
    },
    updateForm(record){
      this.updateUserModal = !this.updateUserModal
      this.selectedUpdate = record
    },
  }
}
</script>

<style scoped>

table#table-transition-example .flip-list-move {
  transition: transform 1s;
}

</style>
