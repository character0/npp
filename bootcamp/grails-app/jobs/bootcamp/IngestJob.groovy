package bootcamp

class IngestJob {

    def ingestService

    static triggers = {
        simple name: 'ReceiverJobTrigger', startDelay: 1000, repeatInterval: 15000
    }

    def execute() {
        // execute job
        ingestService.checkIngestSources()
    }
}
