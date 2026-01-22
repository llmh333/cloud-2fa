import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react-swc'
import { VitePWA } from 'vite-plugin-pwa'
import tailwindcss from '@tailwindcss/vite'

// https://vite.dev/config/
export default defineConfig({
  plugins: [
    react(),
    tailwindcss(),
    VitePWA({
      registerType: 'autoUpdate',
      manifest: {
        name: 'Cloud 2FA',
        short_name: 'Cloud2FA',
        description: 'Cloud 2FA Authenticator',
        theme_color: '#ffffff',
        icons: [
          {
            src: '/vite.svg',
            sizes: '64x64 128x128 256x256 384x384 512x512',
            type: 'image/png',
          },
        ],
      },
    })
  ],
})
