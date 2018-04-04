> Spring AMQP's rabbit namespace includes sevral elts for lazily creating queues,  
exchanges, and the bindings between them  
(excluded ```<> in Element```)

<table>
  <tr>
    <th>Element</th><th>What is dows</th>
  </tr>
  <tr>
    <td>queue</td>
    <td>Creates a queue</td>
  </tr>
  <tr>
    <td>fanout-exchange</td>
    <td>Creates a fanout exchange</td>
  </tr>  
  <tr>
    <td>header-exchange</td>
    <td>Creates a headers exchange</td>
  </tr>
  <tr>
    <td>topic-exchange</td>
    <td>Creates a topic exchange</td>
  </tr>
  <tr>
    <td>direct-exchange</td>
    <td>Creates a direct exchange</td>
  </tr>  
  <tr>
    <td> bindings binding/ /bindings</td>
    <td>
        bindings : a set of one or more binding elts.<br>  
        binding : create a biding between an exchange and  
        a queue
    </td>
  </tr>    
</table>  

> e.g) declare a queue named spittle.alert.queue  

```
<admin connection-factory="connectionFactory"/>
<queue id="spittleAlertQueue" name="spittle.alerts" />
```  

> e.g) have a message routed to multiple queues with no regard  
for the routing key  

```
<admin connection-factory="connectionFactory" />
<queue name="spittle.alert.queue.1">
<queue name="spittle.alert.queue.2">
<queue name="spittle.alert.queue.3">
<fanoutexchange name="spittle.fanout">
  <bindings>
    <binding queue="spittle.alert.queue.1"/>
    <binding queue="spittle.alert.queue.2"/>
    <binding queue="spittle.alert.queue.3" />
  </bindings>
</fanoutexchange>
```  

> Configure RebbitTemplate  

```
<template id="rabbitTemplate" connection-factory="connectionFactory" />
```  












<br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br />












---  
