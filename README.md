# Backend Colombian Cars Parking App:

## Repositorio Frontend

[![Front End](https://badgen.net/badge/FrontEnd/here/green?icon=github)](https://github.com/malenvac/front_colombian_cars)

### _Elaborado por: Cruz Magdalena Vanegas Correa, Jhonny Arley Tobón Acevedo, Mateo Valencia Minota_

---

El Parqueadero “Autos Colombia” parqueadero que presta el servicio de parqueadero por mensualidad necesita un desarrollo de software para la gestión de sus operaciones diarias que le permita, registrar las entradas y salidas de los vehículos, registrar usuarios, manejar las celdas, y registrar novedades ocurridas sobre determinado vehículo en el parqueadero y gestionar los pagos. Esto incluye el análisis de los siguientes requerimientos funcionales:

- Gestión de la entrada y salida de vehículos.

- Gestión de usuarios y gestión de celdas.

- Gestión de pagos.

## Pasos a seguir

- Clonar este repositorio
- Abrir el proyecto en su IDE de preferencia.
- Asignar el usuario y la contraseña de tu base de datos en el archivo _application.properties_
  Como se muestra a continuación:
```sh
spring.datasource.username=usuario
spring.datasource.password= ${contraseña}
```
- Crear un schema en MySQL Workbench o cualquier otro gestor de bases de datos llamado _**colombiancars**_
- Correr el proyecto

Debes ver en la consola lo siguiente:
```sh
2022-07-29 12:12:09.756  INFO 12524 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
```

Ahora puedes probar las API del proyecto con una herramienta como postman. O puedes descargar el repositorio del frontend y probrar el proyecto completo.


---
## Rutas para vehículos
```
${BaseURL} = http://localhost:8080/vehicle
```
| Action                       | Method   | Rout                               |
|------------------------------|----------|------------------------------------|
| Encontrar todos los vehículos | `GET`    | `${BaseURL}/findAllVehicles`       |
| Registrar una entrada        | `POST`   | `${BaseURL}/registerEntry`         |
| Registrar una salida  | `PUT`    | `${BaseURL}/registerDeparture/${licensePlate}` |

## Rutas para celdas
```
${BaseURL} = http://localhost:8080/cell
```
| Action                        | Method | Rout                                        |
|-------------------------------|--------|---------------------------------------------|
| Encontrar celda los por Id    | `GET`  | `${BaseURL}/findCellById/${cellId}`         |
| Encontrar todas las celda     | `GET`  | `${BaseURL}/findAllCells`                  |
| Guardar una nueva celda | `POST` | `${BaseURL}/saveCell` |


## Rutas para tickets
```
${BaseURL} = http://localhost:8080/ticket
```
| Action                      | Method | Rout                                |
|-----------------------------|--------|-------------------------------------|
| Encontrar todos los tickets | `GET`  | `${BaseURL}/findAllTickets`         |




### Aspectos a tener en cuenta

- No se pueden registrar vehículos sin antes crear una celda
- No se puede registrar un vehículo si este ya se encuentra aparcado
- No se puede registrar una salida de vehículo si este no se encuentra aparacado

