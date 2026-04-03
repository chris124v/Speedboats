import { Link, Route, Routes } from 'react-router-dom'
import ToursPage from './pages/ToursPage.jsx'
import CreateTourPage from './pages/CreateTourPage.jsx'
import BookTourPage from './pages/BookTourPage.jsx'

export default function App() {
  return (
    <div className="min-h-screen">
      <header className="sticky top-0 z-10 border-b border-slate-200 bg-white/70 backdrop-blur">
        <div className="sb-container flex items-center justify-between py-3">
          <Link to="/tours" className="flex items-center gap-2">
            <div className="h-9 w-9 rounded-xl bg-gradient-to-br from-sky-500 to-blue-600 shadow-sm" />
            <div>
              <div className="text-sm font-semibold leading-tight text-slate-900">Speedboats</div>
              <div className="text-xs text-slate-600">Tours locales en lancha</div>
            </div>
          </Link>

          <nav className="flex items-center gap-2 text-sm">
            <Link className="sb-btn-secondary" to="/tours">Ver tours</Link>
            <Link className="sb-btn-primary" to="/tours/create">Publicar tour</Link>
          </nav>
        </div>
      </header>

      <main className="sb-container py-8">
        <Routes>
          <Route path="/" element={<ToursPage />} />
          <Route path="/tours" element={<ToursPage />} />
          <Route path="/tours/create" element={<CreateTourPage />} />
          <Route path="/tours/:tourId/book" element={<BookTourPage />} />
        </Routes>
      </main>
    </div>
  )
}
