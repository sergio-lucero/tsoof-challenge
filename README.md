# tsoof-challenge
Sistema para challenge tsoof 
El cual está realizado en java con base de datos postgresSql. El proyecto tiene un archivo docker para levantar una base de datos, con datos de pruebas.
Para ejecutar el archivo de docker ejecutar:
       docker compose up

El servicio cuenta con 3 controladores uno para la empresa, otro para transferencias y un tercero que tiene los métodos de busqueda particulares. Al levantar el proyecto se puede probar tanto desde postman como desde el swagger:
http://localhost:8381/openapi/swagger-ui/index.html

Por defecto el proyecto levanta en el puerto 8381
