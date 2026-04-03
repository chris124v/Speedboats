export const TOURS_BASE_URL = import.meta.env.VITE_TOURS_API ?? 'http://localhost:8001'
export const USERS_BASE_URL = import.meta.env.VITE_USERS_API ?? 'http://localhost:8002'
export const BOOKINGS_BASE_URL = import.meta.env.VITE_BOOKINGS_API ?? 'http://localhost:8003'

async function jsonFetch(url, options) {
  const response = await fetch(url, {
    headers: {
      'Content-Type': 'application/json',
      ...(options?.headers ?? {}),
    },
    ...options,
  })

  if (!response.ok) {
    let message = `Request failed: ${response.status}`
    try {
      const data = await response.json()
      if (typeof data?.message === 'string') message = data.message
    } catch {
      // ignore
    }
    throw new Error(message)
  }

  return response.status === 204 ? null : response.json()
}

export const api = { jsonFetch }
