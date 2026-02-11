import {createRouter, createWebHistory, RouteRecordRaw} from 'vue-router'
import Galery from './Views/Galery.vue'
import Uploader from './Views/Uploader.vue'
import Visualizer from './Views/Visualizer.vue'

const routes: Array<RouteRecordRaw> = [
    {
        path: '/',
        name: 'visualizer',
        component: Visualizer, 
        meta: {title : "Visualiseur"}
    },
    {
        path:'/galery',
        name:'galery',
        component:Galery, 
        meta: {title : "Galerie"}
    },
    {
        path:'/upload',
        name:'upload',
        component: Uploader, 
        meta: {title : "Uploadeur"}
    }
]

const router = createRouter({history: createWebHistory(), routes})
export default router