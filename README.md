# AC2-AF-POO2

Nome: Vitor Guilherme Sanches Magnani de Sobral.  
RA: 190093 

### Admins

##### https://poo2-af-2021-s1.herokuapp.com/admins/

##### Insert & Update

```json
{  
    "name": "Vitor",  
    "email": "vitor@gmail.com",
    "phoneNumber": "(11) 96857455"  
}
```



### Attendees

##### https://poo2-af-2021-s1.herokuapp.com/attendees/

##### Insert & Update

```json
{  
    "name": "Pedro",  
    "email": "pedro@hotmail.com"  
}
```



### Places

##### https://poo2-af-2021-s1.herokuapp.com/places/

##### Insert & Update

```json
{  
   "name": "Predio A",  
    "address": "Rua B, numero 999"  
}
```



#### Events

##### https://poo2-af-2021-s1.herokuapp.com/events/

##### Insert

```json
{  
    "name": "Show de standup",  
    "description": "muito humor e risada",  
    "startDate": "2021-11-17",  
    "endDate": "2021-11-17",  
    "startTime": "19:30:00",  
    "endTime": "21:00:00",  
    "emailContact": "contato@risadaria.com.br",  
    "amountFreeTickets": 50,  
    "amountPayedTickets": 250,  
    "priceTicket": 50,  
    "adminId": 1,   
    "placeId": 1  
}
```
##### Update

```json
{  
    "name": "Show de standup",  
    "description": "muito humor e risada",  
    "startDate": "2021-11-17",  
    "endDate": "2021-11-17",  
    "startTime": "19:30:00",  
    "endTime": "21:00:00",  
    "emailContact": "contato@risadaria.com.br",  
    "amountFreeTickets": 50,  
    "amountPayedTickets": 250,  
    "priceTicket": 50,  
    "oldPlaceId": 1,
    "newPlaceId": 3
}
```

#### Events/id/tickets

##### https://poo2-af-2021-s1.herokuapp.com/events/1/tickets

##### Insert

```json
{
    "attendeeId" : 4,
    "type" : "FREE"
}
```

##### Delete

```json
{
    "attendeeId" : 4,
    "ticketId" : 4
}
```

#### Events/eventId/places/placeId

##### https://poo2-af-2021-s1.herokuapp.com/events/1/places/1


