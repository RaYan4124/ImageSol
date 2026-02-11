<script setup lang="ts">
    import type {Image} from '../../App.vue';
    import { computed, ref } from 'vue';
    const props = defineProps<{
        images: Image[];
    }>();

    const emit = defineEmits<{
        'selected-image': [id: number]
    }>();

    const isSelectOpen = ref(false);
    const selectedId = ref(0)
    const emition = (e: number)=>{
        emit('selected-image', e)
        selectedId.value = e
    }

    const miniImage = computed(()=> props.images.find((img) => img.id == selectedId.value) || props.images[0])
</script>

<template>
    <div class="text-gray-500 bg-white relative rounded-2xl border-1 p-4 w-[800px]">
        <span class="text-base font-bold">Choisir une image</span>
        <div class="group bg-[#fafcfb] w-80 h-16 text-black rounded-lg border-[#d6d6d6] border-1 flex items-center hover:bg-white transition-all duration-200 pl-4" v-on:click=" isSelectOpen = !isSelectOpen">
            <img class="h-[40px] w-[40px] object-cover rounded-lg " :src="miniImage?.url" alt="img.id">
            <span class=" font-semibold self-center pl-5 text-[#363232] text-lg">{{ miniImage?.name }}</span>
        </div>

        <div v-if="isSelectOpen" class="absolute top-full bg-white border-1 border-[#d6d6d6]  w-80 rounded-lg z-10">
            <div v-for="img in images" @click="emition(img.id)"  :key="img.id" class="flex gap-12 h-16 rounded-lg hover:bg-[#e8e8e8] transition-all duration-200">
                <div class="flex items-center pl-4">
                    <img class="h-[40px] w-[40px] object-cover rounded-lg" :src="img.url" alt="img.id">
                    <span :key="img.id" class="font-semibold self-center pl-5 text-[#363232] text-lg">{{ img.name }}</span>
                </div>
            </div>
        </div>
    </div>
</template>
<style>
    @import "tailwindcss";
</style>