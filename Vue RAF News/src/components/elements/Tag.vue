<template>
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
</template>

<script>
export default {
  name: "TagKomp",
  data() {
    return {
      tags: [],
      dirty: false,
      limit: 8,
    }
  },
  computed: {
    state() {
      // Overall component validation state
      return this.dirty ? (this.tags.length > 0 && this.tags.length < 9) : null
    }
  },
  watch: {
    tags() {
      // Set the dirty flag on first change to the tags array
      this.dirty = true
    }
  },
  methods: {
    tagValidator(tag) {
      // Individual tag validator function
      return tag === tag.toLowerCase() && tag.length > 2 && tag.length < 11
    }
  }
}
</script>

<style scoped>

</style>