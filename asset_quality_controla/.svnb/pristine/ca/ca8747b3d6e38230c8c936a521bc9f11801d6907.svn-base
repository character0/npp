package clquality

import grails.test.*
import states.ManzanitaResultStates
import com.clearleap.core.asset.ContentState
import states.AssetStates

class AssetServiceTests extends GrailsUnitTestCase {

    def assetService = new AssetService()

    Asset happyAsset

    protected void setUp() {
        super.setUp()

        Calendar c = Calendar.getInstance()
        c.setTime(new Date())
        c.set(Calendar.YEAR, 2009)
        c.set(Calendar.MONTH, Calendar.JANUARY)
        c.set(Calendar.DATE, 22)
        c.set(Calendar.HOUR_OF_DAY, 10)
        c.set(Calendar.MINUTE, 1)
        c.set(Calendar.SECOND, 1)

        happyAsset = new Asset(centralId: 3, mediaId: "123-123-123", name: "batman",
                startDate: c.time, createDate: new Date(), assetState: AssetStates.STAGED.toString(),
                assetId: "source.asset.id", rating: "R", title: "The Batman",
                shortTitle: "I'm Batman...", episode: "1", ingestSourceId: 2,
                endDate: c.time + 3, description: "It's the Batman", qcLevel: "FULL", hasPreviews: true,
                stagedDirectoryName: "test", runtime: 4961, qcType:  "FULL", advisory: "AC;GL;MV;")
    }

    protected void tearDown() {
        super.tearDown()
    }

    /**
     *
     */
    /*void testDumpAssetMetadataFile() {

        //Original preview
        Content adiContent = new Content(centralId: 4, original: true, name: "ADI.XML",
                checksum: "123456789", dataServiceXml: """ </xml> """,
                size: 100, runtime: 100, contentType: "METADATA", asset: happyAsset,
                profile: "ADIXML", contentState: ContentState.COMPLETE.toString(),
                uri: "/usr/local/clearleap/test/ADI.XML")

        happyAsset.contents.add(adiContent)

        Content content = new Content(centralId: 4, original: true, name: "batman.mpg",
                checksum: "123456789", audioType: "Stereo", dataServiceXml: "</xml>",
                size: 100, runtime: 100, contentType: "PREVIEW", asset: happyAsset,
                profile: "Pro 7", contentState: ContentState.COMPLETE.toString())

        content.manzanitaResult = ManzanitaResultStates.FAILED.toString()
        content.manzanitaReport = """
<tsa-report
        version="1.0.0"
        license-serno="CC0825"
        license-username="Clearleap, Inc."
        date="Fri Jun 17 16:48:18 2011"
        file="/usr/local/clearleap/cache/dev-central2/accounts/2572/assets/1285573/Entourage_S4_08_comp_HBOD000000112574_P7D-1356313.mpg">
    <configuration>
        <analysis-mode value="Auto (MPEG)"/>
        tsa-config 2
        Abort-Limit: none
        About: {CableLabs specification for content containing High Definition
        video and one audio stream.
        Document OC-SP-CEP3.0-I01-100827 (August 27, 2010)
        }
        Use-ATSC-Buffer-Model: on
        PMT-In-Single-Packet: warn
        Warn-On-Missing-AC3-Descriptor: warn
        Max-PCR-Interval: 40.0 warn
        Disc-In-First-PCR-Only: err
        I-Pic-Has-RAI: err
        Max-B-Frame: 2
        Video-All-Pics-Frame: err
        Video-No-Scalable-Ext: err
        I-Pic-Has-DTS: err
        Video-Starts-With-Seq-Start: err
        GOP-First-Is-Closed: err
        GOP-Size-15-or-12: warn
        Video-One-AU-Per-PES: err
        Video-PES-Payload-Has-PCR: err
        Video-AU-Starts-PES: err
        Audio-Starts-With-AU: err
        Audio-Has-No-Partial-AU: err
        Packet-Size: 188
        PAT-Never-Changes: err
        PMT-Never-Changes: err
        PSI-Definition-Precedes-PIDs: err
        No-Partial-Transport-Packets: err
        Max-Rate-PID0-PMT-Vid-Aud: 38000000
        SCTE20-Precedes-ATSC53-CC: err
        Minimum-GOP-Length-Error-Level: warn
        Table [
        PID: 0
        Table-ID: 0
        Maximum-Table-Interval: 250
        ]
        Table [
        Table-ID: 2
        Maximum-Table-Interval: 250
        ]
        Program [
        Video [
        Require-CEA708-DTVCC1: on
        Require-CEA708-CC1: on
        Stream-Type: 2
        Stream-Type-Alternate: 128
        Properties: [
        profile = 4
        level = 4
        aspect_ratio = 3
        chroma_format = 1
        (vertical_size = 1080 horizontal_size = 1920
        (progressive_sequence = 0
        frame_rate = 4) |
        (progressive_sequence = 1
        (frame_rate = 1) | (frame_rate = 2))) |
        (vertical_size = 720 horizontal_size = 1280
        frame_rate = 7
        progressive_sequence = 1)
        ]
        Minimum-GOP-Length: 15
        Maximum-GOP-Length: 15
        PID: 481
        ]
        Audio [
        Stream-Type: 129
        Properties: [
        type = 6
        (channel_mode = 2 bit_rate = 384000) |
        (channel_mode = 7 bit_rate = 384000)
        sample_rate = 48000
        ]
        PID: 482
        ]
        PMT-PID: 480
        PCR-PID: 481
        ]
        end-config
    </configuration>

    <stream-information>
        <transport-rate value="15000000.0"/>
        <duration value="00:27:42.413"/>
        <file-size value="3117026088"/>
        <transport-packet-interval value="188"/>
        <packets-analyzed value="16579926"/>
        <total-errors value="0"/>
        <total-warnings value="3320"/>
    </stream-information>

    <multiplex-summary>
        <pat version="0" pid="0">
            <program program_number="2" pid="0x01E0">
                <pmt version="0" pcr-pid="0x01E1">
                    <stream pid="0x01E2" type="Dolby AC-3 Audio">
                        <descriptor tag="0x05" name="registration_descriptor"/>
                        <descriptor tag="0x0A" name="ISO_639_language_descriptor"/>
                        <descriptor tag="0x81" name="ac3_audio_descriptor"/>
                    </stream>
                    <stream pid="0x01E1" type="MPEG-2 Video">
                    </stream>
                </pmt>
            </program>
        </pat>
    </multiplex-summary>

    <pid-summary>
        <pid-info pid="0x0000" content="PAT"
                  npkt="13601" pkt-rate="12305" payld-rate="12043"/>
        <pid-info pid="0x01E0" content="PMT" programs="2"
                  npkt="13601" pkt-rate="12305" payld-rate="12043"/>
        <pid-info pid="0x01E1" content="MPEG-2 Video, PCR" programs="2"
                  npkt="15844773" pkt-rate="14334901" payld-rate="13999998"
                  errors="0" warnings="3320"/>
        <pid-info pid="0x01E2" content="Dolby AC-3 Audio" programs="2"
                  npkt="466974" pkt-rate="422475" payld-rate="383524"/>
        <pid-info pid="0x1FFF" content="Null"
                  npkt="240977" pkt-rate="218014" payld-rate="218014"/>
    </pid-summary>

    <pid-properties>
        <pid value="0x0000 (0)"/>
        <content value="PAT"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <program-association-table-information>
            <table_id value="0x00"/>
            <version_number value="0"/>
            <transport_stream_id value="0x0001 (1)"/>
        </program-association-table-information>
        <packet-statistics>
            <number-of-packets value="13601"/>
            <first-seen-in-packet-number value="1" time="00:00:00.000"/>
            <last-seen-in-packet-number value="16578712" time="00:27:42.292"/>
            <average-transport-packet-rate value="12305"/>
            <average-payload-rate value="12043"/>
            <average-packet-spacing value="122.2274"/>
            <minimum-packet-spacing value="122.2251"/>
            <packet-number-at-minimum-spacing value="1220" time="00:00:00.122"/>
            <maximum-packet-spacing value="122.3253"/>
            <packet-number-at-maximum-spacing value="7316" time="00:00:00.733"/>
            <minimum-packet-spacing-ends-inclusive value="121.8240"/>
            <maximum-packet-spacing-ends-inclusive value="122.3253"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="13601"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="0"/>
            <number-of-packets-random_access_indicator-is-1 value="0"/>
            <splice_countdown-fields-seen value="No"/>
        </adaptation-field-information>
    </pid-properties>

    <pid-properties>
        <pid value="0x01E0 (480)"/>
        <content value="PMT"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <program-information>
            <pmt-pid-for-program_number value="2"/>
            <listed-in-pat-version_number value="0"/>
        </program-information>
        <program-map-table-information>
            <table_id value="0x02"/>
            <program_number value="2"/>
            <version_number value="0"/>
            <pcr-pid value="0x01E1 (481)"/>
            <section_length value="41"/>
        </program-map-table-information>
        <packet-statistics>
            <number-of-packets value="13601"/>
            <first-seen-in-packet-number value="2" time="00:00:00.000"/>
            <last-seen-in-packet-number value="16578713" time="00:27:42.292"/>
            <average-transport-packet-rate value="12305"/>
            <average-payload-rate value="12043"/>
            <average-packet-spacing value="122.2274"/>
            <minimum-packet-spacing value="122.2251"/>
            <packet-number-at-minimum-spacing value="1221" time="00:00:00.122"/>
            <maximum-packet-spacing value="122.3253"/>
            <packet-number-at-maximum-spacing value="7317" time="00:00:00.733"/>
            <minimum-packet-spacing-ends-inclusive value="0.1003"/>
            <maximum-packet-spacing-ends-inclusive value="122.3253"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="13601"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="0"/>
            <number-of-packets-random_access_indicator-is-1 value="0"/>
            <splice_countdown-fields-seen value="No"/>
        </adaptation-field-information>
    </pid-properties>

    <pid-properties>
        <pid value="0x01E1 (481)"/>
        <content value="MPEG-2 Video, PCR"/>
        <total-errors value="0"/>
        <total-warnings value="3320"/>
        <program-information>
            <member-of-program_number value="2"/>
            <listed-in-pmt-version_number value="0"/>
            <stream_type value="0x02 (H.262 | 13818-2 Video | 11172-2 Constrained Video)"/>
            <listed-as-pcr-pid value="Yes"/>
        </program-information>
        <pcr-statistics>
            <number-of-pcrs-seen value="118328"/>
            <average-pcr-interval value="14.0315"/>
            <minimum-pcr-interval value="0.1003"/>
            <packet-number-at-minimum-pcr-interval value="81142" time="00:00:08.135"/>
            <maximum-pcr-interval value="35.9957"/>
            <packet-number-at-maximum-pcr-interval value="7639694" time="00:12:46.006"/>
            <pcr-jitter-magnitude value="30"/>
        </pcr-statistics>
        <packet-statistics>
            <number-of-packets value="15844773"/>
            <first-seen-in-packet-number value="3" time="00:00:00.000"/>
            <last-seen-in-packet-number value="16559069" time="00:27:40.322"/>
            <average-transport-packet-rate value="14334901"/>
            <average-payload-rate value="13999998"/>
            <average-packet-spacing value="0.1048"/>
            <minimum-packet-spacing value="0.1003"/>
            <packet-number-at-minimum-spacing value="4" time="00:00:00.000"/>
            <maximum-packet-spacing value="23.9637"/>
            <packet-number-at-maximum-spacing value="11225391" time="00:18:45.532"/>
            <minimum-packet-spacing-ends-inclusive value="0.1003"/>
            <maximum-packet-spacing-ends-inclusive value="2091.3621"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="15844773"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="1"/>
            <number-of-packets-random_access_indicator-is-1 value="3318"/>
            <splice_countdown-fields-seen value="No"/>
            <noemptyaf value="Yes"/>
        </adaptation-field-information>
        <pes-information>
            <number-of-pes-headers-seen value="49763"/>
            <pes-stream_id value="0xE0 (224, MPEG Video)"/>
            <number-of-packets-pes_scrambling_control-is-00 value="49763"/>
            <pes-copyright-set value="No"/>
            <number-of-packets-pes_priority-is-1 value="0"/>
            <number-of-ptss-seen value="49763"/>
            <average-pts-interval value="33.3646"/>
            <average-es-buffer-level value="604817"/>
            <minimum-es-buffer-level value="127807"/>
            <packet-number-at-minimum-es-buffer-level value="654323" time="00:01:05.606"/>
            <maximum-es-buffer-level value="718848"/>
            <packet-number-at-maximum-es-buffer-level value="4407" time="00:00:00.441"/>
            <pesalign value="Yes"/>
        </pes-information>
        <video-elementary-stream-information>
            <mpeg-2-video-profile value="Main"/>
            <mpeg-2-video-level value="High"/>
            <mpeg-chroma-format value="4:2:0"/>
            <closed-caption-types-detected value="ATSC (EIA608, EIA708)"/>
            <a-53-cc-channels-detected value="CC1, XDS"/>
            <a-53-dtvcc-services-detected value="1, 2"/>
            <number-of-video-access-units-seen value="49763"/>
            <minimum-number-of-non-i-pictures-between-two-i-pictures value="12"/>
            <maximum-number-of-non-i-pictures-between-two-i-pictures value="14"/>
            <average-number-of-non-i-pictures-between-two-i-pictures value="14"/>
            <percent-of-all-pictures-that-are-p-pictures value="26"/>
            <percent-of-all-pictures-that-are-b-pictures value="66"/>
            <maximum-number-of-b-frames-between-i-or-p-frames value="2"/>
            <video-bit-rate value="14000000"/>
            <vbv-method value="Off"/>
            <vbv-size value="5750784"/>
            <video-multiplexing-buffer-size value="53333"/>
            <video-es-buffer-size value="718848"/>
            <minimum-decode-delay value="0.1050"/>
            <maximum-decode-delay value="0.5229"/>
            <mb-to-eb-transfer-method value="Leak"/>
            <video-frame-rate value="29.9700"/>
            <video-in-film-mode value="No"/>
            <progressive-sequence value="No"/>
            <horizontal-size value="1920"/>
            <vertical-size value="1080"/>
            <aspect-ratio value="16:9"/>
            <sample-aspect-ratio value="1.0000"/>
            <quadbyte value="Yes"/>
            <initial-timecode value="0:00:00:00"/>
            <final-timecode value="0:27:38:21"/>
        </video-elementary-stream-information>

        <xds-information>
            <prograting value="TV-MA L"/>
            <cgms value="Source:Digital SCMS:CopyNever APS:None"/>
        </xds-information>
        <errors>
            <error pid="0x01E1" packet="3182" time="00:00:00.318" code="6-13w"
                   msg="Video GOP length was 12; the configured minimum is 15."/>
            <error pid="0x01E1" packet="8295" time="00:00:00.831" code="6-13w"
                   msg="Video GOP length was 14; the configured minimum is 15."/>
            <error pid="0x01E1" packet="15445" time="00:00:01.548" code="6-13w"
                   msg="Video GOP length was 14; the configured minimum is 15."/>
            <error pid="0x01E1" packet="20010" time="00:00:02.006" code="6-13w"
                   msg="Video GOP length was 14; the configured minimum is 15."/>
            <error pid="0x01E1" packet="25055" time="00:00:02.512" code="6-13w" msg="Video GOP length is less than the configured minimum.
This error was detected 3312 more times in this PID after packet 25055"/>
            <error pid="0x01E1" packet="182128" time="00:00:18.261" code="4-87w"
                   msg="The CEA708 DTVCC packet sequence number was 2, expected 4."/>
            <error pid="0x01E1" packet="658451" time="00:01:06.020" code="4-87w"
                   msg="The CEA708 DTVCC packet sequence number was 0, expected 2."/>
            <error pid="0x01E1" packet="16192511" time="00:27:03.569" code="4-87w"
                   msg="The CEA708 DTVCC packet sequence number was 0, expected 3."/>
        </errors>
    </pid-properties>

    <pid-properties>
        <pid value="0x01E2 (482)"/>
        <content value="Dolby AC-3 Audio"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <program-information>
            <member-of-program_number value="2"/>
            <listed-in-pmt-version_number value="0"/>
            <stream_type value="0x81 (129, AC3 Audio)"/>
            <associated-pcr-pid value="0x01E1 (481)"/>
            <registration_descriptor>
                <tag value="0x05"/>
                <length value="4"/>
                <format_identifier value="0x41432D33 (AC-3)"/>
                <additional_identification_info value="none"/>
            </registration_descriptor>
            <iso_639_language_descriptor>
                <tag value="0x0A (10)"/>
                <length value="4"/>
                <iso_639_language_code value="0x656E67 (eng)"/>
                <audio_type value="0 (Undefined)"/>
            </iso_639_language_descriptor>
            <ac3_audio_descriptor>
                <tag value="0x81 (129)"/>
                <length value="4"/>
                <sample_rate_code value="0 (48 kHz)"/>
                <bsid value="8"/>
                <bit_rate_limit value="0"/>
                <bit_rate_code value="14 (384 kbps)"/>
                <surround_mode value="0 (Not indicated)"/>
                <bsmod value="0 (Main: Complete Main)"/>
                <num_channels value="2 (2/0)"/>
                <full_svc value="1"/>
                <langcod value="0"/>
            </ac3_audio_descriptor>
        </program-information>
        <packet-statistics>
            <number-of-packets value="466974"/>
            <first-seen-in-packet-number value="12" time="00:00:00.001"/>
            <last-seen-in-packet-number value="16562164" time="00:27:40.632"/>
            <average-transport-packet-rate value="422475"/>
            <average-payload-rate value="383524"/>
            <average-packet-spacing value="3.5562"/>
            <minimum-packet-spacing value="1.1029"/>
            <packet-number-at-minimum-spacing value="23" time="00:00:00.002"/>
            <maximum-packet-spacing value="326.2677"/>
            <packet-number-at-maximum-spacing value="3409" time="00:00:00.341"/>
            <minimum-packet-spacing-ends-inclusive value="1.1029"/>
            <maximum-packet-spacing-ends-inclusive value="1781.0368"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="466974"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="0"/>
            <number-of-packets-random_access_indicator-is-1 value="0"/>
            <splice_countdown-fields-seen value="No"/>
        </adaptation-field-information>
        <pes-information>
            <number-of-pes-headers-seen value="51886"/>
            <pes-stream_id value="0xBD (189, Private stream 1)"/>
            <number-of-packets-pes_scrambling_control-is-00 value="51886"/>
            <pes-copyright-set value="No"/>
            <number-of-packets-pes_priority-is-1 value="0"/>
            <number-of-ptss-seen value="51886"/>
            <average-pts-interval value="32.0054"/>
            <average-es-buffer-level value="1695"/>
            <minimum-es-buffer-level value="920"/>
            <packet-number-at-minimum-es-buffer-level value="3409" time="00:00:00.341"/>
            <maximum-es-buffer-level value="2470"/>
            <packet-number-at-maximum-es-buffer-level value="3409" time="00:00:00.341"/>
        </pes-information>
        <audio-elementary-stream-information>
            <audio-type value="Dolby AC-3"/>
            <number-of-audio-access-units-seen value="51886"/>
            <average-number-of-audio-frames-per-pes-packet value="1"/>
            <sample-rate value="48000"/>
            <bit-rate value="384000"/>
            <number-and-location-of-channels value="Stereo channels: L, R: 2/0"/>
            <ac3-audio-service-type value="Main audio service: Complete Main (CM)"/>
            <low-frequency-effects-flag value="0"/>
            <nominal-frame-size value="1536"/>
            <dialogue-normalization value="-16"/>
            <main-audio-buffer-size value="2592"/>
        </audio-elementary-stream-information>
    </pid-properties>

    <pid-properties>
        <pid value="0x1FFF (8191)"/>
        <content value="Null"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <packet-statistics>
            <number-of-packets value="240977"/>
            <first-seen-in-packet-number value="4265" time="00:00:00.427"/>
            <last-seen-in-packet-number value="16579926" time="00:27:42.413"/>
            <average-transport-packet-rate value="218014"/>
            <average-payload-rate value="218014"/>
            <average-packet-spacing value="6.8969"/>
            <minimum-packet-spacing value="0.1003"/>
            <packet-number-at-minimum-spacing value="4266" time="00:00:00.427"/>
            <maximum-packet-spacing value="8138.4448"/>
            <packet-number-at-maximum-spacing value="14548160" time="00:24:18.695"/>
            <minimum-packet-spacing-ends-inclusive value="0.1003"/>
            <maximum-packet-spacing-ends-inclusive value="8138.4448"/>
        </packet-statistics>
    </pid-properties>

<errors>
  <error pid="0x01E1" packet="661" time="00:00:00.264" code="6-13w" msg="Video GOP length was 12; the configured minimum is 15."/>
  <error pid="0x01E1" packet="1973" time="00:00:00.790" code="6-13w" msg="Video GOP length was 14; the configured minimum is 15."/>
  <error pid="0x01E1" packet="3224" time="00:00:01.292" code="6-13w" msg="Video GOP length was 14; the configured minimum is 15."/>
  <error pid="0x01E1" packet="4472" time="00:00:01.793" code="6-13w" msg="Video GOP length was 14; the configured minimum is 15."/>
  <error pid="0x01E1" packet="5718" time="00:00:02.292" code="6-13w" msg="Video GOP length is less than the configured minimum.
This error was detected 129 more times in this PID after packet 5718"/>
  <error pid="0x01E1" code="6-53e" msg="Missing CEA 708 CC1 data; configuration requires."/>
</errors>

</tsa-report>
"""
        content.manzanitaCGMS = "Source:Digital SCMS:CopyNever APS:None"
        content.manzanitaVChip = "TV-MA, L"
        content.manzanitaRuntime = 1750

        happyAsset.contents.add(content)

        happyAsset.save(flush: true)

        assetService.dumpAssetMetadataFile(happyAsset)
    }*/

    /**
     * One original preview content that only contains the 6-53e code.
     */
    void testOriginalPreviewContentHasOnlyOneCCErrorCode() {

        //Original preview
        Content content = new Content(centralId: 4, original: true, name: "batman.mpg",
                checksum: "123456789", audioType: "Stereo", dataServiceXml: "</xml>",
                size: 100, runtime: 100, contentType: "PREVIEW", asset: happyAsset,
                profile: "Pro 7", contentState: ContentState.COMPLETE.toString())

        content.manzanitaResult = ManzanitaResultStates.FAILED.toString()
        content.manzanitaReport = """
<tsa-report
        version="1.0.0"
        license-serno="CC0825"
        license-username="Clearleap, Inc."
        date="Fri Jun 17 16:48:18 2011"
        file="/usr/local/clearleap/cache/dev-central2/accounts/2572/assets/1285573/Entourage_S4_08_comp_HBOD000000112574_P7D-1356313.mpg">
    <configuration>
        <analysis-mode value="Auto (MPEG)"/>
        tsa-config 2
        Abort-Limit: none
        About: {CableLabs specification for content containing High Definition
        video and one audio stream.
        Document OC-SP-CEP3.0-I01-100827 (August 27, 2010)
        }
        Use-ATSC-Buffer-Model: on
        PMT-In-Single-Packet: warn
        Warn-On-Missing-AC3-Descriptor: warn
        Max-PCR-Interval: 40.0 warn
        Disc-In-First-PCR-Only: err
        I-Pic-Has-RAI: err
        Max-B-Frame: 2
        Video-All-Pics-Frame: err
        Video-No-Scalable-Ext: err
        I-Pic-Has-DTS: err
        Video-Starts-With-Seq-Start: err
        GOP-First-Is-Closed: err
        GOP-Size-15-or-12: warn
        Video-One-AU-Per-PES: err
        Video-PES-Payload-Has-PCR: err
        Video-AU-Starts-PES: err
        Audio-Starts-With-AU: err
        Audio-Has-No-Partial-AU: err
        Packet-Size: 188
        PAT-Never-Changes: err
        PMT-Never-Changes: err
        PSI-Definition-Precedes-PIDs: err
        No-Partial-Transport-Packets: err
        Max-Rate-PID0-PMT-Vid-Aud: 38000000
        SCTE20-Precedes-ATSC53-CC: err
        Minimum-GOP-Length-Error-Level: warn
        Table [
        PID: 0
        Table-ID: 0
        Maximum-Table-Interval: 250
        ]
        Table [
        Table-ID: 2
        Maximum-Table-Interval: 250
        ]
        Program [
        Video [
        Require-CEA708-DTVCC1: on
        Require-CEA708-CC1: on
        Stream-Type: 2
        Stream-Type-Alternate: 128
        Properties: [
        profile = 4
        level = 4
        aspect_ratio = 3
        chroma_format = 1
        (vertical_size = 1080 horizontal_size = 1920
        (progressive_sequence = 0
        frame_rate = 4) |
        (progressive_sequence = 1
        (frame_rate = 1) | (frame_rate = 2))) |
        (vertical_size = 720 horizontal_size = 1280
        frame_rate = 7
        progressive_sequence = 1)
        ]
        Minimum-GOP-Length: 15
        Maximum-GOP-Length: 15
        PID: 481
        ]
        Audio [
        Stream-Type: 129
        Properties: [
        type = 6
        (channel_mode = 2 bit_rate = 384000) |
        (channel_mode = 7 bit_rate = 384000)
        sample_rate = 48000
        ]
        PID: 482
        ]
        PMT-PID: 480
        PCR-PID: 481
        ]
        end-config
    </configuration>

    <stream-information>
        <transport-rate value="15000000.0"/>
        <duration value="00:27:42.413"/>
        <file-size value="3117026088"/>
        <transport-packet-interval value="188"/>
        <packets-analyzed value="16579926"/>
        <total-errors value="0"/>
        <total-warnings value="3320"/>
    </stream-information>

    <multiplex-summary>
        <pat version="0" pid="0">
            <program program_number="2" pid="0x01E0">
                <pmt version="0" pcr-pid="0x01E1">
                    <stream pid="0x01E2" type="Dolby AC-3 Audio">
                        <descriptor tag="0x05" name="registration_descriptor"/>
                        <descriptor tag="0x0A" name="ISO_639_language_descriptor"/>
                        <descriptor tag="0x81" name="ac3_audio_descriptor"/>
                    </stream>
                    <stream pid="0x01E1" type="MPEG-2 Video">
                    </stream>
                </pmt>
            </program>
        </pat>
    </multiplex-summary>

    <pid-summary>
        <pid-info pid="0x0000" content="PAT"
                  npkt="13601" pkt-rate="12305" payld-rate="12043"/>
        <pid-info pid="0x01E0" content="PMT" programs="2"
                  npkt="13601" pkt-rate="12305" payld-rate="12043"/>
        <pid-info pid="0x01E1" content="MPEG-2 Video, PCR" programs="2"
                  npkt="15844773" pkt-rate="14334901" payld-rate="13999998"
                  errors="0" warnings="3320"/>
        <pid-info pid="0x01E2" content="Dolby AC-3 Audio" programs="2"
                  npkt="466974" pkt-rate="422475" payld-rate="383524"/>
        <pid-info pid="0x1FFF" content="Null"
                  npkt="240977" pkt-rate="218014" payld-rate="218014"/>
    </pid-summary>

    <pid-properties>
        <pid value="0x0000 (0)"/>
        <content value="PAT"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <program-association-table-information>
            <table_id value="0x00"/>
            <version_number value="0"/>
            <transport_stream_id value="0x0001 (1)"/>
        </program-association-table-information>
        <packet-statistics>
            <number-of-packets value="13601"/>
            <first-seen-in-packet-number value="1" time="00:00:00.000"/>
            <last-seen-in-packet-number value="16578712" time="00:27:42.292"/>
            <average-transport-packet-rate value="12305"/>
            <average-payload-rate value="12043"/>
            <average-packet-spacing value="122.2274"/>
            <minimum-packet-spacing value="122.2251"/>
            <packet-number-at-minimum-spacing value="1220" time="00:00:00.122"/>
            <maximum-packet-spacing value="122.3253"/>
            <packet-number-at-maximum-spacing value="7316" time="00:00:00.733"/>
            <minimum-packet-spacing-ends-inclusive value="121.8240"/>
            <maximum-packet-spacing-ends-inclusive value="122.3253"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="13601"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="0"/>
            <number-of-packets-random_access_indicator-is-1 value="0"/>
            <splice_countdown-fields-seen value="No"/>
        </adaptation-field-information>
    </pid-properties>

    <pid-properties>
        <pid value="0x01E0 (480)"/>
        <content value="PMT"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <program-information>
            <pmt-pid-for-program_number value="2"/>
            <listed-in-pat-version_number value="0"/>
        </program-information>
        <program-map-table-information>
            <table_id value="0x02"/>
            <program_number value="2"/>
            <version_number value="0"/>
            <pcr-pid value="0x01E1 (481)"/>
            <section_length value="41"/>
        </program-map-table-information>
        <packet-statistics>
            <number-of-packets value="13601"/>
            <first-seen-in-packet-number value="2" time="00:00:00.000"/>
            <last-seen-in-packet-number value="16578713" time="00:27:42.292"/>
            <average-transport-packet-rate value="12305"/>
            <average-payload-rate value="12043"/>
            <average-packet-spacing value="122.2274"/>
            <minimum-packet-spacing value="122.2251"/>
            <packet-number-at-minimum-spacing value="1221" time="00:00:00.122"/>
            <maximum-packet-spacing value="122.3253"/>
            <packet-number-at-maximum-spacing value="7317" time="00:00:00.733"/>
            <minimum-packet-spacing-ends-inclusive value="0.1003"/>
            <maximum-packet-spacing-ends-inclusive value="122.3253"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="13601"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="0"/>
            <number-of-packets-random_access_indicator-is-1 value="0"/>
            <splice_countdown-fields-seen value="No"/>
        </adaptation-field-information>
    </pid-properties>

    <pid-properties>
        <pid value="0x01E1 (481)"/>
        <content value="MPEG-2 Video, PCR"/>
        <total-errors value="0"/>
        <total-warnings value="3320"/>
        <program-information>
            <member-of-program_number value="2"/>
            <listed-in-pmt-version_number value="0"/>
            <stream_type value="0x02 (H.262 | 13818-2 Video | 11172-2 Constrained Video)"/>
            <listed-as-pcr-pid value="Yes"/>
        </program-information>
        <pcr-statistics>
            <number-of-pcrs-seen value="118328"/>
            <average-pcr-interval value="14.0315"/>
            <minimum-pcr-interval value="0.1003"/>
            <packet-number-at-minimum-pcr-interval value="81142" time="00:00:08.135"/>
            <maximum-pcr-interval value="35.9957"/>
            <packet-number-at-maximum-pcr-interval value="7639694" time="00:12:46.006"/>
            <pcr-jitter-magnitude value="30"/>
        </pcr-statistics>
        <packet-statistics>
            <number-of-packets value="15844773"/>
            <first-seen-in-packet-number value="3" time="00:00:00.000"/>
            <last-seen-in-packet-number value="16559069" time="00:27:40.322"/>
            <average-transport-packet-rate value="14334901"/>
            <average-payload-rate value="13999998"/>
            <average-packet-spacing value="0.1048"/>
            <minimum-packet-spacing value="0.1003"/>
            <packet-number-at-minimum-spacing value="4" time="00:00:00.000"/>
            <maximum-packet-spacing value="23.9637"/>
            <packet-number-at-maximum-spacing value="11225391" time="00:18:45.532"/>
            <minimum-packet-spacing-ends-inclusive value="0.1003"/>
            <maximum-packet-spacing-ends-inclusive value="2091.3621"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="15844773"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="1"/>
            <number-of-packets-random_access_indicator-is-1 value="3318"/>
            <splice_countdown-fields-seen value="No"/>
            <noemptyaf value="Yes"/>
        </adaptation-field-information>
        <pes-information>
            <number-of-pes-headers-seen value="49763"/>
            <pes-stream_id value="0xE0 (224, MPEG Video)"/>
            <number-of-packets-pes_scrambling_control-is-00 value="49763"/>
            <pes-copyright-set value="No"/>
            <number-of-packets-pes_priority-is-1 value="0"/>
            <number-of-ptss-seen value="49763"/>
            <average-pts-interval value="33.3646"/>
            <average-es-buffer-level value="604817"/>
            <minimum-es-buffer-level value="127807"/>
            <packet-number-at-minimum-es-buffer-level value="654323" time="00:01:05.606"/>
            <maximum-es-buffer-level value="718848"/>
            <packet-number-at-maximum-es-buffer-level value="4407" time="00:00:00.441"/>
            <pesalign value="Yes"/>
        </pes-information>
        <video-elementary-stream-information>
            <mpeg-2-video-profile value="Main"/>
            <mpeg-2-video-level value="High"/>
            <mpeg-chroma-format value="4:2:0"/>
            <closed-caption-types-detected value="ATSC (EIA608, EIA708)"/>
            <a-53-cc-channels-detected value="CC1, XDS"/>
            <a-53-dtvcc-services-detected value="1, 2"/>
            <number-of-video-access-units-seen value="49763"/>
            <minimum-number-of-non-i-pictures-between-two-i-pictures value="12"/>
            <maximum-number-of-non-i-pictures-between-two-i-pictures value="14"/>
            <average-number-of-non-i-pictures-between-two-i-pictures value="14"/>
            <percent-of-all-pictures-that-are-p-pictures value="26"/>
            <percent-of-all-pictures-that-are-b-pictures value="66"/>
            <maximum-number-of-b-frames-between-i-or-p-frames value="2"/>
            <video-bit-rate value="14000000"/>
            <vbv-method value="Off"/>
            <vbv-size value="5750784"/>
            <video-multiplexing-buffer-size value="53333"/>
            <video-es-buffer-size value="718848"/>
            <minimum-decode-delay value="0.1050"/>
            <maximum-decode-delay value="0.5229"/>
            <mb-to-eb-transfer-method value="Leak"/>
            <video-frame-rate value="29.9700"/>
            <video-in-film-mode value="No"/>
            <progressive-sequence value="No"/>
            <horizontal-size value="1920"/>
            <vertical-size value="1080"/>
            <aspect-ratio value="16:9"/>
            <sample-aspect-ratio value="1.0000"/>
            <quadbyte value="Yes"/>
            <initial-timecode value="0:00:00:00"/>
            <final-timecode value="0:27:38:21"/>
        </video-elementary-stream-information>

        <xds-information>
            <prograting value="TV-MA L"/>
            <cgms value="Source:Digital SCMS:CopyNever APS:None"/>
        </xds-information>
        <errors>
            <error pid="0x01E1" packet="3182" time="00:00:00.318" code="6-13w"
                   msg="Video GOP length was 12; the configured minimum is 15."/>
            <error pid="0x01E1" packet="8295" time="00:00:00.831" code="6-13w"
                   msg="Video GOP length was 14; the configured minimum is 15."/>
            <error pid="0x01E1" packet="15445" time="00:00:01.548" code="6-13w"
                   msg="Video GOP length was 14; the configured minimum is 15."/>
            <error pid="0x01E1" packet="20010" time="00:00:02.006" code="6-13w"
                   msg="Video GOP length was 14; the configured minimum is 15."/>
            <error pid="0x01E1" packet="25055" time="00:00:02.512" code="6-13w" msg="Video GOP length is less than the configured minimum.
This error was detected 3312 more times in this PID after packet 25055"/>
            <error pid="0x01E1" packet="182128" time="00:00:18.261" code="4-87w"
                   msg="The CEA708 DTVCC packet sequence number was 2, expected 4."/>
            <error pid="0x01E1" packet="658451" time="00:01:06.020" code="4-87w"
                   msg="The CEA708 DTVCC packet sequence number was 0, expected 2."/>
            <error pid="0x01E1" packet="16192511" time="00:27:03.569" code="4-87w"
                   msg="The CEA708 DTVCC packet sequence number was 0, expected 3."/>
        </errors>
    </pid-properties>

    <pid-properties>
        <pid value="0x01E2 (482)"/>
        <content value="Dolby AC-3 Audio"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <program-information>
            <member-of-program_number value="2"/>
            <listed-in-pmt-version_number value="0"/>
            <stream_type value="0x81 (129, AC3 Audio)"/>
            <associated-pcr-pid value="0x01E1 (481)"/>
            <registration_descriptor>
                <tag value="0x05"/>
                <length value="4"/>
                <format_identifier value="0x41432D33 (AC-3)"/>
                <additional_identification_info value="none"/>
            </registration_descriptor>
            <iso_639_language_descriptor>
                <tag value="0x0A (10)"/>
                <length value="4"/>
                <iso_639_language_code value="0x656E67 (eng)"/>
                <audio_type value="0 (Undefined)"/>
            </iso_639_language_descriptor>
            <ac3_audio_descriptor>
                <tag value="0x81 (129)"/>
                <length value="4"/>
                <sample_rate_code value="0 (48 kHz)"/>
                <bsid value="8"/>
                <bit_rate_limit value="0"/>
                <bit_rate_code value="14 (384 kbps)"/>
                <surround_mode value="0 (Not indicated)"/>
                <bsmod value="0 (Main: Complete Main)"/>
                <num_channels value="2 (2/0)"/>
                <full_svc value="1"/>
                <langcod value="0"/>
            </ac3_audio_descriptor>
        </program-information>
        <packet-statistics>
            <number-of-packets value="466974"/>
            <first-seen-in-packet-number value="12" time="00:00:00.001"/>
            <last-seen-in-packet-number value="16562164" time="00:27:40.632"/>
            <average-transport-packet-rate value="422475"/>
            <average-payload-rate value="383524"/>
            <average-packet-spacing value="3.5562"/>
            <minimum-packet-spacing value="1.1029"/>
            <packet-number-at-minimum-spacing value="23" time="00:00:00.002"/>
            <maximum-packet-spacing value="326.2677"/>
            <packet-number-at-maximum-spacing value="3409" time="00:00:00.341"/>
            <minimum-packet-spacing-ends-inclusive value="1.1029"/>
            <maximum-packet-spacing-ends-inclusive value="1781.0368"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="466974"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="0"/>
            <number-of-packets-random_access_indicator-is-1 value="0"/>
            <splice_countdown-fields-seen value="No"/>
        </adaptation-field-information>
        <pes-information>
            <number-of-pes-headers-seen value="51886"/>
            <pes-stream_id value="0xBD (189, Private stream 1)"/>
            <number-of-packets-pes_scrambling_control-is-00 value="51886"/>
            <pes-copyright-set value="No"/>
            <number-of-packets-pes_priority-is-1 value="0"/>
            <number-of-ptss-seen value="51886"/>
            <average-pts-interval value="32.0054"/>
            <average-es-buffer-level value="1695"/>
            <minimum-es-buffer-level value="920"/>
            <packet-number-at-minimum-es-buffer-level value="3409" time="00:00:00.341"/>
            <maximum-es-buffer-level value="2470"/>
            <packet-number-at-maximum-es-buffer-level value="3409" time="00:00:00.341"/>
        </pes-information>
        <audio-elementary-stream-information>
            <audio-type value="Dolby AC-3"/>
            <number-of-audio-access-units-seen value="51886"/>
            <average-number-of-audio-frames-per-pes-packet value="1"/>
            <sample-rate value="48000"/>
            <bit-rate value="384000"/>
            <number-and-location-of-channels value="Stereo channels: L, R: 2/0"/>
            <ac3-audio-service-type value="Main audio service: Complete Main (CM)"/>
            <low-frequency-effects-flag value="0"/>
            <nominal-frame-size value="1536"/>
            <dialogue-normalization value="-16"/>
            <main-audio-buffer-size value="2592"/>
        </audio-elementary-stream-information>
    </pid-properties>

    <pid-properties>
        <pid value="0x1FFF (8191)"/>
        <content value="Null"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <packet-statistics>
            <number-of-packets value="240977"/>
            <first-seen-in-packet-number value="4265" time="00:00:00.427"/>
            <last-seen-in-packet-number value="16579926" time="00:27:42.413"/>
            <average-transport-packet-rate value="218014"/>
            <average-payload-rate value="218014"/>
            <average-packet-spacing value="6.8969"/>
            <minimum-packet-spacing value="0.1003"/>
            <packet-number-at-minimum-spacing value="4266" time="00:00:00.427"/>
            <maximum-packet-spacing value="8138.4448"/>
            <packet-number-at-maximum-spacing value="14548160" time="00:24:18.695"/>
            <minimum-packet-spacing-ends-inclusive value="0.1003"/>
            <maximum-packet-spacing-ends-inclusive value="8138.4448"/>
        </packet-statistics>
    </pid-properties>

<errors>
  <error pid="0x01E1" packet="661" time="00:00:00.264" code="6-13w" msg="Video GOP length was 12; the configured minimum is 15."/>
  <error pid="0x01E1" packet="1973" time="00:00:00.790" code="6-13w" msg="Video GOP length was 14; the configured minimum is 15."/>
  <error pid="0x01E1" packet="3224" time="00:00:01.292" code="6-13w" msg="Video GOP length was 14; the configured minimum is 15."/>
  <error pid="0x01E1" packet="4472" time="00:00:01.793" code="6-13w" msg="Video GOP length was 14; the configured minimum is 15."/>
  <error pid="0x01E1" packet="5718" time="00:00:02.292" code="6-13w" msg="Video GOP length is less than the configured minimum.
This error was detected 129 more times in this PID after packet 5718"/>
  <error pid="0x01E1" code="6-53e" msg="Missing CEA 708 CC1 data; configuration requires."/>
</errors>

</tsa-report>
"""
        content.manzanitaCGMS = "Source:Digital SCMS:CopyNever APS:None"
        content.manzanitaVChip = "TV-MA, L"
        content.manzanitaRuntime = 1750

        happyAsset.contents.add(content)
        happyAsset.save(flush: true)


        assertEquals(ManzanitaResultStates.FAILED.toString(), content.manzanitaResult)

        assetService.updatePreviewContentsManzanitaResult(happyAsset)

        assertEquals(ManzanitaResultStates.PASSED.toString(), content.manzanitaResult)
    }

