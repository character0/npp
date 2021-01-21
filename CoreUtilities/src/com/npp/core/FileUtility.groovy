
class FileUtility {

    public static void main(String[] args) {


        def BASE_DIR = "M:\\Videos\\TV\\"
        def FOLDER = "American.Dad!\\Season.2\\"
        def SERIES = "American.Dad!"

        File workingFolder = new File(BASE_DIR + FOLDER)

//File[] filesToClean = workingFolder.listFiles()

        println "Looking for work in: ${workingFolder.absolutePath}"

        File[] filesToClean = workingFolder.listFiles(
                { dir, file -> file ==~ /.*\.[m][k][v]/ } as FilenameFilter
                //[g][h][l]\..*?/
        )?.toList()


        println "${filesToClean.length} file names to clean."

        for (file in filesToClean) {

            if (!file.isDirectory()) {

                def filename = file.getName()
                println "Processing $filename"

                def stringTokens = filename.tokenize(".")
                println "Tokens: " + stringTokens
                println "Replacing ${stringTokens[0]} with new value."
                stringTokens[0] = SERIES
                //println "Cleansed name: " + cleansedName + "1080"


                println "Tokens New: " + stringTokens
                // def firstname = stringTokens[1]
                //def lastname = stringTokens[2].startsWith("{") ? "_" : stringTokens[2]

                //def lastToken = (stringTokens[stringTokens.size()-1].replace(")", ""))
                //def fileDate = lastToken.substring(0, lastToken.lastIndexOf(".")).tokenize(".")
                //println "Date: $fileDate"

                //def fileExt = lastToken.substring(lastToken.lastIndexOf(".")+1)
                //println "Ext: " + fileExt

                def newFilename = new StringBuilder()
                newFilename << workingFolder.path
                newFilename << "\\"
                newFilename << stringTokens.pop()

                stringTokens.each { token ->
                    newFilename << ".${token.toLowerCase()}"
                }
                println "New Name: ${newFilename}"
                //file.renameTo(newFilename.toString())

                //NamedProcess remux = new NamedProcess(filename);
                //remux.processCommand = "ffmpeg -i ${file.canonicalPath} -y -vcodec copy -acodec copy -sn ${filename}"
                //remux.start(true)
            }

            /*directory.eachDir { dir ->
            handleDirectories(receiver, dir)
        }*/


            /* */

        }

    }
}