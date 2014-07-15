import com.rabbitmq.client.*
 
@Grab(group='com.rabbitmq', module='amqp-client', version='2.8.7')
factory = new ConnectionFactory([username: 'guest', password: 'guest',
                                 virtualHost: '/', requestedHeartbeat: 0])
conn = factory.newConnection(new Address('localhost', 5672))
channel = conn.createChannel()
 
channel.basicPublish 'myExchange', 'myRoutingKey', null, "Hello, world!".bytes
 
channel.close()
conn.close()