    /**
     * One original preview content with both error codes
     */
    void testOriginalPreviewContentHasTwoCCErrorCodes() {

        //Original preview
        Content content = new Content(centralId: 4, original: true, name: "batman.mpg",
                checksum: "123456789", audioType: "Stereo", dataServiceXml: "</xml>",
                size: 100, runtime: 100, contentType: "PREVIEW", asset: happyAsset,
                profile: "Pro 7", contentState: ContentState.COMPLETE.toString())

        content.manzanitaResult = ManzanitaResultStates.FAILED.toString()
        content.manzanitaReport = """
<tsa-report
        version="1.0.0"
        license-serno="CC0825"
        license-username="Clearleap, Inc."
        date="Fri Jun 17 16:48:18 2011"
        file="/usr/local/clearleap/cache/dev-central2/accounts/2572/assets/1285573/Entourage_S4_08_comp_HBOD000000112574_P7D-1356313.mpg">
    <configuration>
        <analysis-mode value="Auto (MPEG)"/>
        tsa-config 2
        Abort-Limit: none
        About: {CableLabs specification for content containing High Definition
        video and one audio stream.
        Document OC-SP-CEP3.0-I01-100827 (August 27, 2010)
        }
        Use-ATSC-Buffer-Model: on
        PMT-In-Single-Packet: warn
        Warn-On-Missing-AC3-Descriptor: warn
        Max-PCR-Interval: 40.0 warn
        Disc-In-First-PCR-Only: err
        I-Pic-Has-RAI: err
        Max-B-Frame: 2
        Video-All-Pics-Frame: err
        Video-No-Scalable-Ext: err
        I-Pic-Has-DTS: err
        Video-Starts-With-Seq-Start: err
        GOP-First-Is-Closed: err
        GOP-Size-15-or-12: warn
        Video-One-AU-Per-PES: err
        Video-PES-Payload-Has-PCR: err
        Video-AU-Starts-PES: err
        Audio-Starts-With-AU: err
        Audio-Has-No-Partial-AU: err
        Packet-Size: 188
        PAT-Never-Changes: err
        PMT-Never-Changes: err
        PSI-Definition-Precedes-PIDs: err
        No-Partial-Transport-Packets: err
        Max-Rate-PID0-PMT-Vid-Aud: 38000000
        SCTE20-Precedes-ATSC53-CC: err
        Minimum-GOP-Length-Error-Level: warn
        Table [
        PID: 0
        Table-ID: 0
        Maximum-Table-Interval: 250
        ]
        Table [
        Table-ID: 2
        Maximum-Table-Interval: 250
        ]
        Program [
        Video [
        Require-CEA708-DTVCC1: on
        Require-CEA708-CC1: on
        Stream-Type: 2
        Stream-Type-Alternate: 128
        Properties: [
        profile = 4
        level = 4
        aspect_ratio = 3
        chroma_format = 1
        (vertical_size = 1080 horizontal_size = 1920
        (progressive_sequence = 0
        frame_rate = 4) |
        (progressive_sequence = 1
        (frame_rate = 1) | (frame_rate = 2))) |
        (vertical_size = 720 horizontal_size = 1280
        frame_rate = 7
        progressive_sequence = 1)
        ]
        Minimum-GOP-Length: 15
        Maximum-GOP-Length: 15
        PID: 481
        ]
        Audio [
        Stream-Type: 129
        Properties: [
        type = 6
        (channel_mode = 2 bit_rate = 384000) |
        (channel_mode = 7 bit_rate = 384000)
        sample_rate = 48000
        ]
        PID: 482
        ]
        PMT-PID: 480
        PCR-PID: 481
        ]
        end-config
    </configuration>

    <stream-information>
        <transport-rate value="15000000.0"/>
        <duration value="00:27:42.413"/>
        <file-size value="3117026088"/>
        <transport-packet-interval value="188"/>
        <packets-analyzed value="16579926"/>
        <total-errors value="0"/>
        <total-warnings value="3320"/>
    </stream-information>

    <multiplex-summary>
        <pat version="0" pid="0">
            <program program_number="2" pid="0x01E0">
                <pmt version="0" pcr-pid="0x01E1">
                    <stream pid="0x01E2" type="Dolby AC-3 Audio">
                        <descriptor tag="0x05" name="registration_descriptor"/>
                        <descriptor tag="0x0A" name="ISO_639_language_descriptor"/>
                        <descriptor tag="0x81" name="ac3_audio_descriptor"/>
                    </stream>
                    <stream pid="0x01E1" type="MPEG-2 Video">
                    </stream>
                </pmt>
            </program>
        </pat>
    </multiplex-summary>

    <pid-summary>
        <pid-info pid="0x0000" content="PAT"
                  npkt="13601" pkt-rate="12305" payld-rate="12043"/>
        <pid-info pid="0x01E0" content="PMT" programs="2"
                  npkt="13601" pkt-rate="12305" payld-rate="12043"/>
        <pid-info pid="0x01E1" content="MPEG-2 Video, PCR" programs="2"
                  npkt="15844773" pkt-rate="14334901" payld-rate="13999998"
                  errors="0" warnings="3320"/>
        <pid-info pid="0x01E2" content="Dolby AC-3 Audio" programs="2"
                  npkt="466974" pkt-rate="422475" payld-rate="383524"/>
        <pid-info pid="0x1FFF" content="Null"
                  npkt="240977" pkt-rate="218014" payld-rate="218014"/>
    </pid-summary>

    <pid-properties>
        <pid value="0x0000 (0)"/>
        <content value="PAT"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <program-association-table-information>
            <table_id value="0x00"/>
            <version_number value="0"/>
            <transport_stream_id value="0x0001 (1)"/>
        </program-association-table-information>
        <packet-statistics>
            <number-of-packets value="13601"/>
            <first-seen-in-packet-number value="1" time="00:00:00.000"/>
            <last-seen-in-packet-number value="16578712" time="00:27:42.292"/>
            <average-transport-packet-rate value="12305"/>
            <average-payload-rate value="12043"/>
            <average-packet-spacing value="122.2274"/>
            <minimum-packet-spacing value="122.2251"/>
            <packet-number-at-minimum-spacing value="1220" time="00:00:00.122"/>
            <maximum-packet-spacing value="122.3253"/>
            <packet-number-at-maximum-spacing value="7316" time="00:00:00.733"/>
            <minimum-packet-spacing-ends-inclusive value="121.8240"/>
            <maximum-packet-spacing-ends-inclusive value="122.3253"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="13601"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="0"/>
            <number-of-packets-random_access_indicator-is-1 value="0"/>
            <splice_countdown-fields-seen value="No"/>
        </adaptation-field-information>
    </pid-properties>

    <pid-properties>
        <pid value="0x01E0 (480)"/>
        <content value="PMT"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <program-information>
            <pmt-pid-for-program_number value="2"/>
            <listed-in-pat-version_number value="0"/>
        </program-information>
        <program-map-table-information>
            <table_id value="0x02"/>
            <program_number value="2"/>
            <version_number value="0"/>
            <pcr-pid value="0x01E1 (481)"/>
            <section_length value="41"/>
        </program-map-table-information>
        <packet-statistics>
            <number-of-packets value="13601"/>
            <first-seen-in-packet-number value="2" time="00:00:00.000"/>
            <last-seen-in-packet-number value="16578713" time="00:27:42.292"/>
            <average-transport-packet-rate value="12305"/>
            <average-payload-rate value="12043"/>
            <average-packet-spacing value="122.2274"/>
            <minimum-packet-spacing value="122.2251"/>
            <packet-number-at-minimum-spacing value="1221" time="00:00:00.122"/>
            <maximum-packet-spacing value="122.3253"/>
            <packet-number-at-maximum-spacing value="7317" time="00:00:00.733"/>
            <minimum-packet-spacing-ends-inclusive value="0.1003"/>
            <maximum-packet-spacing-ends-inclusive value="122.3253"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="13601"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="0"/>
            <number-of-packets-random_access_indicator-is-1 value="0"/>
            <splice_countdown-fields-seen value="No"/>
        </adaptation-field-information>
    </pid-properties>

    <pid-properties>
        <pid value="0x01E1 (481)"/>
        <content value="MPEG-2 Video, PCR"/>
        <total-errors value="0"/>
        <total-warnings value="3320"/>
        <program-information>
            <member-of-program_number value="2"/>
            <listed-in-pmt-version_number value="0"/>
            <stream_type value="0x02 (H.262 | 13818-2 Video | 11172-2 Constrained Video)"/>
            <listed-as-pcr-pid value="Yes"/>
        </program-information>
        <pcr-statistics>
            <number-of-pcrs-seen value="118328"/>
            <average-pcr-interval value="14.0315"/>
            <minimum-pcr-interval value="0.1003"/>
            <packet-number-at-minimum-pcr-interval value="81142" time="00:00:08.135"/>
            <maximum-pcr-interval value="35.9957"/>
            <packet-number-at-maximum-pcr-interval value="7639694" time="00:12:46.006"/>
            <pcr-jitter-magnitude value="30"/>
        </pcr-statistics>
        <packet-statistics>
            <number-of-packets value="15844773"/>
            <first-seen-in-packet-number value="3" time="00:00:00.000"/>
            <last-seen-in-packet-number value="16559069" time="00:27:40.322"/>
            <average-transport-packet-rate value="14334901"/>
            <average-payload-rate value="13999998"/>
            <average-packet-spacing value="0.1048"/>
            <minimum-packet-spacing value="0.1003"/>
            <packet-number-at-minimum-spacing value="4" time="00:00:00.000"/>
            <maximum-packet-spacing value="23.9637"/>
            <packet-number-at-maximum-spacing value="11225391" time="00:18:45.532"/>
            <minimum-packet-spacing-ends-inclusive value="0.1003"/>
            <maximum-packet-spacing-ends-inclusive value="2091.3621"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="15844773"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="1"/>
            <number-of-packets-random_access_indicator-is-1 value="3318"/>
            <splice_countdown-fields-seen value="No"/>
            <noemptyaf value="Yes"/>
        </adaptation-field-information>
        <pes-information>
            <number-of-pes-headers-seen value="49763"/>
            <pes-stream_id value="0xE0 (224, MPEG Video)"/>
            <number-of-packets-pes_scrambling_control-is-00 value="49763"/>
            <pes-copyright-set value="No"/>
            <number-of-packets-pes_priority-is-1 value="0"/>
            <number-of-ptss-seen value="49763"/>
            <average-pts-interval value="33.3646"/>
            <average-es-buffer-level value="604817"/>
            <minimum-es-buffer-level value="127807"/>
            <packet-number-at-minimum-es-buffer-level value="654323" time="00:01:05.606"/>
            <maximum-es-buffer-level value="718848"/>
            <packet-number-at-maximum-es-buffer-level value="4407" time="00:00:00.441"/>
            <pesalign value="Yes"/>
        </pes-information>
        <video-elementary-stream-information>
            <mpeg-2-video-profile value="Main"/>
            <mpeg-2-video-level value="High"/>
            <mpeg-chroma-format value="4:2:0"/>
            <closed-caption-types-detected value="ATSC (EIA608, EIA708)"/>
            <a-53-cc-channels-detected value="CC1, XDS"/>
            <a-53-dtvcc-services-detected value="1, 2"/>
            <number-of-video-access-units-seen value="49763"/>
            <minimum-number-of-non-i-pictures-between-two-i-pictures value="12"/>
            <maximum-number-of-non-i-pictures-between-two-i-pictures value="14"/>
            <average-number-of-non-i-pictures-between-two-i-pictures value="14"/>
            <percent-of-all-pictures-that-are-p-pictures value="26"/>
            <percent-of-all-pictures-that-are-b-pictures value="66"/>
            <maximum-number-of-b-frames-between-i-or-p-frames value="2"/>
            <video-bit-rate value="14000000"/>
            <vbv-method value="Off"/>
            <vbv-size value="5750784"/>
            <video-multiplexing-buffer-size value="53333"/>
            <video-es-buffer-size value="718848"/>
            <minimum-decode-delay value="0.1050"/>
            <maximum-decode-delay value="0.5229"/>
            <mb-to-eb-transfer-method value="Leak"/>
            <video-frame-rate value="29.9700"/>
            <video-in-film-mode value="No"/>
            <progressive-sequence value="No"/>
            <horizontal-size value="1920"/>
            <vertical-size value="1080"/>
            <aspect-ratio value="16:9"/>
            <sample-aspect-ratio value="1.0000"/>
            <quadbyte value="Yes"/>
            <initial-timecode value="0:00:00:00"/>
            <final-timecode value="0:27:38:21"/>
        </video-elementary-stream-information>

        <xds-information>
            <prograting value="TV-MA L"/>
            <cgms value="Source:Digital SCMS:CopyNever APS:None"/>
        </xds-information>
        <errors>
            <error pid="0x01E1" packet="3182" time="00:00:00.318" code="6-13w"
                   msg="Video GOP length was 12; the configured minimum is 15."/>
            <error pid="0x01E1" packet="8295" time="00:00:00.831" code="6-13w"
                   msg="Video GOP length was 14; the configured minimum is 15."/>
            <error pid="0x01E1" packet="15445" time="00:00:01.548" code="6-13w"
                   msg="Video GOP length was 14; the configured minimum is 15."/>
            <error pid="0x01E1" packet="20010" time="00:00:02.006" code="6-13w"
                   msg="Video GOP length was 14; the configured minimum is 15."/>
            <error pid="0x01E1" packet="25055" time="00:00:02.512" code="6-13w" msg="Video GOP length is less than the configured minimum.
This error was detected 3312 more times in this PID after packet 25055"/>
            <error pid="0x01E1" packet="182128" time="00:00:18.261" code="4-87w"
                   msg="The CEA708 DTVCC packet sequence number was 2, expected 4."/>
            <error pid="0x01E1" packet="658451" time="00:01:06.020" code="4-87w"
                   msg="The CEA708 DTVCC packet sequence number was 0, expected 2."/>
            <error pid="0x01E1" packet="16192511" time="00:27:03.569" code="4-87w"
                   msg="The CEA708 DTVCC packet sequence number was 0, expected 3."/>
        </errors>
    </pid-properties>

    <pid-properties>
        <pid value="0x01E2 (482)"/>
        <content value="Dolby AC-3 Audio"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <program-information>
            <member-of-program_number value="2"/>
            <listed-in-pmt-version_number value="0"/>
            <stream_type value="0x81 (129, AC3 Audio)"/>
            <associated-pcr-pid value="0x01E1 (481)"/>
            <registration_descriptor>
                <tag value="0x05"/>
                <length value="4"/>
                <format_identifier value="0x41432D33 (AC-3)"/>
                <additional_identification_info value="none"/>
            </registration_descriptor>
            <iso_639_language_descriptor>
                <tag value="0x0A (10)"/>
                <length value="4"/>
                <iso_639_language_code value="0x656E67 (eng)"/>
                <audio_type value="0 (Undefined)"/>
            </iso_639_language_descriptor>
            <ac3_audio_descriptor>
                <tag value="0x81 (129)"/>
                <length value="4"/>
                <sample_rate_code value="0 (48 kHz)"/>
                <bsid value="8"/>
                <bit_rate_limit value="0"/>
                <bit_rate_code value="14 (384 kbps)"/>
                <surround_mode value="0 (Not indicated)"/>
                <bsmod value="0 (Main: Complete Main)"/>
                <num_channels value="2 (2/0)"/>
                <full_svc value="1"/>
                <langcod value="0"/>
            </ac3_audio_descriptor>
        </program-information>
        <packet-statistics>
            <number-of-packets value="466974"/>
            <first-seen-in-packet-number value="12" time="00:00:00.001"/>
            <last-seen-in-packet-number value="16562164" time="00:27:40.632"/>
            <average-transport-packet-rate value="422475"/>
            <average-payload-rate value="383524"/>
            <average-packet-spacing value="3.5562"/>
            <minimum-packet-spacing value="1.1029"/>
            <packet-number-at-minimum-spacing value="23" time="00:00:00.002"/>
            <maximum-packet-spacing value="326.2677"/>
            <packet-number-at-maximum-spacing value="3409" time="00:00:00.341"/>
            <minimum-packet-spacing-ends-inclusive value="1.1029"/>
            <maximum-packet-spacing-ends-inclusive value="1781.0368"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="466974"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="0"/>
            <number-of-packets-random_access_indicator-is-1 value="0"/>
            <splice_countdown-fields-seen value="No"/>
        </adaptation-field-information>
        <pes-information>
            <number-of-pes-headers-seen value="51886"/>
            <pes-stream_id value="0xBD (189, Private stream 1)"/>
            <number-of-packets-pes_scrambling_control-is-00 value="51886"/>
            <pes-copyright-set value="No"/>
            <number-of-packets-pes_priority-is-1 value="0"/>
            <number-of-ptss-seen value="51886"/>
            <average-pts-interval value="32.0054"/>
            <average-es-buffer-level value="1695"/>
            <minimum-es-buffer-level value="920"/>
            <packet-number-at-minimum-es-buffer-level value="3409" time="00:00:00.341"/>
            <maximum-es-buffer-level value="2470"/>
            <packet-number-at-maximum-es-buffer-level value="3409" time="00:00:00.341"/>
        </pes-information>
        <audio-elementary-stream-information>
            <audio-type value="Dolby AC-3"/>
            <number-of-audio-access-units-seen value="51886"/>
            <average-number-of-audio-frames-per-pes-packet value="1"/>
            <sample-rate value="48000"/>
            <bit-rate value="384000"/>
            <number-and-location-of-channels value="Stereo channels: L, R: 2/0"/>
            <ac3-audio-service-type value="Main audio service: Complete Main (CM)"/>
            <low-frequency-effects-flag value="0"/>
            <nominal-frame-size value="1536"/>
            <dialogue-normalization value="-16"/>
            <main-audio-buffer-size value="2592"/>
        </audio-elementary-stream-information>
    </pid-properties>

    <pid-properties>
        <pid value="0x1FFF (8191)"/>
        <content value="Null"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <packet-statistics>
            <number-of-packets value="240977"/>
            <first-seen-in-packet-number value="4265" time="00:00:00.427"/>
            <last-seen-in-packet-number value="16579926" time="00:27:42.413"/>
            <average-transport-packet-rate value="218014"/>
            <average-payload-rate value="218014"/>
            <average-packet-spacing value="6.8969"/>
            <minimum-packet-spacing value="0.1003"/>
            <packet-number-at-minimum-spacing value="4266" time="00:00:00.427"/>
            <maximum-packet-spacing value="8138.4448"/>
            <packet-number-at-maximum-spacing value="14548160" time="00:24:18.695"/>
            <minimum-packet-spacing-ends-inclusive value="0.1003"/>
            <maximum-packet-spacing-ends-inclusive value="8138.4448"/>
        </packet-statistics>
    </pid-properties>

<errors>
  <error pid="0x01E1" packet="661" time="00:00:00.264" code="6-13w" msg="Video GOP length was 12; the configured minimum is 15."/>
  <error pid="0x01E1" packet="1973" time="00:00:00.790" code="6-13w" msg="Video GOP length was 14; the configured minimum is 15."/>
  <error pid="0x01E1" packet="3224" time="00:00:01.292" code="6-13w" msg="Video GOP length was 14; the configured minimum is 15."/>
  <error pid="0x01E1" packet="4472" time="00:00:01.793" code="6-13w" msg="Video GOP length was 14; the configured minimum is 15."/>
  <error pid="0x01E1" packet="5718" time="00:00:02.292" code="6-13w" msg="Video GOP length is less than the configured minimum.
This error was detected 129 more times in this PID after packet 5718"/>
  <error pid="0x01E1" code="6-53e" msg="Missing CEA 708 CC1 data; configuration requires."/>
  <error pid="0x01E1" code="6-54e" msg="Missing CEA 708 CC1 data; configuration requires."/>
</errors>

</tsa-report>
"""
        content.manzanitaCGMS = "Source:Digital SCMS:CopyNever APS:None"
        content.manzanitaVChip = "TV-MA, L"
        content.manzanitaRuntime = 1750

        happyAsset.contents.add(content)
        happyAsset.save(flush: true)


        assertEquals(ManzanitaResultStates.FAILED.toString(), content.manzanitaResult)

        assetService.updatePreviewContentsManzanitaResult(happyAsset)

        assertEquals(ManzanitaResultStates.FAILED.toString(), content.manzanitaResult)
    }

