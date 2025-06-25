# Implementación del patrón de diseño proxy a una aplicacion de integración de IA en aplicaciones web, añádiendo un módulo de validación de entradas.

Tomando como base el proyecto de [ginnko2019/aaintegration](https://github.com/ginnko2019/aaintegration), se ha implementado un patrón de diseño proxy para añadir una capa de validación de entradas
antes de que las solicitudes sean procesadas por el servicio de IA. Este enfoque permite interceptar y validar las solicitudes, asegurando que solo se envíen
datos válidos al servicio de IA.


## Instalación

Requisitos:

* Java 17+

* Maven 3.8+

* Claves API para OpenAI y Azure OpenAI

## Uso

Endpoints


| Metodo  | Ruta    | Descripcion |
|---------|---------|-------------|
| POST| /api/ai/generate | Procesa prompts de usuario     |

* Ejemplo de un uso incorrecto:

![bad ex](Assets/badEj.png)

* Ejemplo de uso correcto

![good ex](Assets/goodEx.png)

## Validación de Entradas

Se ha implementado un módulo de validación de entradas que intercepta las solicitudes antes de que sean procesadas por el servicio de IA.

Este módulo valida ciertos aspectos de las solicitudes, como la longitud del prompt, la presencia de caracteres no permitidos, calidad del texto, etc.
.

## Diagrama de clases

![Diagrama de clases](Assets/diagramaClases.png)

## Despliegue en AWS

Usando una instancia EC2 y añadiendo en las reglas la apertura del puerto 8080
![{3F147BDE-59C1-41F0-A373-CA7CB0453B64}](https://github.com/user-attachments/assets/dd48b8ad-ac70-40e1-a004-d8d347ae532b)

Prueba desde Postman

![{30D57E67-2D99-4143-8FC1-95793FF34DD6}](https://github.com/user-attachments/assets/1bebe3c1-8d3e-4681-87ba-f307e17cdff6)



