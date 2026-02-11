import { ref, onMounted } from 'vue'
import type { Image } from '@/App.vue'
import { api } from '@/http-api'

export function useImages() {
  const imgs = ref<Image[]>([])
  const loading = ref(false)
  const error = ref<string | null>(null)

  async function fetchImages() {
    try {
      loading.value = true
      imgs.value = await api.getAllImages()
    } catch (e) {
      error.value = 'Erreur lors du chargement des images'
    } finally {
      loading.value = false
    }
  }

  onMounted(fetchImages)

  return { imgs, loading, error }
}