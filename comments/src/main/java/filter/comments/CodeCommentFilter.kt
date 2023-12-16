package filter.comments

import java.util.Vector

/** Filter the Code Comments.
 */
class CodeCommentFilter(
	/** The input to be filtered.
	 */
	input: String,
) {
	/** Whether the input contained any comments.
	 */
	val hadComments: Boolean

	/** The result of the filter.
	 */
	val output: String

	init {
		var intermediateStr: String?
		// Check if the input contains any multi-line comments
		intermediateStr = removeMultilineComments(input)
		// Check for and remove single-line Comments
		val lineCommentOperation = removeLineComments(
			intermediateStr ?: input
		)
		if (lineCommentOperation != null) {
			// Comments were found, overwrite intermediate
			intermediateStr = lineCommentOperation
		}
		hadComments = intermediateStr != null
		output = intermediateStr ?: input
	}

	/** Remove the Multiline comments.
	 * @param input The input string.
	 * @return The string with the open comments removed.
	 */
	fun removeMultilineComments(
		input: String
	) : String? {
		// Get a list of all comments
		val comments = getMultilineCommentLocations(input)
			?: return null
		return buildString {
			if (comments.size < 2) {
				// There is only one block comment
				val commentData = MultilineComment(input, comments[0])
				append(
					input.subSequence(
						0, commentData.startOfLine
					)
				)
				commentData.getReplacementText()?.also {
					append(it)
				}
				append(
					input.subSequence(
						commentData.endOfLine, input.length
					)
				)
			} else {
				// Apply General Case
				var listIndex = 0
				var builderIndex = 0
				// Loop through the comments
				while (listIndex < comments.size) {
					// Use specialized class to handle the comment
					val commentData = MultilineComment(
						input, comments[listIndex]
					)
					// Append up to the comment
					append(
						input.subSequence(
							builderIndex, commentData.startOfLine
						)
					)
					commentData.getReplacementText()?.also {
						append(it)
					}
					builderIndex = commentData.endOfLine
					listIndex++
				}
				if (builderIndex < input.length) {
					append(
						input.subSequence(
							builderIndex, input.length
						)
					)
				}
			}
		}
	}

	/** Determine the locations of the multiline comments.
	 * @param input The String input to search in.
	 * @return A Vector of Pairs of indices, or null if no comments were found or there is an unclosed comment.
	 */
	fun getMultilineCommentLocations(
		input: String,
	) : Vector<Long>? {
		// Check if any multi-line comments are present
		var openIndex = input.indexOf("/*")
		if (openIndex == -1)
			return null
		// Store all of the Comments in a Vector
		val comments = Vector<Long>(2)
		while (openIndex > -1) {
			val closeIndex = input.indexOf(
				"*/", openIndex + 2
			)
			if (closeIndex == -1)
				return null // Unclosed comment
			comments.add(
				openIndex.toLong().shl(32).xor(closeIndex.toLong())
			)
			openIndex = input.indexOf(
				"/*", closeIndex + 2
			)
		}
		return comments
	}

	/** Remove the line comments.
	 * @param input The input string.
	 * @return The processed string, or null if no line comments were found.
	 */
	fun removeLineComments(
		input: String
	) : String? {
		var index = input.indexOf("//")
		if (index == -1)
			return null
		// Create a vector of all locations
		val comments = Vector<Int>()
		while (index > -1) {
			comments.add(index)
			index = input.indexOf("//", index + 1)
		}
		return buildString {
			if (comments.size < 2) {
				// The simple case with one comment
				val endOfLine = input.indexOf("\n", comments[0])
				if (endOfLine == -1) {
					append(input.subSequence(0, comments[0]).trim())
				} else {
					append(input.subSequence(0, comments[0]).trim())
					append(input.subSequence(endOfLine, input.length))
				}
			} else { // Use the General Case
				var listIndex = 0
				var builderIndex = 0
				// Loop through the comments
				while (listIndex < comments.size) {
					val commentIndex = comments[listIndex]
					// Find the first newline after the comment
					val endOfLine = input.indexOf(
						"\n", commentIndex
					)
					if (endOfLine == -1) {
						// There is no newline after the comment
						append(
							input.subSequence(
								builderIndex, commentIndex
							).trim()
						)
						break
					} else {
						// Append up to the comment
						append(
							input.subSequence(
								builderIndex, commentIndex
							).trim()
						)
						// Set the builder index to the newline
						builderIndex = endOfLine
					}
					listIndex++
				}
				if (builderIndex < input.length) {
					append(
						input.subSequence(
							builderIndex, input.length
						)
					)
				}
			}
		}
	}

}