package ru.bfe.efep.app.inspection.photodoc

import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.domain.Specification.where

fun buildSearchSpecification(
    inspectionId: Long,
    spotIds: List<Long?>?,
    materialIds: List<Long?>?,
    structElemIds: List<Long?>?,
    types: List<PhotoDocType?>?
): Specification<PhotoDoc> {
    return where(Specification<PhotoDoc> { root, query, cb ->
        cb.equal(root.get<Any>("inspection").get<Long>("id"), inspectionId)
    }).and(
        buildListSpec("spot", spotIds)
    ).and(
        buildListSpec("material", materialIds)
    ).and(
        buildListSpec("structElem", structElemIds)
    ).and(
        buildTypeSpec(types)
    )
}

private fun <T> buildListSpec(property: String, values: List<T?>?): Specification<PhotoDoc> {
    return Specification { root, _, cb ->
        when {
            values == null -> cb.conjunction()
            values.isEmpty() -> cb.disjunction()
            else -> {
                val notNullValues = values.filterNotNull()
                val hasNull = values.any { it == null }

                cb.or(
                    if (notNullValues.isNotEmpty()) {
                        root.get<Any>(property).get<Any>("id").`in`(notNullValues)
                    } else {
                        cb.disjunction()
                    },
                    if (hasNull) {
                        cb.isNull(root.get<Any>(property))
                    } else {
                        cb.disjunction()
                    }
                )
            }
        }
    }
}

private fun buildTypeSpec(types: List<PhotoDocType?>?): Specification<PhotoDoc> {
    return Specification { root, _, cb ->
        when {
            types == null -> cb.conjunction()
            types.isEmpty() -> cb.disjunction()
            else -> {
                val notNullTypes = types.filterNotNull()
                val hasNull = types.any { it == null }

                cb.or(
                    if (notNullTypes.isNotEmpty()) {
                        root.get<PhotoDocType>("type").`in`(notNullTypes)
                    } else {
                        cb.disjunction()
                    },
                    if (hasNull) {
                        cb.isNull(root.get<PhotoDocType>("type"))
                    } else {
                        cb.disjunction()
                    }
                )
            }
        }
    }
}