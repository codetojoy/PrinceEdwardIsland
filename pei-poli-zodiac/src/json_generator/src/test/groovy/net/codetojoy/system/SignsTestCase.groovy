
package net.codetojoy.system

import org.junit.*
import static org.junit.Assert.*

class SignsTestCase {
    def signs = new Signs()

    @Test
    void testCanary() {
        assertEquals(2+2, 4)
    }

    @Test
    void testGetDisplaySign_basic() {
        def dataSign = "aries"

	// test
	def result = signs.getDisplaySign(dataSign)

        assertEquals("Aries", result)
    }

    @Test
    void testGetDisplaySign_unknown() {
        def dataSign = "unknown"

	// test
	def result = signs.getDisplaySign(dataSign)

        assertEquals("Unknown", result)
    }

    @Test(expected=IllegalStateException.class)
    void testGetDisplaySign_pathological() {
        def dataSign = "bogus"

	// test
	def result = signs.getDisplaySign(dataSign)
    }
}