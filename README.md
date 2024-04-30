#### Conectados

### Usar la aplicación mediante el enlace del despliegue:

Debemos de tener en cuenta antes de nada, que debido a las restricciones impuestas por la versión gratuita de Render, no podemos gestionar volúmenes, por lo que tal y como se explica en la memoria, tras unos minutos de inactividad Render desactiva el contenedor y por tanto la base da datos vuelve a su estado original. Una vez vuelve a recibir alguna petición, puede llegar a tardar hasta 90 segundos para procesar la misma, esto es debido a que debe volver a levantar el contenedor.

[Acceso a Conectados, versión desplegada](https://conectados-two.vercel.app/initialice)

### Usar la aplicación en local

Debido a los problemas expirimentados con el despliegue, he deasrrollado un Dockerfile, para facilitar el arranque del backend. Este se encuentra en la raiz del proyecto. Parta ejecutarlo solo es mnecesario ejecutar los siguientes comandos desde la raiz del proyecto:

```Shell
docker build -t conectados-back .
docker run -p 8080:8080 --name conectados-back conectados-back
```

## Parta ejecutar el frontend deberemos seguir los siguinetes pasos

## Instalación de NVM - `Windows`

## 1. Descarga el instalador de NVM

Descarga el archivo de `nvm-setup.exe` del siguiente [enlace](https://github.com/coreybutler/nvm-windows/releases/tag/1.1.12).

## 2. Ejecuta el instalador y completa la instalación

## 3. Comprueba la instalación de NVM

```Shell
nvm -v
```

## Instalación de NVM - `MacOS y Linux`

## 1. Navegar al directorio de home

```Shell
cd ~
```

## 2. Crea el archivo `.zshrc` (si no existe)

```Shell
touch .zshrc
```

## 3. Instalar Rossetta - `MacOS Serie M`

```Shell
softwareupdate --install-rosetta
```

## 4. Instala NVM

```Shell
curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.7/install.sh | bash
```

Abre el archivo de `.zshrc` (o `bashrc` según tu caso):

```Shell
open ~/.zshrc
```

Comprueba que se han añadido las siguientes líneas:

```
export NVM_DIR="$HOME/.nvm"
[ -s "$NVM_DIR/nvm.sh" ] && \. "$NVM_DIR/nvm.sh"  # This loads nvm
[ -s "$NVM_DIR/bash_completion" ] && \. "$NVM_DIR/bash_completion"  # This loads nvm bash_completion
```

## 5. Recarga la configuración del terminal

```Shell
source ~/.zshrc
```

## 6. Comprueba la instalación de NVM

```Shell
nvm -v
```

## Instalación de Node

## 1. Instala Node usando NVM

```Shell
nvm install 20.11.1
```

## 2. Indica la versión de Node a usar

```Shell
nvm alias default v20.11.1
```

> Cierra y abre la terminal en caso de que no se actualice la versión que estás usando

## 3. Limpia la cache:

```Shell
nvm cache clear
```

## Instalación de Angular

## 1. Instala Angular de forma Global

```Shell
npm install -g @angular/cli@17.2.0
```

## 2. Comprueba que se ha instalado correctamente

```Shell
ng version
```

## Instalación de Ionic

## 1. Instalación de Ionic

```Shell
npm install -g @ionic/cli@7 native-run cordova-res
```

## 2. Instalación de las dependencias de Ionic

```Shell
npm install -g @ionic/pwa-elements
```

## Instalación de las dependencias del proyecto

## 1. Acceda al proyecto frontend, conectados, y ejecute el comando

```Shell
npm i
```

## Lanzar el frontend

Una vez se ha realizado toda la configuración para poder ejecutar el frontend, solo debemos de ejecutar los comandos de docker previamente descritos y acto seguido navegar hasta la carpeta del proyecto frontend y escribir:

```Shell
ionic serve
```

Tras esto, se debe de ejecutar el proyecto de formma local apuntando al backend, el cual está corriendo gracias a Docker. Debería de abrir una ventana del navegador de forma automática tras finalizar el proceso de compilación, de todos modos, es aconsejable acceder a la url [http://localhost:8100](http://localhost:8100) desde una ventana de incognito de su navegador preferido. Acto seguido ya puede probar la aplicación con total libertad.