    /**
     * 1 original preview content with 6-53e, 1 generated content with both codes.
     */
    void testOriginalPreviewContentHasCCErrorCodeGeneratedHasBothCCErrorCodes() {

        //Original preview
        Content content = new Content(centralId: 4, original: true, name: "batman.mpg",
                checksum: "123456789", audioType: "Stereo", dataServiceXml: "</xml>",
                size: 100, runtime: 100, contentType: "PREVIEW", asset: happyAsset,
                profile: "Pro 7", contentState: ContentState.COMPLETE.toString())

        content.manzanitaResult = ManzanitaResultStates.FAILED.toString()
        content.manzanitaReport = """
<tsa-report
        version="1.0.0"
        license-serno="CC0825"
        license-username="Clearleap, Inc."
        date="Fri Jun 17 16:48:18 2011"
        file="/usr/local/clearleap/cache/dev-central2/accounts/2572/assets/1285573/Entourage_S4_08_comp_HBOD000000112574_P7D-1356313.mpg">
    <configuration>
        <analysis-mode value="Auto (MPEG)"/>
        tsa-config 2
        Abort-Limit: none
        About: {CableLabs specification for content containing High Definition
        video and one audio stream.
        Document OC-SP-CEP3.0-I01-100827 (August 27, 2010)
        }
        Use-ATSC-Buffer-Model: on
        PMT-In-Single-Packet: warn
        Warn-On-Missing-AC3-Descriptor: warn
        Max-PCR-Interval: 40.0 warn
        Disc-In-First-PCR-Only: err
        I-Pic-Has-RAI: err
        Max-B-Frame: 2
        Video-All-Pics-Frame: err
        Video-No-Scalable-Ext: err
        I-Pic-Has-DTS: err
        Video-Starts-With-Seq-Start: err
        GOP-First-Is-Closed: err
        GOP-Size-15-or-12: warn
        Video-One-AU-Per-PES: err
        Video-PES-Payload-Has-PCR: err
        Video-AU-Starts-PES: err
        Audio-Starts-With-AU: err
        Audio-Has-No-Partial-AU: err
        Packet-Size: 188
        PAT-Never-Changes: err
        PMT-Never-Changes: err
        PSI-Definition-Precedes-PIDs: err
        No-Partial-Transport-Packets: err
        Max-Rate-PID0-PMT-Vid-Aud: 38000000
        SCTE20-Precedes-ATSC53-CC: err
        Minimum-GOP-Length-Error-Level: warn
        Table [
        PID: 0
        Table-ID: 0
        Maximum-Table-Interval: 250
        ]
        Table [
        Table-ID: 2
        Maximum-Table-Interval: 250
        ]
        Program [
        Video [
        Require-CEA708-DTVCC1: on
        Require-CEA708-CC1: on
        Stream-Type: 2
        Stream-Type-Alternate: 128
        Properties: [
        profile = 4
        level = 4
        aspect_ratio = 3
        chroma_format = 1
        (vertical_size = 1080 horizontal_size = 1920
        (progressive_sequence = 0
        frame_rate = 4) |
        (progressive_sequence = 1
        (frame_rate = 1) | (frame_rate = 2))) |
        (vertical_size = 720 horizontal_size = 1280
        frame_rate = 7
        progressive_sequence = 1)
        ]
        Minimum-GOP-Length: 15
        Maximum-GOP-Length: 15
        PID: 481
        ]
        Audio [
        Stream-Type: 129
        Properties: [
        type = 6
        (channel_mode = 2 bit_rate = 384000) |
        (channel_mode = 7 bit_rate = 384000)
        sample_rate = 48000
        ]
        PID: 482
        ]
        PMT-PID: 480
        PCR-PID: 481
        ]
        end-config
    </configuration>

    <stream-information>
        <transport-rate value="15000000.0"/>
        <duration value="00:27:42.413"/>
        <file-size value="3117026088"/>
        <transport-packet-interval value="188"/>
        <packets-analyzed value="16579926"/>
        <total-errors value="0"/>
        <total-warnings value="3320"/>
    </stream-information>

    <multiplex-summary>
        <pat version="0" pid="0">
            <program program_number="2" pid="0x01E0">
                <pmt version="0" pcr-pid="0x01E1">
                    <stream pid="0x01E2" type="Dolby AC-3 Audio">
                        <descriptor tag="0x05" name="registration_descriptor"/>
                        <descriptor tag="0x0A" name="ISO_639_language_descriptor"/>
                        <descriptor tag="0x81" name="ac3_audio_descriptor"/>
                    </stream>
                    <stream pid="0x01E1" type="MPEG-2 Video">
                    </stream>
                </pmt>
            </program>
        </pat>
    </multiplex-summary>

    <pid-summary>
        <pid-info pid="0x0000" content="PAT"
                  npkt="13601" pkt-rate="12305" payld-rate="12043"/>
        <pid-info pid="0x01E0" content="PMT" programs="2"
                  npkt="13601" pkt-rate="12305" payld-rate="12043"/>
        <pid-info pid="0x01E1" content="MPEG-2 Video, PCR" programs="2"
                  npkt="15844773" pkt-rate="14334901" payld-rate="13999998"
                  errors="0" warnings="3320"/>
        <pid-info pid="0x01E2" content="Dolby AC-3 Audio" programs="2"
                  npkt="466974" pkt-rate="422475" payld-rate="383524"/>
        <pid-info pid="0x1FFF" content="Null"
                  npkt="240977" pkt-rate="218014" payld-rate="218014"/>
    </pid-summary>

    <pid-properties>
        <pid value="0x0000 (0)"/>
        <content value="PAT"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <program-association-table-information>
            <table_id value="0x00"/>
            <version_number value="0"/>
            <transport_stream_id value="0x0001 (1)"/>
        </program-association-table-information>
        <packet-statistics>
            <number-of-packets value="13601"/>
            <first-seen-in-packet-number value="1" time="00:00:00.000"/>
            <last-seen-in-packet-number value="16578712" time="00:27:42.292"/>
            <average-transport-packet-rate value="12305"/>
            <average-payload-rate value="12043"/>
            <average-packet-spacing value="122.2274"/>
            <minimum-packet-spacing value="122.2251"/>
            <packet-number-at-minimum-spacing value="1220" time="00:00:00.122"/>
            <maximum-packet-spacing value="122.3253"/>
            <packet-number-at-maximum-spacing value="7316" time="00:00:00.733"/>
            <minimum-packet-spacing-ends-inclusive value="121.8240"/>
            <maximum-packet-spacing-ends-inclusive value="122.3253"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="13601"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="0"/>
            <number-of-packets-random_access_indicator-is-1 value="0"/>
            <splice_countdown-fields-seen value="No"/>
        </adaptation-field-information>
    </pid-properties>

    <pid-properties>
        <pid value="0x01E0 (480)"/>
        <content value="PMT"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <program-information>
            <pmt-pid-for-program_number value="2"/>
            <listed-in-pat-version_number value="0"/>
        </program-information>
        <program-map-table-information>
            <table_id value="0x02"/>
            <program_number value="2"/>
            <version_number value="0"/>
            <pcr-pid value="0x01E1 (481)"/>
            <section_length value="41"/>
        </program-map-table-information>
        <packet-statistics>
            <number-of-packets value="13601"/>
            <first-seen-in-packet-number value="2" time="00:00:00.000"/>
            <last-seen-in-packet-number value="16578713" time="00:27:42.292"/>
            <average-transport-packet-rate value="12305"/>
            <average-payload-rate value="12043"/>
            <average-packet-spacing value="122.2274"/>
            <minimum-packet-spacing value="122.2251"/>
            <packet-number-at-minimum-spacing value="1221" time="00:00:00.122"/>
            <maximum-packet-spacing value="122.3253"/>
            <packet-number-at-maximum-spacing value="7317" time="00:00:00.733"/>
            <minimum-packet-spacing-ends-inclusive value="0.1003"/>
            <maximum-packet-spacing-ends-inclusive value="122.3253"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="13601"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="0"/>
            <number-of-packets-random_access_indicator-is-1 value="0"/>
            <splice_countdown-fields-seen value="No"/>
        </adaptation-field-information>
    </pid-properties>

    <pid-properties>
        <pid value="0x01E1 (481)"/>
        <content value="MPEG-2 Video, PCR"/>
        <total-errors value="0"/>
        <total-warnings value="3320"/>
        <program-information>
            <member-of-program_number value="2"/>
            <listed-in-pmt-version_number value="0"/>
            <stream_type value="0x02 (H.262 | 13818-2 Video | 11172-2 Constrained Video)"/>
            <listed-as-pcr-pid value="Yes"/>
        </program-information>
        <pcr-statistics>
            <number-of-pcrs-seen value="118328"/>
            <average-pcr-interval value="14.0315"/>
            <minimum-pcr-interval value="0.1003"/>
            <packet-number-at-minimum-pcr-interval value="81142" time="00:00:08.135"/>
            <maximum-pcr-interval value="35.9957"/>
            <packet-number-at-maximum-pcr-interval value="7639694" time="00:12:46.006"/>
            <pcr-jitter-magnitude value="30"/>
        </pcr-statistics>
        <packet-statistics>
            <number-of-packets value="15844773"/>
            <first-seen-in-packet-number value="3" time="00:00:00.000"/>
            <last-seen-in-packet-number value="16559069" time="00:27:40.322"/>
            <average-transport-packet-rate value="14334901"/>
            <average-payload-rate value="13999998"/>
            <average-packet-spacing value="0.1048"/>
            <minimum-packet-spacing value="0.1003"/>
            <packet-number-at-minimum-spacing value="4" time="00:00:00.000"/>
            <maximum-packet-spacing value="23.9637"/>
            <packet-number-at-maximum-spacing value="11225391" time="00:18:45.532"/>
            <minimum-packet-spacing-ends-inclusive value="0.1003"/>
            <maximum-packet-spacing-ends-inclusive value="2091.3621"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="15844773"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="1"/>
            <number-of-packets-random_access_indicator-is-1 value="3318"/>
            <splice_countdown-fields-seen value="No"/>
            <noemptyaf value="Yes"/>
        </adaptation-field-information>
        <pes-information>
            <number-of-pes-headers-seen value="49763"/>
            <pes-stream_id value="0xE0 (224, MPEG Video)"/>
            <number-of-packets-pes_scrambling_control-is-00 value="49763"/>
            <pes-copyright-set value="No"/>
            <number-of-packets-pes_priority-is-1 value="0"/>
            <number-of-ptss-seen value="49763"/>
            <average-pts-interval value="33.3646"/>
            <average-es-buffer-level value="604817"/>
            <minimum-es-buffer-level value="127807"/>
            <packet-number-at-minimum-es-buffer-level value="654323" time="00:01:05.606"/>
            <maximum-es-buffer-level value="718848"/>
            <packet-number-at-maximum-es-buffer-level value="4407" time="00:00:00.441"/>
            <pesalign value="Yes"/>
        </pes-information>
        <video-elementary-stream-information>
            <mpeg-2-video-profile value="Main"/>
            <mpeg-2-video-level value="High"/>
            <mpeg-chroma-format value="4:2:0"/>
            <closed-caption-types-detected value="ATSC (EIA608, EIA708)"/>
            <a-53-cc-channels-detected value="CC1, XDS"/>
            <a-53-dtvcc-services-detected value="1, 2"/>
            <number-of-video-access-units-seen value="49763"/>
            <minimum-number-of-non-i-pictures-between-two-i-pictures value="12"/>
            <maximum-number-of-non-i-pictures-between-two-i-pictures value="14"/>
            <average-number-of-non-i-pictures-between-two-i-pictures value="14"/>
            <percent-of-all-pictures-that-are-p-pictures value="26"/>
            <percent-of-all-pictures-that-are-b-pictures value="66"/>
            <maximum-number-of-b-frames-between-i-or-p-frames value="2"/>
            <video-bit-rate value="14000000"/>
            <vbv-method value="Off"/>
            <vbv-size value="5750784"/>
            <video-multiplexing-buffer-size value="53333"/>
            <video-es-buffer-size value="718848"/>
            <minimum-decode-delay value="0.1050"/>
            <maximum-decode-delay value="0.5229"/>
            <mb-to-eb-transfer-method value="Leak"/>
            <video-frame-rate value="29.9700"/>
            <video-in-film-mode value="No"/>
            <progressive-sequence value="No"/>
            <horizontal-size value="1920"/>
            <vertical-size value="1080"/>
            <aspect-ratio value="16:9"/>
            <sample-aspect-ratio value="1.0000"/>
            <quadbyte value="Yes"/>
            <initial-timecode value="0:00:00:00"/>
            <final-timecode value="0:27:38:21"/>
        </video-elementary-stream-information>

        <xds-information>
            <prograting value="TV-MA L"/>
            <cgms value="Source:Digital SCMS:CopyNever APS:None"/>
        </xds-information>
        <errors>
            <error pid="0x01E1" packet="3182" time="00:00:00.318" code="6-13w"
                   msg="Video GOP length was 12; the configured minimum is 15."/>
            <error pid="0x01E1" packet="8295" time="00:00:00.831" code="6-13w"
                   msg="Video GOP length was 14; the configured minimum is 15."/>
            <error pid="0x01E1" packet="15445" time="00:00:01.548" code="6-13w"
                   msg="Video GOP length was 14; the configured minimum is 15."/>
            <error pid="0x01E1" packet="20010" time="00:00:02.006" code="6-13w"
                   msg="Video GOP length was 14; the configured minimum is 15."/>
            <error pid="0x01E1" packet="25055" time="00:00:02.512" code="6-13w" msg="Video GOP length is less than the configured minimum.
This error was detected 3312 more times in this PID after packet 25055"/>
            <error pid="0x01E1" packet="182128" time="00:00:18.261" code="4-87w"
                   msg="The CEA708 DTVCC packet sequence number was 2, expected 4."/>
            <error pid="0x01E1" packet="658451" time="00:01:06.020" code="4-87w"
                   msg="The CEA708 DTVCC packet sequence number was 0, expected 2."/>
            <error pid="0x01E1" packet="16192511" time="00:27:03.569" code="4-87w"
                   msg="The CEA708 DTVCC packet sequence number was 0, expected 3."/>
        </errors>
    </pid-properties>

    <pid-properties>
        <pid value="0x01E2 (482)"/>
        <content value="Dolby AC-3 Audio"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <program-information>
            <member-of-program_number value="2"/>
            <listed-in-pmt-version_number value="0"/>
            <stream_type value="0x81 (129, AC3 Audio)"/>
            <associated-pcr-pid value="0x01E1 (481)"/>
            <registration_descriptor>
                <tag value="0x05"/>
                <length value="4"/>
                <format_identifier value="0x41432D33 (AC-3)"/>
                <additional_identification_info value="none"/>
            </registration_descriptor>
            <iso_639_language_descriptor>
                <tag value="0x0A (10)"/>
                <length value="4"/>
                <iso_639_language_code value="0x656E67 (eng)"/>
                <audio_type value="0 (Undefined)"/>
            </iso_639_language_descriptor>
            <ac3_audio_descriptor>
                <tag value="0x81 (129)"/>
                <length value="4"/>
                <sample_rate_code value="0 (48 kHz)"/>
                <bsid value="8"/>
                <bit_rate_limit value="0"/>
                <bit_rate_code value="14 (384 kbps)"/>
                <surround_mode value="0 (Not indicated)"/>
                <bsmod value="0 (Main: Complete Main)"/>
                <num_channels value="2 (2/0)"/>
                <full_svc value="1"/>
                <langcod value="0"/>
            </ac3_audio_descriptor>
        </program-information>
        <packet-statistics>
            <number-of-packets value="466974"/>
            <first-seen-in-packet-number value="12" time="00:00:00.001"/>
            <last-seen-in-packet-number value="16562164" time="00:27:40.632"/>
            <average-transport-packet-rate value="422475"/>
            <average-payload-rate value="383524"/>
            <average-packet-spacing value="3.5562"/>
            <minimum-packet-spacing value="1.1029"/>
            <packet-number-at-minimum-spacing value="23" time="00:00:00.002"/>
            <maximum-packet-spacing value="326.2677"/>
            <packet-number-at-maximum-spacing value="3409" time="00:00:00.341"/>
            <minimum-packet-spacing-ends-inclusive value="1.1029"/>
            <maximum-packet-spacing-ends-inclusive value="1781.0368"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="466974"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="0"/>
            <number-of-packets-random_access_indicator-is-1 value="0"/>
            <splice_countdown-fields-seen value="No"/>
        </adaptation-field-information>
        <pes-information>
            <number-of-pes-headers-seen value="51886"/>
            <pes-stream_id value="0xBD (189, Private stream 1)"/>
            <number-of-packets-pes_scrambling_control-is-00 value="51886"/>
            <pes-copyright-set value="No"/>
            <number-of-packets-pes_priority-is-1 value="0"/>
            <number-of-ptss-seen value="51886"/>
            <average-pts-interval value="32.0054"/>
            <average-es-buffer-level value="1695"/>
            <minimum-es-buffer-level value="920"/>
            <packet-number-at-minimum-es-buffer-level value="3409" time="00:00:00.341"/>
            <maximum-es-buffer-level value="2470"/>
            <packet-number-at-maximum-es-buffer-level value="3409" time="00:00:00.341"/>
        </pes-information>
        <audio-elementary-stream-information>
            <audio-type value="Dolby AC-3"/>
            <number-of-audio-access-units-seen value="51886"/>
            <average-number-of-audio-frames-per-pes-packet value="1"/>
            <sample-rate value="48000"/>
            <bit-rate value="384000"/>
            <number-and-location-of-channels value="Stereo channels: L, R: 2/0"/>
            <ac3-audio-service-type value="Main audio service: Complete Main (CM)"/>
            <low-frequency-effects-flag value="0"/>
            <nominal-frame-size value="1536"/>
            <dialogue-normalization value="-16"/>
            <main-audio-buffer-size value="2592"/>
        </audio-elementary-stream-information>
    </pid-properties>

    <pid-properties>
        <pid value="0x1FFF (8191)"/>
        <content value="Null"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <packet-statistics>
            <number-of-packets value="240977"/>
            <first-seen-in-packet-number value="4265" time="00:00:00.427"/>
            <last-seen-in-packet-number value="16579926" time="00:27:42.413"/>
            <average-transport-packet-rate value="218014"/>
            <average-payload-rate value="218014"/>
            <average-packet-spacing value="6.8969"/>
            <minimum-packet-spacing value="0.1003"/>
            <packet-number-at-minimum-spacing value="4266" time="00:00:00.427"/>
            <maximum-packet-spacing value="8138.4448"/>
            <packet-number-at-maximum-spacing value="14548160" time="00:24:18.695"/>
            <minimum-packet-spacing-ends-inclusive value="0.1003"/>
            <maximum-packet-spacing-ends-inclusive value="8138.4448"/>
        </packet-statistics>
    </pid-properties>

<errors>
  <error pid="0x01E1" packet="661" time="00:00:00.264" code="6-13w" msg="Video GOP length was 12; the configured minimum is 15."/>
  <error pid="0x01E1" packet="1973" time="00:00:00.790" code="6-13w" msg="Video GOP length was 14; the configured minimum is 15."/>
  <error pid="0x01E1" packet="3224" time="00:00:01.292" code="6-13w" msg="Video GOP length was 14; the configured minimum is 15."/>
  <error pid="0x01E1" packet="4472" time="00:00:01.793" code="6-13w" msg="Video GOP length was 14; the configured minimum is 15."/>
  <error pid="0x01E1" packet="5718" time="00:00:02.292" code="6-13w" msg="Video GOP length is less than the configured minimum.
This error was detected 129 more times in this PID after packet 5718"/>
  <error pid="0x01E1" code="6-53e" msg="Missing CEA 708 CC1 data; configuration requires."/>
</errors>

</tsa-report>
"""
        content.manzanitaCGMS = "Source:Digital SCMS:CopyNever APS:None"
        content.manzanitaVChip = "TV-MA, L"
        content.manzanitaRuntime = 1750

        Content generatedContent = new Content(centralId: 5, original: false, name: "batman-1.mpg",
                checksum: "123456789", audioType: "Stereo", dataServiceXml: "</xml>",
                size: 100, runtime: 100, contentType: "PREVIEW", asset: happyAsset,
                profile: "Pro 7", contentState: ContentState.COMPLETE.toString())

        generatedContent.manzanitaResult = ManzanitaResultStates.FAILED.toString()
        generatedContent.manzanitaReport = """
<tsa-report
        version="1.0.0"
        license-serno="CC0825"
        license-username="Clearleap, Inc."
        date="Fri Jun 17 16:48:18 2011"
        file="/usr/local/clearleap/cache/dev-central2/accounts/2572/assets/1285573/Entourage_S4_08_comp_HBOD000000112574_P7D-1356313.mpg">
    <configuration>
        <analysis-mode value="Auto (MPEG)"/>
        tsa-config 2
        Abort-Limit: none
        About: {CableLabs specification for content containing High Definition
        video and one audio stream.
        Document OC-SP-CEP3.0-I01-100827 (August 27, 2010)
        }
        Use-ATSC-Buffer-Model: on
        PMT-In-Single-Packet: warn
        Warn-On-Missing-AC3-Descriptor: warn
        Max-PCR-Interval: 40.0 warn
        Disc-In-First-PCR-Only: err
        I-Pic-Has-RAI: err
        Max-B-Frame: 2
        Video-All-Pics-Frame: err
        Video-No-Scalable-Ext: err
        I-Pic-Has-DTS: err
        Video-Starts-With-Seq-Start: err
        GOP-First-Is-Closed: err
        GOP-Size-15-or-12: warn
        Video-One-AU-Per-PES: err
        Video-PES-Payload-Has-PCR: err
        Video-AU-Starts-PES: err
        Audio-Starts-With-AU: err
        Audio-Has-No-Partial-AU: err
        Packet-Size: 188
        PAT-Never-Changes: err
        PMT-Never-Changes: err
        PSI-Definition-Precedes-PIDs: err
        No-Partial-Transport-Packets: err
        Max-Rate-PID0-PMT-Vid-Aud: 38000000
        SCTE20-Precedes-ATSC53-CC: err
        Minimum-GOP-Length-Error-Level: warn
        Table [
        PID: 0
        Table-ID: 0
        Maximum-Table-Interval: 250
        ]
        Table [
        Table-ID: 2
        Maximum-Table-Interval: 250
        ]
        Program [
        Video [
        Require-CEA708-DTVCC1: on
        Require-CEA708-CC1: on
        Stream-Type: 2
        Stream-Type-Alternate: 128
        Properties: [
        profile = 4
        level = 4
        aspect_ratio = 3
        chroma_format = 1
        (vertical_size = 1080 horizontal_size = 1920
        (progressive_sequence = 0
        frame_rate = 4) |
        (progressive_sequence = 1
        (frame_rate = 1) | (frame_rate = 2))) |
        (vertical_size = 720 horizontal_size = 1280
        frame_rate = 7
        progressive_sequence = 1)
        ]
        Minimum-GOP-Length: 15
        Maximum-GOP-Length: 15
        PID: 481
        ]
        Audio [
        Stream-Type: 129
        Properties: [
        type = 6
        (channel_mode = 2 bit_rate = 384000) |
        (channel_mode = 7 bit_rate = 384000)
        sample_rate = 48000
        ]
        PID: 482
        ]
        PMT-PID: 480
        PCR-PID: 481
        ]
        end-config
    </configuration>

    <stream-information>
        <transport-rate value="15000000.0"/>
        <duration value="00:27:42.413"/>
        <file-size value="3117026088"/>
        <transport-packet-interval value="188"/>
        <packets-analyzed value="16579926"/>
        <total-errors value="0"/>
        <total-warnings value="3320"/>
    </stream-information>

    <multiplex-summary>
        <pat version="0" pid="0">
            <program program_number="2" pid="0x01E0">
                <pmt version="0" pcr-pid="0x01E1">
                    <stream pid="0x01E2" type="Dolby AC-3 Audio">
                        <descriptor tag="0x05" name="registration_descriptor"/>
                        <descriptor tag="0x0A" name="ISO_639_language_descriptor"/>
                        <descriptor tag="0x81" name="ac3_audio_descriptor"/>
                    </stream>
                    <stream pid="0x01E1" type="MPEG-2 Video">
                    </stream>
                </pmt>
            </program>
        </pat>
    </multiplex-summary>

    <pid-summary>
        <pid-info pid="0x0000" content="PAT"
                  npkt="13601" pkt-rate="12305" payld-rate="12043"/>
        <pid-info pid="0x01E0" content="PMT" programs="2"
                  npkt="13601" pkt-rate="12305" payld-rate="12043"/>
        <pid-info pid="0x01E1" content="MPEG-2 Video, PCR" programs="2"
                  npkt="15844773" pkt-rate="14334901" payld-rate="13999998"
                  errors="0" warnings="3320"/>
        <pid-info pid="0x01E2" content="Dolby AC-3 Audio" programs="2"
                  npkt="466974" pkt-rate="422475" payld-rate="383524"/>
        <pid-info pid="0x1FFF" content="Null"
                  npkt="240977" pkt-rate="218014" payld-rate="218014"/>
    </pid-summary>

    <pid-properties>
        <pid value="0x0000 (0)"/>
        <content value="PAT"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <program-association-table-information>
            <table_id value="0x00"/>
            <version_number value="0"/>
            <transport_stream_id value="0x0001 (1)"/>
        </program-association-table-information>
        <packet-statistics>
            <number-of-packets value="13601"/>
            <first-seen-in-packet-number value="1" time="00:00:00.000"/>
            <last-seen-in-packet-number value="16578712" time="00:27:42.292"/>
            <average-transport-packet-rate value="12305"/>
            <average-payload-rate value="12043"/>
            <average-packet-spacing value="122.2274"/>
            <minimum-packet-spacing value="122.2251"/>
            <packet-number-at-minimum-spacing value="1220" time="00:00:00.122"/>
            <maximum-packet-spacing value="122.3253"/>
            <packet-number-at-maximum-spacing value="7316" time="00:00:00.733"/>
            <minimum-packet-spacing-ends-inclusive value="121.8240"/>
            <maximum-packet-spacing-ends-inclusive value="122.3253"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="13601"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="0"/>
            <number-of-packets-random_access_indicator-is-1 value="0"/>
            <splice_countdown-fields-seen value="No"/>
        </adaptation-field-information>
    </pid-properties>

    <pid-properties>
        <pid value="0x01E0 (480)"/>
        <content value="PMT"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <program-information>
            <pmt-pid-for-program_number value="2"/>
            <listed-in-pat-version_number value="0"/>
        </program-information>
        <program-map-table-information>
            <table_id value="0x02"/>
            <program_number value="2"/>
            <version_number value="0"/>
            <pcr-pid value="0x01E1 (481)"/>
            <section_length value="41"/>
        </program-map-table-information>
        <packet-statistics>
            <number-of-packets value="13601"/>
            <first-seen-in-packet-number value="2" time="00:00:00.000"/>
            <last-seen-in-packet-number value="16578713" time="00:27:42.292"/>
            <average-transport-packet-rate value="12305"/>
            <average-payload-rate value="12043"/>
            <average-packet-spacing value="122.2274"/>
            <minimum-packet-spacing value="122.2251"/>
            <packet-number-at-minimum-spacing value="1221" time="00:00:00.122"/>
            <maximum-packet-spacing value="122.3253"/>
            <packet-number-at-maximum-spacing value="7317" time="00:00:00.733"/>
            <minimum-packet-spacing-ends-inclusive value="0.1003"/>
            <maximum-packet-spacing-ends-inclusive value="122.3253"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="13601"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="0"/>
            <number-of-packets-random_access_indicator-is-1 value="0"/>
            <splice_countdown-fields-seen value="No"/>
        </adaptation-field-information>
    </pid-properties>

    <pid-properties>
        <pid value="0x01E1 (481)"/>
        <content value="MPEG-2 Video, PCR"/>
        <total-errors value="0"/>
        <total-warnings value="3320"/>
        <program-information>
            <member-of-program_number value="2"/>
            <listed-in-pmt-version_number value="0"/>
            <stream_type value="0x02 (H.262 | 13818-2 Video | 11172-2 Constrained Video)"/>
            <listed-as-pcr-pid value="Yes"/>
        </program-information>
        <pcr-statistics>
            <number-of-pcrs-seen value="118328"/>
            <average-pcr-interval value="14.0315"/>
            <minimum-pcr-interval value="0.1003"/>
            <packet-number-at-minimum-pcr-interval value="81142" time="00:00:08.135"/>
            <maximum-pcr-interval value="35.9957"/>
            <packet-number-at-maximum-pcr-interval value="7639694" time="00:12:46.006"/>
            <pcr-jitter-magnitude value="30"/>
        </pcr-statistics>
        <packet-statistics>
            <number-of-packets value="15844773"/>
            <first-seen-in-packet-number value="3" time="00:00:00.000"/>
            <last-seen-in-packet-number value="16559069" time="00:27:40.322"/>
            <average-transport-packet-rate value="14334901"/>
            <average-payload-rate value="13999998"/>
            <average-packet-spacing value="0.1048"/>
            <minimum-packet-spacing value="0.1003"/>
            <packet-number-at-minimum-spacing value="4" time="00:00:00.000"/>
            <maximum-packet-spacing value="23.9637"/>
            <packet-number-at-maximum-spacing value="11225391" time="00:18:45.532"/>
            <minimum-packet-spacing-ends-inclusive value="0.1003"/>
            <maximum-packet-spacing-ends-inclusive value="2091.3621"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="15844773"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="1"/>
            <number-of-packets-random_access_indicator-is-1 value="3318"/>
            <splice_countdown-fields-seen value="No"/>
            <noemptyaf value="Yes"/>
        </adaptation-field-information>
        <pes-information>
            <number-of-pes-headers-seen value="49763"/>
            <pes-stream_id value="0xE0 (224, MPEG Video)"/>
            <number-of-packets-pes_scrambling_control-is-00 value="49763"/>
            <pes-copyright-set value="No"/>
            <number-of-packets-pes_priority-is-1 value="0"/>
            <number-of-ptss-seen value="49763"/>
            <average-pts-interval value="33.3646"/>
            <average-es-buffer-level value="604817"/>
            <minimum-es-buffer-level value="127807"/>
            <packet-number-at-minimum-es-buffer-level value="654323" time="00:01:05.606"/>
            <maximum-es-buffer-level value="718848"/>
            <packet-number-at-maximum-es-buffer-level value="4407" time="00:00:00.441"/>
            <pesalign value="Yes"/>
        </pes-information>
        <video-elementary-stream-information>
            <mpeg-2-video-profile value="Main"/>
            <mpeg-2-video-level value="High"/>
            <mpeg-chroma-format value="4:2:0"/>
            <closed-caption-types-detected value="ATSC (EIA608, EIA708)"/>
            <a-53-cc-channels-detected value="CC1, XDS"/>
            <a-53-dtvcc-services-detected value="1, 2"/>
            <number-of-video-access-units-seen value="49763"/>
            <minimum-number-of-non-i-pictures-between-two-i-pictures value="12"/>
            <maximum-number-of-non-i-pictures-between-two-i-pictures value="14"/>
            <average-number-of-non-i-pictures-between-two-i-pictures value="14"/>
            <percent-of-all-pictures-that-are-p-pictures value="26"/>
            <percent-of-all-pictures-that-are-b-pictures value="66"/>
            <maximum-number-of-b-frames-between-i-or-p-frames value="2"/>
            <video-bit-rate value="14000000"/>
            <vbv-method value="Off"/>
            <vbv-size value="5750784"/>
            <video-multiplexing-buffer-size value="53333"/>
            <video-es-buffer-size value="718848"/>
            <minimum-decode-delay value="0.1050"/>
            <maximum-decode-delay value="0.5229"/>
            <mb-to-eb-transfer-method value="Leak"/>
            <video-frame-rate value="29.9700"/>
            <video-in-film-mode value="No"/>
            <progressive-sequence value="No"/>
            <horizontal-size value="1920"/>
            <vertical-size value="1080"/>
            <aspect-ratio value="16:9"/>
            <sample-aspect-ratio value="1.0000"/>
            <quadbyte value="Yes"/>
            <initial-timecode value="0:00:00:00"/>
            <final-timecode value="0:27:38:21"/>
        </video-elementary-stream-information>

        <xds-information>
            <prograting value="TV-MA L"/>
            <cgms value="Source:Digital SCMS:CopyNever APS:None"/>
        </xds-information>
        <errors>
            <error pid="0x01E1" packet="3182" time="00:00:00.318" code="6-13w"
                   msg="Video GOP length was 12; the configured minimum is 15."/>
            <error pid="0x01E1" packet="8295" time="00:00:00.831" code="6-13w"
                   msg="Video GOP length was 14; the configured minimum is 15."/>
            <error pid="0x01E1" packet="15445" time="00:00:01.548" code="6-13w"
                   msg="Video GOP length was 14; the configured minimum is 15."/>
            <error pid="0x01E1" packet="20010" time="00:00:02.006" code="6-13w"
                   msg="Video GOP length was 14; the configured minimum is 15."/>
            <error pid="0x01E1" packet="25055" time="00:00:02.512" code="6-13w" msg="Video GOP length is less than the configured minimum.
This error was detected 3312 more times in this PID after packet 25055"/>
            <error pid="0x01E1" packet="182128" time="00:00:18.261" code="4-87w"
                   msg="The CEA708 DTVCC packet sequence number was 2, expected 4."/>
            <error pid="0x01E1" packet="658451" time="00:01:06.020" code="4-87w"
                   msg="The CEA708 DTVCC packet sequence number was 0, expected 2."/>
            <error pid="0x01E1" packet="16192511" time="00:27:03.569" code="4-87w"
                   msg="The CEA708 DTVCC packet sequence number was 0, expected 3."/>
        </errors>
    </pid-properties>

    <pid-properties>
        <pid value="0x01E2 (482)"/>
        <content value="Dolby AC-3 Audio"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <program-information>
            <member-of-program_number value="2"/>
            <listed-in-pmt-version_number value="0"/>
            <stream_type value="0x81 (129, AC3 Audio)"/>
            <associated-pcr-pid value="0x01E1 (481)"/>
            <registration_descriptor>
                <tag value="0x05"/>
                <length value="4"/>
                <format_identifier value="0x41432D33 (AC-3)"/>
                <additional_identification_info value="none"/>
            </registration_descriptor>
            <iso_639_language_descriptor>
                <tag value="0x0A (10)"/>
                <length value="4"/>
                <iso_639_language_code value="0x656E67 (eng)"/>
                <audio_type value="0 (Undefined)"/>
            </iso_639_language_descriptor>
            <ac3_audio_descriptor>
                <tag value="0x81 (129)"/>
                <length value="4"/>
                <sample_rate_code value="0 (48 kHz)"/>
                <bsid value="8"/>
                <bit_rate_limit value="0"/>
                <bit_rate_code value="14 (384 kbps)"/>
                <surround_mode value="0 (Not indicated)"/>
                <bsmod value="0 (Main: Complete Main)"/>
                <num_channels value="2 (2/0)"/>
                <full_svc value="1"/>
                <langcod value="0"/>
            </ac3_audio_descriptor>
        </program-information>
        <packet-statistics>
            <number-of-packets value="466974"/>
            <first-seen-in-packet-number value="12" time="00:00:00.001"/>
            <last-seen-in-packet-number value="16562164" time="00:27:40.632"/>
            <average-transport-packet-rate value="422475"/>
            <average-payload-rate value="383524"/>
            <average-packet-spacing value="3.5562"/>
            <minimum-packet-spacing value="1.1029"/>
            <packet-number-at-minimum-spacing value="23" time="00:00:00.002"/>
            <maximum-packet-spacing value="326.2677"/>
            <packet-number-at-maximum-spacing value="3409" time="00:00:00.341"/>
            <minimum-packet-spacing-ends-inclusive value="1.1029"/>
            <maximum-packet-spacing-ends-inclusive value="1781.0368"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="466974"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="0"/>
            <number-of-packets-random_access_indicator-is-1 value="0"/>
            <splice_countdown-fields-seen value="No"/>
        </adaptation-field-information>
        <pes-information>
            <number-of-pes-headers-seen value="51886"/>
            <pes-stream_id value="0xBD (189, Private stream 1)"/>
            <number-of-packets-pes_scrambling_control-is-00 value="51886"/>
            <pes-copyright-set value="No"/>
            <number-of-packets-pes_priority-is-1 value="0"/>
            <number-of-ptss-seen value="51886"/>
            <average-pts-interval value="32.0054"/>
            <average-es-buffer-level value="1695"/>
            <minimum-es-buffer-level value="920"/>
            <packet-number-at-minimum-es-buffer-level value="3409" time="00:00:00.341"/>
            <maximum-es-buffer-level value="2470"/>
            <packet-number-at-maximum-es-buffer-level value="3409" time="00:00:00.341"/>
        </pes-information>
        <audio-elementary-stream-information>
            <audio-type value="Dolby AC-3"/>
            <number-of-audio-access-units-seen value="51886"/>
            <average-number-of-audio-frames-per-pes-packet value="1"/>
            <sample-rate value="48000"/>
            <bit-rate value="384000"/>
            <number-and-location-of-channels value="Stereo channels: L, R: 2/0"/>
            <ac3-audio-service-type value="Main audio service: Complete Main (CM)"/>
            <low-frequency-effects-flag value="0"/>
            <nominal-frame-size value="1536"/>
            <dialogue-normalization value="-16"/>
            <main-audio-buffer-size value="2592"/>
        </audio-elementary-stream-information>
    </pid-properties>

    <pid-properties>
        <pid value="0x1FFF (8191)"/>
        <content value="Null"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <packet-statistics>
            <number-of-packets value="240977"/>
            <first-seen-in-packet-number value="4265" time="00:00:00.427"/>
            <last-seen-in-packet-number value="16579926" time="00:27:42.413"/>
            <average-transport-packet-rate value="218014"/>
            <average-payload-rate value="218014"/>
            <average-packet-spacing value="6.8969"/>
            <minimum-packet-spacing value="0.1003"/>
            <packet-number-at-minimum-spacing value="4266" time="00:00:00.427"/>
            <maximum-packet-spacing value="8138.4448"/>
            <packet-number-at-maximum-spacing value="14548160" time="00:24:18.695"/>
            <minimum-packet-spacing-ends-inclusive value="0.1003"/>
            <maximum-packet-spacing-ends-inclusive value="8138.4448"/>
        </packet-statistics>
    </pid-properties>

<errors>
  <error pid="0x01E1" packet="661" time="00:00:00.264" code="6-13w" msg="Video GOP length was 12; the configured minimum is 15."/>
  <error pid="0x01E1" packet="1973" time="00:00:00.790" code="6-13w" msg="Video GOP length was 14; the configured minimum is 15."/>
  <error pid="0x01E1" packet="3224" time="00:00:01.292" code="6-13w" msg="Video GOP length was 14; the configured minimum is 15."/>
  <error pid="0x01E1" packet="4472" time="00:00:01.793" code="6-13w" msg="Video GOP length was 14; the configured minimum is 15."/>
  <error pid="0x01E1" packet="5718" time="00:00:02.292" code="6-13w" msg="Video GOP length is less than the configured minimum.
This error was detected 129 more times in this PID after packet 5718"/>
  <error pid="0x01E1" code="6-53e" msg="Missing CEA 708 CC1 data; configuration requires."/>
  <error pid="0x01E1" code="6-54e" msg="Missing CEA 708 CC1 data; configuration requires."/>
</errors>

</tsa-report>
"""
        generatedContent.manzanitaCGMS = "Source:Digital SCMS:CopyNever APS:None"
        generatedContent.manzanitaVChip = "TV-MA, L"
        generatedContent.manzanitaRuntime = 1750

        happyAsset.contents.add(content)
        happyAsset.contents.add(generatedContent)
        happyAsset.save(flush: true)


        assertEquals(ManzanitaResultStates.FAILED.toString(), content.manzanitaResult)
        assertEquals(ManzanitaResultStates.FAILED.toString(), generatedContent.manzanitaResult)

        assetService.updatePreviewContentsManzanitaResult(happyAsset)

        assertEquals(ManzanitaResultStates.PASSED.toString(), content.manzanitaResult)
        assertEquals(ManzanitaResultStates.PASSED.toString(), generatedContent.manzanitaResult)

    }

