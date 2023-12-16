package filter.comments

/** Test Data Provider for Java Tests.
 */
object JavaTestDataProvider {

	/** Returns an empty class.
	 */
	fun getEmptyClass(): String {
		return """
		public class EmptyClass {
			
		}
		""".trimIndent()
	}

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

}