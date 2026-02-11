<script setup lang="ts">
    import type {Image} from '../App.vue';
    import CustomSelect from '@/components/layout/CustomSelect.vue';
    import { ref } from 'vue';
    import { api } from '../http-api';

    import {Upload, FileImage} from 'lucide-vue-next'; 
    import { Input } from 'postcss';

    let formData = new FormData();
    const file = ref()
    const handleFileUpload = async (event: Event) =>{
        const t = event.target as HTMLInputElement;
        if(t.files != null){
            file.value = t.files[0];
            isUploading.value = true;
            await submitFile();
        }
    }

    const submitFile = async () => {
    await api.uploadImage(file.value).then(function(){
          console.log('SUCCESS!!');
        })
        .catch(function(){
          console.log('FAILURE!!');
    });
  }
    
    const isHover = ref(false)
    const isUploading = ref(false)
    const Progress = ref(0)

    const clickUpload = () => {
        console.log("clicked")
        file.value.click()
    }

    const uploadingSimu = ()=>{
        isUploading.value = true
        Progress.value = 0

        const progressBar = setInterval(()=>{
            Progress.value += 10
            if(Progress.value >= 100){
                clearInterval(progressBar)
                //ensuit ici creation nv obj image et ajout au serv
            }
        },150)
    }
    
    
</script>

<template>
    <div class="flex justify-center items-center min-h-screen">
        <div v-on:click="clickUpload()" class="flex flex-col h-[400px] w-[600px] bg-white items-center justify-center rounded-2xl shadow-2xl gap-4 transition-all duration-400 hover:bg-[#e3e8e6] hover:cursor-pointer">
            <div class="w-20 h-20 bg-indigo-100 rounded-full flex items-center justify-center mb-6 shadow-sm">
                <Upload color="indigo" :size="60"/>
            </div>
            <span class="text-2xl text-black">Glissez votre image</span>
            <span class="text-indigo-500">ou cliquez pour parcourir</span>
            <input ref="file" class="hidden" type="file" value="" @change="handleFileUpload($event)">
        </div>
        <div v-if="isUploading" class="w-64 text-center z-10 animate-in fade-in zoom-in duration-300">
            <div class="mb-4 relative">
              <FileImage :size="48" class="text-indigo-600 mx-auto animate-bounce" />
            </div>
            <div class="h-2 w-full bg-slate-100 rounded-full overflow-hidden mb-2">
              <div 
                class="h-full bg-indigo-600 transition-all duration-200 ease-out" 
                :style="{ width: `${Progress}%` }"
              ></div>
            </div>
            <p class="text-indigo-600 font-medium text-sm">Transfert vers le serveur... {{Progress }}%</p>
        </div>
    </div>
    
</template>
<style>
    @import "tailwindcss";
</style>