# Poison pill pattern

* Poison pill pattern is a fancy term to notify the consumer that producer is done with producing messages & there are no more tasks for the consumer to wait for.
* It is known predefined data item that allows to provide graceful shutdown for separate distributed consumption process
* Obviously, the poison pill has to be the last item placed  on the queue or else the consumer will shut down prematurely. 

References
==========
https://www.baeldung.com/java-blocking-queue

https://stackoverflow.com/questions/8974638/blocking-queue-and-multi-threaded-consumer-how-to-know-when-to-stop

https://dzone.com/articles/producers-and-consumers-part-3

https://www.mkyong.com/java/java-blockingqueue-examples/
