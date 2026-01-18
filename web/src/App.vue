<template>
  <NavBar/>
  <router-view/>
</template>

<script>
import NavBar from "@/components/NavBar.vue"

export default {
  components: {
    NavBar
  },
}
</script>

<style>
body {
  background-image: url("@/assets/images/yb.jpg");
  background-size: cover;
}
</style>