    /**
     * 1 original preview content with 6-53e, 1 generated content with other errors.
     */
    void testOriginalPreviewContentHasCCErrorCodeGeneratedHasOtherErrorCodes() {

        //Original preview
        Content content = new Content(centralId: 4, original: true, name: "batman.mpg",
                checksum: "123456789", audioType: "Stereo", dataServiceXml: "</xml>",
                size: 100, runtime: 100, contentType: "PREVIEW", asset: happyAsset,
                profile: "Pro 7", contentState: ContentState.COMPLETE.toString())

        content.manzanitaResult = ManzanitaResultStates.FAILED.toString()
        content.manzanitaReport = """
<tsa-report
        version="1.0.0"
        license-serno="CC0825"
        license-username="Clearleap, Inc."
        date="Fri Jun 17 16:48:18 2011"
        file="/usr/local/clearleap/cache/dev-central2/accounts/2572/assets/1285573/Entourage_S4_08_comp_HBOD000000112574_P7D-1356313.mpg">
    <configuration>
        <analysis-mode value="Auto (MPEG)"/>
        tsa-config 2
        Abort-Limit: none
        About: {CableLabs specification for content containing High Definition
        video and one audio stream.
        Document OC-SP-CEP3.0-I01-100827 (August 27, 2010)
        }
        Use-ATSC-Buffer-Model: on
        PMT-In-Single-Packet: warn
        Warn-On-Missing-AC3-Descriptor: warn
        Max-PCR-Interval: 40.0 warn
        Disc-In-First-PCR-Only: err
        I-Pic-Has-RAI: err
        Max-B-Frame: 2
        Video-All-Pics-Frame: err
        Video-No-Scalable-Ext: err
        I-Pic-Has-DTS: err
        Video-Starts-With-Seq-Start: err
        GOP-First-Is-Closed: err
        GOP-Size-15-or-12: warn
        Video-One-AU-Per-PES: err
        Video-PES-Payload-Has-PCR: err
        Video-AU-Starts-PES: err
        Audio-Starts-With-AU: err
        Audio-Has-No-Partial-AU: err
        Packet-Size: 188
        PAT-Never-Changes: err
        PMT-Never-Changes: err
        PSI-Definition-Precedes-PIDs: err
        No-Partial-Transport-Packets: err
        Max-Rate-PID0-PMT-Vid-Aud: 38000000
        SCTE20-Precedes-ATSC53-CC: err
        Minimum-GOP-Length-Error-Level: warn
        Table [
        PID: 0
        Table-ID: 0
        Maximum-Table-Interval: 250
        ]
        Table [
        Table-ID: 2
        Maximum-Table-Interval: 250
        ]
        Program [
        Video [
        Require-CEA708-DTVCC1: on
        Require-CEA708-CC1: on
        Stream-Type: 2
        Stream-Type-Alternate: 128
        Properties: [
        profile = 4
        level = 4
        aspect_ratio = 3
        chroma_format = 1
        (vertical_size = 1080 horizontal_size = 1920
        (progressive_sequence = 0
        frame_rate = 4) |
        (progressive_sequence = 1
        (frame_rate = 1) | (frame_rate = 2))) |
        (vertical_size = 720 horizontal_size = 1280
        frame_rate = 7
        progressive_sequence = 1)
        ]
        Minimum-GOP-Length: 15
        Maximum-GOP-Length: 15
        PID: 481
        ]
        Audio [
        Stream-Type: 129
        Properties: [
        type = 6
        (channel_mode = 2 bit_rate = 384000) |
        (channel_mode = 7 bit_rate = 384000)
        sample_rate = 48000
        ]
        PID: 482
        ]
        PMT-PID: 480
        PCR-PID: 481
        ]
        end-config
    </configuration>

    <stream-information>
        <transport-rate value="15000000.0"/>
        <duration value="00:27:42.413"/>
        <file-size value="3117026088"/>
        <transport-packet-interval value="188"/>
        <packets-analyzed value="16579926"/>
        <total-errors value="0"/>
        <total-warnings value="3320"/>
    </stream-information>

    <multiplex-summary>
        <pat version="0" pid="0">
            <program program_number="2" pid="0x01E0">
                <pmt version="0" pcr-pid="0x01E1">
                    <stream pid="0x01E2" type="Dolby AC-3 Audio">
                        <descriptor tag="0x05" name="registration_descriptor"/>
                        <descriptor tag="0x0A" name="ISO_639_language_descriptor"/>
                        <descriptor tag="0x81" name="ac3_audio_descriptor"/>
                    </stream>
                    <stream pid="0x01E1" type="MPEG-2 Video">
                    </stream>
                </pmt>
            </program>
        </pat>
    </multiplex-summary>

    <pid-summary>
        <pid-info pid="0x0000" content="PAT"
                  npkt="13601" pkt-rate="12305" payld-rate="12043"/>
        <pid-info pid="0x01E0" content="PMT" programs="2"
                  npkt="13601" pkt-rate="12305" payld-rate="12043"/>
        <pid-info pid="0x01E1" content="MPEG-2 Video, PCR" programs="2"
                  npkt="15844773" pkt-rate="14334901" payld-rate="13999998"
                  errors="0" warnings="3320"/>
        <pid-info pid="0x01E2" content="Dolby AC-3 Audio" programs="2"
                  npkt="466974" pkt-rate="422475" payld-rate="383524"/>
        <pid-info pid="0x1FFF" content="Null"
                  npkt="240977" pkt-rate="218014" payld-rate="218014"/>
    </pid-summary>

    <pid-properties>
        <pid value="0x0000 (0)"/>
        <content value="PAT"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <program-association-table-information>
            <table_id value="0x00"/>
            <version_number value="0"/>
            <transport_stream_id value="0x0001 (1)"/>
        </program-association-table-information>
        <packet-statistics>
            <number-of-packets value="13601"/>
            <first-seen-in-packet-number value="1" time="00:00:00.000"/>
            <last-seen-in-packet-number value="16578712" time="00:27:42.292"/>
            <average-transport-packet-rate value="12305"/>
            <average-payload-rate value="12043"/>
            <average-packet-spacing value="122.2274"/>
            <minimum-packet-spacing value="122.2251"/>
            <packet-number-at-minimum-spacing value="1220" time="00:00:00.122"/>
            <maximum-packet-spacing value="122.3253"/>
            <packet-number-at-maximum-spacing value="7316" time="00:00:00.733"/>
            <minimum-packet-spacing-ends-inclusive value="121.8240"/>
            <maximum-packet-spacing-ends-inclusive value="122.3253"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="13601"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="0"/>
            <number-of-packets-random_access_indicator-is-1 value="0"/>
            <splice_countdown-fields-seen value="No"/>
        </adaptation-field-information>
    </pid-properties>

    <pid-properties>
        <pid value="0x01E0 (480)"/>
        <content value="PMT"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <program-information>
            <pmt-pid-for-program_number value="2"/>
            <listed-in-pat-version_number value="0"/>
        </program-information>
        <program-map-table-information>
            <table_id value="0x02"/>
            <program_number value="2"/>
            <version_number value="0"/>
            <pcr-pid value="0x01E1 (481)"/>
            <section_length value="41"/>
        </program-map-table-information>
        <packet-statistics>
            <number-of-packets value="13601"/>
            <first-seen-in-packet-number value="2" time="00:00:00.000"/>
            <last-seen-in-packet-number value="16578713" time="00:27:42.292"/>
            <average-transport-packet-rate value="12305"/>
            <average-payload-rate value="12043"/>
            <average-packet-spacing value="122.2274"/>
            <minimum-packet-spacing value="122.2251"/>
            <packet-number-at-minimum-spacing value="1221" time="00:00:00.122"/>
            <maximum-packet-spacing value="122.3253"/>
            <packet-number-at-maximum-spacing value="7317" time="00:00:00.733"/>
            <minimum-packet-spacing-ends-inclusive value="0.1003"/>
            <maximum-packet-spacing-ends-inclusive value="122.3253"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="13601"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="0"/>
            <number-of-packets-random_access_indicator-is-1 value="0"/>
            <splice_countdown-fields-seen value="No"/>
        </adaptation-field-information>
    </pid-properties>

    <pid-properties>
        <pid value="0x01E1 (481)"/>
        <content value="MPEG-2 Video, PCR"/>
        <total-errors value="0"/>
        <total-warnings value="3320"/>
        <program-information>
            <member-of-program_number value="2"/>
            <listed-in-pmt-version_number value="0"/>
            <stream_type value="0x02 (H.262 | 13818-2 Video | 11172-2 Constrained Video)"/>
            <listed-as-pcr-pid value="Yes"/>
        </program-information>
        <pcr-statistics>
            <number-of-pcrs-seen value="118328"/>
            <average-pcr-interval value="14.0315"/>
            <minimum-pcr-interval value="0.1003"/>
            <packet-number-at-minimum-pcr-interval value="81142" time="00:00:08.135"/>
            <maximum-pcr-interval value="35.9957"/>
            <packet-number-at-maximum-pcr-interval value="7639694" time="00:12:46.006"/>
            <pcr-jitter-magnitude value="30"/>
        </pcr-statistics>
        <packet-statistics>
            <number-of-packets value="15844773"/>
            <first-seen-in-packet-number value="3" time="00:00:00.000"/>
            <last-seen-in-packet-number value="16559069" time="00:27:40.322"/>
            <average-transport-packet-rate value="14334901"/>
            <average-payload-rate value="13999998"/>
            <average-packet-spacing value="0.1048"/>
            <minimum-packet-spacing value="0.1003"/>
            <packet-number-at-minimum-spacing value="4" time="00:00:00.000"/>
            <maximum-packet-spacing value="23.9637"/>
            <packet-number-at-maximum-spacing value="11225391" time="00:18:45.532"/>
            <minimum-packet-spacing-ends-inclusive value="0.1003"/>
            <maximum-packet-spacing-ends-inclusive value="2091.3621"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="15844773"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="1"/>
            <number-of-packets-random_access_indicator-is-1 value="3318"/>
            <splice_countdown-fields-seen value="No"/>
            <noemptyaf value="Yes"/>
        </adaptation-field-information>
        <pes-information>
            <number-of-pes-headers-seen value="49763"/>
            <pes-stream_id value="0xE0 (224, MPEG Video)"/>
            <number-of-packets-pes_scrambling_control-is-00 value="49763"/>
            <pes-copyright-set value="No"/>
            <number-of-packets-pes_priority-is-1 value="0"/>
            <number-of-ptss-seen value="49763"/>
            <average-pts-interval value="33.3646"/>
            <average-es-buffer-level value="604817"/>
            <minimum-es-buffer-level value="127807"/>
            <packet-number-at-minimum-es-buffer-level value="654323" time="00:01:05.606"/>
            <maximum-es-buffer-level value="718848"/>
            <packet-number-at-maximum-es-buffer-level value="4407" time="00:00:00.441"/>
            <pesalign value="Yes"/>
        </pes-information>
        <video-elementary-stream-information>
            <mpeg-2-video-profile value="Main"/>
            <mpeg-2-video-level value="High"/>
            <mpeg-chroma-format value="4:2:0"/>
            <closed-caption-types-detected value="ATSC (EIA608, EIA708)"/>
            <a-53-cc-channels-detected value="CC1, XDS"/>
            <a-53-dtvcc-services-detected value="1, 2"/>
            <number-of-video-access-units-seen value="49763"/>
            <minimum-number-of-non-i-pictures-between-two-i-pictures value="12"/>
            <maximum-number-of-non-i-pictures-between-two-i-pictures value="14"/>
            <average-number-of-non-i-pictures-between-two-i-pictures value="14"/>
            <percent-of-all-pictures-that-are-p-pictures value="26"/>
            <percent-of-all-pictures-that-are-b-pictures value="66"/>
            <maximum-number-of-b-frames-between-i-or-p-frames value="2"/>
            <video-bit-rate value="14000000"/>
            <vbv-method value="Off"/>
            <vbv-size value="5750784"/>
            <video-multiplexing-buffer-size value="53333"/>
            <video-es-buffer-size value="718848"/>
            <minimum-decode-delay value="0.1050"/>
            <maximum-decode-delay value="0.5229"/>
            <mb-to-eb-transfer-method value="Leak"/>
            <video-frame-rate value="29.9700"/>
            <video-in-film-mode value="No"/>
            <progressive-sequence value="No"/>
            <horizontal-size value="1920"/>
            <vertical-size value="1080"/>
            <aspect-ratio value="16:9"/>
            <sample-aspect-ratio value="1.0000"/>
            <quadbyte value="Yes"/>
            <initial-timecode value="0:00:00:00"/>
            <final-timecode value="0:27:38:21"/>
        </video-elementary-stream-information>

        <xds-information>
            <prograting value="TV-MA L"/>
            <cgms value="Source:Digital SCMS:CopyNever APS:None"/>
        </xds-information>
        <errors>
            <error pid="0x01E1" packet="3182" time="00:00:00.318" code="6-13w"
                   msg="Video GOP length was 12; the configured minimum is 15."/>
            <error pid="0x01E1" packet="8295" time="00:00:00.831" code="6-13w"
                   msg="Video GOP length was 14; the configured minimum is 15."/>
            <error pid="0x01E1" packet="15445" time="00:00:01.548" code="6-13w"
                   msg="Video GOP length was 14; the configured minimum is 15."/>
            <error pid="0x01E1" packet="20010" time="00:00:02.006" code="6-13w"
                   msg="Video GOP length was 14; the configured minimum is 15."/>
            <error pid="0x01E1" packet="25055" time="00:00:02.512" code="6-13w" msg="Video GOP length is less than the configured minimum.
This error was detected 3312 more times in this PID after packet 25055"/>
            <error pid="0x01E1" packet="182128" time="00:00:18.261" code="4-87w"
                   msg="The CEA708 DTVCC packet sequence number was 2, expected 4."/>
            <error pid="0x01E1" packet="658451" time="00:01:06.020" code="4-87w"
                   msg="The CEA708 DTVCC packet sequence number was 0, expected 2."/>
            <error pid="0x01E1" packet="16192511" time="00:27:03.569" code="4-87w"
                   msg="The CEA708 DTVCC packet sequence number was 0, expected 3."/>
        </errors>
    </pid-properties>

    <pid-properties>
        <pid value="0x01E2 (482)"/>
        <content value="Dolby AC-3 Audio"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <program-information>
            <member-of-program_number value="2"/>
            <listed-in-pmt-version_number value="0"/>
            <stream_type value="0x81 (129, AC3 Audio)"/>
            <associated-pcr-pid value="0x01E1 (481)"/>
            <registration_descriptor>
                <tag value="0x05"/>
                <length value="4"/>
                <format_identifier value="0x41432D33 (AC-3)"/>
                <additional_identification_info value="none"/>
            </registration_descriptor>
            <iso_639_language_descriptor>
                <tag value="0x0A (10)"/>
                <length value="4"/>
                <iso_639_language_code value="0x656E67 (eng)"/>
                <audio_type value="0 (Undefined)"/>
            </iso_639_language_descriptor>
            <ac3_audio_descriptor>
                <tag value="0x81 (129)"/>
                <length value="4"/>
                <sample_rate_code value="0 (48 kHz)"/>
                <bsid value="8"/>
                <bit_rate_limit value="0"/>
                <bit_rate_code value="14 (384 kbps)"/>
                <surround_mode value="0 (Not indicated)"/>
                <bsmod value="0 (Main: Complete Main)"/>
                <num_channels value="2 (2/0)"/>
                <full_svc value="1"/>
                <langcod value="0"/>
            </ac3_audio_descriptor>
        </program-information>
        <packet-statistics>
            <number-of-packets value="466974"/>
            <first-seen-in-packet-number value="12" time="00:00:00.001"/>
            <last-seen-in-packet-number value="16562164" time="00:27:40.632"/>
            <average-transport-packet-rate value="422475"/>
            <average-payload-rate value="383524"/>
            <average-packet-spacing value="3.5562"/>
            <minimum-packet-spacing value="1.1029"/>
            <packet-number-at-minimum-spacing value="23" time="00:00:00.002"/>
            <maximum-packet-spacing value="326.2677"/>
            <packet-number-at-maximum-spacing value="3409" time="00:00:00.341"/>
            <minimum-packet-spacing-ends-inclusive value="1.1029"/>
            <maximum-packet-spacing-ends-inclusive value="1781.0368"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="466974"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="0"/>
            <number-of-packets-random_access_indicator-is-1 value="0"/>
            <splice_countdown-fields-seen value="No"/>
        </adaptation-field-information>
        <pes-information>
            <number-of-pes-headers-seen value="51886"/>
            <pes-stream_id value="0xBD (189, Private stream 1)"/>
            <number-of-packets-pes_scrambling_control-is-00 value="51886"/>
            <pes-copyright-set value="No"/>
            <number-of-packets-pes_priority-is-1 value="0"/>
            <number-of-ptss-seen value="51886"/>
            <average-pts-interval value="32.0054"/>
            <average-es-buffer-level value="1695"/>
            <minimum-es-buffer-level value="920"/>
            <packet-number-at-minimum-es-buffer-level value="3409" time="00:00:00.341"/>
            <maximum-es-buffer-level value="2470"/>
            <packet-number-at-maximum-es-buffer-level value="3409" time="00:00:00.341"/>
        </pes-information>
        <audio-elementary-stream-information>
            <audio-type value="Dolby AC-3"/>
            <number-of-audio-access-units-seen value="51886"/>
            <average-number-of-audio-frames-per-pes-packet value="1"/>
            <sample-rate value="48000"/>
            <bit-rate value="384000"/>
            <number-and-location-of-channels value="Stereo channels: L, R: 2/0"/>
            <ac3-audio-service-type value="Main audio service: Complete Main (CM)"/>
            <low-frequency-effects-flag value="0"/>
            <nominal-frame-size value="1536"/>
            <dialogue-normalization value="-16"/>
            <main-audio-buffer-size value="2592"/>
        </audio-elementary-stream-information>
    </pid-properties>

    <pid-properties>
        <pid value="0x1FFF (8191)"/>
        <content value="Null"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <packet-statistics>
            <number-of-packets value="240977"/>
            <first-seen-in-packet-number value="4265" time="00:00:00.427"/>
            <last-seen-in-packet-number value="16579926" time="00:27:42.413"/>
            <average-transport-packet-rate value="218014"/>
            <average-payload-rate value="218014"/>
            <average-packet-spacing value="6.8969"/>
            <minimum-packet-spacing value="0.1003"/>
            <packet-number-at-minimum-spacing value="4266" time="00:00:00.427"/>
            <maximum-packet-spacing value="8138.4448"/>
            <packet-number-at-maximum-spacing value="14548160" time="00:24:18.695"/>
            <minimum-packet-spacing-ends-inclusive value="0.1003"/>
            <maximum-packet-spacing-ends-inclusive value="8138.4448"/>
        </packet-statistics>
    </pid-properties>

<errors>
  <error pid="0x01E1" packet="661" time="00:00:00.264" code="6-13w" msg="Video GOP length was 12; the configured minimum is 15."/>
  <error pid="0x01E1" packet="1973" time="00:00:00.790" code="6-13w" msg="Video GOP length was 14; the configured minimum is 15."/>
  <error pid="0x01E1" packet="3224" time="00:00:01.292" code="6-13w" msg="Video GOP length was 14; the configured minimum is 15."/>
  <error pid="0x01E1" packet="4472" time="00:00:01.793" code="6-13w" msg="Video GOP length was 14; the configured minimum is 15."/>
  <error pid="0x01E1" packet="5718" time="00:00:02.292" code="6-13w" msg="Video GOP length is less than the configured minimum.
This error was detected 129 more times in this PID after packet 5718"/>
  <error pid="0x01E1" code="6-53e" msg="Missing CEA 708 CC1 data; configuration requires."/>
</errors>

</tsa-report>
"""
        content.manzanitaCGMS = "Source:Digital SCMS:CopyNever APS:None"
        content.manzanitaVChip = "TV-MA, L"
        content.manzanitaRuntime = 1750

        Content generatedContent = new Content(centralId: 5, original: false, name: "batman-1.mpg",
                checksum: "123456789", audioType: "Stereo", dataServiceXml: "</xml>",
                size: 100, runtime: 100, contentType: "PREVIEW", asset: happyAsset,
                profile: "Pro 7", contentState: ContentState.COMPLETE.toString())

        generatedContent.manzanitaResult = ManzanitaResultStates.FAILED.toString()
        generatedContent.manzanitaReport = """
<tsa-report
        version="1.0.0"
        license-serno="CC0825"
        license-username="Clearleap, Inc."
        date="Fri Jun 17 16:48:18 2011"
        file="/usr/local/clearleap/cache/dev-central2/accounts/2572/assets/1285573/Entourage_S4_08_comp_HBOD000000112574_P7D-1356313.mpg">
    <configuration>
        <analysis-mode value="Auto (MPEG)"/>
        tsa-config 2
        Abort-Limit: none
        About: {CableLabs specification for content containing High Definition
        video and one audio stream.
        Document OC-SP-CEP3.0-I01-100827 (August 27, 2010)
        }
        Use-ATSC-Buffer-Model: on
        PMT-In-Single-Packet: warn
        Warn-On-Missing-AC3-Descriptor: warn
        Max-PCR-Interval: 40.0 warn
        Disc-In-First-PCR-Only: err
        I-Pic-Has-RAI: err
        Max-B-Frame: 2
        Video-All-Pics-Frame: err
        Video-No-Scalable-Ext: err
        I-Pic-Has-DTS: err
        Video-Starts-With-Seq-Start: err
        GOP-First-Is-Closed: err
        GOP-Size-15-or-12: warn
        Video-One-AU-Per-PES: err
        Video-PES-Payload-Has-PCR: err
        Video-AU-Starts-PES: err
        Audio-Starts-With-AU: err
        Audio-Has-No-Partial-AU: err
        Packet-Size: 188
        PAT-Never-Changes: err
        PMT-Never-Changes: err
        PSI-Definition-Precedes-PIDs: err
        No-Partial-Transport-Packets: err
        Max-Rate-PID0-PMT-Vid-Aud: 38000000
        SCTE20-Precedes-ATSC53-CC: err
        Minimum-GOP-Length-Error-Level: warn
        Table [
        PID: 0
        Table-ID: 0
        Maximum-Table-Interval: 250
        ]
        Table [
        Table-ID: 2
        Maximum-Table-Interval: 250
        ]
        Program [
        Video [
        Require-CEA708-DTVCC1: on
        Require-CEA708-CC1: on
        Stream-Type: 2
        Stream-Type-Alternate: 128
        Properties: [
        profile = 4
        level = 4
        aspect_ratio = 3
        chroma_format = 1
        (vertical_size = 1080 horizontal_size = 1920
        (progressive_sequence = 0
        frame_rate = 4) |
        (progressive_sequence = 1
        (frame_rate = 1) | (frame_rate = 2))) |
        (vertical_size = 720 horizontal_size = 1280
        frame_rate = 7
        progressive_sequence = 1)
        ]
        Minimum-GOP-Length: 15
        Maximum-GOP-Length: 15
        PID: 481
        ]
        Audio [
        Stream-Type: 129
        Properties: [
        type = 6
        (channel_mode = 2 bit_rate = 384000) |
        (channel_mode = 7 bit_rate = 384000)
        sample_rate = 48000
        ]
        PID: 482
        ]
        PMT-PID: 480
        PCR-PID: 481
        ]
        end-config
    </configuration>

    <stream-information>
        <transport-rate value="15000000.0"/>
        <duration value="00:27:42.413"/>
        <file-size value="3117026088"/>
        <transport-packet-interval value="188"/>
        <packets-analyzed value="16579926"/>
        <total-errors value="0"/>
        <total-warnings value="3320"/>
    </stream-information>

    <multiplex-summary>
        <pat version="0" pid="0">
            <program program_number="2" pid="0x01E0">
                <pmt version="0" pcr-pid="0x01E1">
                    <stream pid="0x01E2" type="Dolby AC-3 Audio">
                        <descriptor tag="0x05" name="registration_descriptor"/>
                        <descriptor tag="0x0A" name="ISO_639_language_descriptor"/>
                        <descriptor tag="0x81" name="ac3_audio_descriptor"/>
                    </stream>
                    <stream pid="0x01E1" type="MPEG-2 Video">
                    </stream>
                </pmt>
            </program>
        </pat>
    </multiplex-summary>

    <pid-summary>
        <pid-info pid="0x0000" content="PAT"
                  npkt="13601" pkt-rate="12305" payld-rate="12043"/>
        <pid-info pid="0x01E0" content="PMT" programs="2"
                  npkt="13601" pkt-rate="12305" payld-rate="12043"/>
        <pid-info pid="0x01E1" content="MPEG-2 Video, PCR" programs="2"
                  npkt="15844773" pkt-rate="14334901" payld-rate="13999998"
                  errors="0" warnings="3320"/>
        <pid-info pid="0x01E2" content="Dolby AC-3 Audio" programs="2"
                  npkt="466974" pkt-rate="422475" payld-rate="383524"/>
        <pid-info pid="0x1FFF" content="Null"
                  npkt="240977" pkt-rate="218014" payld-rate="218014"/>
    </pid-summary>

    <pid-properties>
        <pid value="0x0000 (0)"/>
        <content value="PAT"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <program-association-table-information>
            <table_id value="0x00"/>
            <version_number value="0"/>
            <transport_stream_id value="0x0001 (1)"/>
        </program-association-table-information>
        <packet-statistics>
            <number-of-packets value="13601"/>
            <first-seen-in-packet-number value="1" time="00:00:00.000"/>
            <last-seen-in-packet-number value="16578712" time="00:27:42.292"/>
            <average-transport-packet-rate value="12305"/>
            <average-payload-rate value="12043"/>
            <average-packet-spacing value="122.2274"/>
            <minimum-packet-spacing value="122.2251"/>
            <packet-number-at-minimum-spacing value="1220" time="00:00:00.122"/>
            <maximum-packet-spacing value="122.3253"/>
            <packet-number-at-maximum-spacing value="7316" time="00:00:00.733"/>
            <minimum-packet-spacing-ends-inclusive value="121.8240"/>
            <maximum-packet-spacing-ends-inclusive value="122.3253"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="13601"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="0"/>
            <number-of-packets-random_access_indicator-is-1 value="0"/>
            <splice_countdown-fields-seen value="No"/>
        </adaptation-field-information>
    </pid-properties>

    <pid-properties>
        <pid value="0x01E0 (480)"/>
        <content value="PMT"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <program-information>
            <pmt-pid-for-program_number value="2"/>
            <listed-in-pat-version_number value="0"/>
        </program-information>
        <program-map-table-information>
            <table_id value="0x02"/>
            <program_number value="2"/>
            <version_number value="0"/>
            <pcr-pid value="0x01E1 (481)"/>
            <section_length value="41"/>
        </program-map-table-information>
        <packet-statistics>
            <number-of-packets value="13601"/>
            <first-seen-in-packet-number value="2" time="00:00:00.000"/>
            <last-seen-in-packet-number value="16578713" time="00:27:42.292"/>
            <average-transport-packet-rate value="12305"/>
            <average-payload-rate value="12043"/>
            <average-packet-spacing value="122.2274"/>
            <minimum-packet-spacing value="122.2251"/>
            <packet-number-at-minimum-spacing value="1221" time="00:00:00.122"/>
            <maximum-packet-spacing value="122.3253"/>
            <packet-number-at-maximum-spacing value="7317" time="00:00:00.733"/>
            <minimum-packet-spacing-ends-inclusive value="0.1003"/>
            <maximum-packet-spacing-ends-inclusive value="122.3253"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="13601"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="0"/>
            <number-of-packets-random_access_indicator-is-1 value="0"/>
            <splice_countdown-fields-seen value="No"/>
        </adaptation-field-information>
    </pid-properties>

    <pid-properties>
        <pid value="0x01E1 (481)"/>
        <content value="MPEG-2 Video, PCR"/>
        <total-errors value="0"/>
        <total-warnings value="3320"/>
        <program-information>
            <member-of-program_number value="2"/>
            <listed-in-pmt-version_number value="0"/>
            <stream_type value="0x02 (H.262 | 13818-2 Video | 11172-2 Constrained Video)"/>
            <listed-as-pcr-pid value="Yes"/>
        </program-information>
        <pcr-statistics>
            <number-of-pcrs-seen value="118328"/>
            <average-pcr-interval value="14.0315"/>
            <minimum-pcr-interval value="0.1003"/>
            <packet-number-at-minimum-pcr-interval value="81142" time="00:00:08.135"/>
            <maximum-pcr-interval value="35.9957"/>
            <packet-number-at-maximum-pcr-interval value="7639694" time="00:12:46.006"/>
            <pcr-jitter-magnitude value="30"/>
        </pcr-statistics>
        <packet-statistics>
            <number-of-packets value="15844773"/>
            <first-seen-in-packet-number value="3" time="00:00:00.000"/>
            <last-seen-in-packet-number value="16559069" time="00:27:40.322"/>
            <average-transport-packet-rate value="14334901"/>
            <average-payload-rate value="13999998"/>
            <average-packet-spacing value="0.1048"/>
            <minimum-packet-spacing value="0.1003"/>
            <packet-number-at-minimum-spacing value="4" time="00:00:00.000"/>
            <maximum-packet-spacing value="23.9637"/>
            <packet-number-at-maximum-spacing value="11225391" time="00:18:45.532"/>
            <minimum-packet-spacing-ends-inclusive value="0.1003"/>
            <maximum-packet-spacing-ends-inclusive value="2091.3621"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="15844773"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="1"/>
            <number-of-packets-random_access_indicator-is-1 value="3318"/>
            <splice_countdown-fields-seen value="No"/>
            <noemptyaf value="Yes"/>
        </adaptation-field-information>
        <pes-information>
            <number-of-pes-headers-seen value="49763"/>
            <pes-stream_id value="0xE0 (224, MPEG Video)"/>
            <number-of-packets-pes_scrambling_control-is-00 value="49763"/>
            <pes-copyright-set value="No"/>
            <number-of-packets-pes_priority-is-1 value="0"/>
            <number-of-ptss-seen value="49763"/>
            <average-pts-interval value="33.3646"/>
            <average-es-buffer-level value="604817"/>
            <minimum-es-buffer-level value="127807"/>
            <packet-number-at-minimum-es-buffer-level value="654323" time="00:01:05.606"/>
            <maximum-es-buffer-level value="718848"/>
            <packet-number-at-maximum-es-buffer-level value="4407" time="00:00:00.441"/>
            <pesalign value="Yes"/>
        </pes-information>
        <video-elementary-stream-information>
            <mpeg-2-video-profile value="Main"/>
            <mpeg-2-video-level value="High"/>
            <mpeg-chroma-format value="4:2:0"/>
            <closed-caption-types-detected value="ATSC (EIA608, EIA708)"/>
            <a-53-cc-channels-detected value="CC1, XDS"/>
            <a-53-dtvcc-services-detected value="1, 2"/>
            <number-of-video-access-units-seen value="49763"/>
            <minimum-number-of-non-i-pictures-between-two-i-pictures value="12"/>
            <maximum-number-of-non-i-pictures-between-two-i-pictures value="14"/>
            <average-number-of-non-i-pictures-between-two-i-pictures value="14"/>
            <percent-of-all-pictures-that-are-p-pictures value="26"/>
            <percent-of-all-pictures-that-are-b-pictures value="66"/>
            <maximum-number-of-b-frames-between-i-or-p-frames value="2"/>
            <video-bit-rate value="14000000"/>
            <vbv-method value="Off"/>
            <vbv-size value="5750784"/>
            <video-multiplexing-buffer-size value="53333"/>
            <video-es-buffer-size value="718848"/>
            <minimum-decode-delay value="0.1050"/>
            <maximum-decode-delay value="0.5229"/>
            <mb-to-eb-transfer-method value="Leak"/>
            <video-frame-rate value="29.9700"/>
            <video-in-film-mode value="No"/>
            <progressive-sequence value="No"/>
            <horizontal-size value="1920"/>
            <vertical-size value="1080"/>
            <aspect-ratio value="16:9"/>
            <sample-aspect-ratio value="1.0000"/>
            <quadbyte value="Yes"/>
            <initial-timecode value="0:00:00:00"/>
            <final-timecode value="0:27:38:21"/>
        </video-elementary-stream-information>

        <xds-information>
            <prograting value="TV-MA L"/>
            <cgms value="Source:Digital SCMS:CopyNever APS:None"/>
        </xds-information>
        <errors>
            <error pid="0x01E1" packet="3182" time="00:00:00.318" code="6-13w"
                   msg="Video GOP length was 12; the configured minimum is 15."/>
            <error pid="0x01E1" packet="8295" time="00:00:00.831" code="6-13w"
                   msg="Video GOP length was 14; the configured minimum is 15."/>
            <error pid="0x01E1" packet="15445" time="00:00:01.548" code="6-13w"
                   msg="Video GOP length was 14; the configured minimum is 15."/>
            <error pid="0x01E1" packet="20010" time="00:00:02.006" code="6-13w"
                   msg="Video GOP length was 14; the configured minimum is 15."/>
            <error pid="0x01E1" packet="25055" time="00:00:02.512" code="6-13w" msg="Video GOP length is less than the configured minimum.
This error was detected 3312 more times in this PID after packet 25055"/>
            <error pid="0x01E1" packet="182128" time="00:00:18.261" code="4-87w"
                   msg="The CEA708 DTVCC packet sequence number was 2, expected 4."/>
            <error pid="0x01E1" packet="658451" time="00:01:06.020" code="4-87w"
                   msg="The CEA708 DTVCC packet sequence number was 0, expected 2."/>
            <error pid="0x01E1" packet="16192511" time="00:27:03.569" code="4-87w"
                   msg="The CEA708 DTVCC packet sequence number was 0, expected 3."/>
        </errors>
    </pid-properties>

    <pid-properties>
        <pid value="0x01E2 (482)"/>
        <content value="Dolby AC-3 Audio"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <program-information>
            <member-of-program_number value="2"/>
            <listed-in-pmt-version_number value="0"/>
            <stream_type value="0x81 (129, AC3 Audio)"/>
            <associated-pcr-pid value="0x01E1 (481)"/>
            <registration_descriptor>
                <tag value="0x05"/>
                <length value="4"/>
                <format_identifier value="0x41432D33 (AC-3)"/>
                <additional_identification_info value="none"/>
            </registration_descriptor>
            <iso_639_language_descriptor>
                <tag value="0x0A (10)"/>
                <length value="4"/>
                <iso_639_language_code value="0x656E67 (eng)"/>
                <audio_type value="0 (Undefined)"/>
            </iso_639_language_descriptor>
            <ac3_audio_descriptor>
                <tag value="0x81 (129)"/>
                <length value="4"/>
                <sample_rate_code value="0 (48 kHz)"/>
                <bsid value="8"/>
                <bit_rate_limit value="0"/>
                <bit_rate_code value="14 (384 kbps)"/>
                <surround_mode value="0 (Not indicated)"/>
                <bsmod value="0 (Main: Complete Main)"/>
                <num_channels value="2 (2/0)"/>
                <full_svc value="1"/>
                <langcod value="0"/>
            </ac3_audio_descriptor>
        </program-information>
        <packet-statistics>
            <number-of-packets value="466974"/>
            <first-seen-in-packet-number value="12" time="00:00:00.001"/>
            <last-seen-in-packet-number value="16562164" time="00:27:40.632"/>
            <average-transport-packet-rate value="422475"/>
            <average-payload-rate value="383524"/>
            <average-packet-spacing value="3.5562"/>
            <minimum-packet-spacing value="1.1029"/>
            <packet-number-at-minimum-spacing value="23" time="00:00:00.002"/>
            <maximum-packet-spacing value="326.2677"/>
            <packet-number-at-maximum-spacing value="3409" time="00:00:00.341"/>
            <minimum-packet-spacing-ends-inclusive value="1.1029"/>
            <maximum-packet-spacing-ends-inclusive value="1781.0368"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="466974"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="0"/>
            <number-of-packets-random_access_indicator-is-1 value="0"/>
            <splice_countdown-fields-seen value="No"/>
        </adaptation-field-information>
        <pes-information>
            <number-of-pes-headers-seen value="51886"/>
            <pes-stream_id value="0xBD (189, Private stream 1)"/>
            <number-of-packets-pes_scrambling_control-is-00 value="51886"/>
            <pes-copyright-set value="No"/>
            <number-of-packets-pes_priority-is-1 value="0"/>
            <number-of-ptss-seen value="51886"/>
            <average-pts-interval value="32.0054"/>
            <average-es-buffer-level value="1695"/>
            <minimum-es-buffer-level value="920"/>
            <packet-number-at-minimum-es-buffer-level value="3409" time="00:00:00.341"/>
            <maximum-es-buffer-level value="2470"/>
            <packet-number-at-maximum-es-buffer-level value="3409" time="00:00:00.341"/>
        </pes-information>
        <audio-elementary-stream-information>
            <audio-type value="Dolby AC-3"/>
            <number-of-audio-access-units-seen value="51886"/>
            <average-number-of-audio-frames-per-pes-packet value="1"/>
            <sample-rate value="48000"/>
            <bit-rate value="384000"/>
            <number-and-location-of-channels value="Stereo channels: L, R: 2/0"/>
            <ac3-audio-service-type value="Main audio service: Complete Main (CM)"/>
            <low-frequency-effects-flag value="0"/>
            <nominal-frame-size value="1536"/>
            <dialogue-normalization value="-16"/>
            <main-audio-buffer-size value="2592"/>
        </audio-elementary-stream-information>
    </pid-properties>

    <pid-properties>
        <pid value="0x1FFF (8191)"/>
        <content value="Null"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <packet-statistics>
            <number-of-packets value="240977"/>
            <first-seen-in-packet-number value="4265" time="00:00:00.427"/>
            <last-seen-in-packet-number value="16579926" time="00:27:42.413"/>
            <average-transport-packet-rate value="218014"/>
            <average-payload-rate value="218014"/>
            <average-packet-spacing value="6.8969"/>
            <minimum-packet-spacing value="0.1003"/>
            <packet-number-at-minimum-spacing value="4266" time="00:00:00.427"/>
            <maximum-packet-spacing value="8138.4448"/>
            <packet-number-at-maximum-spacing value="14548160" time="00:24:18.695"/>
            <minimum-packet-spacing-ends-inclusive value="0.1003"/>
            <maximum-packet-spacing-ends-inclusive value="8138.4448"/>
        </packet-statistics>
    </pid-properties>

<errors>
  <error pid="0x01E1" packet="661" time="00:00:00.264" code="6-13w" msg="Video GOP length was 12; the configured minimum is 15."/>
  <error pid="0x01E1" packet="1973" time="00:00:00.790" code="6-13w" msg="Video GOP length was 14; the configured minimum is 15."/>
  <error pid="0x01E1" packet="3224" time="00:00:01.292" code="6-13w" msg="Video GOP length was 14; the configured minimum is 15."/>
  <error pid="0x01E1" packet="4472" time="00:00:01.793" code="6-13w" msg="Video GOP length was 14; the configured minimum is 15."/>
  <error pid="0x01E1" packet="5718" time="00:00:02.292" code="6-13w" msg="Video GOP length is less than the configured minimum.
This error was detected 129 more times in this PID after packet 5718"/>
  <error pid="0x01E1" code="6-55e" msg="Missing CEA 708 CC1 data; configuration requires."/>
  <error pid="0x01E1" code="6-64e" msg="Missing CEA 708 CC1 data; configuration requires."/>
</errors>

</tsa-report>
"""
        generatedContent.manzanitaCGMS = "Source:Digital SCMS:CopyNever APS:None"
        generatedContent.manzanitaVChip = "TV-MA, L"
        generatedContent.manzanitaRuntime = 1750

        happyAsset.contents.add(content)
        happyAsset.contents.add(generatedContent)
        happyAsset.save(flush: true)


        assertEquals(ManzanitaResultStates.FAILED.toString(), content.manzanitaResult)
        assertEquals(ManzanitaResultStates.FAILED.toString(), generatedContent.manzanitaResult)

        assetService.updatePreviewContentsManzanitaResult(happyAsset)

        assertEquals(ManzanitaResultStates.PASSED.toString(), content.manzanitaResult)
        assertEquals(ManzanitaResultStates.FAILED.toString(), generatedContent.manzanitaResult)

    }

