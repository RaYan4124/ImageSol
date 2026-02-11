<script setup lang="ts">
    import type {Image} from '../App.vue';
    import Card from '@/components/layout/Card.vue';
    import { useImages } from '@/Composables/useImages';
    import { ImagePlay, Images } from 'lucide-vue-next';
    import { ref } from 'vue';
    const {imgs} = useImages()
</script>

<template>
    <div class="grid grid-cols-2 sm:grid-cols-3 md:grid-cold-4 gap-4 p-8">
        <Card v-for="value in imgs" :image="value"/>
    </div>
</template>
<style>
    @import "tailwindcss";
</style>