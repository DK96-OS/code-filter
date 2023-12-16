package filter.comments

/** Processes a multiline comment, determining it's properties.
 */
open class MultilineComment(
	/** The input string containing the Comment.
	 */
	input: String,
	/** The opening index of the comment.
	 */
	val openIndex: Int,
	/** The closing index of the comment.
	 */
	val closeIndex: Int,
) {

	/** Constructor.
	 * @param input
	 * @param compactIndices The Compact data containing both indices.
	 */
	constructor(
		input: String,
		compactIndices: Long,
	) : this(
		input,
		compactIndices.ushr(32).toInt(),
		compactIndices.toInt()
	)

	/** The Start of the line before the comment.
	 */
	val startOfLine: Int

	/** The End of the line after the comment.
	 */
	val endOfLine: Int

	/** Whether the Comment opens on a new line.
	 */
	val opensOnNewLine: Boolean

	/** Whether the Comment closes on a new line.
	 */
	val closesOnNewLine: Boolean

	init {
		val lineStart = input.lastIndexOf('\n', openIndex)
		val lineEnd = input.indexOf('\n', closeIndex)
		startOfLine = if (lineStart == -1) {
			0 // The comment starts on the first line
		} else
			lineStart
		val startText = input.substring(
			startOfLine, openIndex
		)
		opensOnNewLine = startText.isBlank()
		endOfLine = if (lineEnd == -1) {
			input.length - 1 // The comment ends on the last line
		} else
			lineEnd
		closesOnNewLine = input.substring(
			closeIndex + 2, endOfLine + 1
		).isBlank()
	}

	/** Get the text to replace the comment with.
	 * This will be null if the comment is on it's own lines.
	 */
	fun getReplacementText(): String? {
		if (opensOnNewLine && closesOnNewLine)
			return null
		return if (opensOnNewLine || closesOnNewLine) "\n"
		else ""
	}

}