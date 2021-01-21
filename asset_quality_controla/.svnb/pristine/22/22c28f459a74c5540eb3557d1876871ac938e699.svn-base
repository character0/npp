class AboutService
{

	static expose = ['jmx']

	boolean transactional = true

	def buildKey    = "dev"
	def buildNumber = "0"
	def buildTime   = "04/11/2011 03:32 PM"
	def SVNVersion  = "dev"

	def String logVersionInfo()
	{
		String info = "Version info: BuildKey:${buildKey} BuildNumber:${buildNumber} SVN#:${SVNVersion} BuildDate:${buildTime}"
        log.info info
        return info
	}
}
