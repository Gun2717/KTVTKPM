import './App.css'
import { useEffect, useMemo, useState } from 'react'
import { api, type Booking, type Movie } from './lib/api'

function App() {
  const [tab, setTab] = useState<'auth' | 'movies' | 'bookings'>('auth')

  const [authMode, setAuthMode] = useState<'login' | 'register'>('register')
  const [username, setUsername] = useState(() => localStorage.getItem('mts.username') ?? '')
  const [password, setPassword] = useState('123')
  const [token, setToken] = useState(() => localStorage.getItem('mts.token') ?? '')
  const isAuthed = Boolean(username && token)

  const [movies, setMovies] = useState<Movie[]>([])
  const [movieTitle, setMovieTitle] = useState('Dune 2')
  const [editMovieId, setEditMovieId] = useState<number | null>(null)
  const [editMovieTitle, setEditMovieTitle] = useState('')

  const [bookings, setBookings] = useState<Booking[]>([])
  const [selectedMovieId, setSelectedMovieId] = useState<number>(1)
  const [seatsText, setSeatsText] = useState('A1,A2')

  const [busy, setBusy] = useState(false)
  const [error, setError] = useState<string | null>(null)
  const [toast, setToast] = useState<string | null>(null)

  useEffect(() => {
    localStorage.setItem('mts.username', username)
    localStorage.setItem('mts.token', token)
  }, [username, token])

  useEffect(() => {
    if (toast) {
      const t = setTimeout(() => setToast(null), 2500)
      return () => clearTimeout(t)
    }
  }, [toast])

  useEffect(() => {
    if (tab !== 'movies') return
    void refreshMovies()
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [tab])

  useEffect(() => {
    if (tab !== 'bookings') return
    void refreshBookings()
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [tab])

  useEffect(() => {
    if (!movies.length) return
    if (!movies.some((m) => m.id === selectedMovieId)) {
      setSelectedMovieId(movies[0].id)
    }
  }, [movies, selectedMovieId])

  const seatList = useMemo(
    () =>
      seatsText
        .split(',')
        .map((s) => s.trim())
        .filter(Boolean),
    [seatsText],
  )

  async function withBusy<T>(fn: () => Promise<T>) {
    setBusy(true)
    setError(null)
    try {
      return await fn()
    } catch (e: any) {
      setError(e?.message ?? 'Something went wrong')
      throw e
    } finally {
      setBusy(false)
    }
  }

  async function refreshMovies() {
    const data = await withBusy(() => api.listMovies())
    setMovies(data)
  }

  async function refreshBookings() {
    const data = await withBusy(() => api.listBookings())
    setBookings(data)
  }

  async function submitAuth() {
    await withBusy(async () => {
      const res =
        authMode === 'register'
          ? await api.register(username, password)
          : await api.login(username, password)
      setToken(res.token)
      setToast(authMode === 'register' ? 'Đăng ký OK (đã publish USER_REGISTERED)' : 'Đăng nhập OK')
      setTab('movies')
    })
  }

  async function createMovie() {
    await withBusy(async () => {
      const created = await api.createMovie(movieTitle.trim())
      setToast(`Đã thêm phim #${created.id}`)
      setMovieTitle('')
      await refreshMovies()
    })
  }

  async function saveMovieEdit() {
    if (!editMovieId) return
    await withBusy(async () => {
      const updated = await api.updateMovie(editMovieId, editMovieTitle.trim())
      setToast(`Đã sửa phim #${updated.id}`)
      setEditMovieId(null)
      setEditMovieTitle('')
      await refreshMovies()
    })
  }

  async function createBooking() {
    await withBusy(async () => {
      const booking = await api.createBooking(username, selectedMovieId, seatList)
      setToast(`Tạo booking #${booking.id} (đã publish BOOKING_CREATED)`)
      await refreshBookings()
    })
  }

  function logout() {
    setToken('')
    setToast('Đã logout')
    setTab('auth')
  }

  return (
    <div className="app">
      <header className="topbar">
        <div className="brand">
          <div className="brandMark" aria-hidden="true" />
          <div>
            <div className="brandTitle">Movie Ticket System</div>
            <div className="brandSub">Event-Driven (RabbitMQ)</div>
          </div>
        </div>

        <nav className="tabs">
          <button className={tab === 'auth' ? 'tab active' : 'tab'} onClick={() => setTab('auth')}>
            Auth
          </button>
          <button
            className={tab === 'movies' ? 'tab active' : 'tab'}
            onClick={() => setTab('movies')}
            disabled={!isAuthed}
            title={!isAuthed ? 'Bạn cần login/register trước' : undefined}
          >
            Movies
          </button>
          <button
            className={tab === 'bookings' ? 'tab active' : 'tab'}
            onClick={() => setTab('bookings')}
            disabled={!isAuthed}
            title={!isAuthed ? 'Bạn cần login/register trước' : undefined}
          >
            Bookings
          </button>
        </nav>

        <div className="right">
          <div className="badge">{isAuthed ? `user: ${username}` : 'not logged in'}</div>
          {isAuthed ? (
            <button className="btn ghost" onClick={logout}>
              Logout
            </button>
          ) : null}
        </div>
      </header>

      <main className="main">
        {toast ? <div className="toast">{toast}</div> : null}
        {error ? <div className="error">{error}</div> : null}

        {tab === 'auth' ? (
          <section className="card">
            <div className="cardTitle">Login / Register</div>
            <div className="row">
              <label className="label">
                Username
                <input value={username} onChange={(e) => setUsername(e.target.value)} placeholder="userA" />
              </label>
              <label className="label">
                Password
                <input value={password} onChange={(e) => setPassword(e.target.value)} placeholder="123" type="password" />
              </label>
            </div>
            <div className="row actions">
              <div className="seg">
                <button
                  className={authMode === 'register' ? 'segBtn active' : 'segBtn'}
                  onClick={() => setAuthMode('register')}
                  type="button"
                >
                  Register
                </button>
                <button
                  className={authMode === 'login' ? 'segBtn active' : 'segBtn'}
                  onClick={() => setAuthMode('login')}
                  type="button"
                >
                  Login
                </button>
              </div>
              <button className="btn" onClick={submitAuth} disabled={busy || !username || !password}>
                {busy ? '...' : authMode === 'register' ? 'Đăng ký' : 'Đăng nhập'}
              </button>
            </div>
            <div className="hint">
              Khi Register, backend sẽ publish event <b>USER_REGISTERED</b>.
            </div>
          </section>
        ) : null}

        {tab === 'movies' ? (
          <section className="grid2">
            <section className="card">
              <div className="cardTitle">Danh sách phim</div>
              <div className="row actions">
                <button className="btn ghost" onClick={refreshMovies} disabled={busy}>
                  Refresh
                </button>
              </div>
              <div className="list">
                {movies.length === 0 ? <div className="muted">Chưa có phim.</div> : null}
                {movies.map((m) => (
                  <div key={m.id} className="listItem">
                    <div>
                      <div className="itemTitle">#{m.id}</div>
                      <div className="itemSub">{m.title}</div>
                    </div>
                    <button
                      className="btn ghost"
                      onClick={() => {
                        setEditMovieId(m.id)
                        setEditMovieTitle(m.title)
                      }}
                    >
                      Sửa
                    </button>
                  </div>
                ))}
              </div>
            </section>

            <section className="card">
              <div className="cardTitle">Thêm / Sửa phim</div>

              <div className="field">
                <label className="label">
                  Thêm phim mới
                  <input value={movieTitle} onChange={(e) => setMovieTitle(e.target.value)} placeholder="Tên phim" />
                </label>
                <button className="btn" onClick={createMovie} disabled={busy || !movieTitle.trim()}>
                  Thêm
                </button>
              </div>

              <div className="divider" />

              <div className="field">
                <label className="label">
                  Sửa phim (chọn từ danh sách)
                  <input
                    value={editMovieTitle}
                    onChange={(e) => setEditMovieTitle(e.target.value)}
                    placeholder={editMovieId ? 'Tên mới' : 'Chọn phim để sửa'}
                    disabled={!editMovieId}
                  />
                </label>
                <button className="btn" onClick={saveMovieEdit} disabled={busy || !editMovieId || !editMovieTitle.trim()}>
                  Lưu
                </button>
              </div>
            </section>
          </section>
        ) : null}

        {tab === 'bookings' ? (
          <section className="grid2">
            <section className="card">
              <div className="cardTitle">Tạo booking</div>
              <div className="field">
                <label className="label">
                  Movie
                  <select
                    value={selectedMovieId}
                    onChange={(e) => setSelectedMovieId(Number(e.target.value))}
                    disabled={busy}
                  >
                    {movies.length ? null : <option value={1}>1</option>}
                    {movies.map((m) => (
                      <option key={m.id} value={m.id}>
                        {m.id} - {m.title}
                      </option>
                    ))}
                  </select>
                </label>
              </div>
              <div className="field">
                <label className="label">
                  Seats (comma)
                  <input value={seatsText} onChange={(e) => setSeatsText(e.target.value)} placeholder="A1,A2" />
                </label>
              </div>
              <div className="row actions">
                <button className="btn ghost" onClick={refreshBookings} disabled={busy}>
                  Refresh bookings
                </button>
                <button className="btn" onClick={createBooking} disabled={busy || !seatList.length || !selectedMovieId}>
                  Tạo booking
                </button>
              </div>
              <div className="hint">
                Khi tạo booking, backend sẽ publish event <b>BOOKING_CREATED</b>. Payment sẽ xử lý random và Notification sẽ log kết quả.
              </div>
            </section>

            <section className="card">
              <div className="cardTitle">Danh sách booking</div>
              <div className="list">
                {bookings.length === 0 ? <div className="muted">Chưa có booking.</div> : null}
                {bookings.map((b) => (
                  <div key={b.id} className="listItem">
                    <div>
                      <div className="itemTitle">
                        #{b.id} <span className={`pill ${b.status}`}>{b.status}</span>
                      </div>
                      <div className="itemSub">
                        user={b.user} · movieId={b.movieId} · seats={b.seats.join(',')}
                      </div>
                    </div>
                  </div>
                ))}
              </div>
            </section>
          </section>
        ) : null}
      </main>

      <footer className="footer">
        Tip: mở console của `payment-service` và `notification-service` để xem event flow.
      </footer>
    </div>
  )
}

export default App
