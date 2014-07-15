import com.rabbitmq.client.*

println "Staring consumer." 
@Grab(group='com.rabbitmq', module='amqp-client', version='3.2.3')
factory = new ConnectionFactory()
println "Factory created."

conn = factory.newConnection(new Address('127.0.0.1', 5672))
println "Connection establsihed, Creating channel."

channel = conn.createChannel()
println "Channel established."

//channel.exchangeDeclare exchangeName, 'direct'
//channel.queueDeclare(queueName, false, true, true, [:])

def queue = channel.queueDeclare().getQueue()
channel.queueBind queue, 'amq.topic', '#'
 
def consumer = new QueueingConsumer(channel)
channel.basicConsume queue, true, consumer
 
while (true) {
  delivery = consumer.nextDelivery()
  println "Received message: ${new String(delivery.body)}"
  channel.basicAck delivery.envelope.deliveryTag, false
}
channel.close()
conn.close()
