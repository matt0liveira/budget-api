package com.rich.budgetapi.infrasctruture.service.query;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rich.budgetapi.domain.filter.TransactionsAnalyticsFilter;
import com.rich.budgetapi.domain.model.Transaction;
import com.rich.budgetapi.domain.model.dto.TotalTransactions;
import com.rich.budgetapi.domain.model.dto.TotalTransactionsByDate;
import com.rich.budgetapi.domain.service.TransactionsAnalyticsQueryService;

@Repository
public class TransactionsAnalyticsQueryServiceImpl implements TransactionsAnalyticsQueryService {

    @Autowired
    private EntityManager manager;

    @Override
    public List<TotalTransactionsByDate> queryTotalTransactionsByDate(TransactionsAnalyticsFilter filter) {
        var builder = manager.getCriteriaBuilder();
        var query = builder.createQuery(TotalTransactionsByDate.class);
        var root = query.from(Transaction.class);
        var predicates = new ArrayList<Predicate>();

        var selection = builder.construct(TotalTransactionsByDate.class,
                root.get("date"),
                builder.count(root.get("id")),
                builder.sum(root.get("value")));

        if (filter.getUserId() != null) {
            predicates.add(builder.equal(root.get("user"), filter.getUserId()));
        }

        if (filter.getType() != null) {
            predicates.add(builder.equal(root.get("type"), filter.getType()));
        }

        if (filter.getDateTransactionInitial() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("date"), filter.getDateTransactionInitial()));
        }

        if (filter.getDateTransactionFinal() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("date"), filter.getDateTransactionFinal()));
        }

        query.select(selection);
        query.where(predicates.toArray(new Predicate[0]));
        query.groupBy(root.get("date"));

        return manager.createQuery(query).getResultList();
    }

    @Override
    public List<TotalTransactions> queryTotalTransactions() {
        // TODO Auto-generated method stub
        return null;
    }

}
