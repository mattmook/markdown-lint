package com.appmattus.markdown.rules

import com.appmattus.markdown.ErrorReporter
import com.appmattus.markdown.MarkdownDocument
import com.appmattus.markdown.Rule
import com.appmattus.markdown.RuleSetup

class FencedCodeLanguageRule(override val config: RuleSetup.Builder.() -> Unit = {}) : Rule("FencedCodeLanguage") {

    override val description = "Fenced code blocks should have a language specified"
    override val tags = listOf("code", "language")

    override fun visitDocument(document: MarkdownDocument, errorReporter: ErrorReporter) {
        document.fencedCodeBlocks.forEach {
            if (it.info.isEmpty) {
                errorReporter.reportError(it.startOffset, it.endOffset, description)
            }
        }
    }
}

/*
rule "MD040", "Fenced code blocks should have a language specified" do
  tags :code, :language
  aliases 'fenced-code-language'
  check do |doc|
    # Kramdown parses code blocks with language settings as code blocks with
    # the class attribute set to language-languagename.
    doc.element_linenumbers(doc.find_type_elements(:codeblock).select{|i|
      not i.attr['class'].to_s.start_with?("language-") and
        not doc.element_line(i).start_with?("    ")})
  end
end
 */
