import clquality.Asset
import clquality.Content

class CLQualityTagLib {

    AboutService aboutService

    def clQualityInfo = { attrs, body ->
        out << aboutService.logVersionInfo()
    }

    def clDisplayAdiXmlLink = { attrs, body ->
        String outStr = ""
        def img = resource(dir: 'images', file: 'xml.png')
        if (attrs.asset) {
            Asset asset = attrs.asset
            // Displays link for as scheduled ADI.XML without channel template applied
            def adiLink = createLink(controller: 'asset', action: 'showAdiXml', id: asset.id)
            outStr += "<a href=\"${adiLink}\">"
            outStr += "<img src='${img}' title='ADI.XML' alt='ADI.XML'>"
            outStr += "</a>"
        }
        out << outStr
    }

    def clDisplayManzanitaXmlLink = { attrs, body ->
        String outStr = ""
        if (attrs.content) {
            Content content = attrs.content
            Asset asset = content.asset
            // Displays link for as scheduled ADI.XML without channel template applied
            def manzanitaLink = createLink(controller: 'content', action: 'showManzanitaXml', id: content.id)
            outStr += "<a href=\"${manzanitaLink}\">"
            outStr += content.manzanitaResult ? content.manzanitaResult : ""
            outStr += "</a>"
        }
        out << outStr
    }

    def clDisplayDataServiceXmlLink = { attrs, body ->
        String outStr = ""
        def img = resource(dir: 'images', file: 'xml.png')
        if (attrs.content) {
            Content content = attrs.content
            Asset asset = content.asset

            if (content.dataServiceXml?.length() > 0) {
                def dsLink = createLink(controller: 'content', action: 'showDataServiceXml', id: content.id)
            outStr += "<a href=\"${dsLink}\">"
            outStr += "<img src='${img}' title='DataService.XML' alt='DataService.XML'>"
            outStr += "</a>"

            }
        }
        out << outStr
    }
	
	def clSplitCamelCase = { attrs, body ->
		def text = "${attrs.text}"
		if (!text) return
		
		char c
		int charVal
		
		def outStr = ""
		for (int i = 0; i < text.length(); i++) {
			c = text.charAt(i)
			charVal = (int)c
			// Determine if this char is a capital letter or number
			if ( i > 0 && ( (charVal >= 65 && charVal <= 90) || (charVal >= 48 && charVal <= 57) ) ) {
				outStr += " ${c}"
			}
			else {
				outStr += "${c}"
			}
		}
		
		out << outStr
	}
}
