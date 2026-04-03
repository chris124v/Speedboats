import { useEffect, useMemo, useState } from 'react'
import { Link } from 'react-router-dom'
import { listTours } from '../api/tours.js'

export default function ToursPage() {
  const [tours, setTours] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)
  const [maxPrice, setMaxPrice] = useState('')

  useEffect(() => {
    let cancelled = false
    setLoading(true)
    setError(null)
    listTours()
      .then((data) => {
        if (cancelled) return
        setTours(Array.isArray(data) ? data : [])
      })
      .catch((e) => {
        if (cancelled) return
        setError(e.message ?? String(e))
      })
      .finally(() => {
        if (cancelled) return
        setLoading(false)
      })

    return () => {
      cancelled = true
    }
  }, [])

  const filtered = useMemo(() => {
    const max = maxPrice === '' ? null : Number(maxPrice)
    if (max == null || Number.isNaN(max)) return tours
    return tours.filter((t) => Number(t.precio) <= max)
  }, [tours, maxPrice])

  return (
    <div className="space-y-6">
      <div className="sb-card">
        <div className="flex flex-col gap-3 sm:flex-row sm:items-end sm:justify-between">
          <div>
            <h1 className="sb-title">Tours disponibles</h1>
            <p className="sb-subtitle">Consulta y reserva tours publicados por guías locales.</p>
          </div>

          <div className="flex gap-2">
            <label className="text-sm">
              <div className="mb-1 text-slate-600">Precio máximo</div>
              <input
                className="sb-input w-44"
                value={maxPrice}
                onChange={(e) => setMaxPrice(e.target.value)}
                placeholder="70"
                inputMode="decimal"
              />
            </label>
          </div>
        </div>
      </div>

      {loading && <div className="text-sm text-slate-600">Cargando…</div>}
      {error && <div className="rounded border border-red-200 bg-red-50 p-3 text-sm text-red-700">{error}</div>}

      {!loading && !error && filtered.length === 0 && (
        <div className="rounded border bg-slate-50 p-3 text-sm">No hay tours para mostrar.</div>
      )}

      <div className="grid gap-4 sm:grid-cols-2">
        {filtered.map((t) => (
          <div key={t.id} className="sb-card">
            <div className="flex items-start justify-between gap-3">
              <div>
                <div className="flex flex-wrap items-center gap-2">
                  <div className="font-semibold text-slate-900">{t.nombre}</div>
                  <span className="sb-badge">Guía #{t.guiaId}</span>
                </div>
                <div className="mt-1 text-sm text-slate-600">
                  Ubicación: {Number(t.ubicacionLat).toFixed(3)}, {Number(t.ubicacionLng).toFixed(3)}
                </div>
                <div className="mt-1 text-sm text-slate-600">Duración: {t.duracion} min</div>
              </div>
              <div className="text-right">
                <div className="text-lg font-semibold text-slate-900">${t.precio}</div>
                <div className="text-xs text-slate-500">Tour #{t.id}</div>
              </div>
            </div>

            <div className="mt-3 flex gap-2">
              <Link
                className="sb-btn-primary"
                to={`/tours/${t.id}/book`}
              >
                Reservar
              </Link>
            </div>
          </div>
        ))}
      </div>
    </div>
  )
}
