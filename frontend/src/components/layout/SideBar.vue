<template>
  <aside class=" w-64 bg-slate-900">
    <div class="p-6 top-20 left-20 flex items-stretch gap-5" id="head-sidebar">
        <div class="w-8 h-8 rounded-lg border-white flex items-center justify-center shadow-lg shadow-indigo-500/50 bg-indigo-500 p-1">
            <images class="ml-10" color="white" :size="22"/>
        </div>
        <span class="text-2xl tracking-tight text-white">Image Server</span>
    </div>
    <hr>
    <nav class="flex-1 p-4 space-y-2 flex flex-col gap-4" id="body-sidebar">
        <router-link to="/">
            <button class="group w-full h-10 flex items-center gap-4 px-4 pt-6 pb-6 bg-indigo-500 rounded-lg transition-all duration-200 hover:bg-white">
                <span><Image class="text-white group-hover:text-red-500" :size="25"/></span>
                <span class="text-white text-xl pb-1 group-hover:text-red-500">Visualiseur</span>
            </button>
        </router-link>
        
        <router-link to="/galery">
            <button class="group w-full h-10 flex items-center gap-4 px-4 pt-6 pb-6 bg-indigo-500 rounded-lg transition-all duration-200 hover:bg-white">
                <span><LayoutGrid class="text-white group-hover:text-red-500" :size="25"/></span>
                <span class="text-white text-xl pb-1 group-hover:text-red-500">Galerie</span>
            </button>
        </router-link>
        
        <router-link to="/upload">
            <button class="group w-full h-10 flex items-center gap-4 px-4 pt-6 pb-6 bg-indigo-500 rounded-lg transition-all duration-200 hover:bg-white">
                <span><Upload class="text-white group-hover:text-red-500" :size="25"/></span>
                <span class="text-white text-xl pb-1 group-hover:text-red-500">Upload</span>
            </button>
        </router-link>
        
    </nav>
  </aside>
</template>

<script setup>
    import {Images, Image, LayoutGrid, Upload} from 'lucide-vue-next'; 
</script>

<style>
    @import "tailwindcss";
</style>
