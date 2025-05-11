package ru.bfe.efep.app.cases

import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import org.springframework.data.jpa.domain.Specification
import java.time.Instant

fun buildCaseSpecification(
    number: String? = null,
    statuses: Set<CaseStatus>? = null,
    priorities: Set<CasePriority>? = null,
    facilityAddress: String? = null,
    courtIds: List<Long?>? = null,
    judgeIds: List<Long?>? = null,
    companyIds: List<Long>? = null,
    regionIds: List<Long>? = null,
    createdByIds: List<Long>? = null,
    createdAtFrom: Instant? = null,
    createdAtTo: Instant? = null
): Specification<Case> {
    return Specification { root, query, cb ->
        val predicates = mutableListOf<Predicate>()
        
        addLikePredicate(predicates, number, "number", root, cb)
        addLikePredicate(predicates, facilityAddress, "facilityAddress", root, cb)

        addEnumPredicate(predicates, statuses, "status", root, cb)
        addEnumPredicate(predicates, priorities, "priority", root, cb)

        addRequiredRelationPredicate(predicates, companyIds, "company", root, cb)
        addRequiredRelationPredicate(predicates, regionIds, "region", root, cb)
        addRequiredRelationPredicate(predicates, createdByIds, "createdBy", root, cb)

        addNullableRelationPredicate(predicates, courtIds, "court", root, cb)
        addNullableRelationPredicate(predicates, judgeIds, "judge", root, cb)

        addDateRangePredicate(predicates, createdAtFrom, createdAtTo, "createdAt", root, cb)

        cb.and(*predicates.toTypedArray())
    }
}

private fun addLikePredicate(
    predicates: MutableList<Predicate>,
    value: String?,
    fieldName: String,
    root: Root<Case>,
    cb: CriteriaBuilder
) {
    value?.takeIf { it.isNotBlank() }?.let {
        predicates.add(cb.like(root.get(fieldName), "%${it}%"))
    }
}

private fun <T : Enum<T>> addEnumPredicate(
    predicates: MutableList<Predicate>,
    values: Set<T>?,
    fieldName: String,
    root: Root<Case>,
    cb: CriteriaBuilder
) {
    values?.takeIf { it.isNotEmpty() }?.let {
        predicates.add(root.get<T>(fieldName).`in`(it))
    }
}

private fun addRequiredRelationPredicate(
    predicates: MutableList<Predicate>,
    ids: List<Long>?,
    relationName: String,
    root: Root<Case>,
    cb: CriteriaBuilder
) {
    ids?.takeIf { it.isNotEmpty() }?.let {
        predicates.add(root.get<Any>(relationName).get<Long>("id").`in`(it))
    }
}

private fun addNullableRelationPredicate(
    predicates: MutableList<Predicate>,
    ids: List<Long?>?,
    relationName: String,
    root: Root<Case>,
    cb: CriteriaBuilder
) {
    ids?.takeIf { it.isNotEmpty() }?.let { idsList ->
        val notNullIds = idsList.filterNotNull()
        val conditions = mutableListOf<Predicate>()

        if (notNullIds.isNotEmpty()) {
            conditions.add(root.get<Any>(relationName).get<Long>("id").`in`(notNullIds))
        }

        if (idsList.any { it == null }) {
            conditions.add(cb.isNull(root.get<Any>(relationName)))
        }

        predicates.add(if (conditions.size > 1) cb.or(*conditions.toTypedArray()) else conditions.first())
    }
}

private fun addDateRangePredicate(
    predicates: MutableList<Predicate>,
    from: Instant?,
    to: Instant?,
    fieldName: String,
    root: Root<Case>,
    cb: CriteriaBuilder
) {
    if (from != null || to != null) {
        val datePath = root.get<Instant>(fieldName)
        when {
            from != null && to != null -> predicates.add(cb.between(datePath, from, to))
            from != null -> predicates.add(cb.greaterThanOrEqualTo(datePath, from))
            to != null -> predicates.add(cb.lessThanOrEqualTo(datePath, to))
        }
    }
}