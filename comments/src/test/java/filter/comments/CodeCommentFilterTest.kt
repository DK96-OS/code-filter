package filter.comments

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/** Testing CodeCommentFilter.
 */
class CodeCommentFilterTest {

	private lateinit var mInstance: CodeCommentFilter

	/** An Input String with no comments.
	 * Should not be affected by the filter.
	 */
	val noCommentInput: String = JavaTestDataProvider.getMainClass()

	/** An Input String with block comments.
	 * Contains 2 block comments, one on the class,
	 * and one on a member method.
	 */
	val blockCommentInput: String = JavaTestDataProvider.getTestProjectFile1()

	/** An Input String with one Multiline comment.
	 * Contains 1 block comment.
	 */
	val blockCommentInput2: String = JavaTestDataProvider.getTestProjectFile2()

	/** An Input String with 3 line comments.
	 * 2 comments are on their own lines, and another
	 * is on the same line as a statement.
	 */
	val lineCommentInput: String = JavaTestDataProvider.getLineCommentClass()

	@Before
	fun testSetup() {
		mInstance = CodeCommentFilter(noCommentInput)
	}

	@Test
	fun testInitialCondition() {
		assertEquals(
			noCommentInput,
			mInstance.output
		)
		assertFalse(
			mInstance.hadComments
		)
	}

	@Test
	fun testGetOutput_LineComment() {
		mInstance = CodeCommentFilter(lineCommentInput)
		assertEquals(
			"""
				class NameOfClass {
					public static void main(String[] args) {
						System.out.println("Hello World!");
					}
				}
			""".trimIndent(),
			mInstance.output
		)
		assertTrue(
			mInstance.hadComments
		)
	}

	@Test
	fun testGetOutput_BlockComment() {
		mInstance = CodeCommentFilter(blockCommentInput)
		assertEquals(
			"""
			package package1;
			
			public final class File1 {
			
				public void updateInstance() {
					System.out.println("updateInstance");
				}
			
				public int getNumber() {
					return 12;
				}
			
			}
			""".trimIndent(),
			mInstance.output
		)
		assertTrue(
			mInstance.hadComments
		)
	}

	@Test
	fun testGetOutput_BlockComment2_() {
		mInstance = CodeCommentFilter(blockCommentInput2)
		assertEquals(
			"""
			package package1;
			
			import math.sqrt;
			
			final class File2 {
			
				float calculate(float input) {
					float answer = sqrt(input);
					return (answer * 2f) * (answer * 2f);
				}
			
			}
			""".trimIndent(),
			mInstance.output
		)
		assertTrue(
			mInstance.hadComments
		)
	}

	@Test
	fun testRemoveLineComments_NoComment_ReturnsNull() {
		assertNull(
			mInstance.removeLineComments(
				noCommentInput
			)
		)
	}

	@Test
	fun testRemoveLineComments_LineComment_ReturnsString() {
		val result = mInstance.removeLineComments(
			lineCommentInput
		)
		assertNotNull(result)
		assertEquals(
			"""
				class NameOfClass {
					public static void main(String[] args) {
						System.out.println("Hello World!");
					}
				}
			""".trimIndent(),
			result
		)
	}

	@Test
	fun testRemoveMultilineComments_NoComment_ReturnsNull() {
		assertNull(
			mInstance.removeMultilineComments(
				noCommentInput
			)
		)
	}

	@Test
	fun testRemoveMultilineComments_LineComment_ReturnsNull() {
		assertNull(
			mInstance.removeMultilineComments(
				lineCommentInput
			)
		)
	}

	@Test
	fun testRemoveMultilineComments_BlockComment_ReturnsString() {
		val result = mInstance.removeMultilineComments(
			blockCommentInput
		)
		assertNotNull(result)
		assertEquals(
			"""
			package package1;

			public final class File1 {

				public void updateInstance() {
					System.out.println("updateInstance");
				}

				public int getNumber() {
					return 12;
				}

			}
			""".trimIndent(),
			result
		)
	}

}