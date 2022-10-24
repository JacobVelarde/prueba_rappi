¿En qué consiste el principio de responsabilidad única? ¿Cuál es su propósito?

El principio de responsabilidad única consiste en que una clase o función solo se encargue de ejecutar un solo proceso y su propósito es hacer un código mantenible, testeable, legible y escaladle

Ejemplo:

Incorrecto:
Una función que se encargue de sumar, restar y multiplicar al mismo tiempo

Correcto:
Una función que sea solo para sumar, otra función para restar y una ultima función para multiplicar


¿Qué características tiene, según su opinión, un “buen” código o código limpio?

Un buen código limpio es aquel código que sigue las buenas practicas de programación como lo son patrones de diseño, una buena arquitectura, principio de responsabilidad única, tiene que ser legible, testeable, mantenible, escalable...


Detalla cómo harías todo aquello que no hayas llegado a completar. 

Me hizo falta la parte Offline, pero eso se soluciona implementando una base de datos como Room, se guarda la información que regresen los servicios y actualizando cada vez que se cuente con internet.

Dentro del código existe una clase repository, la cual se debería de encargar de buscar la información almacenada en la base de datos cuando no cuente con internet la aplicación