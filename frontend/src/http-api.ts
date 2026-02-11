import axios from 'axios';
import type { Image } from './App.vue'; 

export const api = {
  async getAllImages(): Promise<Image[]> {
    const response = await axios.get<Image[]>('/images');
    return response.data;
  },

  /**
   * 
   * @param file
   */
  async uploadImage(file: File): Promise<void> {
    const formData = new FormData();
    formData.append('file', file); 
    await axios.post('/images', formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    });
  },

  async deleteImage(id: number): Promise<void> {
    await axios.delete(`/images?id=${id}`);
  },

  getImageUrl(id: number): string {
    return `/images/${id}`;
  }
};