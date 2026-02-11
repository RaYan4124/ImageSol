<script setup lang="ts">
    import type {Image} from '../../App.vue';
    import { ref } from 'vue';
    defineProps<{
        image: Image;
    }>();
</script>

<template>
  <div class="relative aspect-square overflow-hidden rounded-xl shadow-md cursor-pointer transition duration-300 ease-in-out hover:scale-105 hover:shadow-xl">
    <img
      :src=image.url
      loading="lazy"
      class="h-full w-full object-cover"
    />

    <!-- Overlay -->
    <div
      v-if="image.name"
      class="absolute inset-0 flex items-end
             bg-gradient-to-t from-black/70 via-black/20 to-transparent
             opacity-0 hover:opacity-100 transition-opacity duration-300"
    >
      <span class="p-3 text-sm font-medium text-white">
        {{image.name}}
      </span>
    </div>
  </div>
</template>
<style>
    @import "tailwindcss";
</style>