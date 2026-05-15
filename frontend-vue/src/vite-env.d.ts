/**
 * Types definition for Vue environment variables
 */
declare global {
  namespace NodeJS {
    interface ProcessEnv {
      readonly VITE_API_BASE_URL: string
      readonly VITE_API_TIMEOUT: string
    }
  }
}

interface ImportMetaEnv {
  readonly VITE_API_BASE_URL: string
  readonly VITE_API_TIMEOUT: string
}

interface ImportMeta {
  readonly env: ImportMetaEnv
}

export {}
