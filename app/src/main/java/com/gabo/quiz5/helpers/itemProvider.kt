package com.gabo.quiz5.helpers

import com.gabo.quiz5.model.FieldModel
import com.gabo.quiz5.model.Fields


object itemProvider {
    var pinField = Fields(
        listOf(
            FieldModel(12341, "PIN", "number", "number", true, true, "jemala"),
            FieldModel(12341, "CONFIRM PIN", "number", "number", true, true, "jemala")
        )
    )
}