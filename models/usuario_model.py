from models.conexion import get_connection

class UsuarioModel:

    @staticmethod
    def crear_usuario(nombre, correo, contrasena, rol):
        connection = get_connection()
        cursor = connection.cursor()

        sql = """
            INSERT INTO usuarios (nombre, correo, contrasena, rol)
            VALUES (%s, %s, %s, %s)
        """
        values = (nombre, correo, contrasena, rol)

        cursor.execute(sql, values)
        connection.commit()

        usuario_id = cursor.lastrowid

        cursor.close()
        connection.close()

        return usuario_id

    @staticmethod
    def obtener_usuario_por_correo(correo):
        connection = get_connection()
        cursor = connection.cursor(dictionary=True)

        sql = "SELECT * FROM usuarios WHERE correo = %s"
        cursor.execute(sql, (correo,))
        usuario = cursor.fetchone()

        cursor.close()
        connection.close()

        return usuario