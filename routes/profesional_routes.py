from flask import Blueprint, render_template, request, redirect, url_for, flash, session
from models.profesional_model import ProfesionalModel
from models.horario_model import HorarioModel
from models.cita_model import CitaModel

profesional_bp = Blueprint('profesional', __name__)

@profesional_bp.route('/gestionar_horarios', methods=['GET', 'POST'])
def gestionar_horarios():
    if 'usuario_id' not in session or session.get('usuario_rol') != 'profesional':
        return redirect(url_for('auth.login'))

    profesional = ProfesionalModel.obtener_profesional_por_usuario_id(session['usuario_id'])

    if not profesional:
        flash(f"No se encontró el perfil profesional para usuario_id={session['usuario_id']}", 'error')
        return redirect(url_for('auth.dashboard_profesional'))

    if request.method == 'POST':
        fecha = request.form['fecha']
        hora_inicio = request.form['hora_inicio']
        hora_fin = request.form['hora_fin']

        HorarioModel.crear_horario(profesional['id'], fecha, hora_inicio, hora_fin)
        flash('Horario registrado correctamente.', 'success')
        return redirect(url_for('profesional.gestionar_horarios'))

    horarios = HorarioModel.obtener_horarios_por_profesional(profesional['id'])
    return render_template('gestionar_horarios.html', horarios=horarios)


@profesional_bp.route('/citas_profesional')
def citas_profesional():
    if 'usuario_id' not in session or session.get('usuario_rol') != 'profesional':
        return redirect(url_for('auth.login'))

    profesional = ProfesionalModel.obtener_profesional_por_usuario_id(session['usuario_id'])

    if not profesional:
        flash(f"No se encontró el perfil profesional para usuario_id={session['usuario_id']}", 'error')
        return redirect(url_for('auth.dashboard_profesional'))

    citas = CitaModel.obtener_citas_por_profesional(profesional['id'])
    return render_template('citas_profesional.html', citas=citas)