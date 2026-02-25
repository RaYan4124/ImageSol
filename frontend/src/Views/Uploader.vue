<script setup lang="ts">
    import { ref } from 'vue';
    import { api } from '../http-api';
    import {Upload, FileImage} from 'lucide-vue-next'; 
    import router from '@/view-router';

    const file = ref()
    const fileInput = ref<HTMLInputElement>(null);
    const handleFileUpload = async (event: Event) =>{
        const t = event.target as HTMLInputElement;
        if(t.files != null){
            file.value = t.files[0];
            isUploading.value = true;
            await Promise.all([
                uploadingSimu(), submitFile()
            ]);
            router.push('/galery');
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
        fileInput.value.click()
    }

    const uploadingSimu = (): Promise<void>=>{
        return new Promise((resolve)=>{
            isUploading.value = true
            Progress.value = 0

            const progressBar = setInterval(()=>{
                Progress.value += 10;
                if(Progress.value >= 100){
                    clearInterval(progressBar);
                    resolve(); //valider la promesse
                }
            },150)
        })
    }
    
    
</script>

<template>
    <div class="flex justify-center items-center min-h-screen">
        <div v-if="!isUploading" v-on:click="clickUpload()" class="flex flex-col h-[400px] w-[600px] bg-white items-center justify-center rounded-2xl shadow-2xl gap-4 transition-all duration-400 hover:bg-[#e3e8e6] hover:cursor-pointer">
            <div class="w-20 h-20 bg-indigo-100 rounded-full flex items-center justify-center mb-6 shadow-sm">
                <Upload color="indigo" :size="60"/>
            </div>
            <span class="text-2xl text-black">Glissez votre image</span>
            <span class="text-indigo-500">ou cliquez pour parcourir</span>
            <input ref="fileInput" class="hidden" type="file" value="" @change="handleFileUpload($event)">
        </div>

        <div v-else class="flex flex-col h-[400px] w-[600px] bg-white items-center justify-center rounded-2xl shadow-2xl gap-4 transition-all duration-400 hover:bg-[#e3e8e6] hover:cursor-pointer">
            <div class="w-20 h-20 flex items-center justify-center mb-6">
                <FileImage :size="48" class="text-indigo-600 mx-auto animate-bounce" />
            </div>
            <div class="h-2 w-80 bg-slate-100 rounded-full overflow-hidden mb-2">
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