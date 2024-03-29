package net.codetojoy.system.json

import net.codetojoy.custom.Info
import net.codetojoy.system.*

import groovy.json.*

import org.junit.*
import static org.junit.Assert.*

class JsonElementBuilderTestCase {
    def jsonBuilder = new JsonElementBuilder()

    @Test
    void testBuildWithElements_En() {
        def name = "Mozart"
        def zodiac = "Aquarius"
        def birthday = "January 27"
        def riding = "Salzburg"
        def party = "PC"     // which party would Mozart join?

        def info = new Info(honourific: "", name: name, zodiac: zodiac,
                            birthday: birthday, riding: riding,
                           party: party, hasSign: true)
        def infos = []
        infos << info

        def locale = new Locale("en")

        // test
        def result = jsonBuilder.buildWithElements(infos, locale)

        // this is ridiculous, but YOLO:

        def jsonSlurper = new JsonSlurper()
        def resultMap = jsonSlurper.parseText(result)
        def topLevelChildren = resultMap["children"]

        assertEquals(resultMap["name"], "zodiac")
        assertEquals(topLevelChildren.size(), 4)

        def fireNode = topLevelChildren[0]
        def waterNode = topLevelChildren[1]
        def airNode = topLevelChildren[2]
        def earthNode = topLevelChildren[3]

        assertEquals(fireNode["name"], "Fire")
        assertEquals(waterNode["name"], "Water")
        assertEquals(airNode["name"], "Air")
        assertEquals(earthNode["name"], "Earth")

        def fireChildren = topLevelChildren[0]["children"]
        def waterChildren = topLevelChildren[1]["children"]
        def airChildren = topLevelChildren[2]["children"]
        def earthChildren = topLevelChildren[3]["children"]

        fireChildren.each { child ->
            def sign = child["name"]
            assertTrue(Signs.DISPLAY_SIGNS.contains(sign))
            assertEquals(child["children"].size(), 1)
            def grandChild = child["children"][0]

            assertEquals(grandChild["name"], BuilderConstants.NONE)
            assertEquals(grandChild["party"], "unknown")
            assertEquals(grandChild["size"], 1000)
        }

        waterChildren.each { child ->
            def sign = child["name"]
            assertTrue(Signs.DISPLAY_SIGNS.contains(sign))
            assertEquals(child["children"].size(), 1)
            def grandChild = child["children"][0]

            assertEquals(grandChild["name"], BuilderConstants.NONE)
            assertEquals(grandChild["party"], "unknown")
            assertEquals(grandChild["size"], 1000)
        }

        airChildren.each { child ->
            def sign = child["name"]
            assertTrue(Signs.DISPLAY_SIGNS.contains(sign))
            assertEquals(child["children"].size(), 1)
            def grandChild = child["children"][0]

            if (sign == locale.get("Aquarius")) {
                assertEquals(grandChild["name"], "Mozart")
                assertEquals(grandChild["party"], "PC")
                assertEquals(grandChild["size"], 1000)
            } else {
                assertEquals(grandChild["name"], BuilderConstants.NONE)
                assertEquals(grandChild["party"], "unknown")
                assertEquals(grandChild["size"], 1000)
            }
        }

        earthChildren.each { child ->
            def sign = child["name"]
            assertTrue(Signs.DISPLAY_SIGNS.contains(sign))
            assertEquals(child["children"].size(), 1)
            def grandChild = child["children"][0]

            assertEquals(grandChild["name"], BuilderConstants.NONE)
            assertEquals(grandChild["party"], "unknown")
            assertEquals(grandChild["size"], 1000)
        }
    }

    @Test
    void testBuildWithElements_Fr() {
        def name = "Mozart"
        def zodiac = "Aquarius"
        def birthday = "January 27"
        def riding = "Salzburg"
        def party = "PC"     // which party would Mozart join?

        def info = new Info(honourific: "", name: name, zodiac: zodiac,
                            birthday: birthday, riding: riding,
                           party: party, hasSign: true)
        def infos = []
        infos << info

        def locale = new Locale("fr")

        // test
        def result = jsonBuilder.buildWithElements(infos, locale)

        // this is ridiculous, but YOLO:

        def jsonSlurper = new JsonSlurper()
        def resultMap = jsonSlurper.parseText(result)
        def topLevelChildren = resultMap["children"]

        assertEquals(resultMap["name"], "zodiac")
        assertEquals(topLevelChildren.size(), 4)

        def fireNode = topLevelChildren[0]
        def waterNode = topLevelChildren[1]
        def airNode = topLevelChildren[2]
        def earthNode = topLevelChildren[3]

        assertEquals(fireNode["name"], locale.get(Signs.FIRE))
        assertEquals(waterNode["name"], locale.get(Signs.WATER))
        assertEquals(airNode["name"], locale.get(Signs.AIR))
        assertEquals(earthNode["name"], locale.get(Signs.EARTH))

        def fireChildren = topLevelChildren[0]["children"]
        def waterChildren = topLevelChildren[1]["children"]
        def airChildren = topLevelChildren[2]["children"]
        def earthChildren = topLevelChildren[3]["children"]

        fireChildren.each { child ->
            def sign = child["name"]
            assertTrue(Signs.DISPLAY_SIGNS.contains(locale.getInverse(sign)))
            assertEquals(child["children"].size(), 1)
            def grandChild = child["children"][0]

            assertEquals(grandChild["name"], locale.get(BuilderConstants.NONE))
            assertEquals(grandChild["party"], "unknown")
            assertEquals(grandChild["size"], 1000)
        }

        waterChildren.each { child ->
            def sign = child["name"]
            assertTrue(Signs.DISPLAY_SIGNS.contains(locale.getInverse(sign)))
            assertEquals(child["children"].size(), 1)
            def grandChild = child["children"][0]

            assertEquals(grandChild["name"], locale.get(BuilderConstants.NONE))
            assertEquals(grandChild["party"], "unknown")
            assertEquals(grandChild["size"], 1000)
        }

        airChildren.each { child ->
            def sign = child["name"]
            assertTrue(Signs.DISPLAY_SIGNS.contains(locale.getInverse(sign)))
            assertEquals(child["children"].size(), 1)
            def grandChild = child["children"][0]

            if (sign == locale.get("Aquarius")) {
                assertEquals(grandChild["name"], "Mozart")
                assertEquals(grandChild["party"], "PC")
                assertEquals(grandChild["size"], 1000)
            } else {
                assertEquals(grandChild["name"], locale.get(BuilderConstants.NONE))
                assertEquals(grandChild["party"], "unknown")
                assertEquals(grandChild["size"], 1000)
            }
        }

        earthChildren.each { child ->
            def sign = child["name"]
            assertTrue(Signs.DISPLAY_SIGNS.contains(locale.getInverse(sign)))
            assertEquals(child["children"].size(), 1)
            def grandChild = child["children"][0]

            assertEquals(grandChild["name"], locale.get(BuilderConstants.NONE))
            assertEquals(grandChild["party"], "unknown")
            assertEquals(grandChild["size"], 1000)
        }
    }
}
