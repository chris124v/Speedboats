import { useEffect, useMemo, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import { createBooking } from '../api/bookings.js'
import { listUsers } from '../api/users.js'

function toIsoDate(d) {
  const pad = (n) => String(n).padStart(2, '0')
  return `${d.getFullYear()}-${pad(d.getMonth() + 1)}-${pad(d.getDate())}`
}

export default function BookTourPage() {
  const { tourId } = useParams()
  const navigate = useNavigate()

  const tomorrow = useMemo(() => {
    const d = new Date()
    d.setDate(d.getDate() + 1)
    return toIsoDate(d)
  }, [])

  const [users, setUsers] = useState([])
  const [loadingUsers, setLoadingUsers] = useState(true)
  const [form, setForm] = useState({
    userId: '2',
    fechaReserva: tomorrow,
    numPersonas: '2',
    estado: 'PENDING',
  })

  const [submitting, setSubmitting] = useState(false)
  const [error, setError] = useState(null)
  const [created, setCreated] = useState(null)

  useEffect(() => {
    let cancelled = false
    setLoadingUsers(true)
    setError(null)

    listUsers()
      .then((data) => {
        if (cancelled) return
        setUsers(Array.isArray(data) ? data : [])
      })
      .catch((e) => {
        if (cancelled) return
        setError(e.message ?? String(e))
      })
      .finally(() => {
        if (cancelled) return
        setLoadingUsers(false)
      })

    return () => {
      cancelled = true
    }
  }, [])

  function update(key) {
    return (e) => setForm((f) => ({ ...f, [key]: e.target.value }))
  }

  async function onSubmit(e) {
    e.preventDefault()
    setSubmitting(true)
    setError(null)
    setCreated(null)

    try {
      const payload = {
        tourId: Number(tourId),
        userId: Number(form.userId),
        fechaReserva: form.fechaReserva,
        numPersonas: Number(form.numPersonas),
        estado: form.estado,
      }

      const result = await createBooking(payload)
      setCreated(result)
    } catch (e2) {
      setError(e2.message ?? String(e2))
    } finally {
      setSubmitting(false)
    }
  }

  return (
    <div className="max-w-2xl space-y-6">
      <div className="sb-card">
        <div className="flex flex-col gap-1 sm:flex-row sm:items-center sm:justify-between">
          <div>
            <h1 className="sb-title">Reservar tour</h1>
            <p className="sb-subtitle">Completa los datos de la reserva.</p>
          </div>
          <span className="sb-badge">Tour #{tourId}</span>
        </div>
      </div>

      {error && <div className="sb-card border-red-200 bg-red-50 text-sm text-red-700">{error}</div>}
      {created && (
        <div className="sb-card border-emerald-200 bg-emerald-50 text-sm text-emerald-800">
          Reserva creada con ID <span className="font-semibold">{created.id}</span>
        </div>
      )}

      <form className="sb-card space-y-4" onSubmit={onSubmit}>
        <label className="block text-sm">
          <div className="mb-1 text-slate-600">Usuario</div>
          <select className="sb-select" value={form.userId} onChange={update('userId')} disabled={loadingUsers}>
            {users.length === 0 && <option value={form.userId}>ID {form.userId}</option>}
            {users
              .filter((u) => u.tipo === 'USER')
              .map((u) => (
                <option key={u.id} value={u.id}>
                  {u.nombre} (ID {u.id})
                </option>
              ))}
          </select>
        </label>

        <div className="grid gap-4 sm:grid-cols-2">
          <label className="block text-sm">
            <div className="mb-1 text-slate-600">Fecha (YYYY-MM-DD)</div>
            <input className="sb-input" value={form.fechaReserva} onChange={update('fechaReserva')} />
          </label>
          <label className="block text-sm">
            <div className="mb-1 text-slate-600">Personas</div>
            <input className="sb-input" value={form.numPersonas} onChange={update('numPersonas')} inputMode="numeric" />
          </label>
        </div>

        <label className="block text-sm">
          <div className="mb-1 text-slate-600">Estado</div>
          <select className="sb-select" value={form.estado} onChange={update('estado')}>
            <option value="PENDING">PENDING</option>
            <option value="CONFIRMED">CONFIRMED</option>
            <option value="CANCELLED">CANCELLED</option>
          </select>
        </label>

        <div className="flex flex-wrap gap-2">
          <button type="submit" disabled={submitting} className="sb-btn-primary">
            {submitting ? 'Reservando…' : 'Reservar'}
          </button>
          <button type="button" className="sb-btn-secondary" onClick={() => navigate('/tours')}>
            Volver
          </button>
        </div>
      </form>

      <div className="sb-card bg-slate-50 text-xs text-slate-600">
        Requiere que estén corriendo <span className="font-mono">tours-service</span> y{' '}
        <span className="font-mono">users-service</span> para validar IDs.
      </div>
    </div>
  )
}