    /**
     * 1 original preview content with 6-53e, 1 generated content with both cc codes and other errors.
     */
    void testOriginalPreviewContentHasCCErrorCodeGeneratedHasCCErrorCodesAndOtherErrors() {

        println "Asset: ${happyAsset.dump()}"

        //Original preview
        Content content = new Content(centralId: 4, original: true, name: "batman.mpg",
                checksum: "123456789", audioType: "Stereo", dataServiceXml: "</xml>",
                size: 100, runtime: 100, contentType: "PREVIEW", asset: happyAsset,
                profile: "Pro 7", contentState: ContentState.COMPLETE.toString())

        content.manzanitaResult = ManzanitaResultStates.FAILED.toString()
        content.manzanitaReport = """
<tsa-report
        version="1.0.0"
        license-serno="CC0825"
        license-username="Clearleap, Inc."
        date="Fri Jun 17 16:48:18 2011"
        file="/usr/local/clearleap/cache/dev-central2/accounts/2572/assets/1285573/Entourage_S4_08_comp_HBOD000000112574_P7D-1356313.mpg">
    <configuration>
        <analysis-mode value="Auto (MPEG)"/>
        tsa-config 2
        Abort-Limit: none
        About: {CableLabs specification for content containing High Definition
        video and one audio stream.
        Document OC-SP-CEP3.0-I01-100827 (August 27, 2010)
        }
        Use-ATSC-Buffer-Model: on
        PMT-In-Single-Packet: warn
        Warn-On-Missing-AC3-Descriptor: warn
        Max-PCR-Interval: 40.0 warn
        Disc-In-First-PCR-Only: err
        I-Pic-Has-RAI: err
        Max-B-Frame: 2
        Video-All-Pics-Frame: err
        Video-No-Scalable-Ext: err
        I-Pic-Has-DTS: err
        Video-Starts-With-Seq-Start: err
        GOP-First-Is-Closed: err
        GOP-Size-15-or-12: warn
        Video-One-AU-Per-PES: err
        Video-PES-Payload-Has-PCR: err
        Video-AU-Starts-PES: err
        Audio-Starts-With-AU: err
        Audio-Has-No-Partial-AU: err
        Packet-Size: 188
        PAT-Never-Changes: err
        PMT-Never-Changes: err
        PSI-Definition-Precedes-PIDs: err
        No-Partial-Transport-Packets: err
        Max-Rate-PID0-PMT-Vid-Aud: 38000000
        SCTE20-Precedes-ATSC53-CC: err
        Minimum-GOP-Length-Error-Level: warn
        Table [
        PID: 0
        Table-ID: 0
        Maximum-Table-Interval: 250
        ]
        Table [
        Table-ID: 2
        Maximum-Table-Interval: 250
        ]
        Program [
        Video [
        Require-CEA708-DTVCC1: on
        Require-CEA708-CC1: on
        Stream-Type: 2
        Stream-Type-Alternate: 128
        Properties: [
        profile = 4
        level = 4
        aspect_ratio = 3
        chroma_format = 1
        (vertical_size = 1080 horizontal_size = 1920
        (progressive_sequence = 0
        frame_rate = 4) |
        (progressive_sequence = 1
        (frame_rate = 1) | (frame_rate = 2))) |
        (vertical_size = 720 horizontal_size = 1280
        frame_rate = 7
        progressive_sequence = 1)
        ]
        Minimum-GOP-Length: 15
        Maximum-GOP-Length: 15
        PID: 481
        ]
        Audio [
        Stream-Type: 129
        Properties: [
        type = 6
        (channel_mode = 2 bit_rate = 384000) |
        (channel_mode = 7 bit_rate = 384000)
        sample_rate = 48000
        ]
        PID: 482
        ]
        PMT-PID: 480
        PCR-PID: 481
        ]
        end-config
    </configuration>

    <stream-information>
        <transport-rate value="15000000.0"/>
        <duration value="00:27:42.413"/>
        <file-size value="3117026088"/>
        <transport-packet-interval value="188"/>
        <packets-analyzed value="16579926"/>
        <total-errors value="0"/>
        <total-warnings value="3320"/>
    </stream-information>

    <multiplex-summary>
        <pat version="0" pid="0">
            <program program_number="2" pid="0x01E0">
                <pmt version="0" pcr-pid="0x01E1">
                    <stream pid="0x01E2" type="Dolby AC-3 Audio">
                        <descriptor tag="0x05" name="registration_descriptor"/>
                        <descriptor tag="0x0A" name="ISO_639_language_descriptor"/>
                        <descriptor tag="0x81" name="ac3_audio_descriptor"/>
                    </stream>
                    <stream pid="0x01E1" type="MPEG-2 Video">
                    </stream>
                </pmt>
            </program>
        </pat>
    </multiplex-summary>

    <pid-summary>
        <pid-info pid="0x0000" content="PAT"
                  npkt="13601" pkt-rate="12305" payld-rate="12043"/>
        <pid-info pid="0x01E0" content="PMT" programs="2"
                  npkt="13601" pkt-rate="12305" payld-rate="12043"/>
        <pid-info pid="0x01E1" content="MPEG-2 Video, PCR" programs="2"
                  npkt="15844773" pkt-rate="14334901" payld-rate="13999998"
                  errors="0" warnings="3320"/>
        <pid-info pid="0x01E2" content="Dolby AC-3 Audio" programs="2"
                  npkt="466974" pkt-rate="422475" payld-rate="383524"/>
        <pid-info pid="0x1FFF" content="Null"
                  npkt="240977" pkt-rate="218014" payld-rate="218014"/>
    </pid-summary>

    <pid-properties>
        <pid value="0x0000 (0)"/>
        <content value="PAT"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <program-association-table-information>
            <table_id value="0x00"/>
            <version_number value="0"/>
            <transport_stream_id value="0x0001 (1)"/>
        </program-association-table-information>
        <packet-statistics>
            <number-of-packets value="13601"/>
            <first-seen-in-packet-number value="1" time="00:00:00.000"/>
            <last-seen-in-packet-number value="16578712" time="00:27:42.292"/>
            <average-transport-packet-rate value="12305"/>
            <average-payload-rate value="12043"/>
            <average-packet-spacing value="122.2274"/>
            <minimum-packet-spacing value="122.2251"/>
            <packet-number-at-minimum-spacing value="1220" time="00:00:00.122"/>
            <maximum-packet-spacing value="122.3253"/>
            <packet-number-at-maximum-spacing value="7316" time="00:00:00.733"/>
            <minimum-packet-spacing-ends-inclusive value="121.8240"/>
            <maximum-packet-spacing-ends-inclusive value="122.3253"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="13601"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="0"/>
            <number-of-packets-random_access_indicator-is-1 value="0"/>
            <splice_countdown-fields-seen value="No"/>
        </adaptation-field-information>
    </pid-properties>

    <pid-properties>
        <pid value="0x01E0 (480)"/>
        <content value="PMT"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <program-information>
            <pmt-pid-for-program_number value="2"/>
            <listed-in-pat-version_number value="0"/>
        </program-information>
        <program-map-table-information>
            <table_id value="0x02"/>
            <program_number value="2"/>
            <version_number value="0"/>
            <pcr-pid value="0x01E1 (481)"/>
            <section_length value="41"/>
        </program-map-table-information>
        <packet-statistics>
            <number-of-packets value="13601"/>
            <first-seen-in-packet-number value="2" time="00:00:00.000"/>
            <last-seen-in-packet-number value="16578713" time="00:27:42.292"/>
            <average-transport-packet-rate value="12305"/>
            <average-payload-rate value="12043"/>
            <average-packet-spacing value="122.2274"/>
            <minimum-packet-spacing value="122.2251"/>
            <packet-number-at-minimum-spacing value="1221" time="00:00:00.122"/>
            <maximum-packet-spacing value="122.3253"/>
            <packet-number-at-maximum-spacing value="7317" time="00:00:00.733"/>
            <minimum-packet-spacing-ends-inclusive value="0.1003"/>
            <maximum-packet-spacing-ends-inclusive value="122.3253"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="13601"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="0"/>
            <number-of-packets-random_access_indicator-is-1 value="0"/>
            <splice_countdown-fields-seen value="No"/>
        </adaptation-field-information>
    </pid-properties>

    <pid-properties>
        <pid value="0x01E1 (481)"/>
        <content value="MPEG-2 Video, PCR"/>
        <total-errors value="0"/>
        <total-warnings value="3320"/>
        <program-information>
            <member-of-program_number value="2"/>
            <listed-in-pmt-version_number value="0"/>
            <stream_type value="0x02 (H.262 | 13818-2 Video | 11172-2 Constrained Video)"/>
            <listed-as-pcr-pid value="Yes"/>
        </program-information>
        <pcr-statistics>
            <number-of-pcrs-seen value="118328"/>
            <average-pcr-interval value="14.0315"/>
            <minimum-pcr-interval value="0.1003"/>
            <packet-number-at-minimum-pcr-interval value="81142" time="00:00:08.135"/>
            <maximum-pcr-interval value="35.9957"/>
            <packet-number-at-maximum-pcr-interval value="7639694" time="00:12:46.006"/>
            <pcr-jitter-magnitude value="30"/>
        </pcr-statistics>
        <packet-statistics>
            <number-of-packets value="15844773"/>
            <first-seen-in-packet-number value="3" time="00:00:00.000"/>
            <last-seen-in-packet-number value="16559069" time="00:27:40.322"/>
            <average-transport-packet-rate value="14334901"/>
            <average-payload-rate value="13999998"/>
            <average-packet-spacing value="0.1048"/>
            <minimum-packet-spacing value="0.1003"/>
            <packet-number-at-minimum-spacing value="4" time="00:00:00.000"/>
            <maximum-packet-spacing value="23.9637"/>
            <packet-number-at-maximum-spacing value="11225391" time="00:18:45.532"/>
            <minimum-packet-spacing-ends-inclusive value="0.1003"/>
            <maximum-packet-spacing-ends-inclusive value="2091.3621"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="15844773"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="1"/>
            <number-of-packets-random_access_indicator-is-1 value="3318"/>
            <splice_countdown-fields-seen value="No"/>
            <noemptyaf value="Yes"/>
        </adaptation-field-information>
        <pes-information>
            <number-of-pes-headers-seen value="49763"/>
            <pes-stream_id value="0xE0 (224, MPEG Video)"/>
            <number-of-packets-pes_scrambling_control-is-00 value="49763"/>
            <pes-copyright-set value="No"/>
            <number-of-packets-pes_priority-is-1 value="0"/>
            <number-of-ptss-seen value="49763"/>
            <average-pts-interval value="33.3646"/>
            <average-es-buffer-level value="604817"/>
            <minimum-es-buffer-level value="127807"/>
            <packet-number-at-minimum-es-buffer-level value="654323" time="00:01:05.606"/>
            <maximum-es-buffer-level value="718848"/>
            <packet-number-at-maximum-es-buffer-level value="4407" time="00:00:00.441"/>
            <pesalign value="Yes"/>
        </pes-information>
        <video-elementary-stream-information>
            <mpeg-2-video-profile value="Main"/>
            <mpeg-2-video-level value="High"/>
            <mpeg-chroma-format value="4:2:0"/>
            <closed-caption-types-detected value="ATSC (EIA608, EIA708)"/>
            <a-53-cc-channels-detected value="CC1, XDS"/>
            <a-53-dtvcc-services-detected value="1, 2"/>
            <number-of-video-access-units-seen value="49763"/>
            <minimum-number-of-non-i-pictures-between-two-i-pictures value="12"/>
            <maximum-number-of-non-i-pictures-between-two-i-pictures value="14"/>
            <average-number-of-non-i-pictures-between-two-i-pictures value="14"/>
            <percent-of-all-pictures-that-are-p-pictures value="26"/>
            <percent-of-all-pictures-that-are-b-pictures value="66"/>
            <maximum-number-of-b-frames-between-i-or-p-frames value="2"/>
            <video-bit-rate value="14000000"/>
            <vbv-method value="Off"/>
            <vbv-size value="5750784"/>
            <video-multiplexing-buffer-size value="53333"/>
            <video-es-buffer-size value="718848"/>
            <minimum-decode-delay value="0.1050"/>
            <maximum-decode-delay value="0.5229"/>
            <mb-to-eb-transfer-method value="Leak"/>
            <video-frame-rate value="29.9700"/>
            <video-in-film-mode value="No"/>
            <progressive-sequence value="No"/>
            <horizontal-size value="1920"/>
            <vertical-size value="1080"/>
            <aspect-ratio value="16:9"/>
            <sample-aspect-ratio value="1.0000"/>
            <quadbyte value="Yes"/>
            <initial-timecode value="0:00:00:00"/>
            <final-timecode value="0:27:38:21"/>
        </video-elementary-stream-information>

        <xds-information>
            <prograting value="TV-MA L"/>
            <cgms value="Source:Digital SCMS:CopyNever APS:None"/>
        </xds-information>
        <errors>
            <error pid="0x01E1" packet="3182" time="00:00:00.318" code="6-13w"
                   msg="Video GOP length was 12; the configured minimum is 15."/>
            <error pid="0x01E1" packet="8295" time="00:00:00.831" code="6-13w"
                   msg="Video GOP length was 14; the configured minimum is 15."/>
            <error pid="0x01E1" packet="15445" time="00:00:01.548" code="6-13w"
                   msg="Video GOP length was 14; the configured minimum is 15."/>
            <error pid="0x01E1" packet="20010" time="00:00:02.006" code="6-13w"
                   msg="Video GOP length was 14; the configured minimum is 15."/>
            <error pid="0x01E1" packet="25055" time="00:00:02.512" code="6-13w" msg="Video GOP length is less than the configured minimum.
This error was detected 3312 more times in this PID after packet 25055"/>
            <error pid="0x01E1" packet="182128" time="00:00:18.261" code="4-87w"
                   msg="The CEA708 DTVCC packet sequence number was 2, expected 4."/>
            <error pid="0x01E1" packet="658451" time="00:01:06.020" code="4-87w"
                   msg="The CEA708 DTVCC packet sequence number was 0, expected 2."/>
            <error pid="0x01E1" packet="16192511" time="00:27:03.569" code="4-87w"
                   msg="The CEA708 DTVCC packet sequence number was 0, expected 3."/>
        </errors>
    </pid-properties>

    <pid-properties>
        <pid value="0x01E2 (482)"/>
        <content value="Dolby AC-3 Audio"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <program-information>
            <member-of-program_number value="2"/>
            <listed-in-pmt-version_number value="0"/>
            <stream_type value="0x81 (129, AC3 Audio)"/>
            <associated-pcr-pid value="0x01E1 (481)"/>
            <registration_descriptor>
                <tag value="0x05"/>
                <length value="4"/>
                <format_identifier value="0x41432D33 (AC-3)"/>
                <additional_identification_info value="none"/>
            </registration_descriptor>
            <iso_639_language_descriptor>
                <tag value="0x0A (10)"/>
                <length value="4"/>
                <iso_639_language_code value="0x656E67 (eng)"/>
                <audio_type value="0 (Undefined)"/>
            </iso_639_language_descriptor>
            <ac3_audio_descriptor>
                <tag value="0x81 (129)"/>
                <length value="4"/>
                <sample_rate_code value="0 (48 kHz)"/>
                <bsid value="8"/>
                <bit_rate_limit value="0"/>
                <bit_rate_code value="14 (384 kbps)"/>
                <surround_mode value="0 (Not indicated)"/>
                <bsmod value="0 (Main: Complete Main)"/>
                <num_channels value="2 (2/0)"/>
                <full_svc value="1"/>
                <langcod value="0"/>
            </ac3_audio_descriptor>
        </program-information>
        <packet-statistics>
            <number-of-packets value="466974"/>
            <first-seen-in-packet-number value="12" time="00:00:00.001"/>
            <last-seen-in-packet-number value="16562164" time="00:27:40.632"/>
            <average-transport-packet-rate value="422475"/>
            <average-payload-rate value="383524"/>
            <average-packet-spacing value="3.5562"/>
            <minimum-packet-spacing value="1.1029"/>
            <packet-number-at-minimum-spacing value="23" time="00:00:00.002"/>
            <maximum-packet-spacing value="326.2677"/>
            <packet-number-at-maximum-spacing value="3409" time="00:00:00.341"/>
            <minimum-packet-spacing-ends-inclusive value="1.1029"/>
            <maximum-packet-spacing-ends-inclusive value="1781.0368"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="466974"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="0"/>
            <number-of-packets-random_access_indicator-is-1 value="0"/>
            <splice_countdown-fields-seen value="No"/>
        </adaptation-field-information>
        <pes-information>
            <number-of-pes-headers-seen value="51886"/>
            <pes-stream_id value="0xBD (189, Private stream 1)"/>
            <number-of-packets-pes_scrambling_control-is-00 value="51886"/>
            <pes-copyright-set value="No"/>
            <number-of-packets-pes_priority-is-1 value="0"/>
            <number-of-ptss-seen value="51886"/>
            <average-pts-interval value="32.0054"/>
            <average-es-buffer-level value="1695"/>
            <minimum-es-buffer-level value="920"/>
            <packet-number-at-minimum-es-buffer-level value="3409" time="00:00:00.341"/>
            <maximum-es-buffer-level value="2470"/>
            <packet-number-at-maximum-es-buffer-level value="3409" time="00:00:00.341"/>
        </pes-information>
        <audio-elementary-stream-information>
            <audio-type value="Dolby AC-3"/>
            <number-of-audio-access-units-seen value="51886"/>
            <average-number-of-audio-frames-per-pes-packet value="1"/>
            <sample-rate value="48000"/>
            <bit-rate value="384000"/>
            <number-and-location-of-channels value="Stereo channels: L, R: 2/0"/>
            <ac3-audio-service-type value="Main audio service: Complete Main (CM)"/>
            <low-frequency-effects-flag value="0"/>
            <nominal-frame-size value="1536"/>
            <dialogue-normalization value="-16"/>
            <main-audio-buffer-size value="2592"/>
        </audio-elementary-stream-information>
    </pid-properties>

    <pid-properties>
        <pid value="0x1FFF (8191)"/>
        <content value="Null"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <packet-statistics>
            <number-of-packets value="240977"/>
            <first-seen-in-packet-number value="4265" time="00:00:00.427"/>
            <last-seen-in-packet-number value="16579926" time="00:27:42.413"/>
            <average-transport-packet-rate value="218014"/>
            <average-payload-rate value="218014"/>
            <average-packet-spacing value="6.8969"/>
            <minimum-packet-spacing value="0.1003"/>
            <packet-number-at-minimum-spacing value="4266" time="00:00:00.427"/>
            <maximum-packet-spacing value="8138.4448"/>
            <packet-number-at-maximum-spacing value="14548160" time="00:24:18.695"/>
            <minimum-packet-spacing-ends-inclusive value="0.1003"/>
            <maximum-packet-spacing-ends-inclusive value="8138.4448"/>
        </packet-statistics>
    </pid-properties>

<errors>
  <error pid="0x01E1" packet="661" time="00:00:00.264" code="6-13w" msg="Video GOP length was 12; the configured minimum is 15."/>
  <error pid="0x01E1" packet="1973" time="00:00:00.790" code="6-13w" msg="Video GOP length was 14; the configured minimum is 15."/>
  <error pid="0x01E1" packet="3224" time="00:00:01.292" code="6-13w" msg="Video GOP length was 14; the configured minimum is 15."/>
  <error pid="0x01E1" packet="4472" time="00:00:01.793" code="6-13w" msg="Video GOP length was 14; the configured minimum is 15."/>
  <error pid="0x01E1" packet="5718" time="00:00:02.292" code="6-13w" msg="Video GOP length is less than the configured minimum.
This error was detected 129 more times in this PID after packet 5718"/>
  <error pid="0x01E1" code="6-53e" msg="Missing CEA 708 CC1 data; configuration requires."/>
</errors>

</tsa-report>
"""
        content.manzanitaCGMS = "Source:Digital SCMS:CopyNever APS:None"
        content.manzanitaVChip = "TV-MA, L"
        content.manzanitaRuntime = 1750

        Content generatedContent = new Content(centralId: 5, original: false, name: "batman-1.mpg",
                checksum: "123456789", audioType: "Stereo", dataServiceXml: "</xml>",
                size: 100, runtime: 100, contentType: "PREVIEW", asset: happyAsset,
                profile: "Pro 7", contentState: ContentState.COMPLETE.toString())

        generatedContent.manzanitaResult = ManzanitaResultStates.FAILED.toString()
        generatedContent.manzanitaReport = """
<tsa-report
        version="1.0.0"
        license-serno="CC0825"
        license-username="Clearleap, Inc."
        date="Fri Jun 17 16:48:18 2011"
        file="/usr/local/clearleap/cache/dev-central2/accounts/2572/assets/1285573/Entourage_S4_08_comp_HBOD000000112574_P7D-1356313.mpg">
    <configuration>
        <analysis-mode value="Auto (MPEG)"/>
        tsa-config 2
        Abort-Limit: none
        About: {CableLabs specification for content containing High Definition
        video and one audio stream.
        Document OC-SP-CEP3.0-I01-100827 (August 27, 2010)
        }
        Use-ATSC-Buffer-Model: on
        PMT-In-Single-Packet: warn
        Warn-On-Missing-AC3-Descriptor: warn
        Max-PCR-Interval: 40.0 warn
        Disc-In-First-PCR-Only: err
        I-Pic-Has-RAI: err
        Max-B-Frame: 2
        Video-All-Pics-Frame: err
        Video-No-Scalable-Ext: err
        I-Pic-Has-DTS: err
        Video-Starts-With-Seq-Start: err
        GOP-First-Is-Closed: err
        GOP-Size-15-or-12: warn
        Video-One-AU-Per-PES: err
        Video-PES-Payload-Has-PCR: err
        Video-AU-Starts-PES: err
        Audio-Starts-With-AU: err
        Audio-Has-No-Partial-AU: err
        Packet-Size: 188
        PAT-Never-Changes: err
        PMT-Never-Changes: err
        PSI-Definition-Precedes-PIDs: err
        No-Partial-Transport-Packets: err
        Max-Rate-PID0-PMT-Vid-Aud: 38000000
        SCTE20-Precedes-ATSC53-CC: err
        Minimum-GOP-Length-Error-Level: warn
        Table [
        PID: 0
        Table-ID: 0
        Maximum-Table-Interval: 250
        ]
        Table [
        Table-ID: 2
        Maximum-Table-Interval: 250
        ]
        Program [
        Video [
        Require-CEA708-DTVCC1: on
        Require-CEA708-CC1: on
        Stream-Type: 2
        Stream-Type-Alternate: 128
        Properties: [
        profile = 4
        level = 4
        aspect_ratio = 3
        chroma_format = 1
        (vertical_size = 1080 horizontal_size = 1920
        (progressive_sequence = 0
        frame_rate = 4) |
        (progressive_sequence = 1
        (frame_rate = 1) | (frame_rate = 2))) |
        (vertical_size = 720 horizontal_size = 1280
        frame_rate = 7
        progressive_sequence = 1)
        ]
        Minimum-GOP-Length: 15
        Maximum-GOP-Length: 15
        PID: 481
        ]
        Audio [
        Stream-Type: 129
        Properties: [
        type = 6
        (channel_mode = 2 bit_rate = 384000) |
        (channel_mode = 7 bit_rate = 384000)
        sample_rate = 48000
        ]
        PID: 482
        ]
        PMT-PID: 480
        PCR-PID: 481
        ]
        end-config
    </configuration>

    <stream-information>
        <transport-rate value="15000000.0"/>
        <duration value="00:27:42.413"/>
        <file-size value="3117026088"/>
        <transport-packet-interval value="188"/>
        <packets-analyzed value="16579926"/>
        <total-errors value="0"/>
        <total-warnings value="3320"/>
    </stream-information>

    <multiplex-summary>
        <pat version="0" pid="0">
            <program program_number="2" pid="0x01E0">
                <pmt version="0" pcr-pid="0x01E1">
                    <stream pid="0x01E2" type="Dolby AC-3 Audio">
                        <descriptor tag="0x05" name="registration_descriptor"/>
                        <descriptor tag="0x0A" name="ISO_639_language_descriptor"/>
                        <descriptor tag="0x81" name="ac3_audio_descriptor"/>
                    </stream>
                    <stream pid="0x01E1" type="MPEG-2 Video">
                    </stream>
                </pmt>
            </program>
        </pat>
    </multiplex-summary>

    <pid-summary>
        <pid-info pid="0x0000" content="PAT"
                  npkt="13601" pkt-rate="12305" payld-rate="12043"/>
        <pid-info pid="0x01E0" content="PMT" programs="2"
                  npkt="13601" pkt-rate="12305" payld-rate="12043"/>
        <pid-info pid="0x01E1" content="MPEG-2 Video, PCR" programs="2"
                  npkt="15844773" pkt-rate="14334901" payld-rate="13999998"
                  errors="0" warnings="3320"/>
        <pid-info pid="0x01E2" content="Dolby AC-3 Audio" programs="2"
                  npkt="466974" pkt-rate="422475" payld-rate="383524"/>
        <pid-info pid="0x1FFF" content="Null"
                  npkt="240977" pkt-rate="218014" payld-rate="218014"/>
    </pid-summary>

    <pid-properties>
        <pid value="0x0000 (0)"/>
        <content value="PAT"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <program-association-table-information>
            <table_id value="0x00"/>
            <version_number value="0"/>
            <transport_stream_id value="0x0001 (1)"/>
        </program-association-table-information>
        <packet-statistics>
            <number-of-packets value="13601"/>
            <first-seen-in-packet-number value="1" time="00:00:00.000"/>
            <last-seen-in-packet-number value="16578712" time="00:27:42.292"/>
            <average-transport-packet-rate value="12305"/>
            <average-payload-rate value="12043"/>
            <average-packet-spacing value="122.2274"/>
            <minimum-packet-spacing value="122.2251"/>
            <packet-number-at-minimum-spacing value="1220" time="00:00:00.122"/>
            <maximum-packet-spacing value="122.3253"/>
            <packet-number-at-maximum-spacing value="7316" time="00:00:00.733"/>
            <minimum-packet-spacing-ends-inclusive value="121.8240"/>
            <maximum-packet-spacing-ends-inclusive value="122.3253"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="13601"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="0"/>
            <number-of-packets-random_access_indicator-is-1 value="0"/>
            <splice_countdown-fields-seen value="No"/>
        </adaptation-field-information>
    </pid-properties>

    <pid-properties>
        <pid value="0x01E0 (480)"/>
        <content value="PMT"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <program-information>
            <pmt-pid-for-program_number value="2"/>
            <listed-in-pat-version_number value="0"/>
        </program-information>
        <program-map-table-information>
            <table_id value="0x02"/>
            <program_number value="2"/>
            <version_number value="0"/>
            <pcr-pid value="0x01E1 (481)"/>
            <section_length value="41"/>
        </program-map-table-information>
        <packet-statistics>
            <number-of-packets value="13601"/>
            <first-seen-in-packet-number value="2" time="00:00:00.000"/>
            <last-seen-in-packet-number value="16578713" time="00:27:42.292"/>
            <average-transport-packet-rate value="12305"/>
            <average-payload-rate value="12043"/>
            <average-packet-spacing value="122.2274"/>
            <minimum-packet-spacing value="122.2251"/>
            <packet-number-at-minimum-spacing value="1221" time="00:00:00.122"/>
            <maximum-packet-spacing value="122.3253"/>
            <packet-number-at-maximum-spacing value="7317" time="00:00:00.733"/>
            <minimum-packet-spacing-ends-inclusive value="0.1003"/>
            <maximum-packet-spacing-ends-inclusive value="122.3253"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="13601"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="0"/>
            <number-of-packets-random_access_indicator-is-1 value="0"/>
            <splice_countdown-fields-seen value="No"/>
        </adaptation-field-information>
    </pid-properties>

    <pid-properties>
        <pid value="0x01E1 (481)"/>
        <content value="MPEG-2 Video, PCR"/>
        <total-errors value="0"/>
        <total-warnings value="3320"/>
        <program-information>
            <member-of-program_number value="2"/>
            <listed-in-pmt-version_number value="0"/>
            <stream_type value="0x02 (H.262 | 13818-2 Video | 11172-2 Constrained Video)"/>
            <listed-as-pcr-pid value="Yes"/>
        </program-information>
        <pcr-statistics>
            <number-of-pcrs-seen value="118328"/>
            <average-pcr-interval value="14.0315"/>
            <minimum-pcr-interval value="0.1003"/>
            <packet-number-at-minimum-pcr-interval value="81142" time="00:00:08.135"/>
            <maximum-pcr-interval value="35.9957"/>
            <packet-number-at-maximum-pcr-interval value="7639694" time="00:12:46.006"/>
            <pcr-jitter-magnitude value="30"/>
        </pcr-statistics>
        <packet-statistics>
            <number-of-packets value="15844773"/>
            <first-seen-in-packet-number value="3" time="00:00:00.000"/>
            <last-seen-in-packet-number value="16559069" time="00:27:40.322"/>
            <average-transport-packet-rate value="14334901"/>
            <average-payload-rate value="13999998"/>
            <average-packet-spacing value="0.1048"/>
            <minimum-packet-spacing value="0.1003"/>
            <packet-number-at-minimum-spacing value="4" time="00:00:00.000"/>
            <maximum-packet-spacing value="23.9637"/>
            <packet-number-at-maximum-spacing value="11225391" time="00:18:45.532"/>
            <minimum-packet-spacing-ends-inclusive value="0.1003"/>
            <maximum-packet-spacing-ends-inclusive value="2091.3621"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="15844773"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="1"/>
            <number-of-packets-random_access_indicator-is-1 value="3318"/>
            <splice_countdown-fields-seen value="No"/>
            <noemptyaf value="Yes"/>
        </adaptation-field-information>
        <pes-information>
            <number-of-pes-headers-seen value="49763"/>
            <pes-stream_id value="0xE0 (224, MPEG Video)"/>
            <number-of-packets-pes_scrambling_control-is-00 value="49763"/>
            <pes-copyright-set value="No"/>
            <number-of-packets-pes_priority-is-1 value="0"/>
            <number-of-ptss-seen value="49763"/>
            <average-pts-interval value="33.3646"/>
            <average-es-buffer-level value="604817"/>
            <minimum-es-buffer-level value="127807"/>
            <packet-number-at-minimum-es-buffer-level value="654323" time="00:01:05.606"/>
            <maximum-es-buffer-level value="718848"/>
            <packet-number-at-maximum-es-buffer-level value="4407" time="00:00:00.441"/>
            <pesalign value="Yes"/>
        </pes-information>
        <video-elementary-stream-information>
            <mpeg-2-video-profile value="Main"/>
            <mpeg-2-video-level value="High"/>
            <mpeg-chroma-format value="4:2:0"/>
            <closed-caption-types-detected value="ATSC (EIA608, EIA708)"/>
            <a-53-cc-channels-detected value="CC1, XDS"/>
            <a-53-dtvcc-services-detected value="1, 2"/>
            <number-of-video-access-units-seen value="49763"/>
            <minimum-number-of-non-i-pictures-between-two-i-pictures value="12"/>
            <maximum-number-of-non-i-pictures-between-two-i-pictures value="14"/>
            <average-number-of-non-i-pictures-between-two-i-pictures value="14"/>
            <percent-of-all-pictures-that-are-p-pictures value="26"/>
            <percent-of-all-pictures-that-are-b-pictures value="66"/>
            <maximum-number-of-b-frames-between-i-or-p-frames value="2"/>
            <video-bit-rate value="14000000"/>
            <vbv-method value="Off"/>
            <vbv-size value="5750784"/>
            <video-multiplexing-buffer-size value="53333"/>
            <video-es-buffer-size value="718848"/>
            <minimum-decode-delay value="0.1050"/>
            <maximum-decode-delay value="0.5229"/>
            <mb-to-eb-transfer-method value="Leak"/>
            <video-frame-rate value="29.9700"/>
            <video-in-film-mode value="No"/>
            <progressive-sequence value="No"/>
            <horizontal-size value="1920"/>
            <vertical-size value="1080"/>
            <aspect-ratio value="16:9"/>
            <sample-aspect-ratio value="1.0000"/>
            <quadbyte value="Yes"/>
            <initial-timecode value="0:00:00:00"/>
            <final-timecode value="0:27:38:21"/>
        </video-elementary-stream-information>

        <xds-information>
            <prograting value="TV-MA L"/>
            <cgms value="Source:Digital SCMS:CopyNever APS:None"/>
        </xds-information>
        <errors>
            <error pid="0x01E1" packet="3182" time="00:00:00.318" code="6-13w"
                   msg="Video GOP length was 12; the configured minimum is 15."/>
            <error pid="0x01E1" packet="8295" time="00:00:00.831" code="6-13w"
                   msg="Video GOP length was 14; the configured minimum is 15."/>
            <error pid="0x01E1" packet="15445" time="00:00:01.548" code="6-13w"
                   msg="Video GOP length was 14; the configured minimum is 15."/>
            <error pid="0x01E1" packet="20010" time="00:00:02.006" code="6-13w"
                   msg="Video GOP length was 14; the configured minimum is 15."/>
            <error pid="0x01E1" packet="25055" time="00:00:02.512" code="6-13w" msg="Video GOP length is less than the configured minimum.
This error was detected 3312 more times in this PID after packet 25055"/>
            <error pid="0x01E1" packet="182128" time="00:00:18.261" code="4-87w"
                   msg="The CEA708 DTVCC packet sequence number was 2, expected 4."/>
            <error pid="0x01E1" packet="658451" time="00:01:06.020" code="4-87w"
                   msg="The CEA708 DTVCC packet sequence number was 0, expected 2."/>
            <error pid="0x01E1" packet="16192511" time="00:27:03.569" code="4-87w"
                   msg="The CEA708 DTVCC packet sequence number was 0, expected 3."/>
        </errors>
    </pid-properties>

    <pid-properties>
        <pid value="0x01E2 (482)"/>
        <content value="Dolby AC-3 Audio"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <program-information>
            <member-of-program_number value="2"/>
            <listed-in-pmt-version_number value="0"/>
            <stream_type value="0x81 (129, AC3 Audio)"/>
            <associated-pcr-pid value="0x01E1 (481)"/>
            <registration_descriptor>
                <tag value="0x05"/>
                <length value="4"/>
                <format_identifier value="0x41432D33 (AC-3)"/>
                <additional_identification_info value="none"/>
            </registration_descriptor>
            <iso_639_language_descriptor>
                <tag value="0x0A (10)"/>
                <length value="4"/>
                <iso_639_language_code value="0x656E67 (eng)"/>
                <audio_type value="0 (Undefined)"/>
            </iso_639_language_descriptor>
            <ac3_audio_descriptor>
                <tag value="0x81 (129)"/>
                <length value="4"/>
                <sample_rate_code value="0 (48 kHz)"/>
                <bsid value="8"/>
                <bit_rate_limit value="0"/>
                <bit_rate_code value="14 (384 kbps)"/>
                <surround_mode value="0 (Not indicated)"/>
                <bsmod value="0 (Main: Complete Main)"/>
                <num_channels value="2 (2/0)"/>
                <full_svc value="1"/>
                <langcod value="0"/>
            </ac3_audio_descriptor>
        </program-information>
        <packet-statistics>
            <number-of-packets value="466974"/>
            <first-seen-in-packet-number value="12" time="00:00:00.001"/>
            <last-seen-in-packet-number value="16562164" time="00:27:40.632"/>
            <average-transport-packet-rate value="422475"/>
            <average-payload-rate value="383524"/>
            <average-packet-spacing value="3.5562"/>
            <minimum-packet-spacing value="1.1029"/>
            <packet-number-at-minimum-spacing value="23" time="00:00:00.002"/>
            <maximum-packet-spacing value="326.2677"/>
            <packet-number-at-maximum-spacing value="3409" time="00:00:00.341"/>
            <minimum-packet-spacing-ends-inclusive value="1.1029"/>
            <maximum-packet-spacing-ends-inclusive value="1781.0368"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="466974"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="0"/>
            <number-of-packets-random_access_indicator-is-1 value="0"/>
            <splice_countdown-fields-seen value="No"/>
        </adaptation-field-information>
        <pes-information>
            <number-of-pes-headers-seen value="51886"/>
            <pes-stream_id value="0xBD (189, Private stream 1)"/>
            <number-of-packets-pes_scrambling_control-is-00 value="51886"/>
            <pes-copyright-set value="No"/>
            <number-of-packets-pes_priority-is-1 value="0"/>
            <number-of-ptss-seen value="51886"/>
            <average-pts-interval value="32.0054"/>
            <average-es-buffer-level value="1695"/>
            <minimum-es-buffer-level value="920"/>
            <packet-number-at-minimum-es-buffer-level value="3409" time="00:00:00.341"/>
            <maximum-es-buffer-level value="2470"/>
            <packet-number-at-maximum-es-buffer-level value="3409" time="00:00:00.341"/>
        </pes-information>
        <audio-elementary-stream-information>
            <audio-type value="Dolby AC-3"/>
            <number-of-audio-access-units-seen value="51886"/>
            <average-number-of-audio-frames-per-pes-packet value="1"/>
            <sample-rate value="48000"/>
            <bit-rate value="384000"/>
            <number-and-location-of-channels value="Stereo channels: L, R: 2/0"/>
            <ac3-audio-service-type value="Main audio service: Complete Main (CM)"/>
            <low-frequency-effects-flag value="0"/>
            <nominal-frame-size value="1536"/>
            <dialogue-normalization value="-16"/>
            <main-audio-buffer-size value="2592"/>
        </audio-elementary-stream-information>
    </pid-properties>

    <pid-properties>
        <pid value="0x1FFF (8191)"/>
        <content value="Null"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <packet-statistics>
            <number-of-packets value="240977"/>
            <first-seen-in-packet-number value="4265" time="00:00:00.427"/>
            <last-seen-in-packet-number value="16579926" time="00:27:42.413"/>
            <average-transport-packet-rate value="218014"/>
            <average-payload-rate value="218014"/>
            <average-packet-spacing value="6.8969"/>
            <minimum-packet-spacing value="0.1003"/>
            <packet-number-at-minimum-spacing value="4266" time="00:00:00.427"/>
            <maximum-packet-spacing value="8138.4448"/>
            <packet-number-at-maximum-spacing value="14548160" time="00:24:18.695"/>
            <minimum-packet-spacing-ends-inclusive value="0.1003"/>
            <maximum-packet-spacing-ends-inclusive value="8138.4448"/>
        </packet-statistics>
    </pid-properties>

<errors>
  <error pid="0x01E1" packet="661" time="00:00:00.264" code="6-13w" msg="Video GOP length was 12; the configured minimum is 15."/>
  <error pid="0x01E1" packet="1973" time="00:00:00.790" code="6-13w" msg="Video GOP length was 14; the configured minimum is 15."/>
  <error pid="0x01E1" packet="3224" time="00:00:01.292" code="6-13w" msg="Video GOP length was 14; the configured minimum is 15."/>
  <error pid="0x01E1" packet="4472" time="00:00:01.793" code="6-13w" msg="Video GOP length was 14; the configured minimum is 15."/>
  <error pid="0x01E1" packet="5718" time="00:00:02.292" code="6-13w" msg="Video GOP length is less than the configured minimum.
This error was detected 129 more times in this PID after packet 5718"/>
  <error pid="0x01E1" code="6-53e" msg="Missing CEA 708 CC1 data; configuration requires."/>
  <error pid="0x01E1" code="6-54e" msg="Missing CEA 708 CC1 data; configuration requires."/>
  <error pid="0x01E1" code="6-55e" msg="Missing CEA 708 CC1 data; configuration requires."/>
  <error pid="0x01E1" code="6-64e" msg="Missing CEA 708 CC1 data; configuration requires."/>
</errors>

</tsa-report>
"""
        generatedContent.manzanitaCGMS = "Source:Digital SCMS:CopyNever APS:None"
        generatedContent.manzanitaVChip = "TV-MA, L"
        generatedContent.manzanitaRuntime = 1750

        happyAsset.contents.add(content)
        happyAsset.contents.add(generatedContent)
        happyAsset.save(flush: true)


        assertEquals(ManzanitaResultStates.FAILED.toString(), content.manzanitaResult)
        assertEquals(ManzanitaResultStates.FAILED.toString(), generatedContent.manzanitaResult)

        assetService.updatePreviewContentsManzanitaResult(happyAsset)

        assertEquals(ManzanitaResultStates.PASSED.toString(), content.manzanitaResult)
        assertEquals(ManzanitaResultStates.FAILED.toString(), generatedContent.manzanitaResult)

    }

