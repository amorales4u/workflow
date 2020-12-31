Storage:

En los servicios de storage se pueden almancenar registros (Files) en carpetas (folders)
Todo file se guarda en un folder.

Un folder su nombre SIEMPRE termina en / ejemplo:

Path:
/Catálogos/Usuarios/

Nombre de carpeta:
Usuarios/

Cuando es necesario se manda como body un json con la estructura de Storage.java


llamados REST
web-context => "workflow"

Obtener la versión de Storage
GET http://localhost:8089/workflow/storage/version

Operaciones de folders

Obtiene un folder:
GET http://localhost:8089/workflow/storage/folder/{ruta  **}

Crea un folder:
POST http://localhost:8089/workflow/storage/folder/{ruta **}

Actualiza un folder:
PUT http://localhost:8089/workflow/storage/folder/{ruta **}

Elimina de forma logica un folder:
DELETE http://localhost:8089/workflow/storage/folder/{ruta  **}


Operaciones de files:

Obtiene un file:
GET http://localhost:8089/workflow/storage/file/{ruta  **}

Crea un file:
POST http://localhost:8089/workflow/storage/file/{ruta  **}

Actualiza un file:
PUT http://localhost:8089/workflow/storage/file/{ruta  **}

Elimina de forma logica un file:
DELETE http://localhost:8089/workflow/storage/file/{ruta  **}

Cada file o folder puede tener notas:

Crear:
POST http://localhost:8089/workflow/storage/note/{ruta **}

{ check Note.java for body }

Obtener todas las notas de un storage:
GET http://localhost:8089/workflow/storage/note/{ruta  **}

Cada file o folder puede tener un Log:

Crear:
POST http://localhost:8089/workflow/storage/log/{ruta **}

{ check Log.java for body }

Obtener todo el log de un storage:
GET http://localhost:8089/workflow/storage/log/{ruta  **}

Cada file o folder puede tener una serie de values, "key pair values":

Crear:
POST http://localhost:8089/workflow/storage/value/{ruta **}

{ check Value.java for body }

Obtener todos los values de un storage:
GET http://localhost:8089/workflow/storage/value/{ruta  **}

Actualizar:
POST http://localhost:8089/workflow/storage/value/{ruta **}

Eliminar fisicamente:
DELETE http://localhost:8089/workflow/storage/value/{ruta **}

Cada file o folder puede tener una serie de Attachments:

Crear:
POST http://localhost:8089/workflow/storage/attach/{ruta **}

{ check Attach.java for body }

Obtener todos los values de un storage:
GET http://localhost:8089/workflow/storage/attach/{ruta  **}

Actualizar:
POST http://localhost:8089/workflow/storage/attach/{ruta **}

Eliminar fisicamente:
DELETE http://localhost:8089/workflow/storage/attach/{ruta **}

Descargar el file attac:
GET http://localhost:8089/workflow/storage/download/{ruta **}

Cada file o folder puede tener datos en formato JSON de uso libre:

Crear:
POST http://localhost:8089/workflow/storage/data/{ruta **}

{ any JSON }

Obtener todos los values de un storage:
GET http://localhost:8089/workflow/storage/data/{ruta  **}

Actualizar:
POST http://localhost:8089/workflow/storage/data/{ruta **}

Eliminar fisicamente:
DELETE http://localhost:8089/workflow/storage/data/{ruta **}


Cada file o folder puede tener permisos por registro:

Crear:
POST http://localhost:8089/workflow/storage/perm/{ruta **}

{ Check Perm.java }

Obtener todos los permisos de un storage:
GET http://localhost:8089/workflow/storage/perm/{ruta  **}

Actualizar:
POST http://localhost:8089/workflow/storage/perm/{ruta **}

Eliminar fisicamente:
DELETE http://localhost:8089/workflow/storage/perm/{ruta **}

