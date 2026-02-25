<script setup lang="ts">
  import SideBar from './components/layout/SideBar.vue'
  import { useRoute } from 'vue-router';
  import { computed, ref } from 'vue'

  export interface Image{
    id: number;
    name: string;
    url: string;
  }

  const route = useRoute()
  const ActualPage = computed(() => route.meta.title || "Visualiseur") // computed reagi au changement de route 

</script>

<template>
  <div class="min-h-screen w-full  bg-[#fafcfb] flex items-center justify-center p-4">
    <div class="w-full h-full max-w-6xl bg-blue-500 rounded-2xl shadow-2xl flex overflow-hidden">
      <SideBar/>
      <main class="flex-1 bg-[#edeff2] relative overflow-y-auto overflow-x-hidden text-white">
        <header class="bg-white h-[110px] px-8 py-7">
          <h1 class="text-3xl font-bold text-black">{{ ActualPage }}</h1>
          <span class="text-gray-500">Gérez et stoquer vos images facilement.</span>
        </header>
        <div>
          <router-view/>
        </div>
      </main>
    </div>
  </div>
</template>

<style>
  html, body {
    margin: 0;
    padding: 0 ;
    width: 100% ;
    min-height: 100%;
  }

  #app {
    max-width: none;
    margin: 0;
    padding: 0;
    width: 100vw;
    display: flex ; 
    place-items: unset; 
  }
</style>
