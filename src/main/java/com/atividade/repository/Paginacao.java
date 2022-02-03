package com.atividade.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Paginacao<T> implements Page<T> {



    private List<T> content = new ArrayList<>();
    private final Pageable pageable;
    private final long total;



    public Paginacao(List<T> content, Pageable pageable, long total) {
        this.content = content;
        this.pageable = pageable;
        this.total = total;
    }



    @Override
    public int getTotalPages() {
        return getSize() == 0 ? 1 : (int) Math.ceil((double) total / (double) getSize());
    }



    @Override
    public long getTotalElements() {
        return total;
    }



    @Override
    public <U> Page<U> map(Function<? super T, ? extends U> converter) {
        return new Paginacao<>(getConvertedContent(converter), getPageable(), total);
    }



    protected <U> List<U> getConvertedContent(Function<? super T, ? extends U> converter) {


        return this.stream().map(converter::apply).collect(Collectors.toList());
    }



    @Override
    public boolean hasNext() {
        return getNumber() + 1 < getTotalPages();
    }



    @Override
    public int getSize() {
        return pageable.isPaged() ? pageable.getPageSize() : content.size();
    }



    @Override
    public int getNumberOfElements() {
        return content.size();
    }



    @Override
    public List<T> getContent() {
        return Collections.unmodifiableList(content);
    }



    @Override
    public boolean hasContent() {
        return !content.isEmpty();
    }



    @Override
    public Sort getSort() {
        return pageable.getSort();
    }



    @Override
    public boolean isFirst() {
        return !hasPrevious();
    }



    @Override
    public boolean hasPrevious() {
        return getNumber() > 0;
    }



    @Override
    public Pageable nextPageable() {
        return hasNext() ? pageable.next() : Pageable.unpaged();
    }



    @Override
    public Pageable previousPageable() {
        return hasPrevious() ? pageable.previousOrFirst() : Pageable.unpaged();
    }



    @Override
    public Iterator<T> iterator() {
        return content.iterator();
    }



    @Override
    public int getNumber() {
        return pageable.isPaged() ? pageable.getPageNumber() : 0;
    }



    @Override
    public boolean isLast() {
        return !hasNext();
    }



}