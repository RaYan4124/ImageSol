<script setup lang="ts">
    import type {Image} from '../App.vue';
    import CustomSelect from '@/components/layout/CustomSelect.vue';
    import { useImages } from '@/Composables/useImages';
    import { ref, computed } from 'vue';

    const selectedId = ref<number | null>(null)
    const {imgs} =  useImages()
    const imageToShow = computed(() => imgs.value.find(img => img.id === selectedId.value)?.url ?? null) //? = optional chaining, permet d'acceder a une propriété qui pourrait ne pas exister sans crash
    

    function handleSelector(id: number){
        selectedId.value = id
    }
</script>

<template>
    <div class="pt-8 px-11 flex flex-col gap-8">
        <CustomSelect @selected-image="handleSelector" :images="imgs"/>
        <div class="bg-white rounded-xl shadow-sm border border-slate-200 h-[600px] flex items-center justify-center relative overflow-hidden">
            <img :src="imageToShow" alt="">
        </div>
    </div>
</template>
<style>
    @import "tailwindcss";
</style>