    /**
     * 1 original preview content with 6-53e, 2 generated content with both cc codes.
     */
    void testOriginalPreviewContentHasCCErrorCodeMultipleGeneratedHasBothCCErrorCodes() {

        //Original preview
        Content content = new Content(centralId: 4, original: true, name: "batman.mpg",
                checksum: "123456789", audioType: "Stereo", dataServiceXml: "</xml>",
                size: 100, runtime: 100, contentType: "PREVIEW", asset: happyAsset,
                profile: "Pro 7", contentState: ContentState.COMPLETE.toString())

        content.manzanitaResult = ManzanitaResultStates.FAILED.toString()
        content.manzanitaReport = """
<tsa-report
        version="1.0.0"
        license-serno="CC0825"
        license-username="Clearleap, Inc."
        date="Fri Jun 17 16:48:18 2011"
        file="/usr/local/clearleap/cache/dev-central2/accounts/2572/assets/1285573/Entourage_S4_08_comp_HBOD000000112574_P7D-1356313.mpg">
    <configuration>
        <analysis-mode value="Auto (MPEG)"/>
        tsa-config 2
        Abort-Limit: none
        About: {CableLabs specification for content containing High Definition
        video and one audio stream.
        Document OC-SP-CEP3.0-I01-100827 (August 27, 2010)
        }
        Use-ATSC-Buffer-Model: on
        PMT-In-Single-Packet: warn
        Warn-On-Missing-AC3-Descriptor: warn
        Max-PCR-Interval: 40.0 warn
        Disc-In-First-PCR-Only: err
        I-Pic-Has-RAI: err
        Max-B-Frame: 2
        Video-All-Pics-Frame: err
        Video-No-Scalable-Ext: err
        I-Pic-Has-DTS: err
        Video-Starts-With-Seq-Start: err
        GOP-First-Is-Closed: err
        GOP-Size-15-or-12: warn
        Video-One-AU-Per-PES: err
        Video-PES-Payload-Has-PCR: err
        Video-AU-Starts-PES: err
        Audio-Starts-With-AU: err
        Audio-Has-No-Partial-AU: err
        Packet-Size: 188
        PAT-Never-Changes: err
        PMT-Never-Changes: err
        PSI-Definition-Precedes-PIDs: err
        No-Partial-Transport-Packets: err
        Max-Rate-PID0-PMT-Vid-Aud: 38000000
        SCTE20-Precedes-ATSC53-CC: err
        Minimum-GOP-Length-Error-Level: warn
        Table [
        PID: 0
        Table-ID: 0
        Maximum-Table-Interval: 250
        ]
        Table [
        Table-ID: 2
        Maximum-Table-Interval: 250
        ]
        Program [
        Video [
        Require-CEA708-DTVCC1: on
        Require-CEA708-CC1: on
        Stream-Type: 2
        Stream-Type-Alternate: 128
        Properties: [
        profile = 4
        level = 4
        aspect_ratio = 3
        chroma_format = 1
        (vertical_size = 1080 horizontal_size = 1920
        (progressive_sequence = 0
        frame_rate = 4) |
        (progressive_sequence = 1
        (frame_rate = 1) | (frame_rate = 2))) |
        (vertical_size = 720 horizontal_size = 1280
        frame_rate = 7
        progressive_sequence = 1)
        ]
        Minimum-GOP-Length: 15
        Maximum-GOP-Length: 15
        PID: 481
        ]
        Audio [
        Stream-Type: 129
        Properties: [
        type = 6
        (channel_mode = 2 bit_rate = 384000) |
        (channel_mode = 7 bit_rate = 384000)
        sample_rate = 48000
        ]
        PID: 482
        ]
        PMT-PID: 480
        PCR-PID: 481
        ]
        end-config
    </configuration>

    <stream-information>
        <transport-rate value="15000000.0"/>
        <duration value="00:27:42.413"/>
        <file-size value="3117026088"/>
        <transport-packet-interval value="188"/>
        <packets-analyzed value="16579926"/>
        <total-errors value="0"/>
        <total-warnings value="3320"/>
    </stream-information>

    <multiplex-summary>
        <pat version="0" pid="0">
            <program program_number="2" pid="0x01E0">
                <pmt version="0" pcr-pid="0x01E1">
                    <stream pid="0x01E2" type="Dolby AC-3 Audio">
                        <descriptor tag="0x05" name="registration_descriptor"/>
                        <descriptor tag="0x0A" name="ISO_639_language_descriptor"/>
                        <descriptor tag="0x81" name="ac3_audio_descriptor"/>
                    </stream>
                    <stream pid="0x01E1" type="MPEG-2 Video">
                    </stream>
                </pmt>
            </program>
        </pat>
    </multiplex-summary>

    <pid-summary>
        <pid-info pid="0x0000" content="PAT"
                  npkt="13601" pkt-rate="12305" payld-rate="12043"/>
        <pid-info pid="0x01E0" content="PMT" programs="2"
                  npkt="13601" pkt-rate="12305" payld-rate="12043"/>
        <pid-info pid="0x01E1" content="MPEG-2 Video, PCR" programs="2"
                  npkt="15844773" pkt-rate="14334901" payld-rate="13999998"
                  errors="0" warnings="3320"/>
        <pid-info pid="0x01E2" content="Dolby AC-3 Audio" programs="2"
                  npkt="466974" pkt-rate="422475" payld-rate="383524"/>
        <pid-info pid="0x1FFF" content="Null"
                  npkt="240977" pkt-rate="218014" payld-rate="218014"/>
    </pid-summary>

    <pid-properties>
        <pid value="0x0000 (0)"/>
        <content value="PAT"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <program-association-table-information>
            <table_id value="0x00"/>
            <version_number value="0"/>
            <transport_stream_id value="0x0001 (1)"/>
        </program-association-table-information>
        <packet-statistics>
            <number-of-packets value="13601"/>
            <first-seen-in-packet-number value="1" time="00:00:00.000"/>
            <last-seen-in-packet-number value="16578712" time="00:27:42.292"/>
            <average-transport-packet-rate value="12305"/>
            <average-payload-rate value="12043"/>
            <average-packet-spacing value="122.2274"/>
            <minimum-packet-spacing value="122.2251"/>
            <packet-number-at-minimum-spacing value="1220" time="00:00:00.122"/>
            <maximum-packet-spacing value="122.3253"/>
            <packet-number-at-maximum-spacing value="7316" time="00:00:00.733"/>
            <minimum-packet-spacing-ends-inclusive value="121.8240"/>
            <maximum-packet-spacing-ends-inclusive value="122.3253"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="13601"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="0"/>
            <number-of-packets-random_access_indicator-is-1 value="0"/>
            <splice_countdown-fields-seen value="No"/>
        </adaptation-field-information>
    </pid-properties>

    <pid-properties>
        <pid value="0x01E0 (480)"/>
        <content value="PMT"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <program-information>
            <pmt-pid-for-program_number value="2"/>
            <listed-in-pat-version_number value="0"/>
        </program-information>
        <program-map-table-information>
            <table_id value="0x02"/>
            <program_number value="2"/>
            <version_number value="0"/>
            <pcr-pid value="0x01E1 (481)"/>
            <section_length value="41"/>
        </program-map-table-information>
        <packet-statistics>
            <number-of-packets value="13601"/>
            <first-seen-in-packet-number value="2" time="00:00:00.000"/>
            <last-seen-in-packet-number value="16578713" time="00:27:42.292"/>
            <average-transport-packet-rate value="12305"/>
            <average-payload-rate value="12043"/>
            <average-packet-spacing value="122.2274"/>
            <minimum-packet-spacing value="122.2251"/>
            <packet-number-at-minimum-spacing value="1221" time="00:00:00.122"/>
            <maximum-packet-spacing value="122.3253"/>
            <packet-number-at-maximum-spacing value="7317" time="00:00:00.733"/>
            <minimum-packet-spacing-ends-inclusive value="0.1003"/>
            <maximum-packet-spacing-ends-inclusive value="122.3253"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="13601"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="0"/>
            <number-of-packets-random_access_indicator-is-1 value="0"/>
            <splice_countdown-fields-seen value="No"/>
        </adaptation-field-information>
    </pid-properties>

    <pid-properties>
        <pid value="0x01E1 (481)"/>
        <content value="MPEG-2 Video, PCR"/>
        <total-errors value="0"/>
        <total-warnings value="3320"/>
        <program-information>
            <member-of-program_number value="2"/>
            <listed-in-pmt-version_number value="0"/>
            <stream_type value="0x02 (H.262 | 13818-2 Video | 11172-2 Constrained Video)"/>
            <listed-as-pcr-pid value="Yes"/>
        </program-information>
        <pcr-statistics>
            <number-of-pcrs-seen value="118328"/>
            <average-pcr-interval value="14.0315"/>
            <minimum-pcr-interval value="0.1003"/>
            <packet-number-at-minimum-pcr-interval value="81142" time="00:00:08.135"/>
            <maximum-pcr-interval value="35.9957"/>
            <packet-number-at-maximum-pcr-interval value="7639694" time="00:12:46.006"/>
            <pcr-jitter-magnitude value="30"/>
        </pcr-statistics>
        <packet-statistics>
            <number-of-packets value="15844773"/>
            <first-seen-in-packet-number value="3" time="00:00:00.000"/>
            <last-seen-in-packet-number value="16559069" time="00:27:40.322"/>
            <average-transport-packet-rate value="14334901"/>
            <average-payload-rate value="13999998"/>
            <average-packet-spacing value="0.1048"/>
            <minimum-packet-spacing value="0.1003"/>
            <packet-number-at-minimum-spacing value="4" time="00:00:00.000"/>
            <maximum-packet-spacing value="23.9637"/>
            <packet-number-at-maximum-spacing value="11225391" time="00:18:45.532"/>
            <minimum-packet-spacing-ends-inclusive value="0.1003"/>
            <maximum-packet-spacing-ends-inclusive value="2091.3621"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="15844773"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="1"/>
            <number-of-packets-random_access_indicator-is-1 value="3318"/>
            <splice_countdown-fields-seen value="No"/>
            <noemptyaf value="Yes"/>
        </adaptation-field-information>
        <pes-information>
            <number-of-pes-headers-seen value="49763"/>
            <pes-stream_id value="0xE0 (224, MPEG Video)"/>
            <number-of-packets-pes_scrambling_control-is-00 value="49763"/>
            <pes-copyright-set value="No"/>
            <number-of-packets-pes_priority-is-1 value="0"/>
            <number-of-ptss-seen value="49763"/>
            <average-pts-interval value="33.3646"/>
            <average-es-buffer-level value="604817"/>
            <minimum-es-buffer-level value="127807"/>
            <packet-number-at-minimum-es-buffer-level value="654323" time="00:01:05.606"/>
            <maximum-es-buffer-level value="718848"/>
            <packet-number-at-maximum-es-buffer-level value="4407" time="00:00:00.441"/>
            <pesalign value="Yes"/>
        </pes-information>
        <video-elementary-stream-information>
            <mpeg-2-video-profile value="Main"/>
            <mpeg-2-video-level value="High"/>
            <mpeg-chroma-format value="4:2:0"/>
            <closed-caption-types-detected value="ATSC (EIA608, EIA708)"/>
            <a-53-cc-channels-detected value="CC1, XDS"/>
            <a-53-dtvcc-services-detected value="1, 2"/>
            <number-of-video-access-units-seen value="49763"/>
            <minimum-number-of-non-i-pictures-between-two-i-pictures value="12"/>
            <maximum-number-of-non-i-pictures-between-two-i-pictures value="14"/>
            <average-number-of-non-i-pictures-between-two-i-pictures value="14"/>
            <percent-of-all-pictures-that-are-p-pictures value="26"/>
            <percent-of-all-pictures-that-are-b-pictures value="66"/>
            <maximum-number-of-b-frames-between-i-or-p-frames value="2"/>
            <video-bit-rate value="14000000"/>
            <vbv-method value="Off"/>
            <vbv-size value="5750784"/>
            <video-multiplexing-buffer-size value="53333"/>
            <video-es-buffer-size value="718848"/>
            <minimum-decode-delay value="0.1050"/>
            <maximum-decode-delay value="0.5229"/>
            <mb-to-eb-transfer-method value="Leak"/>
            <video-frame-rate value="29.9700"/>
            <video-in-film-mode value="No"/>
            <progressive-sequence value="No"/>
            <horizontal-size value="1920"/>
            <vertical-size value="1080"/>
            <aspect-ratio value="16:9"/>
            <sample-aspect-ratio value="1.0000"/>
            <quadbyte value="Yes"/>
            <initial-timecode value="0:00:00:00"/>
            <final-timecode value="0:27:38:21"/>
        </video-elementary-stream-information>

        <xds-information>
            <prograting value="TV-MA L"/>
            <cgms value="Source:Digital SCMS:CopyNever APS:None"/>
        </xds-information>
        <errors>
            <error pid="0x01E1" packet="3182" time="00:00:00.318" code="6-13w"
                   msg="Video GOP length was 12; the configured minimum is 15."/>
            <error pid="0x01E1" packet="8295" time="00:00:00.831" code="6-13w"
                   msg="Video GOP length was 14; the configured minimum is 15."/>
            <error pid="0x01E1" packet="15445" time="00:00:01.548" code="6-13w"
                   msg="Video GOP length was 14; the configured minimum is 15."/>
            <error pid="0x01E1" packet="20010" time="00:00:02.006" code="6-13w"
                   msg="Video GOP length was 14; the configured minimum is 15."/>
            <error pid="0x01E1" packet="25055" time="00:00:02.512" code="6-13w" msg="Video GOP length is less than the configured minimum.
This error was detected 3312 more times in this PID after packet 25055"/>
            <error pid="0x01E1" packet="182128" time="00:00:18.261" code="4-87w"
                   msg="The CEA708 DTVCC packet sequence number was 2, expected 4."/>
            <error pid="0x01E1" packet="658451" time="00:01:06.020" code="4-87w"
                   msg="The CEA708 DTVCC packet sequence number was 0, expected 2."/>
            <error pid="0x01E1" packet="16192511" time="00:27:03.569" code="4-87w"
                   msg="The CEA708 DTVCC packet sequence number was 0, expected 3."/>
        </errors>
    </pid-properties>

    <pid-properties>
        <pid value="0x01E2 (482)"/>
        <content value="Dolby AC-3 Audio"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <program-information>
            <member-of-program_number value="2"/>
            <listed-in-pmt-version_number value="0"/>
            <stream_type value="0x81 (129, AC3 Audio)"/>
            <associated-pcr-pid value="0x01E1 (481)"/>
            <registration_descriptor>
                <tag value="0x05"/>
                <length value="4"/>
                <format_identifier value="0x41432D33 (AC-3)"/>
                <additional_identification_info value="none"/>
            </registration_descriptor>
            <iso_639_language_descriptor>
                <tag value="0x0A (10)"/>
                <length value="4"/>
                <iso_639_language_code value="0x656E67 (eng)"/>
                <audio_type value="0 (Undefined)"/>
            </iso_639_language_descriptor>
            <ac3_audio_descriptor>
                <tag value="0x81 (129)"/>
                <length value="4"/>
                <sample_rate_code value="0 (48 kHz)"/>
                <bsid value="8"/>
                <bit_rate_limit value="0"/>
                <bit_rate_code value="14 (384 kbps)"/>
                <surround_mode value="0 (Not indicated)"/>
                <bsmod value="0 (Main: Complete Main)"/>
                <num_channels value="2 (2/0)"/>
                <full_svc value="1"/>
                <langcod value="0"/>
            </ac3_audio_descriptor>
        </program-information>
        <packet-statistics>
            <number-of-packets value="466974"/>
            <first-seen-in-packet-number value="12" time="00:00:00.001"/>
            <last-seen-in-packet-number value="16562164" time="00:27:40.632"/>
            <average-transport-packet-rate value="422475"/>
            <average-payload-rate value="383524"/>
            <average-packet-spacing value="3.5562"/>
            <minimum-packet-spacing value="1.1029"/>
            <packet-number-at-minimum-spacing value="23" time="00:00:00.002"/>
            <maximum-packet-spacing value="326.2677"/>
            <packet-number-at-maximum-spacing value="3409" time="00:00:00.341"/>
            <minimum-packet-spacing-ends-inclusive value="1.1029"/>
            <maximum-packet-spacing-ends-inclusive value="1781.0368"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="466974"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="0"/>
            <number-of-packets-random_access_indicator-is-1 value="0"/>
            <splice_countdown-fields-seen value="No"/>
        </adaptation-field-information>
        <pes-information>
            <number-of-pes-headers-seen value="51886"/>
            <pes-stream_id value="0xBD (189, Private stream 1)"/>
            <number-of-packets-pes_scrambling_control-is-00 value="51886"/>
            <pes-copyright-set value="No"/>
            <number-of-packets-pes_priority-is-1 value="0"/>
            <number-of-ptss-seen value="51886"/>
            <average-pts-interval value="32.0054"/>
            <average-es-buffer-level value="1695"/>
            <minimum-es-buffer-level value="920"/>
            <packet-number-at-minimum-es-buffer-level value="3409" time="00:00:00.341"/>
            <maximum-es-buffer-level value="2470"/>
            <packet-number-at-maximum-es-buffer-level value="3409" time="00:00:00.341"/>
        </pes-information>
        <audio-elementary-stream-information>
            <audio-type value="Dolby AC-3"/>
            <number-of-audio-access-units-seen value="51886"/>
            <average-number-of-audio-frames-per-pes-packet value="1"/>
            <sample-rate value="48000"/>
            <bit-rate value="384000"/>
            <number-and-location-of-channels value="Stereo channels: L, R: 2/0"/>
            <ac3-audio-service-type value="Main audio service: Complete Main (CM)"/>
            <low-frequency-effects-flag value="0"/>
            <nominal-frame-size value="1536"/>
            <dialogue-normalization value="-16"/>
            <main-audio-buffer-size value="2592"/>
        </audio-elementary-stream-information>
    </pid-properties>

    <pid-properties>
        <pid value="0x1FFF (8191)"/>
        <content value="Null"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <packet-statistics>
            <number-of-packets value="240977"/>
            <first-seen-in-packet-number value="4265" time="00:00:00.427"/>
            <last-seen-in-packet-number value="16579926" time="00:27:42.413"/>
            <average-transport-packet-rate value="218014"/>
            <average-payload-rate value="218014"/>
            <average-packet-spacing value="6.8969"/>
            <minimum-packet-spacing value="0.1003"/>
            <packet-number-at-minimum-spacing value="4266" time="00:00:00.427"/>
            <maximum-packet-spacing value="8138.4448"/>
            <packet-number-at-maximum-spacing value="14548160" time="00:24:18.695"/>
            <minimum-packet-spacing-ends-inclusive value="0.1003"/>
            <maximum-packet-spacing-ends-inclusive value="8138.4448"/>
        </packet-statistics>
    </pid-properties>

<errors>
  <error pid="0x01E1" packet="661" time="00:00:00.264" code="6-13w" msg="Video GOP length was 12; the configured minimum is 15."/>
  <error pid="0x01E1" packet="1973" time="00:00:00.790" code="6-13w" msg="Video GOP length was 14; the configured minimum is 15."/>
  <error pid="0x01E1" packet="3224" time="00:00:01.292" code="6-13w" msg="Video GOP length was 14; the configured minimum is 15."/>
  <error pid="0x01E1" packet="4472" time="00:00:01.793" code="6-13w" msg="Video GOP length was 14; the configured minimum is 15."/>
  <error pid="0x01E1" packet="5718" time="00:00:02.292" code="6-13w" msg="Video GOP length is less than the configured minimum.
This error was detected 129 more times in this PID after packet 5718"/>
  <error pid="0x01E1" code="6-53e" msg="Missing CEA 708 CC1 data; configuration requires."/>
</errors>

</tsa-report>
"""
        content.manzanitaCGMS = "Source:Digital SCMS:CopyNever APS:None"
        content.manzanitaVChip = "TV-MA, L"
        content.manzanitaRuntime = 1750

        Content generatedContent1 = new Content(centralId: 5, original: false, name: "batman-1.mpg",
                checksum: "123456789", audioType: "Stereo", dataServiceXml: "</xml>",
                size: 100, runtime: 100, contentType: "PREVIEW", asset: happyAsset,
                profile: "Pro 7", contentState: ContentState.COMPLETE.toString())

        generatedContent1.manzanitaResult = ManzanitaResultStates.FAILED.toString()
        generatedContent1.manzanitaReport = """
<tsa-report
        version="1.0.0"
        license-serno="CC0825"
        license-username="Clearleap, Inc."
        date="Fri Jun 17 16:48:18 2011"
        file="/usr/local/clearleap/cache/dev-central2/accounts/2572/assets/1285573/Entourage_S4_08_comp_HBOD000000112574_P7D-1356313.mpg">
    <configuration>
        <analysis-mode value="Auto (MPEG)"/>
        tsa-config 2
        Abort-Limit: none
        About: {CableLabs specification for content containing High Definition
        video and one audio stream.
        Document OC-SP-CEP3.0-I01-100827 (August 27, 2010)
        }
        Use-ATSC-Buffer-Model: on
        PMT-In-Single-Packet: warn
        Warn-On-Missing-AC3-Descriptor: warn
        Max-PCR-Interval: 40.0 warn
        Disc-In-First-PCR-Only: err
        I-Pic-Has-RAI: err
        Max-B-Frame: 2
        Video-All-Pics-Frame: err
        Video-No-Scalable-Ext: err
        I-Pic-Has-DTS: err
        Video-Starts-With-Seq-Start: err
        GOP-First-Is-Closed: err
        GOP-Size-15-or-12: warn
        Video-One-AU-Per-PES: err
        Video-PES-Payload-Has-PCR: err
        Video-AU-Starts-PES: err
        Audio-Starts-With-AU: err
        Audio-Has-No-Partial-AU: err
        Packet-Size: 188
        PAT-Never-Changes: err
        PMT-Never-Changes: err
        PSI-Definition-Precedes-PIDs: err
        No-Partial-Transport-Packets: err
        Max-Rate-PID0-PMT-Vid-Aud: 38000000
        SCTE20-Precedes-ATSC53-CC: err
        Minimum-GOP-Length-Error-Level: warn
        Table [
        PID: 0
        Table-ID: 0
        Maximum-Table-Interval: 250
        ]
        Table [
        Table-ID: 2
        Maximum-Table-Interval: 250
        ]
        Program [
        Video [
        Require-CEA708-DTVCC1: on
        Require-CEA708-CC1: on
        Stream-Type: 2
        Stream-Type-Alternate: 128
        Properties: [
        profile = 4
        level = 4
        aspect_ratio = 3
        chroma_format = 1
        (vertical_size = 1080 horizontal_size = 1920
        (progressive_sequence = 0
        frame_rate = 4) |
        (progressive_sequence = 1
        (frame_rate = 1) | (frame_rate = 2))) |
        (vertical_size = 720 horizontal_size = 1280
        frame_rate = 7
        progressive_sequence = 1)
        ]
        Minimum-GOP-Length: 15
        Maximum-GOP-Length: 15
        PID: 481
        ]
        Audio [
        Stream-Type: 129
        Properties: [
        type = 6
        (channel_mode = 2 bit_rate = 384000) |
        (channel_mode = 7 bit_rate = 384000)
        sample_rate = 48000
        ]
        PID: 482
        ]
        PMT-PID: 480
        PCR-PID: 481
        ]
        end-config
    </configuration>

    <stream-information>
        <transport-rate value="15000000.0"/>
        <duration value="00:27:42.413"/>
        <file-size value="3117026088"/>
        <transport-packet-interval value="188"/>
        <packets-analyzed value="16579926"/>
        <total-errors value="0"/>
        <total-warnings value="3320"/>
    </stream-information>

    <multiplex-summary>
        <pat version="0" pid="0">
            <program program_number="2" pid="0x01E0">
                <pmt version="0" pcr-pid="0x01E1">
                    <stream pid="0x01E2" type="Dolby AC-3 Audio">
                        <descriptor tag="0x05" name="registration_descriptor"/>
                        <descriptor tag="0x0A" name="ISO_639_language_descriptor"/>
                        <descriptor tag="0x81" name="ac3_audio_descriptor"/>
                    </stream>
                    <stream pid="0x01E1" type="MPEG-2 Video">
                    </stream>
                </pmt>
            </program>
        </pat>
    </multiplex-summary>

    <pid-summary>
        <pid-info pid="0x0000" content="PAT"
                  npkt="13601" pkt-rate="12305" payld-rate="12043"/>
        <pid-info pid="0x01E0" content="PMT" programs="2"
                  npkt="13601" pkt-rate="12305" payld-rate="12043"/>
        <pid-info pid="0x01E1" content="MPEG-2 Video, PCR" programs="2"
                  npkt="15844773" pkt-rate="14334901" payld-rate="13999998"
                  errors="0" warnings="3320"/>
        <pid-info pid="0x01E2" content="Dolby AC-3 Audio" programs="2"
                  npkt="466974" pkt-rate="422475" payld-rate="383524"/>
        <pid-info pid="0x1FFF" content="Null"
                  npkt="240977" pkt-rate="218014" payld-rate="218014"/>
    </pid-summary>

    <pid-properties>
        <pid value="0x0000 (0)"/>
        <content value="PAT"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <program-association-table-information>
            <table_id value="0x00"/>
            <version_number value="0"/>
            <transport_stream_id value="0x0001 (1)"/>
        </program-association-table-information>
        <packet-statistics>
            <number-of-packets value="13601"/>
            <first-seen-in-packet-number value="1" time="00:00:00.000"/>
            <last-seen-in-packet-number value="16578712" time="00:27:42.292"/>
            <average-transport-packet-rate value="12305"/>
            <average-payload-rate value="12043"/>
            <average-packet-spacing value="122.2274"/>
            <minimum-packet-spacing value="122.2251"/>
            <packet-number-at-minimum-spacing value="1220" time="00:00:00.122"/>
            <maximum-packet-spacing value="122.3253"/>
            <packet-number-at-maximum-spacing value="7316" time="00:00:00.733"/>
            <minimum-packet-spacing-ends-inclusive value="121.8240"/>
            <maximum-packet-spacing-ends-inclusive value="122.3253"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="13601"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="0"/>
            <number-of-packets-random_access_indicator-is-1 value="0"/>
            <splice_countdown-fields-seen value="No"/>
        </adaptation-field-information>
    </pid-properties>

    <pid-properties>
        <pid value="0x01E0 (480)"/>
        <content value="PMT"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <program-information>
            <pmt-pid-for-program_number value="2"/>
            <listed-in-pat-version_number value="0"/>
        </program-information>
        <program-map-table-information>
            <table_id value="0x02"/>
            <program_number value="2"/>
            <version_number value="0"/>
            <pcr-pid value="0x01E1 (481)"/>
            <section_length value="41"/>
        </program-map-table-information>
        <packet-statistics>
            <number-of-packets value="13601"/>
            <first-seen-in-packet-number value="2" time="00:00:00.000"/>
            <last-seen-in-packet-number value="16578713" time="00:27:42.292"/>
            <average-transport-packet-rate value="12305"/>
            <average-payload-rate value="12043"/>
            <average-packet-spacing value="122.2274"/>
            <minimum-packet-spacing value="122.2251"/>
            <packet-number-at-minimum-spacing value="1221" time="00:00:00.122"/>
            <maximum-packet-spacing value="122.3253"/>
            <packet-number-at-maximum-spacing value="7317" time="00:00:00.733"/>
            <minimum-packet-spacing-ends-inclusive value="0.1003"/>
            <maximum-packet-spacing-ends-inclusive value="122.3253"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="13601"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="0"/>
            <number-of-packets-random_access_indicator-is-1 value="0"/>
            <splice_countdown-fields-seen value="No"/>
        </adaptation-field-information>
    </pid-properties>

    <pid-properties>
        <pid value="0x01E1 (481)"/>
        <content value="MPEG-2 Video, PCR"/>
        <total-errors value="0"/>
        <total-warnings value="3320"/>
        <program-information>
            <member-of-program_number value="2"/>
            <listed-in-pmt-version_number value="0"/>
            <stream_type value="0x02 (H.262 | 13818-2 Video | 11172-2 Constrained Video)"/>
            <listed-as-pcr-pid value="Yes"/>
        </program-information>
        <pcr-statistics>
            <number-of-pcrs-seen value="118328"/>
            <average-pcr-interval value="14.0315"/>
            <minimum-pcr-interval value="0.1003"/>
            <packet-number-at-minimum-pcr-interval value="81142" time="00:00:08.135"/>
            <maximum-pcr-interval value="35.9957"/>
            <packet-number-at-maximum-pcr-interval value="7639694" time="00:12:46.006"/>
            <pcr-jitter-magnitude value="30"/>
        </pcr-statistics>
        <packet-statistics>
            <number-of-packets value="15844773"/>
            <first-seen-in-packet-number value="3" time="00:00:00.000"/>
            <last-seen-in-packet-number value="16559069" time="00:27:40.322"/>
            <average-transport-packet-rate value="14334901"/>
            <average-payload-rate value="13999998"/>
            <average-packet-spacing value="0.1048"/>
            <minimum-packet-spacing value="0.1003"/>
            <packet-number-at-minimum-spacing value="4" time="00:00:00.000"/>
            <maximum-packet-spacing value="23.9637"/>
            <packet-number-at-maximum-spacing value="11225391" time="00:18:45.532"/>
            <minimum-packet-spacing-ends-inclusive value="0.1003"/>
            <maximum-packet-spacing-ends-inclusive value="2091.3621"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="15844773"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="1"/>
            <number-of-packets-random_access_indicator-is-1 value="3318"/>
            <splice_countdown-fields-seen value="No"/>
            <noemptyaf value="Yes"/>
        </adaptation-field-information>
        <pes-information>
            <number-of-pes-headers-seen value="49763"/>
            <pes-stream_id value="0xE0 (224, MPEG Video)"/>
            <number-of-packets-pes_scrambling_control-is-00 value="49763"/>
            <pes-copyright-set value="No"/>
            <number-of-packets-pes_priority-is-1 value="0"/>
            <number-of-ptss-seen value="49763"/>
            <average-pts-interval value="33.3646"/>
            <average-es-buffer-level value="604817"/>
            <minimum-es-buffer-level value="127807"/>
            <packet-number-at-minimum-es-buffer-level value="654323" time="00:01:05.606"/>
            <maximum-es-buffer-level value="718848"/>
            <packet-number-at-maximum-es-buffer-level value="4407" time="00:00:00.441"/>
            <pesalign value="Yes"/>
        </pes-information>
        <video-elementary-stream-information>
            <mpeg-2-video-profile value="Main"/>
            <mpeg-2-video-level value="High"/>
            <mpeg-chroma-format value="4:2:0"/>
            <closed-caption-types-detected value="ATSC (EIA608, EIA708)"/>
            <a-53-cc-channels-detected value="CC1, XDS"/>
            <a-53-dtvcc-services-detected value="1, 2"/>
            <number-of-video-access-units-seen value="49763"/>
            <minimum-number-of-non-i-pictures-between-two-i-pictures value="12"/>
            <maximum-number-of-non-i-pictures-between-two-i-pictures value="14"/>
            <average-number-of-non-i-pictures-between-two-i-pictures value="14"/>
            <percent-of-all-pictures-that-are-p-pictures value="26"/>
            <percent-of-all-pictures-that-are-b-pictures value="66"/>
            <maximum-number-of-b-frames-between-i-or-p-frames value="2"/>
            <video-bit-rate value="14000000"/>
            <vbv-method value="Off"/>
            <vbv-size value="5750784"/>
            <video-multiplexing-buffer-size value="53333"/>
            <video-es-buffer-size value="718848"/>
            <minimum-decode-delay value="0.1050"/>
            <maximum-decode-delay value="0.5229"/>
            <mb-to-eb-transfer-method value="Leak"/>
            <video-frame-rate value="29.9700"/>
            <video-in-film-mode value="No"/>
            <progressive-sequence value="No"/>
            <horizontal-size value="1920"/>
            <vertical-size value="1080"/>
            <aspect-ratio value="16:9"/>
            <sample-aspect-ratio value="1.0000"/>
            <quadbyte value="Yes"/>
            <initial-timecode value="0:00:00:00"/>
            <final-timecode value="0:27:38:21"/>
        </video-elementary-stream-information>

        <xds-information>
            <prograting value="TV-MA L"/>
            <cgms value="Source:Digital SCMS:CopyNever APS:None"/>
        </xds-information>
        <errors>
            <error pid="0x01E1" packet="3182" time="00:00:00.318" code="6-13w"
                   msg="Video GOP length was 12; the configured minimum is 15."/>
            <error pid="0x01E1" packet="8295" time="00:00:00.831" code="6-13w"
                   msg="Video GOP length was 14; the configured minimum is 15."/>
            <error pid="0x01E1" packet="15445" time="00:00:01.548" code="6-13w"
                   msg="Video GOP length was 14; the configured minimum is 15."/>
            <error pid="0x01E1" packet="20010" time="00:00:02.006" code="6-13w"
                   msg="Video GOP length was 14; the configured minimum is 15."/>
            <error pid="0x01E1" packet="25055" time="00:00:02.512" code="6-13w" msg="Video GOP length is less than the configured minimum.
This error was detected 3312 more times in this PID after packet 25055"/>
            <error pid="0x01E1" packet="182128" time="00:00:18.261" code="4-87w"
                   msg="The CEA708 DTVCC packet sequence number was 2, expected 4."/>
            <error pid="0x01E1" packet="658451" time="00:01:06.020" code="4-87w"
                   msg="The CEA708 DTVCC packet sequence number was 0, expected 2."/>
            <error pid="0x01E1" packet="16192511" time="00:27:03.569" code="4-87w"
                   msg="The CEA708 DTVCC packet sequence number was 0, expected 3."/>
        </errors>
    </pid-properties>

    <pid-properties>
        <pid value="0x01E2 (482)"/>
        <content value="Dolby AC-3 Audio"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <program-information>
            <member-of-program_number value="2"/>
            <listed-in-pmt-version_number value="0"/>
            <stream_type value="0x81 (129, AC3 Audio)"/>
            <associated-pcr-pid value="0x01E1 (481)"/>
            <registration_descriptor>
                <tag value="0x05"/>
                <length value="4"/>
                <format_identifier value="0x41432D33 (AC-3)"/>
                <additional_identification_info value="none"/>
            </registration_descriptor>
            <iso_639_language_descriptor>
                <tag value="0x0A (10)"/>
                <length value="4"/>
                <iso_639_language_code value="0x656E67 (eng)"/>
                <audio_type value="0 (Undefined)"/>
            </iso_639_language_descriptor>
            <ac3_audio_descriptor>
                <tag value="0x81 (129)"/>
                <length value="4"/>
                <sample_rate_code value="0 (48 kHz)"/>
                <bsid value="8"/>
                <bit_rate_limit value="0"/>
                <bit_rate_code value="14 (384 kbps)"/>
                <surround_mode value="0 (Not indicated)"/>
                <bsmod value="0 (Main: Complete Main)"/>
                <num_channels value="2 (2/0)"/>
                <full_svc value="1"/>
                <langcod value="0"/>
            </ac3_audio_descriptor>
        </program-information>
        <packet-statistics>
            <number-of-packets value="466974"/>
            <first-seen-in-packet-number value="12" time="00:00:00.001"/>
            <last-seen-in-packet-number value="16562164" time="00:27:40.632"/>
            <average-transport-packet-rate value="422475"/>
            <average-payload-rate value="383524"/>
            <average-packet-spacing value="3.5562"/>
            <minimum-packet-spacing value="1.1029"/>
            <packet-number-at-minimum-spacing value="23" time="00:00:00.002"/>
            <maximum-packet-spacing value="326.2677"/>
            <packet-number-at-maximum-spacing value="3409" time="00:00:00.341"/>
            <minimum-packet-spacing-ends-inclusive value="1.1029"/>
            <maximum-packet-spacing-ends-inclusive value="1781.0368"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="466974"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="0"/>
            <number-of-packets-random_access_indicator-is-1 value="0"/>
            <splice_countdown-fields-seen value="No"/>
        </adaptation-field-information>
        <pes-information>
            <number-of-pes-headers-seen value="51886"/>
            <pes-stream_id value="0xBD (189, Private stream 1)"/>
            <number-of-packets-pes_scrambling_control-is-00 value="51886"/>
            <pes-copyright-set value="No"/>
            <number-of-packets-pes_priority-is-1 value="0"/>
            <number-of-ptss-seen value="51886"/>
            <average-pts-interval value="32.0054"/>
            <average-es-buffer-level value="1695"/>
            <minimum-es-buffer-level value="920"/>
            <packet-number-at-minimum-es-buffer-level value="3409" time="00:00:00.341"/>
            <maximum-es-buffer-level value="2470"/>
            <packet-number-at-maximum-es-buffer-level value="3409" time="00:00:00.341"/>
        </pes-information>
        <audio-elementary-stream-information>
            <audio-type value="Dolby AC-3"/>
            <number-of-audio-access-units-seen value="51886"/>
            <average-number-of-audio-frames-per-pes-packet value="1"/>
            <sample-rate value="48000"/>
            <bit-rate value="384000"/>
            <number-and-location-of-channels value="Stereo channels: L, R: 2/0"/>
            <ac3-audio-service-type value="Main audio service: Complete Main (CM)"/>
            <low-frequency-effects-flag value="0"/>
            <nominal-frame-size value="1536"/>
            <dialogue-normalization value="-16"/>
            <main-audio-buffer-size value="2592"/>
        </audio-elementary-stream-information>
    </pid-properties>

    <pid-properties>
        <pid value="0x1FFF (8191)"/>
        <content value="Null"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <packet-statistics>
            <number-of-packets value="240977"/>
            <first-seen-in-packet-number value="4265" time="00:00:00.427"/>
            <last-seen-in-packet-number value="16579926" time="00:27:42.413"/>
            <average-transport-packet-rate value="218014"/>
            <average-payload-rate value="218014"/>
            <average-packet-spacing value="6.8969"/>
            <minimum-packet-spacing value="0.1003"/>
            <packet-number-at-minimum-spacing value="4266" time="00:00:00.427"/>
            <maximum-packet-spacing value="8138.4448"/>
            <packet-number-at-maximum-spacing value="14548160" time="00:24:18.695"/>
            <minimum-packet-spacing-ends-inclusive value="0.1003"/>
            <maximum-packet-spacing-ends-inclusive value="8138.4448"/>
        </packet-statistics>
    </pid-properties>

<errors>
  <error pid="0x01E1" packet="661" time="00:00:00.264" code="6-13w" msg="Video GOP length was 12; the configured minimum is 15."/>
  <error pid="0x01E1" packet="1973" time="00:00:00.790" code="6-13w" msg="Video GOP length was 14; the configured minimum is 15."/>
  <error pid="0x01E1" packet="3224" time="00:00:01.292" code="6-13w" msg="Video GOP length was 14; the configured minimum is 15."/>
  <error pid="0x01E1" packet="4472" time="00:00:01.793" code="6-13w" msg="Video GOP length was 14; the configured minimum is 15."/>
  <error pid="0x01E1" packet="5718" time="00:00:02.292" code="6-13w" msg="Video GOP length is less than the configured minimum.
This error was detected 129 more times in this PID after packet 5718"/>
  <error pid="0x01E1" code="6-53e" msg="Missing CEA 708 CC1 data; configuration requires."/>
  <error pid="0x01E1" code="6-54e" msg="Missing CEA 708 CC1 data; configuration requires."/>
</errors>

</tsa-report>
"""
        generatedContent1.manzanitaCGMS = "Source:Digital SCMS:CopyNever APS:None"
        generatedContent1.manzanitaVChip = "TV-MA, L"
        generatedContent1.manzanitaRuntime = 1750

        Content generatedContent2 = new Content(centralId: 7, original: false, name: "batman-2.mpg",
                checksum: "123456789", audioType: "Stereo", dataServiceXml: "</xml>",
                size: 100, runtime: 100, contentType: "PREVIEW", asset: happyAsset,
                profile: "Pro 7", contentState: ContentState.COMPLETE.toString())

        generatedContent2.manzanitaResult = ManzanitaResultStates.FAILED.toString()
        generatedContent2.manzanitaReport = """
<tsa-report
        version="1.0.0"
        license-serno="CC0825"
        license-username="Clearleap, Inc."
        date="Fri Jun 17 16:48:18 2011"
        file="/usr/local/clearleap/cache/dev-central2/accounts/2572/assets/1285573/Entourage_S4_08_comp_HBOD000000112574_P7D-1356313.mpg">
    <configuration>
        <analysis-mode value="Auto (MPEG)"/>
        tsa-config 2
        Abort-Limit: none
        About: {CableLabs specification for content containing High Definition
        video and one audio stream.
        Document OC-SP-CEP3.0-I01-100827 (August 27, 2010)
        }
        Use-ATSC-Buffer-Model: on
        PMT-In-Single-Packet: warn
        Warn-On-Missing-AC3-Descriptor: warn
        Max-PCR-Interval: 40.0 warn
        Disc-In-First-PCR-Only: err
        I-Pic-Has-RAI: err
        Max-B-Frame: 2
        Video-All-Pics-Frame: err
        Video-No-Scalable-Ext: err
        I-Pic-Has-DTS: err
        Video-Starts-With-Seq-Start: err
        GOP-First-Is-Closed: err
        GOP-Size-15-or-12: warn
        Video-One-AU-Per-PES: err
        Video-PES-Payload-Has-PCR: err
        Video-AU-Starts-PES: err
        Audio-Starts-With-AU: err
        Audio-Has-No-Partial-AU: err
        Packet-Size: 188
        PAT-Never-Changes: err
        PMT-Never-Changes: err
        PSI-Definition-Precedes-PIDs: err
        No-Partial-Transport-Packets: err
        Max-Rate-PID0-PMT-Vid-Aud: 38000000
        SCTE20-Precedes-ATSC53-CC: err
        Minimum-GOP-Length-Error-Level: warn
        Table [
        PID: 0
        Table-ID: 0
        Maximum-Table-Interval: 250
        ]
        Table [
        Table-ID: 2
        Maximum-Table-Interval: 250
        ]
        Program [
        Video [
        Require-CEA708-DTVCC1: on
        Require-CEA708-CC1: on
        Stream-Type: 2
        Stream-Type-Alternate: 128
        Properties: [
        profile = 4
        level = 4
        aspect_ratio = 3
        chroma_format = 1
        (vertical_size = 1080 horizontal_size = 1920
        (progressive_sequence = 0
        frame_rate = 4) |
        (progressive_sequence = 1
        (frame_rate = 1) | (frame_rate = 2))) |
        (vertical_size = 720 horizontal_size = 1280
        frame_rate = 7
        progressive_sequence = 1)
        ]
        Minimum-GOP-Length: 15
        Maximum-GOP-Length: 15
        PID: 481
        ]
        Audio [
        Stream-Type: 129
        Properties: [
        type = 6
        (channel_mode = 2 bit_rate = 384000) |
        (channel_mode = 7 bit_rate = 384000)
        sample_rate = 48000
        ]
        PID: 482
        ]
        PMT-PID: 480
        PCR-PID: 481
        ]
        end-config
    </configuration>

    <stream-information>
        <transport-rate value="15000000.0"/>
        <duration value="00:27:42.413"/>
        <file-size value="3117026088"/>
        <transport-packet-interval value="188"/>
        <packets-analyzed value="16579926"/>
        <total-errors value="0"/>
        <total-warnings value="3320"/>
    </stream-information>

    <multiplex-summary>
        <pat version="0" pid="0">
            <program program_number="2" pid="0x01E0">
                <pmt version="0" pcr-pid="0x01E1">
                    <stream pid="0x01E2" type="Dolby AC-3 Audio">
                        <descriptor tag="0x05" name="registration_descriptor"/>
                        <descriptor tag="0x0A" name="ISO_639_language_descriptor"/>
                        <descriptor tag="0x81" name="ac3_audio_descriptor"/>
                    </stream>
                    <stream pid="0x01E1" type="MPEG-2 Video">
                    </stream>
                </pmt>
            </program>
        </pat>
    </multiplex-summary>

    <pid-summary>
        <pid-info pid="0x0000" content="PAT"
                  npkt="13601" pkt-rate="12305" payld-rate="12043"/>
        <pid-info pid="0x01E0" content="PMT" programs="2"
                  npkt="13601" pkt-rate="12305" payld-rate="12043"/>
        <pid-info pid="0x01E1" content="MPEG-2 Video, PCR" programs="2"
                  npkt="15844773" pkt-rate="14334901" payld-rate="13999998"
                  errors="0" warnings="3320"/>
        <pid-info pid="0x01E2" content="Dolby AC-3 Audio" programs="2"
                  npkt="466974" pkt-rate="422475" payld-rate="383524"/>
        <pid-info pid="0x1FFF" content="Null"
                  npkt="240977" pkt-rate="218014" payld-rate="218014"/>
    </pid-summary>

    <pid-properties>
        <pid value="0x0000 (0)"/>
        <content value="PAT"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <program-association-table-information>
            <table_id value="0x00"/>
            <version_number value="0"/>
            <transport_stream_id value="0x0001 (1)"/>
        </program-association-table-information>
        <packet-statistics>
            <number-of-packets value="13601"/>
            <first-seen-in-packet-number value="1" time="00:00:00.000"/>
            <last-seen-in-packet-number value="16578712" time="00:27:42.292"/>
            <average-transport-packet-rate value="12305"/>
            <average-payload-rate value="12043"/>
            <average-packet-spacing value="122.2274"/>
            <minimum-packet-spacing value="122.2251"/>
            <packet-number-at-minimum-spacing value="1220" time="00:00:00.122"/>
            <maximum-packet-spacing value="122.3253"/>
            <packet-number-at-maximum-spacing value="7316" time="00:00:00.733"/>
            <minimum-packet-spacing-ends-inclusive value="121.8240"/>
            <maximum-packet-spacing-ends-inclusive value="122.3253"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="13601"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="0"/>
            <number-of-packets-random_access_indicator-is-1 value="0"/>
            <splice_countdown-fields-seen value="No"/>
        </adaptation-field-information>
    </pid-properties>

    <pid-properties>
        <pid value="0x01E0 (480)"/>
        <content value="PMT"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <program-information>
            <pmt-pid-for-program_number value="2"/>
            <listed-in-pat-version_number value="0"/>
        </program-information>
        <program-map-table-information>
            <table_id value="0x02"/>
            <program_number value="2"/>
            <version_number value="0"/>
            <pcr-pid value="0x01E1 (481)"/>
            <section_length value="41"/>
        </program-map-table-information>
        <packet-statistics>
            <number-of-packets value="13601"/>
            <first-seen-in-packet-number value="2" time="00:00:00.000"/>
            <last-seen-in-packet-number value="16578713" time="00:27:42.292"/>
            <average-transport-packet-rate value="12305"/>
            <average-payload-rate value="12043"/>
            <average-packet-spacing value="122.2274"/>
            <minimum-packet-spacing value="122.2251"/>
            <packet-number-at-minimum-spacing value="1221" time="00:00:00.122"/>
            <maximum-packet-spacing value="122.3253"/>
            <packet-number-at-maximum-spacing value="7317" time="00:00:00.733"/>
            <minimum-packet-spacing-ends-inclusive value="0.1003"/>
            <maximum-packet-spacing-ends-inclusive value="122.3253"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="13601"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="0"/>
            <number-of-packets-random_access_indicator-is-1 value="0"/>
            <splice_countdown-fields-seen value="No"/>
        </adaptation-field-information>
    </pid-properties>

    <pid-properties>
        <pid value="0x01E1 (481)"/>
        <content value="MPEG-2 Video, PCR"/>
        <total-errors value="0"/>
        <total-warnings value="3320"/>
        <program-information>
            <member-of-program_number value="2"/>
            <listed-in-pmt-version_number value="0"/>
            <stream_type value="0x02 (H.262 | 13818-2 Video | 11172-2 Constrained Video)"/>
            <listed-as-pcr-pid value="Yes"/>
        </program-information>
        <pcr-statistics>
            <number-of-pcrs-seen value="118328"/>
            <average-pcr-interval value="14.0315"/>
            <minimum-pcr-interval value="0.1003"/>
            <packet-number-at-minimum-pcr-interval value="81142" time="00:00:08.135"/>
            <maximum-pcr-interval value="35.9957"/>
            <packet-number-at-maximum-pcr-interval value="7639694" time="00:12:46.006"/>
            <pcr-jitter-magnitude value="30"/>
        </pcr-statistics>
        <packet-statistics>
            <number-of-packets value="15844773"/>
            <first-seen-in-packet-number value="3" time="00:00:00.000"/>
            <last-seen-in-packet-number value="16559069" time="00:27:40.322"/>
            <average-transport-packet-rate value="14334901"/>
            <average-payload-rate value="13999998"/>
            <average-packet-spacing value="0.1048"/>
            <minimum-packet-spacing value="0.1003"/>
            <packet-number-at-minimum-spacing value="4" time="00:00:00.000"/>
            <maximum-packet-spacing value="23.9637"/>
            <packet-number-at-maximum-spacing value="11225391" time="00:18:45.532"/>
            <minimum-packet-spacing-ends-inclusive value="0.1003"/>
            <maximum-packet-spacing-ends-inclusive value="2091.3621"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="15844773"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="1"/>
            <number-of-packets-random_access_indicator-is-1 value="3318"/>
            <splice_countdown-fields-seen value="No"/>
            <noemptyaf value="Yes"/>
        </adaptation-field-information>
        <pes-information>
            <number-of-pes-headers-seen value="49763"/>
            <pes-stream_id value="0xE0 (224, MPEG Video)"/>
            <number-of-packets-pes_scrambling_control-is-00 value="49763"/>
            <pes-copyright-set value="No"/>
            <number-of-packets-pes_priority-is-1 value="0"/>
            <number-of-ptss-seen value="49763"/>
            <average-pts-interval value="33.3646"/>
            <average-es-buffer-level value="604817"/>
            <minimum-es-buffer-level value="127807"/>
            <packet-number-at-minimum-es-buffer-level value="654323" time="00:01:05.606"/>
            <maximum-es-buffer-level value="718848"/>
            <packet-number-at-maximum-es-buffer-level value="4407" time="00:00:00.441"/>
            <pesalign value="Yes"/>
        </pes-information>
        <video-elementary-stream-information>
            <mpeg-2-video-profile value="Main"/>
            <mpeg-2-video-level value="High"/>
            <mpeg-chroma-format value="4:2:0"/>
            <closed-caption-types-detected value="ATSC (EIA608, EIA708)"/>
            <a-53-cc-channels-detected value="CC1, XDS"/>
            <a-53-dtvcc-services-detected value="1, 2"/>
            <number-of-video-access-units-seen value="49763"/>
            <minimum-number-of-non-i-pictures-between-two-i-pictures value="12"/>
            <maximum-number-of-non-i-pictures-between-two-i-pictures value="14"/>
            <average-number-of-non-i-pictures-between-two-i-pictures value="14"/>
            <percent-of-all-pictures-that-are-p-pictures value="26"/>
            <percent-of-all-pictures-that-are-b-pictures value="66"/>
            <maximum-number-of-b-frames-between-i-or-p-frames value="2"/>
            <video-bit-rate value="14000000"/>
            <vbv-method value="Off"/>
            <vbv-size value="5750784"/>
            <video-multiplexing-buffer-size value="53333"/>
            <video-es-buffer-size value="718848"/>
            <minimum-decode-delay value="0.1050"/>
            <maximum-decode-delay value="0.5229"/>
            <mb-to-eb-transfer-method value="Leak"/>
            <video-frame-rate value="29.9700"/>
            <video-in-film-mode value="No"/>
            <progressive-sequence value="No"/>
            <horizontal-size value="1920"/>
            <vertical-size value="1080"/>
            <aspect-ratio value="16:9"/>
            <sample-aspect-ratio value="1.0000"/>
            <quadbyte value="Yes"/>
            <initial-timecode value="0:00:00:00"/>
            <final-timecode value="0:27:38:21"/>
        </video-elementary-stream-information>

        <xds-information>
            <prograting value="TV-MA L"/>
            <cgms value="Source:Digital SCMS:CopyNever APS:None"/>
        </xds-information>
        <errors>
            <error pid="0x01E1" packet="3182" time="00:00:00.318" code="6-13w"
                   msg="Video GOP length was 12; the configured minimum is 15."/>
            <error pid="0x01E1" packet="8295" time="00:00:00.831" code="6-13w"
                   msg="Video GOP length was 14; the configured minimum is 15."/>
            <error pid="0x01E1" packet="15445" time="00:00:01.548" code="6-13w"
                   msg="Video GOP length was 14; the configured minimum is 15."/>
            <error pid="0x01E1" packet="20010" time="00:00:02.006" code="6-13w"
                   msg="Video GOP length was 14; the configured minimum is 15."/>
            <error pid="0x01E1" packet="25055" time="00:00:02.512" code="6-13w" msg="Video GOP length is less than the configured minimum.
This error was detected 3312 more times in this PID after packet 25055"/>
            <error pid="0x01E1" packet="182128" time="00:00:18.261" code="4-87w"
                   msg="The CEA708 DTVCC packet sequence number was 2, expected 4."/>
            <error pid="0x01E1" packet="658451" time="00:01:06.020" code="4-87w"
                   msg="The CEA708 DTVCC packet sequence number was 0, expected 2."/>
            <error pid="0x01E1" packet="16192511" time="00:27:03.569" code="4-87w"
                   msg="The CEA708 DTVCC packet sequence number was 0, expected 3."/>
        </errors>
    </pid-properties>

    <pid-properties>
        <pid value="0x01E2 (482)"/>
        <content value="Dolby AC-3 Audio"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <program-information>
            <member-of-program_number value="2"/>
            <listed-in-pmt-version_number value="0"/>
            <stream_type value="0x81 (129, AC3 Audio)"/>
            <associated-pcr-pid value="0x01E1 (481)"/>
            <registration_descriptor>
                <tag value="0x05"/>
                <length value="4"/>
                <format_identifier value="0x41432D33 (AC-3)"/>
                <additional_identification_info value="none"/>
            </registration_descriptor>
            <iso_639_language_descriptor>
                <tag value="0x0A (10)"/>
                <length value="4"/>
                <iso_639_language_code value="0x656E67 (eng)"/>
                <audio_type value="0 (Undefined)"/>
            </iso_639_language_descriptor>
            <ac3_audio_descriptor>
                <tag value="0x81 (129)"/>
                <length value="4"/>
                <sample_rate_code value="0 (48 kHz)"/>
                <bsid value="8"/>
                <bit_rate_limit value="0"/>
                <bit_rate_code value="14 (384 kbps)"/>
                <surround_mode value="0 (Not indicated)"/>
                <bsmod value="0 (Main: Complete Main)"/>
                <num_channels value="2 (2/0)"/>
                <full_svc value="1"/>
                <langcod value="0"/>
            </ac3_audio_descriptor>
        </program-information>
        <packet-statistics>
            <number-of-packets value="466974"/>
            <first-seen-in-packet-number value="12" time="00:00:00.001"/>
            <last-seen-in-packet-number value="16562164" time="00:27:40.632"/>
            <average-transport-packet-rate value="422475"/>
            <average-payload-rate value="383524"/>
            <average-packet-spacing value="3.5562"/>
            <minimum-packet-spacing value="1.1029"/>
            <packet-number-at-minimum-spacing value="23" time="00:00:00.002"/>
            <maximum-packet-spacing value="326.2677"/>
            <packet-number-at-maximum-spacing value="3409" time="00:00:00.341"/>
            <minimum-packet-spacing-ends-inclusive value="1.1029"/>
            <maximum-packet-spacing-ends-inclusive value="1781.0368"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="466974"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="0"/>
            <number-of-packets-random_access_indicator-is-1 value="0"/>
            <splice_countdown-fields-seen value="No"/>
        </adaptation-field-information>
        <pes-information>
            <number-of-pes-headers-seen value="51886"/>
            <pes-stream_id value="0xBD (189, Private stream 1)"/>
            <number-of-packets-pes_scrambling_control-is-00 value="51886"/>
            <pes-copyright-set value="No"/>
            <number-of-packets-pes_priority-is-1 value="0"/>
            <number-of-ptss-seen value="51886"/>
            <average-pts-interval value="32.0054"/>
            <average-es-buffer-level value="1695"/>
            <minimum-es-buffer-level value="920"/>
            <packet-number-at-minimum-es-buffer-level value="3409" time="00:00:00.341"/>
            <maximum-es-buffer-level value="2470"/>
            <packet-number-at-maximum-es-buffer-level value="3409" time="00:00:00.341"/>
        </pes-information>
        <audio-elementary-stream-information>
            <audio-type value="Dolby AC-3"/>
            <number-of-audio-access-units-seen value="51886"/>
            <average-number-of-audio-frames-per-pes-packet value="1"/>
            <sample-rate value="48000"/>
            <bit-rate value="384000"/>
            <number-and-location-of-channels value="Stereo channels: L, R: 2/0"/>
            <ac3-audio-service-type value="Main audio service: Complete Main (CM)"/>
            <low-frequency-effects-flag value="0"/>
            <nominal-frame-size value="1536"/>
            <dialogue-normalization value="-16"/>
            <main-audio-buffer-size value="2592"/>
        </audio-elementary-stream-information>
    </pid-properties>

    <pid-properties>
        <pid value="0x1FFF (8191)"/>
        <content value="Null"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <packet-statistics>
            <number-of-packets value="240977"/>
            <first-seen-in-packet-number value="4265" time="00:00:00.427"/>
            <last-seen-in-packet-number value="16579926" time="00:27:42.413"/>
            <average-transport-packet-rate value="218014"/>
            <average-payload-rate value="218014"/>
            <average-packet-spacing value="6.8969"/>
            <minimum-packet-spacing value="0.1003"/>
            <packet-number-at-minimum-spacing value="4266" time="00:00:00.427"/>
            <maximum-packet-spacing value="8138.4448"/>
            <packet-number-at-maximum-spacing value="14548160" time="00:24:18.695"/>
            <minimum-packet-spacing-ends-inclusive value="0.1003"/>
            <maximum-packet-spacing-ends-inclusive value="8138.4448"/>
        </packet-statistics>
    </pid-properties>

<errors>
  <error pid="0x01E1" packet="661" time="00:00:00.264" code="6-13w" msg="Video GOP length was 12; the configured minimum is 15."/>
  <error pid="0x01E1" packet="1973" time="00:00:00.790" code="6-13w" msg="Video GOP length was 14; the configured minimum is 15."/>
  <error pid="0x01E1" packet="3224" time="00:00:01.292" code="6-13w" msg="Video GOP length was 14; the configured minimum is 15."/>
  <error pid="0x01E1" packet="4472" time="00:00:01.793" code="6-13w" msg="Video GOP length was 14; the configured minimum is 15."/>
  <error pid="0x01E1" packet="5718" time="00:00:02.292" code="6-13w" msg="Video GOP length is less than the configured minimum.
This error was detected 129 more times in this PID after packet 5718"/>
  <error pid="0x01E1" code="6-53e" msg="Missing CEA 708 CC1 data; configuration requires."/>
  <error pid="0x01E1" code="6-54e" msg="Missing CEA 708 CC1 data; configuration requires."/>
</errors>

</tsa-report>
"""
        generatedContent2.manzanitaCGMS = "Source:Digital SCMS:CopyNever APS:None"
        generatedContent2.manzanitaVChip = "TV-MA, L"
        generatedContent2.manzanitaRuntime = 1750

        happyAsset.contents.add(content)
        happyAsset.contents.add(generatedContent1)
        happyAsset.contents.add(generatedContent2)
        happyAsset.save(flush: true)


        assertEquals(ManzanitaResultStates.FAILED.toString(), content.manzanitaResult)
        assertEquals(ManzanitaResultStates.FAILED.toString(), generatedContent1.manzanitaResult)
        assertEquals(ManzanitaResultStates.FAILED.toString(), generatedContent2.manzanitaResult)

        assetService.updatePreviewContentsManzanitaResult(happyAsset)

        assertEquals(ManzanitaResultStates.PASSED.toString(), content.manzanitaResult)
        assertEquals(ManzanitaResultStates.PASSED.toString(), generatedContent1.manzanitaResult)
        assertEquals(ManzanitaResultStates.PASSED.toString(), generatedContent2.manzanitaResult)
    }

