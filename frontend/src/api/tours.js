import { api, TOURS_BASE_URL } from './config.js'

export function listTours() {
  return api.jsonFetch(`${TOURS_BASE_URL}/api/tours`)
}

export function createTour(payload) {
  return api.jsonFetch(`${TOURS_BASE_URL}/api/tours`, {
    method: 'POST',
    body: JSON.stringify(payload),
  })
}
