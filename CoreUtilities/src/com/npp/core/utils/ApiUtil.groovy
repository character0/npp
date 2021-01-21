package com.pabslabs.core.utils


import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

import java.security.SecureRandom

/**
 * Synchronized version of CircularFifoBuffer class. The add method is implemented to be synchronized amongst threads.
 *
 * Created with IntelliJ IDEA.
 * User: npp
 * Date: 5/7/13
 * Time: 10:09 PM
 * To change this template use File | Settings | File Templates.
 */
class ApiUtil {

    private static final Log log = LogFactory.getLog("com.pabslabs.core.utils.ApiUtil")
    private static Map mapOfRingBuffers = new HashMap<Long, com.pabslabs.core.SynchronizedRingBuffer<Long>>()
    private static SecureRandom prng

    static boolean apiRequestThrottle(userId, requestsPerBlock, requestBlockTime) {

        def requestTime = System.currentTimeMillis()
        if (mapOfRingBuffers.containsKey(userId)) {

            com.pabslabs.core.SynchronizedRingBuffer usersRingBuffer = mapOfRingBuffers.get(userId)

            //TODO event somewhere to notify someone is blocked out
            if (usersRingBuffer.full) {
                log.debug "apiRequestThrottle - User ${userId}'s API ringBuffer is full"
                // Gets the oldest member
                def firstRequestMade = usersRingBuffer.get()
                log.debug "apiRequestThrottle - Time passed since user ${userId}'s first request in ring buffer ${requestTime - firstRequestMade} ms"
                if ((requestTime - firstRequestMade) <= (requestBlockTime * 1000)) {
                    log.warn "apiRequestThrottle - Enough time has not passed to allow the next API request for user ${userId}."
                    usersRingBuffer.add(requestTime)
                    return false
                } else {
                    // Buffer is full but request came after delay setting.
                    log.debug "apiRequestThrottle - Enough time has passed to allow the next API request for user ${userId}."
                    // Will evict least recent when full
                    usersRingBuffer.add(requestTime)
                }
            } else {
                log.debug "apiRequestThrottle - API request ringBuffer is not yet full for user ${userId}; Contains ${usersRingBuffer.getRingBuffer().size()} elements"
                usersRingBuffer.add(requestTime)
            }

        } else {
            log.debug "apiRequestThrottle - First API request for user ${userId}"
            def ringBuffer = new com.pabslabs.core.SynchronizedRingBuffer(requestsPerBlock)
            log.debug "apiRequestThrottle - User ${userId}'s ring buffer size initialized to size of ${ringBuffer.getRingBuffer().maxSize()}"
            ringBuffer.add(requestTime)
            mapOfRingBuffers.put(userId, ringBuffer)
        }

        return true
    }


    static String generateApiKey() {
        if (!prng) {
            prng = SecureRandom.getInstance("SHA1PRNG")
        }

        byte[] randomBytes = new byte[48]
        prng.nextBytes(randomBytes)

        def apiKey = randomBytes.encodeAsBase64().replaceAll("[^0-9a-zA-Z]", "1")[0..29]
        log.info "generateApiKey: Returning API key value: $apiKey"
        return apiKey
    }
}
