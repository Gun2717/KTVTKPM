export type AuthResponse = { username: string; token: string }
export type Movie = { id: number; title: string }
export type BookingStatus = 'PENDING_PAYMENT' | 'PAID' | 'FAILED'
export type Booking = {
  id: number
  user: string
  movieId: number
  seats: string[]
  status: BookingStatus
  createdAt: string
}

const API = import.meta.env.VITE_API ?? 'http://localhost:8085'

async function http<T>(url: string, init?: RequestInit): Promise<T> {
  const res = await fetch(url, {
    ...init,
    headers: { 'Content-Type': 'application/json', ...(init?.headers ?? {}) },
  })
  const text = await res.text()
  const data = text ? (JSON.parse(text) as unknown) : null
  if (!res.ok) {
    const msg = (data as any)?.message ?? `${res.status} ${res.statusText}`
    throw new Error(msg)
  }
  return data as T
}

export const api = {
  register: (username: string, password: string) =>
    http<AuthResponse>(`${API}/api/users/register`, {
      method: 'POST',
      body: JSON.stringify({ username, password }),
    }),
  login: (username: string, password: string) =>
    http<AuthResponse>(`${API}/api/users/login`, {
      method: 'POST',
      body: JSON.stringify({ username, password }),
    }),

  listMovies: () => http<Movie[]>(`${API}/api/movies`),
  createMovie: (title: string) =>
    http<Movie>(`${API}/api/movies`, {
      method: 'POST',
      body: JSON.stringify({ title }),
    }),
  updateMovie: (id: number, title: string) =>
    http<Movie>(`${API}/api/movies/${id}`, {
      method: 'PUT',
      body: JSON.stringify({ title }),
    }),

  listBookings: () => http<Booking[]>(`${API}/api/bookings`),
  createBooking: (user: string, movieId: number, seats: string[]) =>
    http<Booking>(`${API}/api/bookings`, {
      method: 'POST',
      body: JSON.stringify({ user, movieId, seats }),
    }),
}

