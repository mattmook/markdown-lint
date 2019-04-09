package com.appmattus.markdown.rules

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature

object ListIndentRuleTest : Spek({
    Feature("ListIndentRule") {
        FileRuleScenario(files = listOf("list-indent-rule-different-parents.md")) { ListIndentRule() }

        FileRuleScenario { ListIndentRule() }
    }
})
