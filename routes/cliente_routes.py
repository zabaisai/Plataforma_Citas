from flask import Blueprint, render_template, request, redirect, url_for, flash, session
from models.cliente_model import ClienteModel
from models.horario_model import HorarioModel
from models.cita_model import CitaModel

cliente_bp = Blueprint('cliente', __name__)

@cliente_bp.route('/agendar_cita', methods=['GET', 'POST'])
def agendar_cita():
    if 'usuario_id' not in session or session.get('usuario_rol') != 'cliente':
        return redirect(url_for('auth.login'))

    cliente = ClienteModel.obtener_cliente_por_usuario_id(session['usuario_id'])

    if not cliente:
        flash(f"No se encontró el perfil cliente para usuario_id={session['usuario_id']}", 'error')
        return redirect(url_for('auth.dashboard_cliente'))

    if request.method == 'POST':
        horario_id = request.form['horario_id']
        profesional_id = request.form['profesional_id']
        motivo = request.form['motivo']

        CitaModel.crear_cita(cliente['id'], profesional_id, horario_id, motivo)
        flash('Cita agendada correctamente.', 'success')
        return redirect(url_for('cliente.mis_citas'))

    horarios = HorarioModel.obtener_horarios_disponibles()
    return render_template('agendar_cita.html', horarios=horarios)


@cliente_bp.route('/mis_citas')
def mis_citas():
    if 'usuario_id' not in session or session.get('usuario_rol') != 'cliente':
        return redirect(url_for('auth.login'))

    cliente = ClienteModel.obtener_cliente_por_usuario_id(session['usuario_id'])

    if not cliente:
        flash(f"No se encontró el perfil cliente para usuario_id={session['usuario_id']}", 'error')
        return redirect(url_for('auth.dashboard_cliente'))

    citas = CitaModel.obtener_citas_por_cliente(cliente['id'])
    return render_template('mis_citas.html', citas=citas)