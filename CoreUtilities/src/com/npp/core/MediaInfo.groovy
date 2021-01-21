package com.pabslabs.core

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

import java.security.MessageDigest

class MediaInfo
{
	def filename
	String checksum = null
	Long filesize = null
	Double runtime = null
	Long videoBitRate = null
	Long audioBitRate = null
	Long aggregateBitRate = null
	String videoCodec = null
	String audioCodec = null
	Long videoStreams = 0
	Long audioStreams = 0
	Long x = null
	Long y = null
	String par = null
	String dar = null
	Boolean hasAudio = false
	Boolean hasVideo = false
	String audioType = null
	Double fps = null
	String container = null
	Long audioSampleRate


	protected final Log logger = LogFactory.getLog(getClass())

    MediaInfo(def filename)
	{

		this.filename = filename

        File file = new File(filename)
        if (!file) {
			println("Null file. No Thank you. Refusing to try.")
            return
		}

		if (!file.exists()) {
			println("Requested info on a file that doesn't exist. No Thank you. Refusing to try.")
            return
        }

		filesize = file.length()
		getFFMpegData()

	}

	def calcMD5()
	{
		MessageDigest digest = null

        digest = MessageDigest.getInstance("MD5")

        InputStream is = new FileInputStream(filename)
        byte[] buffer = new byte[8192]
        int read = 0

        while ((read = is.read(buffer)) > 0) {
			digest.update(buffer, 0, read)
        }

		byte[] md5sum = digest.digest()
        StringBuffer hexString = new StringBuffer()

        for (int i = 0; i < md5sum.length; ++i) {
			hexString.append(Integer.toHexString(0x0100 + (md5sum[i] & 0x00FF)).substring(1))
        }

		checksum = hexString.toString()

        //md5 = Base64.encodeBase64( md5sum ).toString();

	}

	def getFFMpegData()
	{
		def cmds = ["ffmpeg", "-i", filename]
		def proc = cmds.execute()

		proc.err.eachLine {line ->
			line = line.trim()

			if (line.startsWith("Input #")) {
				container = line.split(",")[1].trim()
			}

			if (line.startsWith("Duration:")) {
				handleDurationLine(line.replaceFirst("Duration:", "").trim())
			}
			if (line.startsWith("Stream") && line.contains("Audio:")) {
				hasAudio = true
                if (audioStreams++ == 0) {
					handleAudioLine(line.replaceFirst("Stream.+Audio:", "").trim())
				}
			}
			if (line.startsWith("Stream") && line.contains("Video:")) {
				hasVideo = true
                if (videoStreams++ == 0) {
					handleVideoLine(line.replaceFirst("Stream.+Video:", "").trim())
				}
			}
		}
	}

	def handleDurationLine(def line)
	{
		def parms = line.split(",")
		def strDuration = parms[0].trim()
		def hms = strDuration.split(":")


		if (hms.length == 3) {
			// Try and get the runtime .
			try { // Protect against bad parse
				runtime = new Double(hms[0]).intValue() * 60 * 60
                runtime += new Double(hms[1]).intValue() * 60
                runtime += new Double(hms[2]).doubleValue() // Get the fractional 1/10
			} catch (Exception e) {
				runtime = null // If anything went wrong, it's invalid
			}
		}

		parms.each {parm ->
			if (parm.contains("bitrate") && parm.contains("kb/s")) {
				def str = parm.split(":")[1].trim().split(" ")[0]
				try { // Protect against bad parse
					aggregateBitRate = new Double(parm.split(":")[1].trim().split(" ")[0]) * 1024
				} catch (Exception e) {}
			}
		}
	}

	def handleVideoLine(def line)
	{
		def parms = line.split(",")
		// Get the positional stuff
		videoCodec = parms[0]

        def matcher
		// Find pixel dimensions
		matcher = (line =~ /\d+x\d+/)
		if (matcher.count == 1) {
			try { // Protect against malformed data
				x = Long.parseLong(matcher[0].split("x")[0])
				y = Long.parseLong(matcher[0].split("x")[1])
			} catch (Exception e) {}

		}

		// Bitrate
		matcher = (line =~ /\d+ +kb\/s/)
		if (matcher.count == 1) {
			def str = matcher[0]
			matcher = (str =~ /\d+/)
			try { // Protect against malformed data
				videoBitRate = new Long(matcher[0]) * 1024
			} catch (Exception e) {}
		}

		// PAR
		matcher = (line =~ /PAR +\d+:\d+/)
		if (matcher.count == 1) {
			def str = matcher[0]
			matcher = (str =~ /\d+:\d+/)
			par = matcher[0]
		}
		// DAR
		matcher = (line =~ /DAR +\d+:\d+/)
		if (matcher.count == 1) {
			def str = matcher[0]
			matcher = (str =~ /\d+:\d+/)
			dar = matcher[0]
		}
		// FPS
		matcher = (line =~ /\d+\.\d+ tb.*r/)
		if (matcher.count == 1) {
			def str = matcher[0]
			matcher = (str =~ /\d+\.\d+/)
			fps = new Double(matcher[0])
		}


	}

	def handleAudioLine(def line)
	{
		def matcher
		def parms = line.split(",")
		audioCodec = parms[0].trim()
        if (parms.length >= 3) {
			audioType = filterAudioType(parms[2].trim()) // Is this safe?
		}

		// Sample Rate
		matcher = (line =~ /\d+ +Hz/)
		if (matcher.count == 1) {
			def str = matcher[0]
			matcher = (str =~ /\d+/)
			audioSampleRate = new Double(matcher[0])
		}


		parms.each {parm ->
			if (parm.contains("kb/s")) {
				try { // Protect against malformed data
					audioBitRate = Long.parseLong(parm.trim().split(" ")[0]) * 1024
				} catch (Exception e) {}

			}
		}


	}

	def getChecksum()
	{
		calcMD5()
		return checksum
	}

	/**
	 * Need to blow out
	 */
	def filterAudioType(def audiotype)
	{
		if (audiotype == "stereo") {
			return "Stereo"
        }
		return audiotype
	}
}
