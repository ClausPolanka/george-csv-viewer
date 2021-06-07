package com.akhvatov.cvsreader;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TableRendererTest {

    static final int FIRST_PAGE = 1;

    final TableRenderer renderer = new TableRenderer();

    @Test
    void shouldRenderTable() {
        // given
        final Table table = prepareTable();

        // when
        final String render = renderer.render(table, FIRST_PAGE);

        // then
        final String expectString =
                "Name |Age|City    |\n" +
                        "-----+---+--------+\n" +
                        "Peter|42 |New York|\n" +
                        "Paul |57 |London  |\n" +
                        "Mary |35 |Munich  |\n" +
                        "Page 1 of 1\n" +
                        "N(ext page, P(revious page, F(irst page, L(ast page, eX(it";

        assertEquals(expectString, render);
    }

    @Test
    void shouldReturnEmptyStringIfTableIsEmpty() {
        // given
        final Table emptyTable = Table.empty();

        // when
        final String render = renderer.render(emptyTable, 1);

        // then
        assertTrue(render.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 2})
    void shouldReturnEmptyStringIfPageIndexIsIncorrect(int pageIndex) {
        // given
        final Table table = prepareTable();

        // when
        final String render = renderer.render(table, pageIndex);

        // then
        assertTrue(render.isEmpty());
    }

    private Table prepareTable() {
        return new Table(Arrays.asList("Name", "Age", "City"), Collections.singletonList(Page.from(
                Arrays.asList(
                        Arrays.asList("Peter", "42", "New York"),
                        Arrays.asList("Paul", "57", "London"),
                        Arrays.asList("Mary", "35", "Munich")
                )
        )));
    }
}