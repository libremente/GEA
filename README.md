# Sistema Informativo Gestione Atti (GEA)
## Consiglio regionale della Lombardia
Il Sistema Informativo di __Gestioni Atti del Consiglio regionale della Lombardia__ ha come architettura:

* __Front - end__: maschera che gli utenti utilizzano per entrare nel sistema.
* __Back - end__ : Alfresco Community 4.
* __Back - end__ : Sistema di pubblicazioni OPENDATA (web service).

Il __front - end__ è sviluppato utilizzando __Primefaces__ insieme a __Java JSF__. __Primefaces__ è una libreria di User Interface che è completamente integrabile con __Java JSF__. __Java Server Faces__ è una tecnologia Java che segue il pattern di Ingegneria del Software __Model-View-Controller__, il cui scopo è quello di sempliﬁcare lo sviluppo dell'interfaccia utente (UI) di una applicazione Web; può quindi essere considerata un framework per componenti lato server di interfaccia utente.

Il collegamento del __front-end__(maschera) con __Alfresco__ avviene tramite i __REST SERVICES__ di __Alfresco__ per supportare tutto il ciclo di vita di un atto. L’autenticazione si fa utilizzando un ticket di sessione con Alfresco che si fornisce ad Alfresco in ogni chiamata REST. Tutto il ciclo di vita dell’atto si trova all’interno di Alfresco mappato in una gerarchia di cartelle.

__Alfresco__ è un'applicazione Java di __Enterprise Content Management (ECM)__ basata su due differenti tipologie di storage:
* File system
* Database All'interno del ﬁle system Alfresco gestisce internamente tutti i ﬁle binari dei contenuti e tutti gli indici di ricerca.
  
__Gea__ pubblica inoltre, sul portale opendata di Regione Lombardia (www.dati.lombardia.it), le principali informazioni relative agli atti gestiti. Le pubblicazioni avvengono attraverso delle chiamate, effettuate direttamente in Alfresco utilizzando __Apache CXF__, a un webservice __SOAP__ messo a disposizione dal Consiglio regionale che si occupa di interfacciarsi con il portale opendata.

__Apache CXF__ è un framework sviluppato e mantenuto dalla fondazione Apache. Il suo nome deriva dalla fusione di due progetti (Celtix e Xﬁre) e l’obiettivo è quello di fornire delle astrazioni e strumenti di sviluppo per esporre varie tipologie si servizi web.

Le potenzialitá del framework si possono riassumere nelle seguenti:
* Netta separazione dall’interfaccia JAX-WS e l’implementazione
* Semplicitá di deﬁnire servizi web e client tramite annotazioni
* Alte performance
* Alta modularitá dei suoi componenti (che rende possibile esporre servizi standalone o su servlet container)

![alt](/stack.png)