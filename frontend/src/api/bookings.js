import { api, BOOKINGS_BASE_URL } from './config.js'

export function createBooking(payload) {
  return api.jsonFetch(`${BOOKINGS_BASE_URL}/api/bookings`, {
    method: 'POST',
    body: JSON.stringify(payload),
  })
}
