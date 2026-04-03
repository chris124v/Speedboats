import { useState } from 'react'
import { createTour } from '../api/tours.js'

export default function CreateTourPage() {
  const [form, setForm] = useState({
    nombre: '',
    ubicacionLat: '9.963',
    ubicacionLng: '-84.778',
    precio: '55',
    duracion: '90',
    guiaId: '1',
  })
  const [submitting, setSubmitting] = useState(false)
  const [error, setError] = useState(null)
  const [created, setCreated] = useState(null)

  async function onSubmit(e) {
    e.preventDefault()
    setSubmitting(true)
    setError(null)
    setCreated(null)

    try {
      const payload = {
        nombre: form.nombre,
        ubicacionLat: Number(form.ubicacionLat),
        ubicacionLng: Number(form.ubicacionLng),
        precio: Number(form.precio),
        duracion: Number(form.duracion),
        guiaId: Number(form.guiaId),
      }
      const result = await createTour(payload)
      setCreated(result)
    } catch (e2) {
      setError(e2.message ?? String(e2))
    } finally {
      setSubmitting(false)
    }
  }

  function update(key) {
    return (e) => setForm((f) => ({ ...f, [key]: e.target.value }))
  }

  return (
    <div className="max-w-2xl space-y-6">
      <div className="sb-card">
        <h1 className="sb-title">Publicar tour</h1>
        <p className="sb-subtitle">Crea un tour con nombre, ubicación y precio.</p>
      </div>

      {error && <div className="sb-card border-red-200 bg-red-50 text-sm text-red-700">{error}</div>}
      {created && (
        <div className="sb-card border-emerald-200 bg-emerald-50 text-sm text-emerald-800">
          Tour creado con ID <span className="font-semibold">{created.id}</span>
        </div>
      )}

      <form className="sb-card space-y-4" onSubmit={onSubmit}>
        <label className="block text-sm">
          <div className="mb-1 text-slate-600">Nombre</div>
          <input className="sb-input" value={form.nombre} onChange={update('nombre')} placeholder="Tour Manglares" />
        </label>

        <div className="grid gap-4 sm:grid-cols-2">
          <label className="block text-sm">
            <div className="mb-1 text-slate-600">Lat</div>
            <input className="sb-input" value={form.ubicacionLat} onChange={update('ubicacionLat')} />
          </label>
          <label className="block text-sm">
            <div className="mb-1 text-slate-600">Lng</div>
            <input className="sb-input" value={form.ubicacionLng} onChange={update('ubicacionLng')} />
          </label>
        </div>

        <div className="grid gap-4 sm:grid-cols-3">
          <label className="block text-sm">
            <div className="mb-1 text-slate-600">Precio</div>
            <input className="sb-input" value={form.precio} onChange={update('precio')} />
          </label>
          <label className="block text-sm">
            <div className="mb-1 text-slate-600">Duración (min)</div>
            <input className="sb-input" value={form.duracion} onChange={update('duracion')} />
          </label>
          <label className="block text-sm">
            <div className="mb-1 text-slate-600">Guía ID</div>
            <input className="sb-input" value={form.guiaId} onChange={update('guiaId')} />
          </label>
        </div>

        <div className="flex items-center gap-2">
          <button type="submit" disabled={submitting} className="sb-btn-primary">
            {submitting ? 'Publicando…' : 'Publicar'}
          </button>
          <span className="text-xs text-slate-500">Se guardará en SQLite automáticamente.</span>
        </div>
      </form>

      <div className="sb-card bg-slate-50 text-xs text-slate-600">
        Nota: el servicio crea una base SQLite automáticamente en <span className="font-mono">./data</span>.
      </div>
    </div>
  )
}
