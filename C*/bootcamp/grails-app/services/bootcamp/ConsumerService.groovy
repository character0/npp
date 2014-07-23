package bootcamp

import com.rabbitmq.client.*

import grails.transaction.Transactional

@Transactional
class ConsumerService {

    def consume() {
        log.info "consume -> Establishing connection to queue."
        def factory = new ConnectionFactory([username: 'guest', password: 'guest', virtualHost: '/', requestedHeartbeat: 0])
        def conn = factory.newConnection(new Address('localhost', 5672))
        def channel = conn.createChannel()

        def exchangeName = 'myExchange'
        def queueName = 'myQueue'

        channel.exchangeDeclare exchangeName, 'direct'
        channel.queueDeclare(queueName, false, true, true, [:])
        channel.queueBind queueName, exchangeName, 'myRoutingKey'

        def consumer = new QueueingConsumer(channel)
        channel.basicConsume queueName, false, consumer

        log.info "consume -> Starting to do the consuming."
        while (true) {
            log.info "consume -> Consuming....."
            def delivery = consumer.nextDelivery()
            log.info "consume -> Received message: ${new String(delivery.body)}"
            channel.basicAck delivery.envelope.deliveryTag, false
        }

        channel.close()
        conn.close()
    }

}





