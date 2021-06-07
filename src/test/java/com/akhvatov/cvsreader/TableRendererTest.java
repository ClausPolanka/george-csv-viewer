package com.akhvatov.cvsreader;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TableRendererTest {

    final TableRenderer renderer = new TableRenderer();

    @Test
    void shouldRenderTable() {
        // given
        final Table table = new Table(Arrays.asList("Name", "Age", "City"), Collections.singletonList(Page.from(
                Arrays.asList(
                        new String[]{"Peter", "42", "New York"},
                        new String[]{"Paul", "57", "London"},
                        new String[]{"Mary", "35", "Munich"}
                )
        )));

        // when
        final String render = renderer.render(table, 0);

        // then
        final String expectString =
                "Name |Age|City    |\n" +
                "-----+---+--------+\n" +
                "Peter|42 |New York|\n" +
                "Paul |57 |London  |\n" +
                "Mary |35 |Munich  |\n" +
                "N(ext page, P(revious page, F(irst page, L(ast page, eX(it";

        assertEquals(expectString, render);
    }

    @Test
    void shouldReturnEmptyStringIfTableIsEmpty() {
        // given
        final Table emptyTable = Table.empty();

        // when
        final String render = renderer.render(emptyTable, 0);

        // then
        assertTrue(render.isEmpty());
    }
}