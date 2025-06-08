package ru.bfe.efep.app.inspection.photodoc

import ru.bfe.efep.app.standard.Standard

fun DefectInfo.applyTemplate(): String? {
    return if (defect != null) {
        var result = defect!!.template

        if (defect!!.hasValue) {
            result = result.applyPlaceholder(VALUE_PLACEHOLDER, value ?: "")
        }

        if (defect!!.hasCause) {
            result = result.applyPlaceholder(CAUSE_PLACEHOLDER, cause ?: "")
        }

        result
    } else {
        null
    }
}

private fun String.applyPlaceholder(placeholder: String, value: String): String {
    return replace(placeholder, value)
}


const val VALUE_PLACEHOLDER = "{{ЗНАЧЕНИЕ}}"
const val CAUSE_PLACEHOLDER = "{{ПРИЧИНА}}"

fun DefectInfo.standard(): Standard? {
    return defect?.standard ?: technicalReportRow?.standard
}