from flask import Flask
from config import Config
from routes.auth_routes import auth_bp
from routes.profesional_routes import profesional_bp
from routes.cliente_routes import cliente_bp

app = Flask(__name__)
app.config.from_object(Config)

app.register_blueprint(auth_bp)
app.register_blueprint(profesional_bp)
app.register_blueprint(cliente_bp)

if __name__ == '__main__':
    app.run(debug=True)