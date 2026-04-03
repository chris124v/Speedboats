import { api, USERS_BASE_URL } from './config.js'

export function listUsers() {
  return api.jsonFetch(`${USERS_BASE_URL}/api/users`)
}
