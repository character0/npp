package bootcamp

import com.rabbitmq.client.*

import grails.transaction.Transactional
import groovy.json.JsonBuilder

@Transactional
class PublisherService {

    def grailsApplication

    def publishNewsData(String d, String t, String s1, String n, int newsId, String s2) {

        def builder = new groovy.json.JsonBuilder()
        def json = new JsonBuilder()

        json {
            'date'(d)
            'title'(t)
            'symbol'(s1)
            'news'(n)
            'news_id'(newsId)
            'source'(s2)
        }

        log.info "publish -> Publishing ${json.toPrettyString()} to queue."
        publishHelper(json, grailsApplication.config.mq.news.topic, grailsApplication.config.mq.news.routing.key,)
        // channel.basicPublish 'amq.topic', 'NewsData', null, json.toString().bytes
    }

    def publishReviews(String uid, String s, Date t, String usid, double sc, int h, int o, String pn, String pi, String txt) {

        def builder = new groovy.json.JsonBuilder()
        def json = new JsonBuilder()

        json {
            'unique_id'(uid)
            'summary'(s)
            'time'(t)
            'user_id'(usid)
            'score'(sc)
            'helpful'(h)
            'out_of'(o)
            'profile_name'(pn)
            'product_id'(pi)
            'text'(txt)
        }

//        unique_id text,
//        summary text,
//        time timestamp,
//        user_id text,
//        score double,
//        helpful int,
//        out_of int,
//        profile_name text,
//        product_id text,
//        text varchar,

        publishHelper(json, 'amq.reviews', 'reviews',)
        // channel.basicPublish 'amq.topic', 'NewsData', null, json.toString().bytes
    }

    def publishHelper(def json, def exchangeName, def routingKey) {

        log.info "publishHelper -> Establishing connection to queue."
        def factory = new ConnectionFactory()
        def conn = factory.newConnection(new Address(grailsApplication.config.mq.host, 5672))
        def channel = conn.createChannel()

        log.info "publishHelper -> Publishing ${json} to $exchangeName (key: $routingKey)."
        channel.basicPublish exchangeName, routingKey, null, json.toString().bytes
        channel.close()
        conn.close()
    }

}



