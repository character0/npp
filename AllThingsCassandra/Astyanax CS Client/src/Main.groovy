import domain.Asset
import java.util.concurrent.atomic.AtomicLong

int totalWritesPerThread = 3000 //100_100_000_000
int totalRows = totalWritesPerThread
int totalReadsPerThread = 6000
int numberOfThreads = 1
int totalWrites = numberOfThreads * totalWritesPerThread
int totalReads = numberOfThreads * totalReadsPerThread



AtomicLong totalWriteTime = new AtomicLong(0)
double averageWriteTime = 0
AtomicLong totalReadTime = new AtomicLong(0)
double averageReadTime = 0

List threadsList = []
def threadCounter = 0

(1..numberOfThreads).each {threads->
    threadsList << Thread.start {  start ->
        def threadNum = ++threadCounter
        Random random = new Random()
        (1..totalWritesPerThread).each { it->
            Asset a = new Asset();
            a.guid = [1, random.nextInt(totalWritesPerThread)].max() as String
            a.assetId = "MXPD0000000000${a.guid}"
            a.createDate = new Date()
            a.description = (1..2600).collect{"a"}.join();
            a.startDate = new Date()
            a.endDate = new Date() + 100

            def startTime = System.currentTimeMillis()
            AssetDao.save(a)
            int writeTime = System.currentTimeMillis() - startTime;
            if (writeTime > 10) {
                println "threadnum $threadNum, writeTime $writeTime"
            }
            totalWriteTime.addAndGet(writeTime);
        }

        println "Write done..."


        (1..totalReadsPerThread).each { it->
            def startTime = System.currentTimeMillis()
            def guid = [1, random.nextInt(totalWritesPerThread)].max() as String
            def asset = AssetDao.get(guid)
            if (asset) assert asset.assetId == "MXPD0000000000${asset.guid}"
            totalReadTime.addAndGet(System.currentTimeMillis() - startTime)
        }
        println "Read done"

        /*(1..totalWritesPerThread).each { it->
            AssetDao.delete(it as String)
        }
        println "Delete done"*/

    }
}

threadsList.each {it.join()}

println "Total write time $totalWriteTime"

averageWriteTime = totalWriteTime / totalWrites
averageReadTime = totalReadTime / totalReads

println "Total write time ${totalWriteTime/1000} seconds for $totalWrites writes "
println "Total read time ${totalReadTime/1000} seconds for $totalReads reads"
println "Average Write time $averageWriteTime ms"
println "Average Read time $averageReadTime ms"






