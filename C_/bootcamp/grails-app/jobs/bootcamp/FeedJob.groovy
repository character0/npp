package bootcamp

class FeedJob  {

	def feedService

    static triggers = {
        simple name: 'FeedJobTrigger', startDelay: 3000, repeatInterval: 15000
    }

    def execute() {
        // execute job
        feedService.checkFeeds()
    }

}