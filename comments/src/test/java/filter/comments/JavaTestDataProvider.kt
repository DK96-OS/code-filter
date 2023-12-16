package filter.comments

/** Test Data Provider for Java code samples.
 */
object JavaTestDataProvider {

	/** Returns a simple class with a main method,
	 * and a package declaration.
	 */
	fun getMainClass(): String {
		return """
			package package1;

			public final class Main {
			
				public static void main(String[] args) {
					System.out.println("Hello World!");
				}
				
			}
		""".trimIndent()
	}

	/** Returns a string containing a method, and one line comment.
	 */
	fun getLineCommentMethod(): String {
		return """
		// A Line Comment
		public static void main() {
		}
		""".trimIndent()
	}

	/** Returns a string containing a class, and 3 line comments.
	 */
	fun getLineCommentClass(): String {
		return """
		// A Line Comment
		class NameOfClass {
			public static void main(String[] args) {
				System.out.println("Hello World!"); // Says Hello
				// Another line comment
			}
		}
		""".trimIndent()
	}

	/** Test Project File 1 contains 2 multiline comments.
	 */
	fun getTestProjectFile1(): String {
		return """
			package package1;

			/** The First File in this Test-Project.
			 */
			public final class File1 {

				public void updateInstance() {
					System.out.println("updateInstance");
				}

				/** Returns the number 12.
			 	 * @return The number 12.
				 */
				public int getNumber() {
					return 12;
				}

			}
		""".trimIndent()
	}

	/** Test Project File 1 contains 1 multiline comment.
	 */
	fun getTestProjectFile2(): String {
		return """
			package package1;

			import math.sqrt;

			/** The Second File in this Test-Project.
			 */
			final class File2 {

				float calculate(float input) {
					float answer = sqrt(input);
					return (answer * 2f) * (answer * 2f);
				}

			}
		""".trimIndent()
	}

	/** A String containing an unclosed comment.
	 */
	fun getUnclosedMultilineComment(): String {
		return """
			class UnclosedMultilineTest {
				/**
				public void method() {}
			}
		""".trimIndent()
	}

}