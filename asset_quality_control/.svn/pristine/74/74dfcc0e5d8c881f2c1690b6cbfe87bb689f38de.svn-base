eventCompileStart = { type ->
  println "CompileStart event handler - assembling external jar and conf files"

  def clcoreHome = "${basedir}/../clcore"

  if (Ant.antProject.properties."environment.CLCORE_DIR_NAME") {
    println("Setting clclore home from ENV: ${Ant.antProject.properties."environment.CLCORE_DIR_NAME"}")
    clcoreHome = "${basedir}/../${Ant.antProject.properties."environment.CLCORE_DIR_NAME"}"
  }

  def clcoreSrc = "${clcoreHome}/src/com/clearleap/core"
  def clwebHibernate = "${basedir}/grails-app/conf/hibernate"

  Ant.copy(file: "${clcoreHome}/lib/clcore.jar", todir: "${basedir}/lib", overwrite: true)
  Ant.copy(file: "${clcoreHome}/lib/commons-compress-1.5.jar", todir: "${basedir}/lib", overwrite: true)
}

eventPackagingEnd = {
  println "PackagingEnd - copying liquibase changelog.xml to WEB-INF"
  Ant.copy(file:'grails-app/migrations/changelog.xml',todir:'web-app/WEB-INF')
}

eventSetClasspath = { rootLoader ->

	// clcore isn't there yet, but it will be
	rootLoader.addURL(new URL( "file://${grailsSettings.baseDir}/lib/clcore.jar" ))

	// This is where to find the changelog.xml
	//rootLoader.addURL(new URL( "file://${grailsSettings.baseDir}/web-app/WEB-INF" ))

	println("eventSetClasspath: dumping paths")

	rootLoader.getURLs().each {
		println(it)
	}
}
