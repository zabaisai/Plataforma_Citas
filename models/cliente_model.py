from models.conexion import get_connection

class ClienteModel:

    @staticmethod
    def crear_cliente(usuario_id, telefono=""):
        connection = get_connection()
        cursor = connection.cursor()

        sql = """
            INSERT INTO clientes (usuario_id, telefono)
            VALUES (%s, %s)
        """
        values = (usuario_id, telefono)

        cursor.execute(sql, values)
        connection.commit()

        cursor.close()
        connection.close()

    @staticmethod
    def obtener_cliente_por_usuario_id(usuario_id):
        connection = get_connection()
        cursor = connection.cursor(dictionary=True)

        sql = "SELECT * FROM clientes WHERE usuario_id = %s"
        cursor.execute(sql, (usuario_id,))
        cliente = cursor.fetchone()

        cursor.close()
        connection.close()

        return cliente