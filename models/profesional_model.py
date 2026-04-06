from models.conexion import get_connection

class ProfesionalModel:

    @staticmethod
    def crear_profesional(usuario_id, especialidad="General", telefono=""):
        connection = get_connection()
        cursor = connection.cursor()

        sql = """
            INSERT INTO profesionales (usuario_id, especialidad, telefono)
            VALUES (%s, %s, %s)
        """
        values = (usuario_id, especialidad, telefono)

        cursor.execute(sql, values)
        connection.commit()

        cursor.close()
        connection.close()

    @staticmethod
    def obtener_profesional_por_usuario_id(usuario_id):
        connection = get_connection()
        cursor = connection.cursor(dictionary=True)

        sql = "SELECT * FROM profesionales WHERE usuario_id = %s"
        cursor.execute(sql, (usuario_id,))
        profesional = cursor.fetchone()

        cursor.close()
        connection.close()

        return profesional