import com.rabbitmq.client.*
 
println "Starting Producer…"

@Grab(group='com.rabbitmq', module='amqp-client', version='3.2.3')
factory = new ConnectionFactory()
factory.setPort(5672)
factory.setHost("192.168.1.2")
//factory.setHost("127.0.0.1")
conn = factory.newConnection()
println "Connection established"

println "Creating channel"
channel = conn.createChannel()
 
println "Publishing"
channel.basicPublish "amq.topic", "#", null, "Hello, world!".bytes
println "Published" 

channel.close()
conn.close()
