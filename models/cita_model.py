from models.conexion import get_connection

class CitaModel:

    @staticmethod
    def crear_cita(cliente_id, profesional_id, horario_id, motivo):
        connection = get_connection()
        cursor = connection.cursor()

        sql = """
            INSERT INTO citas (cliente_id, profesional_id, horario_id, motivo, estado)
            VALUES (%s, %s, %s, %s, 'pendiente')
        """
        values = (cliente_id, profesional_id, horario_id, motivo)

        cursor.execute(sql, values)

        sql_update = "UPDATE horarios SET disponible = FALSE WHERE id = %s"
        cursor.execute(sql_update, (horario_id,))

        connection.commit()

        cursor.close()
        connection.close()

    @staticmethod
    def obtener_citas_por_cliente(cliente_id):
        connection = get_connection()
        cursor = connection.cursor(dictionary=True)

        sql = """
            SELECT c.id, c.motivo, c.estado, c.fecha_creacion,
                   h.fecha, h.hora_inicio, h.hora_fin,
                   u.nombre AS profesional_nombre
            FROM citas c
            INNER JOIN horarios h ON c.horario_id = h.id
            INNER JOIN profesionales p ON c.profesional_id = p.id
            INNER JOIN usuarios u ON p.usuario_id = u.id
            WHERE c.cliente_id = %s
            ORDER BY h.fecha ASC, h.hora_inicio ASC
        """
        cursor.execute(sql, (cliente_id,))
        citas = cursor.fetchall()

        cursor.close()
        connection.close()

        return citas

    @staticmethod
    def obtener_citas_por_profesional(profesional_id):
        connection = get_connection()
        cursor = connection.cursor(dictionary=True)

        sql = """
            SELECT c.id, c.motivo, c.estado, c.fecha_creacion,
                   h.fecha, h.hora_inicio, h.hora_fin,
                   u.nombre AS cliente_nombre
            FROM citas c
            INNER JOIN horarios h ON c.horario_id = h.id
            INNER JOIN clientes cl ON c.cliente_id = cl.id
            INNER JOIN usuarios u ON cl.usuario_id = u.id
            WHERE c.profesional_id = %s
            ORDER BY h.fecha ASC, h.hora_inicio ASC
        """
        cursor.execute(sql, (profesional_id,))
        citas = cursor.fetchall()

        cursor.close()
        connection.close()

        return citas