    /**
     * 1 original preview content with 6-53e, 1 generated content with both cc codes, 1 generated with other errors.
     */
    void testOriginalPreviewContentHasCCErrorCodeMultipleGeneratedOnePassOneFail() {

        //Original preview
        Content content = new Content(centralId: 4, original: true, name: "batman.mpg",
                checksum: "123456789", audioType: "Stereo", dataServiceXml: "</xml>",
                size: 100, runtime: 100, contentType: "PREVIEW", asset: happyAsset,
                profile: "Pro 7", contentState: ContentState.COMPLETE.toString())

        content.manzanitaResult = ManzanitaResultStates.FAILED.toString()
        content.manzanitaReport = """
<tsa-report
        version="1.0.0"
        license-serno="CC0825"
        license-username="Clearleap, Inc."
        date="Fri Jun 17 16:48:18 2011"
        file="/usr/local/clearleap/cache/dev-central2/accounts/2572/assets/1285573/Entourage_S4_08_comp_HBOD000000112574_P7D-1356313.mpg">
    <configuration>
        <analysis-mode value="Auto (MPEG)"/>
        tsa-config 2
        Abort-Limit: none
        About: {CableLabs specification for content containing High Definition
        video and one audio stream.
        Document OC-SP-CEP3.0-I01-100827 (August 27, 2010)
        }
        Use-ATSC-Buffer-Model: on
        PMT-In-Single-Packet: warn
        Warn-On-Missing-AC3-Descriptor: warn
        Max-PCR-Interval: 40.0 warn
        Disc-In-First-PCR-Only: err
        I-Pic-Has-RAI: err
        Max-B-Frame: 2
        Video-All-Pics-Frame: err
        Video-No-Scalable-Ext: err
        I-Pic-Has-DTS: err
        Video-Starts-With-Seq-Start: err
        GOP-First-Is-Closed: err
        GOP-Size-15-or-12: warn
        Video-One-AU-Per-PES: err
        Video-PES-Payload-Has-PCR: err
        Video-AU-Starts-PES: err
        Audio-Starts-With-AU: err
        Audio-Has-No-Partial-AU: err
        Packet-Size: 188
        PAT-Never-Changes: err
        PMT-Never-Changes: err
        PSI-Definition-Precedes-PIDs: err
        No-Partial-Transport-Packets: err
        Max-Rate-PID0-PMT-Vid-Aud: 38000000
        SCTE20-Precedes-ATSC53-CC: err
        Minimum-GOP-Length-Error-Level: warn
        Table [
        PID: 0
        Table-ID: 0
        Maximum-Table-Interval: 250
        ]
        Table [
        Table-ID: 2
        Maximum-Table-Interval: 250
        ]
        Program [
        Video [
        Require-CEA708-DTVCC1: on
        Require-CEA708-CC1: on
        Stream-Type: 2
        Stream-Type-Alternate: 128
        Properties: [
        profile = 4
        level = 4
        aspect_ratio = 3
        chroma_format = 1
        (vertical_size = 1080 horizontal_size = 1920
        (progressive_sequence = 0
        frame_rate = 4) |
        (progressive_sequence = 1
        (frame_rate = 1) | (frame_rate = 2))) |
        (vertical_size = 720 horizontal_size = 1280
        frame_rate = 7
        progressive_sequence = 1)
        ]
        Minimum-GOP-Length: 15
        Maximum-GOP-Length: 15
        PID: 481
        ]
        Audio [
        Stream-Type: 129
        Properties: [
        type = 6
        (channel_mode = 2 bit_rate = 384000) |
        (channel_mode = 7 bit_rate = 384000)
        sample_rate = 48000
        ]
        PID: 482
        ]
        PMT-PID: 480
        PCR-PID: 481
        ]
        end-config
    </configuration>

    <stream-information>
        <transport-rate value="15000000.0"/>
        <duration value="00:27:42.413"/>
        <file-size value="3117026088"/>
        <transport-packet-interval value="188"/>
        <packets-analyzed value="16579926"/>
        <total-errors value="0"/>
        <total-warnings value="3320"/>
    </stream-information>

    <multiplex-summary>
        <pat version="0" pid="0">
            <program program_number="2" pid="0x01E0">
                <pmt version="0" pcr-pid="0x01E1">
                    <stream pid="0x01E2" type="Dolby AC-3 Audio">
                        <descriptor tag="0x05" name="registration_descriptor"/>
                        <descriptor tag="0x0A" name="ISO_639_language_descriptor"/>
                        <descriptor tag="0x81" name="ac3_audio_descriptor"/>
                    </stream>
                    <stream pid="0x01E1" type="MPEG-2 Video">
                    </stream>
                </pmt>
            </program>
        </pat>
    </multiplex-summary>

    <pid-summary>
        <pid-info pid="0x0000" content="PAT"
                  npkt="13601" pkt-rate="12305" payld-rate="12043"/>
        <pid-info pid="0x01E0" content="PMT" programs="2"
                  npkt="13601" pkt-rate="12305" payld-rate="12043"/>
        <pid-info pid="0x01E1" content="MPEG-2 Video, PCR" programs="2"
                  npkt="15844773" pkt-rate="14334901" payld-rate="13999998"
                  errors="0" warnings="3320"/>
        <pid-info pid="0x01E2" content="Dolby AC-3 Audio" programs="2"
                  npkt="466974" pkt-rate="422475" payld-rate="383524"/>
        <pid-info pid="0x1FFF" content="Null"
                  npkt="240977" pkt-rate="218014" payld-rate="218014"/>
    </pid-summary>

    <pid-properties>
        <pid value="0x0000 (0)"/>
        <content value="PAT"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <program-association-table-information>
            <table_id value="0x00"/>
            <version_number value="0"/>
            <transport_stream_id value="0x0001 (1)"/>
        </program-association-table-information>
        <packet-statistics>
            <number-of-packets value="13601"/>
            <first-seen-in-packet-number value="1" time="00:00:00.000"/>
            <last-seen-in-packet-number value="16578712" time="00:27:42.292"/>
            <average-transport-packet-rate value="12305"/>
            <average-payload-rate value="12043"/>
            <average-packet-spacing value="122.2274"/>
            <minimum-packet-spacing value="122.2251"/>
            <packet-number-at-minimum-spacing value="1220" time="00:00:00.122"/>
            <maximum-packet-spacing value="122.3253"/>
            <packet-number-at-maximum-spacing value="7316" time="00:00:00.733"/>
            <minimum-packet-spacing-ends-inclusive value="121.8240"/>
            <maximum-packet-spacing-ends-inclusive value="122.3253"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="13601"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="0"/>
            <number-of-packets-random_access_indicator-is-1 value="0"/>
            <splice_countdown-fields-seen value="No"/>
        </adaptation-field-information>
    </pid-properties>

    <pid-properties>
        <pid value="0x01E0 (480)"/>
        <content value="PMT"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <program-information>
            <pmt-pid-for-program_number value="2"/>
            <listed-in-pat-version_number value="0"/>
        </program-information>
        <program-map-table-information>
            <table_id value="0x02"/>
            <program_number value="2"/>
            <version_number value="0"/>
            <pcr-pid value="0x01E1 (481)"/>
            <section_length value="41"/>
        </program-map-table-information>
        <packet-statistics>
            <number-of-packets value="13601"/>
            <first-seen-in-packet-number value="2" time="00:00:00.000"/>
            <last-seen-in-packet-number value="16578713" time="00:27:42.292"/>
            <average-transport-packet-rate value="12305"/>
            <average-payload-rate value="12043"/>
            <average-packet-spacing value="122.2274"/>
            <minimum-packet-spacing value="122.2251"/>
            <packet-number-at-minimum-spacing value="1221" time="00:00:00.122"/>
            <maximum-packet-spacing value="122.3253"/>
            <packet-number-at-maximum-spacing value="7317" time="00:00:00.733"/>
            <minimum-packet-spacing-ends-inclusive value="0.1003"/>
            <maximum-packet-spacing-ends-inclusive value="122.3253"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="13601"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="0"/>
            <number-of-packets-random_access_indicator-is-1 value="0"/>
            <splice_countdown-fields-seen value="No"/>
        </adaptation-field-information>
    </pid-properties>

    <pid-properties>
        <pid value="0x01E1 (481)"/>
        <content value="MPEG-2 Video, PCR"/>
        <total-errors value="0"/>
        <total-warnings value="3320"/>
        <program-information>
            <member-of-program_number value="2"/>
            <listed-in-pmt-version_number value="0"/>
            <stream_type value="0x02 (H.262 | 13818-2 Video | 11172-2 Constrained Video)"/>
            <listed-as-pcr-pid value="Yes"/>
        </program-information>
        <pcr-statistics>
            <number-of-pcrs-seen value="118328"/>
            <average-pcr-interval value="14.0315"/>
            <minimum-pcr-interval value="0.1003"/>
            <packet-number-at-minimum-pcr-interval value="81142" time="00:00:08.135"/>
            <maximum-pcr-interval value="35.9957"/>
            <packet-number-at-maximum-pcr-interval value="7639694" time="00:12:46.006"/>
            <pcr-jitter-magnitude value="30"/>
        </pcr-statistics>
        <packet-statistics>
            <number-of-packets value="15844773"/>
            <first-seen-in-packet-number value="3" time="00:00:00.000"/>
            <last-seen-in-packet-number value="16559069" time="00:27:40.322"/>
            <average-transport-packet-rate value="14334901"/>
            <average-payload-rate value="13999998"/>
            <average-packet-spacing value="0.1048"/>
            <minimum-packet-spacing value="0.1003"/>
            <packet-number-at-minimum-spacing value="4" time="00:00:00.000"/>
            <maximum-packet-spacing value="23.9637"/>
            <packet-number-at-maximum-spacing value="11225391" time="00:18:45.532"/>
            <minimum-packet-spacing-ends-inclusive value="0.1003"/>
            <maximum-packet-spacing-ends-inclusive value="2091.3621"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="15844773"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="1"/>
            <number-of-packets-random_access_indicator-is-1 value="3318"/>
            <splice_countdown-fields-seen value="No"/>
            <noemptyaf value="Yes"/>
        </adaptation-field-information>
        <pes-information>
            <number-of-pes-headers-seen value="49763"/>
            <pes-stream_id value="0xE0 (224, MPEG Video)"/>
            <number-of-packets-pes_scrambling_control-is-00 value="49763"/>
            <pes-copyright-set value="No"/>
            <number-of-packets-pes_priority-is-1 value="0"/>
            <number-of-ptss-seen value="49763"/>
            <average-pts-interval value="33.3646"/>
            <average-es-buffer-level value="604817"/>
            <minimum-es-buffer-level value="127807"/>
            <packet-number-at-minimum-es-buffer-level value="654323" time="00:01:05.606"/>
            <maximum-es-buffer-level value="718848"/>
            <packet-number-at-maximum-es-buffer-level value="4407" time="00:00:00.441"/>
            <pesalign value="Yes"/>
        </pes-information>
        <video-elementary-stream-information>
            <mpeg-2-video-profile value="Main"/>
            <mpeg-2-video-level value="High"/>
            <mpeg-chroma-format value="4:2:0"/>
            <closed-caption-types-detected value="ATSC (EIA608, EIA708)"/>
            <a-53-cc-channels-detected value="CC1, XDS"/>
            <a-53-dtvcc-services-detected value="1, 2"/>
            <number-of-video-access-units-seen value="49763"/>
            <minimum-number-of-non-i-pictures-between-two-i-pictures value="12"/>
            <maximum-number-of-non-i-pictures-between-two-i-pictures value="14"/>
            <average-number-of-non-i-pictures-between-two-i-pictures value="14"/>
            <percent-of-all-pictures-that-are-p-pictures value="26"/>
            <percent-of-all-pictures-that-are-b-pictures value="66"/>
            <maximum-number-of-b-frames-between-i-or-p-frames value="2"/>
            <video-bit-rate value="14000000"/>
            <vbv-method value="Off"/>
            <vbv-size value="5750784"/>
            <video-multiplexing-buffer-size value="53333"/>
            <video-es-buffer-size value="718848"/>
            <minimum-decode-delay value="0.1050"/>
            <maximum-decode-delay value="0.5229"/>
            <mb-to-eb-transfer-method value="Leak"/>
            <video-frame-rate value="29.9700"/>
            <video-in-film-mode value="No"/>
            <progressive-sequence value="No"/>
            <horizontal-size value="1920"/>
            <vertical-size value="1080"/>
            <aspect-ratio value="16:9"/>
            <sample-aspect-ratio value="1.0000"/>
            <quadbyte value="Yes"/>
            <initial-timecode value="0:00:00:00"/>
            <final-timecode value="0:27:38:21"/>
        </video-elementary-stream-information>

        <xds-information>
            <prograting value="TV-MA L"/>
            <cgms value="Source:Digital SCMS:CopyNever APS:None"/>
        </xds-information>
        <errors>
            <error pid="0x01E1" packet="3182" time="00:00:00.318" code="6-13w"
                   msg="Video GOP length was 12; the configured minimum is 15."/>
            <error pid="0x01E1" packet="8295" time="00:00:00.831" code="6-13w"
                   msg="Video GOP length was 14; the configured minimum is 15."/>
            <error pid="0x01E1" packet="15445" time="00:00:01.548" code="6-13w"
                   msg="Video GOP length was 14; the configured minimum is 15."/>
            <error pid="0x01E1" packet="20010" time="00:00:02.006" code="6-13w"
                   msg="Video GOP length was 14; the configured minimum is 15."/>
            <error pid="0x01E1" packet="25055" time="00:00:02.512" code="6-13w" msg="Video GOP length is less than the configured minimum.
This error was detected 3312 more times in this PID after packet 25055"/>
            <error pid="0x01E1" packet="182128" time="00:00:18.261" code="4-87w"
                   msg="The CEA708 DTVCC packet sequence number was 2, expected 4."/>
            <error pid="0x01E1" packet="658451" time="00:01:06.020" code="4-87w"
                   msg="The CEA708 DTVCC packet sequence number was 0, expected 2."/>
            <error pid="0x01E1" packet="16192511" time="00:27:03.569" code="4-87w"
                   msg="The CEA708 DTVCC packet sequence number was 0, expected 3."/>
        </errors>
    </pid-properties>

    <pid-properties>
        <pid value="0x01E2 (482)"/>
        <content value="Dolby AC-3 Audio"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <program-information>
            <member-of-program_number value="2"/>
            <listed-in-pmt-version_number value="0"/>
            <stream_type value="0x81 (129, AC3 Audio)"/>
            <associated-pcr-pid value="0x01E1 (481)"/>
            <registration_descriptor>
                <tag value="0x05"/>
                <length value="4"/>
                <format_identifier value="0x41432D33 (AC-3)"/>
                <additional_identification_info value="none"/>
            </registration_descriptor>
            <iso_639_language_descriptor>
                <tag value="0x0A (10)"/>
                <length value="4"/>
                <iso_639_language_code value="0x656E67 (eng)"/>
                <audio_type value="0 (Undefined)"/>
            </iso_639_language_descriptor>
            <ac3_audio_descriptor>
                <tag value="0x81 (129)"/>
                <length value="4"/>
                <sample_rate_code value="0 (48 kHz)"/>
                <bsid value="8"/>
                <bit_rate_limit value="0"/>
                <bit_rate_code value="14 (384 kbps)"/>
                <surround_mode value="0 (Not indicated)"/>
                <bsmod value="0 (Main: Complete Main)"/>
                <num_channels value="2 (2/0)"/>
                <full_svc value="1"/>
                <langcod value="0"/>
            </ac3_audio_descriptor>
        </program-information>
        <packet-statistics>
            <number-of-packets value="466974"/>
            <first-seen-in-packet-number value="12" time="00:00:00.001"/>
            <last-seen-in-packet-number value="16562164" time="00:27:40.632"/>
            <average-transport-packet-rate value="422475"/>
            <average-payload-rate value="383524"/>
            <average-packet-spacing value="3.5562"/>
            <minimum-packet-spacing value="1.1029"/>
            <packet-number-at-minimum-spacing value="23" time="00:00:00.002"/>
            <maximum-packet-spacing value="326.2677"/>
            <packet-number-at-maximum-spacing value="3409" time="00:00:00.341"/>
            <minimum-packet-spacing-ends-inclusive value="1.1029"/>
            <maximum-packet-spacing-ends-inclusive value="1781.0368"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="466974"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="0"/>
            <number-of-packets-random_access_indicator-is-1 value="0"/>
            <splice_countdown-fields-seen value="No"/>
        </adaptation-field-information>
        <pes-information>
            <number-of-pes-headers-seen value="51886"/>
            <pes-stream_id value="0xBD (189, Private stream 1)"/>
            <number-of-packets-pes_scrambling_control-is-00 value="51886"/>
            <pes-copyright-set value="No"/>
            <number-of-packets-pes_priority-is-1 value="0"/>
            <number-of-ptss-seen value="51886"/>
            <average-pts-interval value="32.0054"/>
            <average-es-buffer-level value="1695"/>
            <minimum-es-buffer-level value="920"/>
            <packet-number-at-minimum-es-buffer-level value="3409" time="00:00:00.341"/>
            <maximum-es-buffer-level value="2470"/>
            <packet-number-at-maximum-es-buffer-level value="3409" time="00:00:00.341"/>
        </pes-information>
        <audio-elementary-stream-information>
            <audio-type value="Dolby AC-3"/>
            <number-of-audio-access-units-seen value="51886"/>
            <average-number-of-audio-frames-per-pes-packet value="1"/>
            <sample-rate value="48000"/>
            <bit-rate value="384000"/>
            <number-and-location-of-channels value="Stereo channels: L, R: 2/0"/>
            <ac3-audio-service-type value="Main audio service: Complete Main (CM)"/>
            <low-frequency-effects-flag value="0"/>
            <nominal-frame-size value="1536"/>
            <dialogue-normalization value="-16"/>
            <main-audio-buffer-size value="2592"/>
        </audio-elementary-stream-information>
    </pid-properties>

    <pid-properties>
        <pid value="0x1FFF (8191)"/>
        <content value="Null"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <packet-statistics>
            <number-of-packets value="240977"/>
            <first-seen-in-packet-number value="4265" time="00:00:00.427"/>
            <last-seen-in-packet-number value="16579926" time="00:27:42.413"/>
            <average-transport-packet-rate value="218014"/>
            <average-payload-rate value="218014"/>
            <average-packet-spacing value="6.8969"/>
            <minimum-packet-spacing value="0.1003"/>
            <packet-number-at-minimum-spacing value="4266" time="00:00:00.427"/>
            <maximum-packet-spacing value="8138.4448"/>
            <packet-number-at-maximum-spacing value="14548160" time="00:24:18.695"/>
            <minimum-packet-spacing-ends-inclusive value="0.1003"/>
            <maximum-packet-spacing-ends-inclusive value="8138.4448"/>
        </packet-statistics>
    </pid-properties>

<errors>
  <error pid="0x01E1" packet="661" time="00:00:00.264" code="6-13w" msg="Video GOP length was 12; the configured minimum is 15."/>
  <error pid="0x01E1" packet="1973" time="00:00:00.790" code="6-13w" msg="Video GOP length was 14; the configured minimum is 15."/>
  <error pid="0x01E1" packet="3224" time="00:00:01.292" code="6-13w" msg="Video GOP length was 14; the configured minimum is 15."/>
  <error pid="0x01E1" packet="4472" time="00:00:01.793" code="6-13w" msg="Video GOP length was 14; the configured minimum is 15."/>
  <error pid="0x01E1" packet="5718" time="00:00:02.292" code="6-13w" msg="Video GOP length is less than the configured minimum.
This error was detected 129 more times in this PID after packet 5718"/>
  <error pid="0x01E1" code="6-53e" msg="Missing CEA 708 CC1 data; configuration requires."/>
</errors>

</tsa-report>
"""
        content.manzanitaCGMS = "Source:Digital SCMS:CopyNever APS:None"
        content.manzanitaVChip = "TV-MA, L"
        content.manzanitaRuntime = 1750

        Content generatedContent1 = new Content(centralId: 5, original: false, name: "batman-1.mpg",
                checksum: "123456789", audioType: "Stereo", dataServiceXml: "</xml>",
                size: 100, runtime: 100, contentType: "PREVIEW", asset: happyAsset,
                profile: "Pro 7", contentState: ContentState.COMPLETE.toString())

        generatedContent1.manzanitaResult = ManzanitaResultStates.FAILED.toString()
        generatedContent1.manzanitaReport = """
<tsa-report
        version="1.0.0"
        license-serno="CC0825"
        license-username="Clearleap, Inc."
        date="Fri Jun 17 16:48:18 2011"
        file="/usr/local/clearleap/cache/dev-central2/accounts/2572/assets/1285573/Entourage_S4_08_comp_HBOD000000112574_P7D-1356313.mpg">
    <configuration>
        <analysis-mode value="Auto (MPEG)"/>
        tsa-config 2
        Abort-Limit: none
        About: {CableLabs specification for content containing High Definition
        video and one audio stream.
        Document OC-SP-CEP3.0-I01-100827 (August 27, 2010)
        }
        Use-ATSC-Buffer-Model: on
        PMT-In-Single-Packet: warn
        Warn-On-Missing-AC3-Descriptor: warn
        Max-PCR-Interval: 40.0 warn
        Disc-In-First-PCR-Only: err
        I-Pic-Has-RAI: err
        Max-B-Frame: 2
        Video-All-Pics-Frame: err
        Video-No-Scalable-Ext: err
        I-Pic-Has-DTS: err
        Video-Starts-With-Seq-Start: err
        GOP-First-Is-Closed: err
        GOP-Size-15-or-12: warn
        Video-One-AU-Per-PES: err
        Video-PES-Payload-Has-PCR: err
        Video-AU-Starts-PES: err
        Audio-Starts-With-AU: err
        Audio-Has-No-Partial-AU: err
        Packet-Size: 188
        PAT-Never-Changes: err
        PMT-Never-Changes: err
        PSI-Definition-Precedes-PIDs: err
        No-Partial-Transport-Packets: err
        Max-Rate-PID0-PMT-Vid-Aud: 38000000
        SCTE20-Precedes-ATSC53-CC: err
        Minimum-GOP-Length-Error-Level: warn
        Table [
        PID: 0
        Table-ID: 0
        Maximum-Table-Interval: 250
        ]
        Table [
        Table-ID: 2
        Maximum-Table-Interval: 250
        ]
        Program [
        Video [
        Require-CEA708-DTVCC1: on
        Require-CEA708-CC1: on
        Stream-Type: 2
        Stream-Type-Alternate: 128
        Properties: [
        profile = 4
        level = 4
        aspect_ratio = 3
        chroma_format = 1
        (vertical_size = 1080 horizontal_size = 1920
        (progressive_sequence = 0
        frame_rate = 4) |
        (progressive_sequence = 1
        (frame_rate = 1) | (frame_rate = 2))) |
        (vertical_size = 720 horizontal_size = 1280
        frame_rate = 7
        progressive_sequence = 1)
        ]
        Minimum-GOP-Length: 15
        Maximum-GOP-Length: 15
        PID: 481
        ]
        Audio [
        Stream-Type: 129
        Properties: [
        type = 6
        (channel_mode = 2 bit_rate = 384000) |
        (channel_mode = 7 bit_rate = 384000)
        sample_rate = 48000
        ]
        PID: 482
        ]
        PMT-PID: 480
        PCR-PID: 481
        ]
        end-config
    </configuration>

    <stream-information>
        <transport-rate value="15000000.0"/>
        <duration value="00:27:42.413"/>
        <file-size value="3117026088"/>
        <transport-packet-interval value="188"/>
        <packets-analyzed value="16579926"/>
        <total-errors value="0"/>
        <total-warnings value="3320"/>
    </stream-information>

    <multiplex-summary>
        <pat version="0" pid="0">
            <program program_number="2" pid="0x01E0">
                <pmt version="0" pcr-pid="0x01E1">
                    <stream pid="0x01E2" type="Dolby AC-3 Audio">
                        <descriptor tag="0x05" name="registration_descriptor"/>
                        <descriptor tag="0x0A" name="ISO_639_language_descriptor"/>
                        <descriptor tag="0x81" name="ac3_audio_descriptor"/>
                    </stream>
                    <stream pid="0x01E1" type="MPEG-2 Video">
                    </stream>
                </pmt>
            </program>
        </pat>
    </multiplex-summary>

    <pid-summary>
        <pid-info pid="0x0000" content="PAT"
                  npkt="13601" pkt-rate="12305" payld-rate="12043"/>
        <pid-info pid="0x01E0" content="PMT" programs="2"
                  npkt="13601" pkt-rate="12305" payld-rate="12043"/>
        <pid-info pid="0x01E1" content="MPEG-2 Video, PCR" programs="2"
                  npkt="15844773" pkt-rate="14334901" payld-rate="13999998"
                  errors="0" warnings="3320"/>
        <pid-info pid="0x01E2" content="Dolby AC-3 Audio" programs="2"
                  npkt="466974" pkt-rate="422475" payld-rate="383524"/>
        <pid-info pid="0x1FFF" content="Null"
                  npkt="240977" pkt-rate="218014" payld-rate="218014"/>
    </pid-summary>

    <pid-properties>
        <pid value="0x0000 (0)"/>
        <content value="PAT"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <program-association-table-information>
            <table_id value="0x00"/>
            <version_number value="0"/>
            <transport_stream_id value="0x0001 (1)"/>
        </program-association-table-information>
        <packet-statistics>
            <number-of-packets value="13601"/>
            <first-seen-in-packet-number value="1" time="00:00:00.000"/>
            <last-seen-in-packet-number value="16578712" time="00:27:42.292"/>
            <average-transport-packet-rate value="12305"/>
            <average-payload-rate value="12043"/>
            <average-packet-spacing value="122.2274"/>
            <minimum-packet-spacing value="122.2251"/>
            <packet-number-at-minimum-spacing value="1220" time="00:00:00.122"/>
            <maximum-packet-spacing value="122.3253"/>
            <packet-number-at-maximum-spacing value="7316" time="00:00:00.733"/>
            <minimum-packet-spacing-ends-inclusive value="121.8240"/>
            <maximum-packet-spacing-ends-inclusive value="122.3253"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="13601"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="0"/>
            <number-of-packets-random_access_indicator-is-1 value="0"/>
            <splice_countdown-fields-seen value="No"/>
        </adaptation-field-information>
    </pid-properties>

    <pid-properties>
        <pid value="0x01E0 (480)"/>
        <content value="PMT"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <program-information>
            <pmt-pid-for-program_number value="2"/>
            <listed-in-pat-version_number value="0"/>
        </program-information>
        <program-map-table-information>
            <table_id value="0x02"/>
            <program_number value="2"/>
            <version_number value="0"/>
            <pcr-pid value="0x01E1 (481)"/>
            <section_length value="41"/>
        </program-map-table-information>
        <packet-statistics>
            <number-of-packets value="13601"/>
            <first-seen-in-packet-number value="2" time="00:00:00.000"/>
            <last-seen-in-packet-number value="16578713" time="00:27:42.292"/>
            <average-transport-packet-rate value="12305"/>
            <average-payload-rate value="12043"/>
            <average-packet-spacing value="122.2274"/>
            <minimum-packet-spacing value="122.2251"/>
            <packet-number-at-minimum-spacing value="1221" time="00:00:00.122"/>
            <maximum-packet-spacing value="122.3253"/>
            <packet-number-at-maximum-spacing value="7317" time="00:00:00.733"/>
            <minimum-packet-spacing-ends-inclusive value="0.1003"/>
            <maximum-packet-spacing-ends-inclusive value="122.3253"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="13601"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="0"/>
            <number-of-packets-random_access_indicator-is-1 value="0"/>
            <splice_countdown-fields-seen value="No"/>
        </adaptation-field-information>
    </pid-properties>

    <pid-properties>
        <pid value="0x01E1 (481)"/>
        <content value="MPEG-2 Video, PCR"/>
        <total-errors value="0"/>
        <total-warnings value="3320"/>
        <program-information>
            <member-of-program_number value="2"/>
            <listed-in-pmt-version_number value="0"/>
            <stream_type value="0x02 (H.262 | 13818-2 Video | 11172-2 Constrained Video)"/>
            <listed-as-pcr-pid value="Yes"/>
        </program-information>
        <pcr-statistics>
            <number-of-pcrs-seen value="118328"/>
            <average-pcr-interval value="14.0315"/>
            <minimum-pcr-interval value="0.1003"/>
            <packet-number-at-minimum-pcr-interval value="81142" time="00:00:08.135"/>
            <maximum-pcr-interval value="35.9957"/>
            <packet-number-at-maximum-pcr-interval value="7639694" time="00:12:46.006"/>
            <pcr-jitter-magnitude value="30"/>
        </pcr-statistics>
        <packet-statistics>
            <number-of-packets value="15844773"/>
            <first-seen-in-packet-number value="3" time="00:00:00.000"/>
            <last-seen-in-packet-number value="16559069" time="00:27:40.322"/>
            <average-transport-packet-rate value="14334901"/>
            <average-payload-rate value="13999998"/>
            <average-packet-spacing value="0.1048"/>
            <minimum-packet-spacing value="0.1003"/>
            <packet-number-at-minimum-spacing value="4" time="00:00:00.000"/>
            <maximum-packet-spacing value="23.9637"/>
            <packet-number-at-maximum-spacing value="11225391" time="00:18:45.532"/>
            <minimum-packet-spacing-ends-inclusive value="0.1003"/>
            <maximum-packet-spacing-ends-inclusive value="2091.3621"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="15844773"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="1"/>
            <number-of-packets-random_access_indicator-is-1 value="3318"/>
            <splice_countdown-fields-seen value="No"/>
            <noemptyaf value="Yes"/>
        </adaptation-field-information>
        <pes-information>
            <number-of-pes-headers-seen value="49763"/>
            <pes-stream_id value="0xE0 (224, MPEG Video)"/>
            <number-of-packets-pes_scrambling_control-is-00 value="49763"/>
            <pes-copyright-set value="No"/>
            <number-of-packets-pes_priority-is-1 value="0"/>
            <number-of-ptss-seen value="49763"/>
            <average-pts-interval value="33.3646"/>
            <average-es-buffer-level value="604817"/>
            <minimum-es-buffer-level value="127807"/>
            <packet-number-at-minimum-es-buffer-level value="654323" time="00:01:05.606"/>
            <maximum-es-buffer-level value="718848"/>
            <packet-number-at-maximum-es-buffer-level value="4407" time="00:00:00.441"/>
            <pesalign value="Yes"/>
        </pes-information>
        <video-elementary-stream-information>
            <mpeg-2-video-profile value="Main"/>
            <mpeg-2-video-level value="High"/>
            <mpeg-chroma-format value="4:2:0"/>
            <closed-caption-types-detected value="ATSC (EIA608, EIA708)"/>
            <a-53-cc-channels-detected value="CC1, XDS"/>
            <a-53-dtvcc-services-detected value="1, 2"/>
            <number-of-video-access-units-seen value="49763"/>
            <minimum-number-of-non-i-pictures-between-two-i-pictures value="12"/>
            <maximum-number-of-non-i-pictures-between-two-i-pictures value="14"/>
            <average-number-of-non-i-pictures-between-two-i-pictures value="14"/>
            <percent-of-all-pictures-that-are-p-pictures value="26"/>
            <percent-of-all-pictures-that-are-b-pictures value="66"/>
            <maximum-number-of-b-frames-between-i-or-p-frames value="2"/>
            <video-bit-rate value="14000000"/>
            <vbv-method value="Off"/>
            <vbv-size value="5750784"/>
            <video-multiplexing-buffer-size value="53333"/>
            <video-es-buffer-size value="718848"/>
            <minimum-decode-delay value="0.1050"/>
            <maximum-decode-delay value="0.5229"/>
            <mb-to-eb-transfer-method value="Leak"/>
            <video-frame-rate value="29.9700"/>
            <video-in-film-mode value="No"/>
            <progressive-sequence value="No"/>
            <horizontal-size value="1920"/>
            <vertical-size value="1080"/>
            <aspect-ratio value="16:9"/>
            <sample-aspect-ratio value="1.0000"/>
            <quadbyte value="Yes"/>
            <initial-timecode value="0:00:00:00"/>
            <final-timecode value="0:27:38:21"/>
        </video-elementary-stream-information>

        <xds-information>
            <prograting value="TV-MA L"/>
            <cgms value="Source:Digital SCMS:CopyNever APS:None"/>
        </xds-information>
        <errors>
            <error pid="0x01E1" packet="3182" time="00:00:00.318" code="6-13w"
                   msg="Video GOP length was 12; the configured minimum is 15."/>
            <error pid="0x01E1" packet="8295" time="00:00:00.831" code="6-13w"
                   msg="Video GOP length was 14; the configured minimum is 15."/>
            <error pid="0x01E1" packet="15445" time="00:00:01.548" code="6-13w"
                   msg="Video GOP length was 14; the configured minimum is 15."/>
            <error pid="0x01E1" packet="20010" time="00:00:02.006" code="6-13w"
                   msg="Video GOP length was 14; the configured minimum is 15."/>
            <error pid="0x01E1" packet="25055" time="00:00:02.512" code="6-13w" msg="Video GOP length is less than the configured minimum.
This error was detected 3312 more times in this PID after packet 25055"/>
            <error pid="0x01E1" packet="182128" time="00:00:18.261" code="4-87w"
                   msg="The CEA708 DTVCC packet sequence number was 2, expected 4."/>
            <error pid="0x01E1" packet="658451" time="00:01:06.020" code="4-87w"
                   msg="The CEA708 DTVCC packet sequence number was 0, expected 2."/>
            <error pid="0x01E1" packet="16192511" time="00:27:03.569" code="4-87w"
                   msg="The CEA708 DTVCC packet sequence number was 0, expected 3."/>
        </errors>
    </pid-properties>

    <pid-properties>
        <pid value="0x01E2 (482)"/>
        <content value="Dolby AC-3 Audio"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <program-information>
            <member-of-program_number value="2"/>
            <listed-in-pmt-version_number value="0"/>
            <stream_type value="0x81 (129, AC3 Audio)"/>
            <associated-pcr-pid value="0x01E1 (481)"/>
            <registration_descriptor>
                <tag value="0x05"/>
                <length value="4"/>
                <format_identifier value="0x41432D33 (AC-3)"/>
                <additional_identification_info value="none"/>
            </registration_descriptor>
            <iso_639_language_descriptor>
                <tag value="0x0A (10)"/>
                <length value="4"/>
                <iso_639_language_code value="0x656E67 (eng)"/>
                <audio_type value="0 (Undefined)"/>
            </iso_639_language_descriptor>
            <ac3_audio_descriptor>
                <tag value="0x81 (129)"/>
                <length value="4"/>
                <sample_rate_code value="0 (48 kHz)"/>
                <bsid value="8"/>
                <bit_rate_limit value="0"/>
                <bit_rate_code value="14 (384 kbps)"/>
                <surround_mode value="0 (Not indicated)"/>
                <bsmod value="0 (Main: Complete Main)"/>
                <num_channels value="2 (2/0)"/>
                <full_svc value="1"/>
                <langcod value="0"/>
            </ac3_audio_descriptor>
        </program-information>
        <packet-statistics>
            <number-of-packets value="466974"/>
            <first-seen-in-packet-number value="12" time="00:00:00.001"/>
            <last-seen-in-packet-number value="16562164" time="00:27:40.632"/>
            <average-transport-packet-rate value="422475"/>
            <average-payload-rate value="383524"/>
            <average-packet-spacing value="3.5562"/>
            <minimum-packet-spacing value="1.1029"/>
            <packet-number-at-minimum-spacing value="23" time="00:00:00.002"/>
            <maximum-packet-spacing value="326.2677"/>
            <packet-number-at-maximum-spacing value="3409" time="00:00:00.341"/>
            <minimum-packet-spacing-ends-inclusive value="1.1029"/>
            <maximum-packet-spacing-ends-inclusive value="1781.0368"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="466974"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="0"/>
            <number-of-packets-random_access_indicator-is-1 value="0"/>
            <splice_countdown-fields-seen value="No"/>
        </adaptation-field-information>
        <pes-information>
            <number-of-pes-headers-seen value="51886"/>
            <pes-stream_id value="0xBD (189, Private stream 1)"/>
            <number-of-packets-pes_scrambling_control-is-00 value="51886"/>
            <pes-copyright-set value="No"/>
            <number-of-packets-pes_priority-is-1 value="0"/>
            <number-of-ptss-seen value="51886"/>
            <average-pts-interval value="32.0054"/>
            <average-es-buffer-level value="1695"/>
            <minimum-es-buffer-level value="920"/>
            <packet-number-at-minimum-es-buffer-level value="3409" time="00:00:00.341"/>
            <maximum-es-buffer-level value="2470"/>
            <packet-number-at-maximum-es-buffer-level value="3409" time="00:00:00.341"/>
        </pes-information>
        <audio-elementary-stream-information>
            <audio-type value="Dolby AC-3"/>
            <number-of-audio-access-units-seen value="51886"/>
            <average-number-of-audio-frames-per-pes-packet value="1"/>
            <sample-rate value="48000"/>
            <bit-rate value="384000"/>
            <number-and-location-of-channels value="Stereo channels: L, R: 2/0"/>
            <ac3-audio-service-type value="Main audio service: Complete Main (CM)"/>
            <low-frequency-effects-flag value="0"/>
            <nominal-frame-size value="1536"/>
            <dialogue-normalization value="-16"/>
            <main-audio-buffer-size value="2592"/>
        </audio-elementary-stream-information>
    </pid-properties>

    <pid-properties>
        <pid value="0x1FFF (8191)"/>
        <content value="Null"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <packet-statistics>
            <number-of-packets value="240977"/>
            <first-seen-in-packet-number value="4265" time="00:00:00.427"/>
            <last-seen-in-packet-number value="16579926" time="00:27:42.413"/>
            <average-transport-packet-rate value="218014"/>
            <average-payload-rate value="218014"/>
            <average-packet-spacing value="6.8969"/>
            <minimum-packet-spacing value="0.1003"/>
            <packet-number-at-minimum-spacing value="4266" time="00:00:00.427"/>
            <maximum-packet-spacing value="8138.4448"/>
            <packet-number-at-maximum-spacing value="14548160" time="00:24:18.695"/>
            <minimum-packet-spacing-ends-inclusive value="0.1003"/>
            <maximum-packet-spacing-ends-inclusive value="8138.4448"/>
        </packet-statistics>
    </pid-properties>

<errors>
  <error pid="0x01E1" packet="661" time="00:00:00.264" code="6-13w" msg="Video GOP length was 12; the configured minimum is 15."/>
  <error pid="0x01E1" packet="1973" time="00:00:00.790" code="6-13w" msg="Video GOP length was 14; the configured minimum is 15."/>
  <error pid="0x01E1" packet="3224" time="00:00:01.292" code="6-13w" msg="Video GOP length was 14; the configured minimum is 15."/>
  <error pid="0x01E1" packet="4472" time="00:00:01.793" code="6-13w" msg="Video GOP length was 14; the configured minimum is 15."/>
  <error pid="0x01E1" packet="5718" time="00:00:02.292" code="6-13w" msg="Video GOP length is less than the configured minimum.
This error was detected 129 more times in this PID after packet 5718"/>
  <error pid="0x01E1" code="6-53e" msg="Missing CEA 708 CC1 data; configuration requires."/>
  <error pid="0x01E1" code="6-56e" msg="Missing CEA 708 CC1 data; configuration requires."/>
  <error pid="0x01E1" code="6-54e" msg="Missing CEA 708 CC1 data; configuration requires."/>
</errors>

</tsa-report>
"""
        generatedContent1.manzanitaCGMS = "Source:Digital SCMS:CopyNever APS:None"
        generatedContent1.manzanitaVChip = "TV-MA, L"
        generatedContent1.manzanitaRuntime = 1750

        Content generatedContent2 = new Content(centralId: 7, original: false, name: "batman-2.mpg",
                checksum: "123456789", audioType: "Stereo", dataServiceXml: "</xml>",
                size: 100, runtime: 100, contentType: "PREVIEW", asset: happyAsset,
                profile: "Pro 7", contentState: ContentState.COMPLETE.toString())

        generatedContent2.manzanitaResult = ManzanitaResultStates.FAILED.toString()
        generatedContent2.manzanitaReport = """
<tsa-report
        version="1.0.0"
        license-serno="CC0825"
        license-username="Clearleap, Inc."
        date="Fri Jun 17 16:48:18 2011"
        file="/usr/local/clearleap/cache/dev-central2/accounts/2572/assets/1285573/Entourage_S4_08_comp_HBOD000000112574_P7D-1356313.mpg">
    <configuration>
        <analysis-mode value="Auto (MPEG)"/>
        tsa-config 2
        Abort-Limit: none
        About: {CableLabs specification for content containing High Definition
        video and one audio stream.
        Document OC-SP-CEP3.0-I01-100827 (August 27, 2010)
        }
        Use-ATSC-Buffer-Model: on
        PMT-In-Single-Packet: warn
        Warn-On-Missing-AC3-Descriptor: warn
        Max-PCR-Interval: 40.0 warn
        Disc-In-First-PCR-Only: err
        I-Pic-Has-RAI: err
        Max-B-Frame: 2
        Video-All-Pics-Frame: err
        Video-No-Scalable-Ext: err
        I-Pic-Has-DTS: err
        Video-Starts-With-Seq-Start: err
        GOP-First-Is-Closed: err
        GOP-Size-15-or-12: warn
        Video-One-AU-Per-PES: err
        Video-PES-Payload-Has-PCR: err
        Video-AU-Starts-PES: err
        Audio-Starts-With-AU: err
        Audio-Has-No-Partial-AU: err
        Packet-Size: 188
        PAT-Never-Changes: err
        PMT-Never-Changes: err
        PSI-Definition-Precedes-PIDs: err
        No-Partial-Transport-Packets: err
        Max-Rate-PID0-PMT-Vid-Aud: 38000000
        SCTE20-Precedes-ATSC53-CC: err
        Minimum-GOP-Length-Error-Level: warn
        Table [
        PID: 0
        Table-ID: 0
        Maximum-Table-Interval: 250
        ]
        Table [
        Table-ID: 2
        Maximum-Table-Interval: 250
        ]
        Program [
        Video [
        Require-CEA708-DTVCC1: on
        Require-CEA708-CC1: on
        Stream-Type: 2
        Stream-Type-Alternate: 128
        Properties: [
        profile = 4
        level = 4
        aspect_ratio = 3
        chroma_format = 1
        (vertical_size = 1080 horizontal_size = 1920
        (progressive_sequence = 0
        frame_rate = 4) |
        (progressive_sequence = 1
        (frame_rate = 1) | (frame_rate = 2))) |
        (vertical_size = 720 horizontal_size = 1280
        frame_rate = 7
        progressive_sequence = 1)
        ]
        Minimum-GOP-Length: 15
        Maximum-GOP-Length: 15
        PID: 481
        ]
        Audio [
        Stream-Type: 129
        Properties: [
        type = 6
        (channel_mode = 2 bit_rate = 384000) |
        (channel_mode = 7 bit_rate = 384000)
        sample_rate = 48000
        ]
        PID: 482
        ]
        PMT-PID: 480
        PCR-PID: 481
        ]
        end-config
    </configuration>

    <stream-information>
        <transport-rate value="15000000.0"/>
        <duration value="00:27:42.413"/>
        <file-size value="3117026088"/>
        <transport-packet-interval value="188"/>
        <packets-analyzed value="16579926"/>
        <total-errors value="0"/>
        <total-warnings value="3320"/>
    </stream-information>

    <multiplex-summary>
        <pat version="0" pid="0">
            <program program_number="2" pid="0x01E0">
                <pmt version="0" pcr-pid="0x01E1">
                    <stream pid="0x01E2" type="Dolby AC-3 Audio">
                        <descriptor tag="0x05" name="registration_descriptor"/>
                        <descriptor tag="0x0A" name="ISO_639_language_descriptor"/>
                        <descriptor tag="0x81" name="ac3_audio_descriptor"/>
                    </stream>
                    <stream pid="0x01E1" type="MPEG-2 Video">
                    </stream>
                </pmt>
            </program>
        </pat>
    </multiplex-summary>

    <pid-summary>
        <pid-info pid="0x0000" content="PAT"
                  npkt="13601" pkt-rate="12305" payld-rate="12043"/>
        <pid-info pid="0x01E0" content="PMT" programs="2"
                  npkt="13601" pkt-rate="12305" payld-rate="12043"/>
        <pid-info pid="0x01E1" content="MPEG-2 Video, PCR" programs="2"
                  npkt="15844773" pkt-rate="14334901" payld-rate="13999998"
                  errors="0" warnings="3320"/>
        <pid-info pid="0x01E2" content="Dolby AC-3 Audio" programs="2"
                  npkt="466974" pkt-rate="422475" payld-rate="383524"/>
        <pid-info pid="0x1FFF" content="Null"
                  npkt="240977" pkt-rate="218014" payld-rate="218014"/>
    </pid-summary>

    <pid-properties>
        <pid value="0x0000 (0)"/>
        <content value="PAT"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <program-association-table-information>
            <table_id value="0x00"/>
            <version_number value="0"/>
            <transport_stream_id value="0x0001 (1)"/>
        </program-association-table-information>
        <packet-statistics>
            <number-of-packets value="13601"/>
            <first-seen-in-packet-number value="1" time="00:00:00.000"/>
            <last-seen-in-packet-number value="16578712" time="00:27:42.292"/>
            <average-transport-packet-rate value="12305"/>
            <average-payload-rate value="12043"/>
            <average-packet-spacing value="122.2274"/>
            <minimum-packet-spacing value="122.2251"/>
            <packet-number-at-minimum-spacing value="1220" time="00:00:00.122"/>
            <maximum-packet-spacing value="122.3253"/>
            <packet-number-at-maximum-spacing value="7316" time="00:00:00.733"/>
            <minimum-packet-spacing-ends-inclusive value="121.8240"/>
            <maximum-packet-spacing-ends-inclusive value="122.3253"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="13601"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="0"/>
            <number-of-packets-random_access_indicator-is-1 value="0"/>
            <splice_countdown-fields-seen value="No"/>
        </adaptation-field-information>
    </pid-properties>

    <pid-properties>
        <pid value="0x01E0 (480)"/>
        <content value="PMT"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <program-information>
            <pmt-pid-for-program_number value="2"/>
            <listed-in-pat-version_number value="0"/>
        </program-information>
        <program-map-table-information>
            <table_id value="0x02"/>
            <program_number value="2"/>
            <version_number value="0"/>
            <pcr-pid value="0x01E1 (481)"/>
            <section_length value="41"/>
        </program-map-table-information>
        <packet-statistics>
            <number-of-packets value="13601"/>
            <first-seen-in-packet-number value="2" time="00:00:00.000"/>
            <last-seen-in-packet-number value="16578713" time="00:27:42.292"/>
            <average-transport-packet-rate value="12305"/>
            <average-payload-rate value="12043"/>
            <average-packet-spacing value="122.2274"/>
            <minimum-packet-spacing value="122.2251"/>
            <packet-number-at-minimum-spacing value="1221" time="00:00:00.122"/>
            <maximum-packet-spacing value="122.3253"/>
            <packet-number-at-maximum-spacing value="7317" time="00:00:00.733"/>
            <minimum-packet-spacing-ends-inclusive value="0.1003"/>
            <maximum-packet-spacing-ends-inclusive value="122.3253"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="13601"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="0"/>
            <number-of-packets-random_access_indicator-is-1 value="0"/>
            <splice_countdown-fields-seen value="No"/>
        </adaptation-field-information>
    </pid-properties>

    <pid-properties>
        <pid value="0x01E1 (481)"/>
        <content value="MPEG-2 Video, PCR"/>
        <total-errors value="0"/>
        <total-warnings value="3320"/>
        <program-information>
            <member-of-program_number value="2"/>
            <listed-in-pmt-version_number value="0"/>
            <stream_type value="0x02 (H.262 | 13818-2 Video | 11172-2 Constrained Video)"/>
            <listed-as-pcr-pid value="Yes"/>
        </program-information>
        <pcr-statistics>
            <number-of-pcrs-seen value="118328"/>
            <average-pcr-interval value="14.0315"/>
            <minimum-pcr-interval value="0.1003"/>
            <packet-number-at-minimum-pcr-interval value="81142" time="00:00:08.135"/>
            <maximum-pcr-interval value="35.9957"/>
            <packet-number-at-maximum-pcr-interval value="7639694" time="00:12:46.006"/>
            <pcr-jitter-magnitude value="30"/>
        </pcr-statistics>
        <packet-statistics>
            <number-of-packets value="15844773"/>
            <first-seen-in-packet-number value="3" time="00:00:00.000"/>
            <last-seen-in-packet-number value="16559069" time="00:27:40.322"/>
            <average-transport-packet-rate value="14334901"/>
            <average-payload-rate value="13999998"/>
            <average-packet-spacing value="0.1048"/>
            <minimum-packet-spacing value="0.1003"/>
            <packet-number-at-minimum-spacing value="4" time="00:00:00.000"/>
            <maximum-packet-spacing value="23.9637"/>
            <packet-number-at-maximum-spacing value="11225391" time="00:18:45.532"/>
            <minimum-packet-spacing-ends-inclusive value="0.1003"/>
            <maximum-packet-spacing-ends-inclusive value="2091.3621"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="15844773"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="1"/>
            <number-of-packets-random_access_indicator-is-1 value="3318"/>
            <splice_countdown-fields-seen value="No"/>
            <noemptyaf value="Yes"/>
        </adaptation-field-information>
        <pes-information>
            <number-of-pes-headers-seen value="49763"/>
            <pes-stream_id value="0xE0 (224, MPEG Video)"/>
            <number-of-packets-pes_scrambling_control-is-00 value="49763"/>
            <pes-copyright-set value="No"/>
            <number-of-packets-pes_priority-is-1 value="0"/>
            <number-of-ptss-seen value="49763"/>
            <average-pts-interval value="33.3646"/>
            <average-es-buffer-level value="604817"/>
            <minimum-es-buffer-level value="127807"/>
            <packet-number-at-minimum-es-buffer-level value="654323" time="00:01:05.606"/>
            <maximum-es-buffer-level value="718848"/>
            <packet-number-at-maximum-es-buffer-level value="4407" time="00:00:00.441"/>
            <pesalign value="Yes"/>
        </pes-information>
        <video-elementary-stream-information>
            <mpeg-2-video-profile value="Main"/>
            <mpeg-2-video-level value="High"/>
            <mpeg-chroma-format value="4:2:0"/>
            <closed-caption-types-detected value="ATSC (EIA608, EIA708)"/>
            <a-53-cc-channels-detected value="CC1, XDS"/>
            <a-53-dtvcc-services-detected value="1, 2"/>
            <number-of-video-access-units-seen value="49763"/>
            <minimum-number-of-non-i-pictures-between-two-i-pictures value="12"/>
            <maximum-number-of-non-i-pictures-between-two-i-pictures value="14"/>
            <average-number-of-non-i-pictures-between-two-i-pictures value="14"/>
            <percent-of-all-pictures-that-are-p-pictures value="26"/>
            <percent-of-all-pictures-that-are-b-pictures value="66"/>
            <maximum-number-of-b-frames-between-i-or-p-frames value="2"/>
            <video-bit-rate value="14000000"/>
            <vbv-method value="Off"/>
            <vbv-size value="5750784"/>
            <video-multiplexing-buffer-size value="53333"/>
            <video-es-buffer-size value="718848"/>
            <minimum-decode-delay value="0.1050"/>
            <maximum-decode-delay value="0.5229"/>
            <mb-to-eb-transfer-method value="Leak"/>
            <video-frame-rate value="29.9700"/>
            <video-in-film-mode value="No"/>
            <progressive-sequence value="No"/>
            <horizontal-size value="1920"/>
            <vertical-size value="1080"/>
            <aspect-ratio value="16:9"/>
            <sample-aspect-ratio value="1.0000"/>
            <quadbyte value="Yes"/>
            <initial-timecode value="0:00:00:00"/>
            <final-timecode value="0:27:38:21"/>
        </video-elementary-stream-information>

        <xds-information>
            <prograting value="TV-MA L"/>
            <cgms value="Source:Digital SCMS:CopyNever APS:None"/>
        </xds-information>
        <errors>
            <error pid="0x01E1" packet="3182" time="00:00:00.318" code="6-13w"
                   msg="Video GOP length was 12; the configured minimum is 15."/>
            <error pid="0x01E1" packet="8295" time="00:00:00.831" code="6-13w"
                   msg="Video GOP length was 14; the configured minimum is 15."/>
            <error pid="0x01E1" packet="15445" time="00:00:01.548" code="6-13w"
                   msg="Video GOP length was 14; the configured minimum is 15."/>
            <error pid="0x01E1" packet="20010" time="00:00:02.006" code="6-13w"
                   msg="Video GOP length was 14; the configured minimum is 15."/>
            <error pid="0x01E1" packet="25055" time="00:00:02.512" code="6-13w" msg="Video GOP length is less than the configured minimum.
This error was detected 3312 more times in this PID after packet 25055"/>
            <error pid="0x01E1" packet="182128" time="00:00:18.261" code="4-87w"
                   msg="The CEA708 DTVCC packet sequence number was 2, expected 4."/>
            <error pid="0x01E1" packet="658451" time="00:01:06.020" code="4-87w"
                   msg="The CEA708 DTVCC packet sequence number was 0, expected 2."/>
            <error pid="0x01E1" packet="16192511" time="00:27:03.569" code="4-87w"
                   msg="The CEA708 DTVCC packet sequence number was 0, expected 3."/>
        </errors>
    </pid-properties>

    <pid-properties>
        <pid value="0x01E2 (482)"/>
        <content value="Dolby AC-3 Audio"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <program-information>
            <member-of-program_number value="2"/>
            <listed-in-pmt-version_number value="0"/>
            <stream_type value="0x81 (129, AC3 Audio)"/>
            <associated-pcr-pid value="0x01E1 (481)"/>
            <registration_descriptor>
                <tag value="0x05"/>
                <length value="4"/>
                <format_identifier value="0x41432D33 (AC-3)"/>
                <additional_identification_info value="none"/>
            </registration_descriptor>
            <iso_639_language_descriptor>
                <tag value="0x0A (10)"/>
                <length value="4"/>
                <iso_639_language_code value="0x656E67 (eng)"/>
                <audio_type value="0 (Undefined)"/>
            </iso_639_language_descriptor>
            <ac3_audio_descriptor>
                <tag value="0x81 (129)"/>
                <length value="4"/>
                <sample_rate_code value="0 (48 kHz)"/>
                <bsid value="8"/>
                <bit_rate_limit value="0"/>
                <bit_rate_code value="14 (384 kbps)"/>
                <surround_mode value="0 (Not indicated)"/>
                <bsmod value="0 (Main: Complete Main)"/>
                <num_channels value="2 (2/0)"/>
                <full_svc value="1"/>
                <langcod value="0"/>
            </ac3_audio_descriptor>
        </program-information>
        <packet-statistics>
            <number-of-packets value="466974"/>
            <first-seen-in-packet-number value="12" time="00:00:00.001"/>
            <last-seen-in-packet-number value="16562164" time="00:27:40.632"/>
            <average-transport-packet-rate value="422475"/>
            <average-payload-rate value="383524"/>
            <average-packet-spacing value="3.5562"/>
            <minimum-packet-spacing value="1.1029"/>
            <packet-number-at-minimum-spacing value="23" time="00:00:00.002"/>
            <maximum-packet-spacing value="326.2677"/>
            <packet-number-at-maximum-spacing value="3409" time="00:00:00.341"/>
            <minimum-packet-spacing-ends-inclusive value="1.1029"/>
            <maximum-packet-spacing-ends-inclusive value="1781.0368"/>
        </packet-statistics>
        <transport-header-information>
            <number-of-packets-transport_error_indicator-is-1 value="0"/>
            <number-of-packets-transport_priority-is-1 value="0"/>
            <number-of-packets-transport_scrambling_control-is-00 value="466974"/>
            <number-of-duplicate-packets-seen value="0"/>
            <number-of-continuity-counter-errors-seen value="0"/>
        </transport-header-information>
        <adaptation-field-information>
            <number-of-packets-discontinuity_indicator-is-1 value="0"/>
            <number-of-packets-random_access_indicator-is-1 value="0"/>
            <splice_countdown-fields-seen value="No"/>
        </adaptation-field-information>
        <pes-information>
            <number-of-pes-headers-seen value="51886"/>
            <pes-stream_id value="0xBD (189, Private stream 1)"/>
            <number-of-packets-pes_scrambling_control-is-00 value="51886"/>
            <pes-copyright-set value="No"/>
            <number-of-packets-pes_priority-is-1 value="0"/>
            <number-of-ptss-seen value="51886"/>
            <average-pts-interval value="32.0054"/>
            <average-es-buffer-level value="1695"/>
            <minimum-es-buffer-level value="920"/>
            <packet-number-at-minimum-es-buffer-level value="3409" time="00:00:00.341"/>
            <maximum-es-buffer-level value="2470"/>
            <packet-number-at-maximum-es-buffer-level value="3409" time="00:00:00.341"/>
        </pes-information>
        <audio-elementary-stream-information>
            <audio-type value="Dolby AC-3"/>
            <number-of-audio-access-units-seen value="51886"/>
            <average-number-of-audio-frames-per-pes-packet value="1"/>
            <sample-rate value="48000"/>
            <bit-rate value="384000"/>
            <number-and-location-of-channels value="Stereo channels: L, R: 2/0"/>
            <ac3-audio-service-type value="Main audio service: Complete Main (CM)"/>
            <low-frequency-effects-flag value="0"/>
            <nominal-frame-size value="1536"/>
            <dialogue-normalization value="-16"/>
            <main-audio-buffer-size value="2592"/>
        </audio-elementary-stream-information>
    </pid-properties>

    <pid-properties>
        <pid value="0x1FFF (8191)"/>
        <content value="Null"/>
        <total-errors value="0"/>
        <total-warnings value="0"/>
        <packet-statistics>
            <number-of-packets value="240977"/>
            <first-seen-in-packet-number value="4265" time="00:00:00.427"/>
            <last-seen-in-packet-number value="16579926" time="00:27:42.413"/>
            <average-transport-packet-rate value="218014"/>
            <average-payload-rate value="218014"/>
            <average-packet-spacing value="6.8969"/>
            <minimum-packet-spacing value="0.1003"/>
            <packet-number-at-minimum-spacing value="4266" time="00:00:00.427"/>
            <maximum-packet-spacing value="8138.4448"/>
            <packet-number-at-maximum-spacing value="14548160" time="00:24:18.695"/>
            <minimum-packet-spacing-ends-inclusive value="0.1003"/>
            <maximum-packet-spacing-ends-inclusive value="8138.4448"/>
        </packet-statistics>
    </pid-properties>

<errors>
  <error pid="0x01E1" packet="661" time="00:00:00.264" code="6-13w" msg="Video GOP length was 12; the configured minimum is 15."/>
  <error pid="0x01E1" packet="1973" time="00:00:00.790" code="6-13w" msg="Video GOP length was 14; the configured minimum is 15."/>
  <error pid="0x01E1" packet="3224" time="00:00:01.292" code="6-13w" msg="Video GOP length was 14; the configured minimum is 15."/>
  <error pid="0x01E1" packet="4472" time="00:00:01.793" code="6-13w" msg="Video GOP length was 14; the configured minimum is 15."/>
  <error pid="0x01E1" packet="5718" time="00:00:02.292" code="6-13w" msg="Video GOP length is less than the configured minimum.
This error was detected 129 more times in this PID after packet 5718"/>
  <error pid="0x01E1" code="6-53e" msg="Missing CEA 708 CC1 data; configuration requires."/>
  <error pid="0x01E1" code="6-54e" msg="Missing CEA 708 CC1 data; configuration requires."/>
</errors>

</tsa-report>
"""
        generatedContent2.manzanitaCGMS = "Source:Digital SCMS:CopyNever APS:None"
        generatedContent2.manzanitaVChip = "TV-MA, L"
        generatedContent2.manzanitaRuntime = 1750

        happyAsset.contents.add(content)
        happyAsset.contents.add(generatedContent1)
        happyAsset.contents.add(generatedContent2)
        happyAsset.save(flush: true)


        assertEquals(ManzanitaResultStates.FAILED.toString(), content.manzanitaResult)
        assertEquals(ManzanitaResultStates.FAILED.toString(), generatedContent1.manzanitaResult)
        assertEquals(ManzanitaResultStates.FAILED.toString(), generatedContent2.manzanitaResult)

        assetService.updatePreviewContentsManzanitaResult(happyAsset)

        assertEquals(ManzanitaResultStates.PASSED.toString(), content.manzanitaResult)
        assertEquals(ManzanitaResultStates.FAILED.toString(), generatedContent1.manzanitaResult)
        assertEquals(ManzanitaResultStates.PASSED.toString(), generatedContent2.manzanitaResult)
    }

}
