from models.conexion import get_connection

class HorarioModel:

    @staticmethod
    def crear_horario(profesional_id, fecha, hora_inicio, hora_fin):
        connection = get_connection()
        cursor = connection.cursor()

        sql = """
            INSERT INTO horarios (profesional_id, fecha, hora_inicio, hora_fin)
            VALUES (%s, %s, %s, %s)
        """
        values = (profesional_id, fecha, hora_inicio, hora_fin)

        cursor.execute(sql, values)
        connection.commit()

        cursor.close()
        connection.close()

    @staticmethod
    def obtener_horarios_por_profesional(profesional_id):
        connection = get_connection()
        cursor = connection.cursor(dictionary=True)

        sql = """
            SELECT * FROM horarios
            WHERE profesional_id = %s
            ORDER BY fecha ASC, hora_inicio ASC
        """
        cursor.execute(sql, (profesional_id,))
        horarios = cursor.fetchall()

        cursor.close()
        connection.close()

        return horarios

    @staticmethod
    def obtener_horarios_disponibles():
        connection = get_connection()
        cursor = connection.cursor(dictionary=True)

        sql = """
            SELECT h.id, h.fecha, h.hora_inicio, h.hora_fin,
                   p.id AS profesional_id,
                   u.nombre AS profesional_nombre,
                   p.especialidad
            FROM horarios h
            INNER JOIN profesionales p ON h.profesional_id = p.id
            INNER JOIN usuarios u ON p.usuario_id = u.id
            WHERE h.disponible = TRUE
            ORDER BY h.fecha ASC, h.hora_inicio ASC
        """
        cursor.execute(sql)
        horarios = cursor.fetchall()

        cursor.close()
        connection.close()

        return horarios