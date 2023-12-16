package filter.comments

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

/** Testing MultilineComment.
 */
class MultilineCommentTest {

	val singleLineInput: String = "/** single line */"

	val twoLineInput: String = """
		/** two line
		 */
	""".trimIndent()

	val prefixedLineInput: String = """
		var a = 1; /** prefix
		 */
	""".trimIndent()

	val suffixLineInput: String = """
		/** suffix
		 */ var a = 1;
	""".trimIndent()

	val bothSidesInput: String = """
		var a = 1;/** code on both
		sides of the comment */var x = Math.pi;
	""".trimIndent()

	/** Get the pair of indices for the comment.
	 */
	fun getPair(input: String): Long {
		val openIndex = input.indexOf("/*")
		val closeIndex = input.indexOf("*/")
		return openIndex.toLong().shl(32).xor(closeIndex.toLong())
	}

	val mSingleLine = MultilineComment(
		singleLineInput, getPair(singleLineInput)
	)
	val mTwoLine = MultilineComment(
		twoLineInput, getPair(twoLineInput)
	)
	val mPrefix = MultilineComment(
		prefixedLineInput, getPair(prefixedLineInput)
	)
	val mSuffix = MultilineComment(
		suffixLineInput, getPair(suffixLineInput)
	)
	val mBoth = MultilineComment(
		bothSidesInput, getPair(bothSidesInput)
	)

	@Test
	fun testCommentIndices_SingleLine() {
		assertEquals(0, mSingleLine.openIndex)
		assertEquals(singleLineInput.length - 2, mSingleLine.closeIndex)
	}

	@Test
	fun testCommentIndices_TwoLine() {
		assertEquals(0, mTwoLine.openIndex)
		assertEquals(twoLineInput.length - 2, mTwoLine.closeIndex)
	}

	@Test
	fun testLineIndices_SingleLine() {
		assertEquals(0, mSingleLine.startOfLine)
		assertEquals(singleLineInput.length - 1, mSingleLine.endOfLine)
	}

	@Test
	fun testLineIndices_TwoLine() {
		assertEquals(0, mTwoLine.startOfLine)
		assertEquals(twoLineInput.length - 1, mTwoLine.endOfLine)
	}

	@Test
	fun testNewLineBooleans_SingleLine_ReturnsTrue() {
		assertTrue(mSingleLine.opensOnNewLine)
		assertTrue(mSingleLine.closesOnNewLine)
	}

	@Test
	fun testNewLineBooleans_TwoLine_ReturnsTrue() {
		assertTrue(mTwoLine.opensOnNewLine)
		assertTrue(mTwoLine.closesOnNewLine)
	}

	@Test
	fun testNewLineBooleans_Prefix_() {
		assertFalse(mPrefix.opensOnNewLine)
		assertTrue(mPrefix.closesOnNewLine)
	}

	@Test
	fun testNewLineBooleans_Suffix_() {
		assertTrue(mSuffix.opensOnNewLine)
		assertFalse(mSuffix.closesOnNewLine)
	}

	@Test
	fun testNewLineBooleans_Both_() {
		assertFalse(mBoth.opensOnNewLine)
		assertFalse(mBoth.closesOnNewLine)
	}

	@Test
	fun testGetReplacementText_SingleLine_ReturnsNull() {
		assertNull(mSingleLine.getReplacementText())
	}

	@Test
	fun testGetReplacementText_TwoLine_ReturnsNull() {
		assertNull(mTwoLine.getReplacementText())
	}

	@Test
	fun testGetReplacementText_Prefix_ReturnsNewLine() {
		assertEquals(
			"\n", mPrefix.getReplacementText()
		)
	}

	@Test
	fun testGetReplacementText_Suffix_ReturnsNewLine() {
		assertEquals(
			"\n", mSuffix.getReplacementText()
		)
	}

	@Test
	fun testGetReplacementTest_BothSides_ReturnsEmptyString() {
		assertEquals(
			"", mBoth.getReplacementText()
		)
	}

}