package com.rich.budgetapi.infrasctruture.service.query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rich.budgetapi.domain.filter.TotalTransactionsWithoutDateFilter;
import com.rich.budgetapi.domain.filter.TransactionFilter;
import com.rich.budgetapi.domain.model.Transaction;
import com.rich.budgetapi.domain.model.dto.TotalTransactions;
import com.rich.budgetapi.domain.model.dto.TotalTransactionsByCurdate;
import com.rich.budgetapi.domain.model.dto.TotalTransactionsByDate;
import com.rich.budgetapi.domain.model.dto.TotalTransactionsByMonth;
import com.rich.budgetapi.domain.model.dto.TotalTransactionsLastFourYears;
import com.rich.budgetapi.domain.service.TransactionsAnalyticsQueryService;

@Repository
public class TransactionsAnalyticsQueryServiceImpl implements TransactionsAnalyticsQueryService {

    @Autowired
    private EntityManager manager;

    @Override
    public List<TotalTransactionsByDate> queryTotalTransactionsByDate(TransactionFilter filter) {
        var builder = manager.getCriteriaBuilder();
        var query = builder.createQuery(TotalTransactionsByDate.class);
        var root = query.from(Transaction.class);
        var predicates = new ArrayList<Predicate>();

        var selection = builder.construct(TotalTransactionsByDate.class,
                root.get("date"),
                builder.count(root.get("id")),
                builder.sum(root.get("value")));

        addPredicatesWithDate(filter, builder, root, predicates);

        query.select(selection);
        query.where(predicates.toArray(new Predicate[0]));
        query.groupBy(root.get("date"));

        return manager.createQuery(query).getResultList();
    }

    @Override
    public List<TotalTransactions> queryTotalTransactions(TransactionFilter filter) {
        var builder = manager.getCriteriaBuilder();
        var query = builder.createQuery(TotalTransactions.class);
        var root = query.from(Transaction.class);
        var predicates = new ArrayList<Predicate>();

        var selection = builder.construct(TotalTransactions.class,
                builder.count(root.get("id")),
                builder.sum(root.get("value")));

        addPredicatesWithDate(filter, builder, root, predicates);

        query.select(selection);
        query.where(predicates.toArray(new Predicate[0]));

        return manager.createQuery(query).getResultList();
    }

    @Override
    public List<TotalTransactionsLastFourYears> queryTotalTransactionsLastFourYears(
            TotalTransactionsWithoutDateFilter filter) {
        var builder = manager.getCriteriaBuilder();
        var query = builder.createQuery(TotalTransactionsLastFourYears.class);
        var root = query.from(Transaction.class);
        var predicates = new ArrayList<Predicate>();

        var functionYear = builder.function("year", Integer.class, root.get("date"));

        var selection = builder.construct(TotalTransactionsLastFourYears.class,
                functionYear,
                builder.count(root.get("id")),
                builder.sum(root.get("value")));

        addPredicatesWithoutDate(filter, builder, root, predicates);

        // var functionCurdate = builder.function("curdate", Date.class);
        // var functionYearWithCurdate = builder.function("year", Integer.class,
        // functionCurdate);
        // predicates.add(functionYear.in(functionYearWithCurdate,
        // functionYearWithCurdate));

        query.select(selection);
        query.where(predicates.toArray(new Predicate[0]));
        query.groupBy(functionYear);

        return manager.createQuery(query).getResultList();
    }

    @Override
    public List<TotalTransactionsByCurdate> queryTotalTransactionsByCurdate(TotalTransactionsWithoutDateFilter filter) {
        var builder = manager.getCriteriaBuilder();
        var query = builder.createQuery(TotalTransactionsByCurdate.class);
        var root = query.from(Transaction.class);
        var predicates = new ArrayList<Predicate>();

        var functionCurdate = builder.function("curdate", Date.class);
        var selection = builder.construct(TotalTransactionsByCurdate.class,
                functionCurdate,
                builder.count(root.get("id")),
                builder.sum(root.get("value")));

        addPredicatesWithoutDate(filter, builder, root, predicates);

        predicates.add(builder.equal(root.get("date"), functionCurdate));

        query.select(selection);
        query.where(predicates.toArray(new Predicate[0]));

        return manager.createQuery(query).getResultList();
    }

    @Override
    public List<TotalTransactionsByMonth> queryTotalTransactionsByMonths(TotalTransactionsWithoutDateFilter filter) {
        // TODO Auto-generated method stub
        return null;
    }

    private void addPredicatesWithDate(TransactionFilter filter, CriteriaBuilder builder,
            Root<Transaction> root,
            ArrayList<Predicate> predicates) {
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
    }

    private void addPredicatesWithoutDate(TotalTransactionsWithoutDateFilter filter, CriteriaBuilder builder,
            Root<Transaction> root,
            ArrayList<Predicate> predicates) {
        if (filter.getType() != null) {
            predicates.add(builder.equal(root.get("type"), filter.getType()));
        }

        if (filter.getUserId() != null) {
            predicates.add(builder.equal(root.get("user"), filter.getUserId()));
        }

        if (filter.getCategoryId() != null) {
            predicates.add(builder.equal(root.get("category"), filter.getCategoryId()));
        }
    }
}