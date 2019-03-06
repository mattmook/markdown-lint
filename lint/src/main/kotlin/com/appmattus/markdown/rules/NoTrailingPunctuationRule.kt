package com.appmattus.markdown.rules

import com.appmattus.markdown.ErrorReporter
import com.appmattus.markdown.MarkdownDocument
import com.appmattus.markdown.Rule
import com.appmattus.markdown.RuleSetup
import com.vladsch.flexmark.ast.ListItem

class NoTrailingPunctuationRule(
    private val punctuation: String = ".,;:!?",
    override val config: RuleSetup.Builder.() -> Unit = {}
) : Rule("NoTrailingPunctuation") {

    override val description = "Trailing punctuation in header"
    override val tags = listOf("headers")

    override fun visitDocument(document: MarkdownDocument, errorReporter: ErrorReporter) {
        document.headings.filterNot { it.parent is ListItem }.filter { heading ->
            punctuation.contains(heading.text.lastChar())
        }.forEach { heading ->
            errorReporter.reportError(heading.startOffset, heading.endOffset, description)
        }
    }
}

/*
rule "MD026", "Trailing punctuation in header" do
  tags :headers
  aliases 'no-trailing-punctuation'
  params :punctuation => '.,;:!?'
  check do |doc|
    doc.find_type(:header).select {
      |h| h[:raw_text].match(/[#{params[:punctuation]}]$/) }.map {
      |h| doc.element_linenumber(h) }
  end
end
 */
