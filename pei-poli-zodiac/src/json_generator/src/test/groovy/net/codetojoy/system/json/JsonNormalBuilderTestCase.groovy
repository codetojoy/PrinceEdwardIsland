package net.codetojoy.system.json

import net.codetojoy.custom.Info
import net.codetojoy.system.*

import groovy.json.*

import org.junit.*
import static org.junit.Assert.*

class JsonBuilderTestCase {
    def jsonBuilder = new JsonNormalBuilder()

    @Test
    void testBuildNormal() {
        def name = "Mozart"
        def zodiac = "Aquarius"
        def birthday = "January 27"
        def riding = "Salzburg"
        def party = "PC"     // which party would Mozart join?

        def info = new Info(honourific: "Hon.", name: name, zodiac: zodiac,
                            birthday: birthday, riding: riding,
                           party: party, hasSign: true)
        def infos = []
        infos << info

        def locale = new Locale("en")

        // test
        def result = jsonBuilder.buildNormal(infos, locale)

        // this is ridiculous, but YOLO:

        def jsonSlurper = new JsonSlurper()
        def resultMap = jsonSlurper.parseText(result)
        def topLevelChildren = resultMap["children"]

        assertEquals(resultMap["name"], "zodiac")
        assertEquals(topLevelChildren.size(), 12)

        topLevelChildren.each { child ->
            def sign = child["name"]
            assertTrue(Signs.DISPLAY_SIGNS.contains(sign))
            assertEquals(child["children"].size(), 1)
            def grandChild = child["children"][0]

            if (sign == locale.get("Aquarius")) {
                assertEquals(grandChild["name"], "Hon. Mozart")
                assertEquals(grandChild["party"], "PC")
                assertEquals(grandChild["size"], 1000)
            } else {
                assertEquals(grandChild["name"], BuilderConstants.NONE)
                assertEquals(grandChild["party"], "unknown")
                assertEquals(grandChild["size"], 1000)
            }
        }
    }

    @Test
    void testBuildNormal_Fr() {
        def name = "Mozart"
        def zodiac = "Aquarius"
        def birthday = "January 27"
        def riding = "Salzburg"
        def party = "PC"     // which party would Mozart join?

        def info = new Info(honourific: "Hon.", name: name, zodiac: zodiac,
                            birthday: birthday, riding: riding,
                           party: party, hasSign: true)
        def infos = []
        infos << info

        def locale = new Locale("fr")

        // test
        def result = jsonBuilder.buildNormal(infos, locale)

        // this is ridiculous, but YOLO:

        def jsonSlurper = new JsonSlurper()
        def resultMap = jsonSlurper.parseText(result)
        def topLevelChildren = resultMap["children"]

        assertEquals(resultMap["name"], "zodiac")
        assertEquals(topLevelChildren.size(), 12)

        topLevelChildren.each { child ->
            def sign = child["name"]
            assertTrue(Signs.DISPLAY_SIGNS.contains(locale.getInverse(sign)))
            assertEquals(child["children"].size(), 1)
            def grandChild = child["children"][0]

            if (sign == locale.get("Aquarius")) {
                assertEquals(grandChild["name"], "Hon. Mozart")
                assertEquals(grandChild["party"], "PC")
                assertEquals(grandChild["size"], 1000)
            } else {
                assertEquals(grandChild["name"], locale.get(BuilderConstants.NONE))
                assertEquals(grandChild["party"], "unknown")
                assertEquals(grandChild["size"], 1000)
            }
        }
    }
}
