package com.clearleap.cs

import java.util.concurrent.atomic.AtomicLong
import org.apache.log4j.Logger
import com.clearleap.cs.domain.Asset
import com.clearleap.cs.datastax.DatastaxAssetDao


class TestService {

    private static Logger log = Logger.getLogger(com.clearleap.cs.TestService.getClass().getName())

    int totalWritesPerThread = 3000 //100_100_000_000
    int totalRows = totalWritesPerThread
    int totalReadsPerThread = 3000
    int numberOfThreads = 10
    int totalWrites = numberOfThreads * totalWritesPerThread
    int totalReads = numberOfThreads * totalReadsPerThread

    AtomicLong totalWriteTime = new AtomicLong(0)
    double averageWriteTime = 0
    AtomicLong totalReadTime = new AtomicLong(0)
    double averageReadTime = 0

    List threadsList = []
    def threadCounter = 0


    def executeTest(AssetDao dao) {

        (1..numberOfThreads).each {threads ->

            threadsList << Thread.start {  start ->
                def threadNum = ++threadCounter
                Random random = new Random()

                for (int i=1; i < totalWritesPerThread; i++) {
                    Asset a = new Asset();
                    a.guid = UUID.randomUUID()
                    a.assetId = "MXPD${threads}000000000${i}"
                    a.createDate = new Date()
                    a.description = "This is the default asset for API testing."
                    a.startDate = new Date()
                    a.endDate = new Date() + 100

                    def startTime = System.currentTimeMillis()
                    dao.save(a)
                    int writeTime = System.currentTimeMillis() - startTime;
                    if (writeTime > 10) {
                        log.info "threadnum $threadNum, writeTime $writeTime"
                    }
                    totalWriteTime.addAndGet(writeTime);
                }

                log.info "Write done..."
                for (int i=1; i < totalReadsPerThread; i++) {
                    def startTime = System.currentTimeMillis()
                    def asset = dao.get("MXPD${threads}000000000${i}")
                    totalReadTime.addAndGet(System.currentTimeMillis() - startTime)
                }
                log.info "Read done"
            }
        }

        threadsList.each {it.join()}

        log.info "Total write time $totalWriteTime"

        averageWriteTime = totalWriteTime / totalWrites
        averageReadTime = totalReadTime / totalReads

        log.info "Total write time ${totalWriteTime / 1000} seconds for $totalWrites writes "
        log.info "Total read time ${totalReadTime / 1000} seconds for $totalReads reads"
        log.info "Average Write time $averageWriteTime ms"
        log.info "Average Read time $averageReadTime ms"
    }


}