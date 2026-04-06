from flask import Blueprint, render_template, request, redirect, url_for, flash, session
from werkzeug.security import generate_password_hash, check_password_hash
from models.usuario_model import UsuarioModel
from models.profesional_model import ProfesionalModel
from models.cliente_model import ClienteModel

auth_bp = Blueprint('auth', __name__)

@auth_bp.route('/')
def index():
    return render_template('index.html')


@auth_bp.route('/register', methods=['GET', 'POST'])
def register():
    if request.method == 'POST':
        nombre = request.form['nombre']
        correo = request.form['correo']
        contrasena = request.form['contrasena']
        rol = request.form['rol']

        usuario_existente = UsuarioModel.obtener_usuario_por_correo(correo)

        if usuario_existente:
            flash('El correo ya está registrado.', 'error')
            return redirect(url_for('auth.register'))

        contrasena_hash = generate_password_hash(contrasena)

        usuario_id = UsuarioModel.crear_usuario(
            nombre,
            correo,
            contrasena_hash,
            rol
        )

        if rol == 'profesional':
            ProfesionalModel.crear_profesional(usuario_id)
        elif rol == 'cliente':
            ClienteModel.crear_cliente(usuario_id)

        flash('Usuario registrado correctamente. Ahora inicia sesión.', 'success')
        return redirect(url_for('auth.login'))

    return render_template('register.html')


@auth_bp.route('/login', methods=['GET', 'POST'])
def login():
    if request.method == 'POST':
        correo = request.form['correo']
        contrasena = request.form['contrasena']

        usuario = UsuarioModel.obtener_usuario_por_correo(correo)

        if usuario and check_password_hash(usuario['contrasena'], contrasena):
            session['usuario_id'] = usuario['id']
            session['usuario_nombre'] = usuario['nombre']
            session['usuario_rol'] = usuario['rol']

            if usuario['rol'] == 'cliente':
                return redirect(url_for('auth.dashboard_cliente'))
            elif usuario['rol'] == 'profesional':
                return redirect(url_for('auth.dashboard_profesional'))

        flash('Correo o contraseña incorrectos.', 'error')
        return redirect(url_for('auth.login'))

    return render_template('login.html')


@auth_bp.route('/dashboard_cliente')
def dashboard_cliente():
    if 'usuario_id' not in session or session.get('usuario_rol') != 'cliente':
        return redirect(url_for('auth.login'))

    return render_template('dashboard_cliente.html')


@auth_bp.route('/dashboard_profesional')
def dashboard_profesional():
    if 'usuario_id' not in session or session.get('usuario_rol') != 'profesional':
        return redirect(url_for('auth.login'))

    return render_template('dashboard_profesional.html')


@auth_bp.route('/logout')
def logout():
    session.clear()
    flash('Has cerrado sesión correctamente.', 'success')
    return redirect(url_for('auth.login'))